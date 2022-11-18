package me.l2jliga.lootbagmod_fabric.droplist;

import me.l2jliga.lootbagmod_fabric.LootBagMod;
import me.l2jliga.lootbagmod_fabric.item.LootBagItem;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.Optional;

import static me.l2jliga.lootbagmod_fabric.LootBagMod.CONFIG;

public class LootTablesModifier {
    private static final List<Identifier> CHESTS_LIST = CONFIG.ChestsLists().stream().map(Identifier::new).toList();
    private static final List<Identifier> MOBS_LIST = CONFIG.MobList().stream().map(Identifier::new).toList();

    public static void modifyTreasureLootTables(LootBagItem item) {
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, lootTableId, tableBuilder, source) -> {
            if (!source.isBuiltin()) return;
            if (CHESTS_LIST.contains(lootTableId)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1)).conditionally(RandomChanceLootCondition.builder(0.237f)).with(ItemEntry.builder(item));
                tableBuilder.pool(poolBuilder);
            }
        }));
    }

    public static void modifyMobsLootTables(LootBagItem item) {
        float dropChance = CONFIG.DropChance().floatValue() / 100;

        LootTableEvents.MODIFY.register(((resourceManager, lootManager, lootTableId, tableBuilder, source) -> {
            if (!source.isBuiltin()) return;
            if (!lootTableId.getPath().startsWith("entities/")) return;

            Identifier id = new Identifier(lootTableId.getNamespace(), lootTableId.getPath().split("/")[1]);
            EntityType<?> entityType = Registry.ENTITY_TYPE.getOrEmpty(id).orElse(null);
            if (entityType == null) return;

            boolean isPeaceful = entityType.getSpawnGroup().isPeaceful();
            if (CONFIG.AllHostileMobs() && !isPeaceful || CONFIG.AllNonHostileMobs() && isPeaceful || MOBS_LIST.contains(lootTableId) || MOBS_LIST.contains(id)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1)).conditionally(RandomChanceLootCondition.builder(dropChance)).with(ItemEntry.builder(item));
                tableBuilder.pool(poolBuilder);
            }
        }));
    }
}