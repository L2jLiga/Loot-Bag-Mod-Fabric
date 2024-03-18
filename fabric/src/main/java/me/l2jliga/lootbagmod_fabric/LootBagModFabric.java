package me.l2jliga.lootbagmod_fabric;

import me.l2jliga.lootbagmod.LootBagMod;
import me.l2jliga.lootbagmod.config.LootBagModConfig;
import me.l2jliga.lootbagmod.droplist.DropList;
import me.l2jliga.lootbagmod.item.LootBagItem;
import me.l2jliga.lootbagmod_fabric.droplist.LootTablesModifier;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

import static me.l2jliga.lootbagmod.LootBagMod.CONTAINER_ID;
import static me.l2jliga.lootbagmod.LootBagMod.LOOT_BAG_ITEM;

public class LootBagModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        LootBagMod.onInitialize();

        LOOT_BAG_ITEM = new LootBagItem();
        Registry.register(Registries.ITEM, LootBagMod.ITEM_ID, LOOT_BAG_ITEM);

        ItemGroup itemGroup = FabricItemGroup.builder().displayName(Text.translatable("itemGroup.lootbagmod.lootbagmod")).icon(() -> new ItemStack(LOOT_BAG_ITEM)).build();
        Registry.register(Registries.ITEM_GROUP, CONTAINER_ID, itemGroup);
        Registries.ITEM_GROUP.getKey(itemGroup).ifPresent(registryKey -> ItemGroupEvents.modifyEntriesEvent(registryKey).register(entries -> entries.add(LOOT_BAG_ITEM)));

        DropList.initializeItemsLists();

        // Adds loot bag to loot tables
        if (LootBagModConfig.ChestDrops) LootTablesModifier.modifyTreasureLootTables();
        if (LootBagModConfig.MobDrops) LootTablesModifier.modifyMobsLootTables();
    }
}