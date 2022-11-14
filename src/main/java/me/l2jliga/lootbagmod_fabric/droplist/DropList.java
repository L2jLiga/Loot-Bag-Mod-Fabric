package me.l2jliga.lootbagmod_fabric.droplist;

import me.l2jliga.lootbagmod_fabric.LootBagMod;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class DropList {
    private static final Boolean whitelist = LootBagMod.CONFIG.EnableWhitelist();
    public static List<Identifier> potentialItems = new ArrayList<>();
    public static List<Identifier> whitelistedItems = new ArrayList<>();

    public static void initializeItemsLists() {
        for (Item item : Registry.ITEM) {
            potentialItems.add(Registry.ITEM.getId(item));
        }
        if (LootBagMod.CONFIG.EnableWhitelist()) {
            whitelistedItems.addAll(LootBagMod.CONFIG.WhiteList().stream().map(Identifier::new).toList());
        } else {
            potentialItems.removeAll(LootBagMod.CONFIG.BlackList().stream().map(Identifier::new).toList());
        }
        if (LootBagMod.CONFIG.EnableContainsList()) {
            DropList.containsList();
        } else {
            DropList.modList();
        }
        LootBagMod.LOGGER.info("All potential items retrieved");
    }

    private static void modList() {
        List<String> modNames = new ArrayList<>(LootBagMod.CONFIG.Namespaces());
        List<Identifier> items = new ArrayList<>();
        for (Identifier drops : potentialItems) {
            for (String modName : modNames) {
                if (drops.getNamespace().equals(modName)) {
                    items.add(drops);
                    LootBagMod.LOGGER.info("modList: " + drops);
                }
            }
        }
        if (whitelist) whitelistedItems.addAll(items);
        else potentialItems.removeAll(items);
        LootBagMod.LOGGER.info("ModList Completed");
    }

    private static void containsList() {
        List<Identifier> items = new ArrayList<>();
        for (Identifier drops : potentialItems) {
            for (int i = 0; i < LootBagMod.CONFIG.ContainsList().size(); i++) {
                if (drops.equals(new Identifier(LootBagMod.CONFIG.ContainsList().get(i)))) {
                    items.add(drops);
                    LootBagMod.LOGGER.info("containsList: " + drops);
                }
            }
        }
        if (whitelist) whitelistedItems.addAll(items);
        else potentialItems.removeAll(items);
        LootBagMod.LOGGER.info("Contains list completed");
        DropList.modList();
    }
}