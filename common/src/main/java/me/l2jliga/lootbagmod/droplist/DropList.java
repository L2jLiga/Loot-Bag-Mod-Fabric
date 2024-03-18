package me.l2jliga.lootbagmod.droplist;

import me.l2jliga.lootbagmod.LootBagMod;
import me.l2jliga.lootbagmod.config.LootBagModConfig;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DropList {
    public static List<Identifier> potentialItems = new ArrayList<>();
    public static List<Identifier> whitelistedItems = new ArrayList<>();

    public static void initializeItemsLists() {
        DropList.updateItemsList();
    }

    private static void updateItemsList() {
        potentialItems.clear();
        whitelistedItems.clear();
        potentialItems.addAll(Registries.ITEM.getIds());
        if (LootBagModConfig.EnableWhitelist) {
            whitelistedItems.addAll(LootBagModConfig.WhiteList.stream().filter(identifier -> {
                if (isInvalidIdentifier(identifier)) {
                    LootBagMod.LOGGER.warn("Invalid identifier given %s".formatted(String.join(":", identifier)));
                    return false;
                }
                return true;
            }).map(Identifier::new).toList());
        } else {
            potentialItems.removeAll(LootBagModConfig.BlackList.stream().filter(identifier -> {
                if (isInvalidIdentifier(identifier)) {
                    LootBagMod.LOGGER.warn("Invalid identifier given %s".formatted(String.join(":", identifier)));
                    return false;
                }
                return true;
            }).map(Identifier::new).toList());
        }
        if (LootBagModConfig.EnableContainsList) {
            DropList.containsList();
        }
        DropList.modList();
        LootBagMod.LOGGER.info("All potential items retrieved");
    }

    private static boolean isInvalidIdentifier(String identifier) {
        var id = identifier.split(":");
        return id.length != 2 || !Identifier.isNamespaceValid(id[0]) || !Identifier.isPathValid(id[1]);
    }

    private static void modList() {
        Set<String> modNames = new HashSet<>(LootBagModConfig.Namespaces);
        List<Identifier> items = new ArrayList<>();
        for (Identifier drops : potentialItems) {
            if (modNames.contains(drops.getNamespace())) {
                items.add(drops);
                LootBagMod.LOGGER.info("modList: " + drops);
            }
        }
        if (LootBagModConfig.EnableWhitelist) whitelistedItems.addAll(items);
        else potentialItems.removeAll(items);
        LootBagMod.LOGGER.info("ModList Completed");
    }

    private static void containsList() {
        Set<String> containsList = new HashSet<>(LootBagModConfig.ContainsList);
        List<Identifier> items = new ArrayList<>();
        for (Identifier drops : potentialItems) {
            if (containsList.stream().anyMatch(it -> drops.toString().contains(it))) {
                items.add(drops);
                LootBagMod.LOGGER.info("containsList: " + drops);
            }
        }
        if (LootBagModConfig.EnableWhitelist) whitelistedItems.addAll(items);
        else potentialItems.removeAll(items);
        LootBagMod.LOGGER.info("Contains list completed");
    }
}