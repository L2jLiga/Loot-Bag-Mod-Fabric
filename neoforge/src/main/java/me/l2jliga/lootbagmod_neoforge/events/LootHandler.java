package me.l2jliga.lootbagmod_neoforge.events;

import me.l2jliga.lootbagmod.LootBagMod;
import me.l2jliga.lootbagmod.config.LootBagModConfig;
import me.l2jliga.lootbagmod.droplist.PoolBuilder;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

import java.util.Random;

@Mod.EventBusSubscriber(modid = LootBagMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LootHandler {
    private static final Random rand = new Random();

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        var poolBuilder = PoolBuilder.getChestPoolTable(event.getName());

        if (poolBuilder != null) {
            event.getTable().addPool(poolBuilder.build());
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onMobDrops(LivingDropsEvent event) {
        var poolBuilder = PoolBuilder.getMobLootTable(event.getEntity().getLootTable());
        if (poolBuilder != null) {
            int dropchance = rand.nextInt(100);
            int totalchance = LootBagModConfig.DropChance * 100;

            LootBagMod.LOGGER.info("Chance to get loot is %d / %d".formatted(dropchance, totalchance));

            if (dropchance > totalchance) {
                event.getDrops().add(new ItemEntity(
                        event.getEntity().getWorld(),
                        event.getEntity().getX(),
                        event.getEntity().getY(),
                        event.getEntity().getZ(),
                        new ItemStack(LootBagMod.LOOT_BAG_ITEM, 1)
                ));
            }
        }
    }
}
