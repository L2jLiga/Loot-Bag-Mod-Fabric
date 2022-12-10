package me.l2jliga.lootbagmod_fabric.droplist;

import io.wispforest.owo.config.Option;
import me.l2jliga.lootbagmod_fabric.LootBagMod;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DropList {
    private static final List<Option.Key> keysToObserve = List.of(
            new Option.Key("EnableWhitelist"),
            new Option.Key("BlackList"),
            new Option.Key("WhiteList"),
            new Option.Key("Namespaces"),
            new Option.Key("EnableContainsList"),
            new Option.Key("ContainsList")
    );
    public static List<Identifier> potentialItems = new ArrayList<>();
    public static List<Identifier> whitelistedItems = new ArrayList<>();

    @SuppressWarnings("ConstantConditions")
    public static void initializeItemsLists() {
        DropList.updateItemsList(null);
        keysToObserve.forEach(key -> LootBagMod.CONFIG.optionForKey(key).observe(DropList::updateItemsList));
    }

    private static void updateItemsList(Object _option) {
        potentialItems.clear();
        whitelistedItems.clear();
        potentialItems.addAll(Registries.ITEM.getIds());
        if (LootBagMod.CONFIG.EnableWhitelist()) {
            whitelistedItems.addAll(LootBagMod.CONFIG.WhiteList().stream().map(Identifier::new).toList());
        } else {
            potentialItems.removeAll(LootBagMod.CONFIG.BlackList().stream().map(Identifier::new).toList());
        }
        if (LootBagMod.CONFIG.EnableContainsList()) {
            DropList.containsList();
        }
        DropList.modList();
        LootBagMod.LOGGER.info("All potential items retrieved");
    }

    private static void modList() {
        Set<String> modNames = new HashSet<>(LootBagMod.CONFIG.Namespaces());
        List<Identifier> items = new ArrayList<>();
        for (Identifier drops : potentialItems) {
            if (modNames.contains(drops.getNamespace())) {
                items.add(drops);
                LootBagMod.LOGGER.info("modList: " + drops);
            }
        }
        if (LootBagMod.CONFIG.EnableWhitelist()) whitelistedItems.addAll(items);
        else potentialItems.removeAll(items);
        LootBagMod.LOGGER.info("ModList Completed");
    }

    private static void containsList() {
        Set<String> containsList = new HashSet<>(LootBagMod.CONFIG.ContainsList());
        List<Identifier> items = new ArrayList<>();
        for (Identifier drops : potentialItems) {
            if (containsList.contains(drops.toString())) {
                items.add(drops);
                LootBagMod.LOGGER.info("containsList: " + drops);
            }
        }
        if (LootBagMod.CONFIG.EnableWhitelist()) whitelistedItems.addAll(items);
        else potentialItems.removeAll(items);
        LootBagMod.LOGGER.info("Contains list completed");
    }
}