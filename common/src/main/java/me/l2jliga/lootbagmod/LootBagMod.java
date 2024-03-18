package me.l2jliga.lootbagmod;

import eu.midnightdust.lib.config.MidnightConfig;
import me.l2jliga.lootbagmod.config.LootBagModConfig;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootBagMod {
    public static final Logger LOGGER = LogManager.getLogger("LootBagMod");
    public static final String MOD_ID = "lootbagmod";
    public static final Identifier CONTAINER_ID = new Identifier(MOD_ID, "lootbagmod");
    public static final Identifier ITEM_ID = new Identifier(MOD_ID, "lootbag");
    public static Item LOOT_BAG_ITEM;

    public static void onInitialize() {
        MidnightConfig.init(MOD_ID, LootBagModConfig.class);
    }
}
