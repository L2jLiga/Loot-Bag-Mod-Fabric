package me.l2jliga.lootbagmod.item;

import me.l2jliga.lootbagmod.LootBagMod;
import me.l2jliga.lootbagmod.config.LootBagModConfig;
import me.l2jliga.lootbagmod.droplist.DropList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LootBagItem extends Item {
    public LootBagItem() {
        super(new Item.Settings().maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        try {
            Random generator = new Random();
            List<Identifier> possibleItems = LootBagModConfig.EnableWhitelist ? DropList.whitelistedItems : DropList.potentialItems;
            Collections.shuffle(possibleItems);

            int drops;
            for (drops = 0; drops < LootBagModConfig.Drops; drops++) {
                Collections.shuffle(possibleItems);

                Item itemToGive = Registries.ITEM.get(possibleItems.get(generator.nextInt(possibleItems.size())));
                int itemsCount = new ItemStack(itemToGive).isStackable() ? generator.nextInt(1, LootBagModConfig.StackSize) : 1;

                boolean isGiven = playerIn.giveItemStack(new ItemStack(itemToGive, itemsCount));
                if (!isGiven)
                    playerIn.dropItem(new ItemStack(itemToGive, itemsCount), true);
            }
        } catch (Exception e) {
            LootBagMod.LOGGER.info("LootBagMod Error: " + e);
        }

        return TypedActionResult.pass(ItemStack.EMPTY);
    }
}
