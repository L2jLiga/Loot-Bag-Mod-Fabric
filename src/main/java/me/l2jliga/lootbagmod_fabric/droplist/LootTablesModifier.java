package me.l2jliga.lootbagmod_fabric.droplist;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.List;

import static me.l2jliga.lootbagmod_fabric.LootBagMod.CONFIG;
import static me.l2jliga.lootbagmod_fabric.LootBagMod.LOOT_BAG_ITEM;

public class LootTablesModifier {
    private static final List<Identifier> CHESTS_LIST = CONFIG.ChestsLists().stream().map(Identifier::new).toList();
    private static final List<Identifier> MOBS_LIST = CONFIG.MobList().stream().map(Identifier::new).toList();

    public static void modifyTreasureLootTables() {
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, lootTableId, tableBuilder, source) -> {
            if (!source.isBuiltin()) return;
            if (CHESTS_LIST.contains(lootTableId)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1)).conditionally(RandomChanceLootCondition.builder(0.237f)).with(ItemEntry.builder(LOOT_BAG_ITEM));
                tableBuilder.pool(poolBuilder);
            }
        }));
    }

    public static void modifyMobsLootTables() {
        float dropChance = CONFIG.DropChance().floatValue() / 100;

        LootTableEvents.MODIFY.register(((resourceManager, lootManager, lootTableId, tableBuilder, source) -> {
            if (!source.isBuiltin()) return;
            if (!lootTableId.getPath().startsWith("entities/")) return;

            Identifier id = new Identifier(lootTableId.getNamespace(), lootTableId.getPath().split("/")[1]);
            EntityType<?> entityType = Registries.ENTITY_TYPE.getOrEmpty(id).orElse(null);
            if (entityType == null) return;

            boolean isPeaceful = entityType.getSpawnGroup().isPeaceful();
            if (CONFIG.AllHostileMobs() && !isPeaceful || CONFIG.AllNonHostileMobs() && isPeaceful || MOBS_LIST.contains(lootTableId) || MOBS_LIST.contains(id)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1)).conditionally(RandomChanceLootCondition.builder(dropChance)).with(ItemEntry.builder(LOOT_BAG_ITEM));
                tableBuilder.pool(poolBuilder);
            }
        }));
    }
}