package me.l2jliga.lootbagmod_fabric.item;

import me.l2jliga.lootbagmod_fabric.droplist.DropList;
import me.l2jliga.lootbagmod_fabric.LootBagMod;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LootBagItem extends Item implements FabricItem {

    public LootBagItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {

        try {
            Random generator = new Random();

            //LootBagMod.LOGGER.info("*********************" + LootBagConfig.EnableWhitelist.get() + "******************");
            List<Identifier> possibleItems = LootBagMod.CONFIG.EnableWhitelist() ? DropList.whitelistedItems : DropList.potentialItems;
            Collections.shuffle(possibleItems);

            int drops;
            for (drops = 0; drops < LootBagMod.CONFIG.Drops(); drops++) {
                Collections.shuffle(possibleItems);

                Item itemToGive = Registry.ITEM.get(possibleItems.get(generator.nextInt(possibleItems.size())));
                int itemsCount = new ItemStack(itemToGive).isStackable() ? generator.nextInt(1, LootBagMod.CONFIG.StackSize()) : 1;

                // TODO: Adjust logic to take into account if user has such item already and it can be stacked
                if (!(playerIn.getInventory().getEmptySlot() <= 0)) {
                    playerIn.giveItemStack(new ItemStack(itemToGive, itemsCount));
                } else {
                    playerIn.dropItem(new ItemStack(itemToGive, itemsCount), true);
                }
            }
        } catch (Exception e) {
            LootBagMod.LOGGER.info("LootBagMod Error: " + e);
        }

        if (playerIn.getInventory().contains(new ItemStack(this)) && !playerIn.isCreative()) {
            ItemStack itemInHang = playerIn.getEquippedStack(EquipmentSlot.MAINHAND);
            if (itemInHang.isItemEqual(new ItemStack(this))) {
                itemInHang.setCount(itemInHang.getCount() - 1);
            }
        }

        return super.use(worldIn, playerIn, handIn);
    }

}
