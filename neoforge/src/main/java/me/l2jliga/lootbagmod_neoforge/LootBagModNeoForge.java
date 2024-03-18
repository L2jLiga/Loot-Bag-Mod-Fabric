package me.l2jliga.lootbagmod_neoforge;

import eu.midnightdust.lib.config.MidnightConfig;
import me.l2jliga.lootbagmod.LootBagMod;
import me.l2jliga.lootbagmod.droplist.DropList;
import me.l2jliga.lootbagmod.item.LootBagItem;
import me.l2jliga.lootbagmod_neoforge.events.LootHandler;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.ConfigScreenHandler;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Objects;

import static me.l2jliga.lootbagmod.LootBagMod.MOD_ID;
import static net.neoforged.fml.IExtensionPoint.DisplayTest.IGNORESERVERONLY;

@Mod(MOD_ID)
public class LootBagModNeoForge {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MOD_ID);

    public LootBagModNeoForge() {
        LootBagMod.onInitialize();
        ITEMS.register(Objects.requireNonNull(ModLoadingContext.get().getActiveContainer().getEventBus()));
        ITEMS.register("lootbag", () -> {
            LootBagMod.LOOT_BAG_ITEM = new LootBagItem();
            return LootBagMod.LOOT_BAG_ITEM;
        });

        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> IGNORESERVERONLY, (remote, server) -> true));
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> MidnightConfig.getScreen(parent, MOD_ID)));

        NeoForge.EVENT_BUS.register(new LootHandler());
        DropList.initializeItemsLists();
    }
}