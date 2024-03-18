package me.l2jliga.lootbagmod_fabric.droplist;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;

import static me.l2jliga.lootbagmod.droplist.PoolBuilder.getChestPoolTable;
import static me.l2jliga.lootbagmod.droplist.PoolBuilder.getMobLootTable;

public class LootTablesModifier {
    public static void modifyTreasureLootTables() {
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, lootTableId, tableBuilder, source) -> {
            if (!source.isBuiltin()) return;
            var poolBuilder = getChestPoolTable(lootTableId);
            if (poolBuilder != null) tableBuilder.pool(poolBuilder);
        }));
    }

    public static void modifyMobsLootTables() {
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, lootTableId, tableBuilder, source) -> {
            if (!source.isBuiltin()) return;
            var pool = getMobLootTable(lootTableId);
            if (pool != null) tableBuilder.pool(pool);
        }));
    }
}