package me.l2jliga.lootbagmod_fabric;

import me.l2jliga.lootbagmod_fabric.config.LootBagConfig;
import me.l2jliga.lootbagmod_fabric.droplist.DropList;
import me.l2jliga.lootbagmod_fabric.droplist.LootTablesModifier;
import me.l2jliga.lootbagmod_fabric.item.LootBagItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootBagMod implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Identifier CONTAINER_ID = new Identifier("lootbagmod", "lootbagmod");
    public static final Identifier ITEM_ID = new Identifier("lootbagmod", "lootbag");
    public static final LootBagItem LOOT_BAG_ITEM = new LootBagItem(new FabricItemSettings().maxCount(1));
    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder(CONTAINER_ID).icon(() -> new ItemStack(LOOT_BAG_ITEM)).build();

    public static LootBagConfig CONFIG = LootBagConfig.createAndLoad();

    @Override
    public void onInitialize() {
        // Register item
        Registry.register(Registries.ITEM, ITEM_ID, LOOT_BAG_ITEM);
        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(entries -> entries.add(LOOT_BAG_ITEM));

        // Observe configuration to build possible drop list
        DropList.initializeItemsLists();

        // Adds loot bag to loot tables
        if (CONFIG.ChestDrops()) LootTablesModifier.modifyTreasureLootTables();
        if (CONFIG.MobDrops()) LootTablesModifier.modifyMobsLootTables();
    }
}