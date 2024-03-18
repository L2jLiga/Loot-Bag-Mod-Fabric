package me.l2jliga.lootbagmod.droplist;

import me.l2jliga.lootbagmod.config.LootBagModConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static me.l2jliga.lootbagmod.LootBagMod.LOOT_BAG_ITEM;

public class PoolBuilder {
    private static final List<Identifier> CHESTS_LIST = LootBagModConfig.ChestsLists.stream().map(Identifier::new).toList();
    private static final List<Identifier> MOBS_LIST = LootBagModConfig.MobList.stream().map(Identifier::new).toList();

    private static final float dropChance = LootBagModConfig.DropChance.floatValue() / 100;

    public static LootPool.Builder getChestPoolTable(Identifier lootTableId) {
        return CHESTS_LIST.contains(lootTableId)
                ? LootPool.builder().rolls(ConstantLootNumberProvider.create(1)).conditionally(RandomChanceLootCondition.builder(0.237f)).with(ItemEntry.builder(LOOT_BAG_ITEM))
                : null;
    }

    @Nullable
    public static LootPool.Builder getMobLootTable(Identifier lootTableId) {
        if (!lootTableId.getPath().startsWith("entities/")) return null;

        if (!isBaseLootTable(lootTableId.getPath())) return null;

        Identifier id = new Identifier(lootTableId.getNamespace(), lootTableId.getPath().split("/")[1]);
        EntityType<?> entityType = Registries.ENTITY_TYPE.getOrEmpty(id).orElse(null);
        if (entityType == null) return null;

        boolean isPeaceful = entityType.getSpawnGroup().isPeaceful();
        if (LootBagModConfig.AllHostileMobs && !isPeaceful || LootBagModConfig.AllNonHostileMobs && isPeaceful || MOBS_LIST.contains(lootTableId) || MOBS_LIST.contains(id)) {
            return LootPool.builder().rolls(ConstantLootNumberProvider.create(1)).conditionally(RandomChanceLootCondition.builder(dropChance)).with(ItemEntry.builder(LOOT_BAG_ITEM));
        }

        return null;
    }

    /**
     * Some entities like Sheep may have several loot tables split by color
     * this is used to drop different wool colors
     * e.g. minecraft:entities/sheep/white, minecraft:entities/sheep/blue, minecraft:entities/sheep/black
     * as well, they all should have base entity for common drop
     * e.g. minecraft:entities/sheep
     * since we are not depend on color we have to add loot bag only to base entity
     *
     * @param lootTableId - loot table identifier check whether it's base entity or not
     * @return is lootTableId for base entity
     */
    private static boolean isBaseLootTable(String lootTableId) {
        return lootTableId.split("/").length == 2;
    }
}
