package de.stynxyxy.betterminecraft.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PenItem extends Item {
    public PenItem(Properties properties) {
        super(properties);

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand Hand) {
        player.sendSystemMessage(Component.literal("you clicked the Item pen in your Hand!"));
        return super.use(world, player, Hand);

    }

}
