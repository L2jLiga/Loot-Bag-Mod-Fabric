package me.l2jliga.lootbagmod_fabric.droplist;

import me.l2jliga.lootbagmod_fabric.item.LootBagItem;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.Map;
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
        float dropChance = CONFIG.DropChance().floatValue();

        LootTableEvents.MODIFY.register(((resourceManager, lootManager, lootTableId, tableBuilder, source) -> {
            if (!source.isBuiltin()) return;
            if (!lootTableId.getPath().startsWith("/entity")) return;

            Identifier id = new Identifier(lootTableId.getNamespace(), lootTableId.getPath().split("/")[1]);
            Optional<EntityType<?>> mayBeEntityType = Registry.ENTITY_TYPE.getOrEmpty(id);
            EntityType<?> entityType = mayBeEntityType.orElse(null);
            if (entityType == null) return;

            if (CONFIG.AllHostileMobs() && entitiesTypeByConfig.get(MobsConfig.HOSTILE).contains(entityType.getSpawnGroup()) || CONFIG.AllNonHostileMobs() && entitiesTypeByConfig.get(MobsConfig.NON_HOSTILE).contains(entityType.getSpawnGroup()) || MOBS_LIST.contains(lootTableId) || MOBS_LIST.contains(id)) {
                LootPool.Builder poolBuilder = LootPool.builder().rolls(ConstantLootNumberProvider.create(1)).conditionally(RandomChanceLootCondition.builder(dropChance)).with(ItemEntry.builder(item));

                tableBuilder.pool(poolBuilder);
            }
        }));
    }

    public enum MobsConfig {
        HOSTILE, NON_HOSTILE
    }

    private static final Map<MobsConfig, List<SpawnGroup>> entitiesTypeByConfig = Map.of(
            MobsConfig.HOSTILE, List.of(SpawnGroup.MONSTER),
            MobsConfig.NON_HOSTILE, List.of(SpawnGroup.MONSTER, SpawnGroup.CREATURE, SpawnGroup.WATER_CREATURE, SpawnGroup.UNDERGROUND_WATER_CREATURE)
    );
}
