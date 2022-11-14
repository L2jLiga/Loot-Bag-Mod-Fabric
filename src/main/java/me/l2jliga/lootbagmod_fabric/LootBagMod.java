package me.l2jliga.lootbagmod_fabric;

import me.l2jliga.lootbagmod_fabric.config.LootBagConfig;
import me.l2jliga.lootbagmod_fabric.droplist.DropList;
import me.l2jliga.lootbagmod_fabric.droplist.LootTablesModifier;
import me.l2jliga.lootbagmod_fabric.item.LootBagItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootBagMod implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Identifier CONTAINER_ID = id("lootbagmod");
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(CONTAINER_ID, () -> new ItemStack(Registry.ITEM.get(id("lootbag"))));
    public static LootBagConfig CONFIG = LootBagConfig.createAndLoad();

    public static Identifier id(String name) {
        return new Identifier("lootbagmod", name);
    }

    @Override
    public void onInitialize() {
        FabricItemSettings settings = new FabricItemSettings().group(GROUP).maxCount(1);
        LootBagItem item = new LootBagItem(settings);
        Registry.register(Registry.ITEM, id("lootbag"), item);
        DropList.initializeItemsLists();
        if (CONFIG.ChestDrops()) LootTablesModifier.modifyTreasureLootTables(item);
        if (CONFIG.MobDrops()) LootTablesModifier.modifyMobsLootTables(item);
    }
}