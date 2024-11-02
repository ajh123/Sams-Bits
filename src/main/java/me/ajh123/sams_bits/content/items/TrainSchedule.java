package me.ajh123.sams_bits.content.items;

import eu.pb4.polymer.core.api.item.PolymerItem;
import me.ajh123.sams_bits.utils.UseOnEntityEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


public class TrainSchedule extends Item implements UseOnEntityEvent, PolymerItem {
    public TrainSchedule(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnAnyEntity(World world, Entity target, PlayerEntity player, ItemStack heldItem, Hand hand) {
        if (!world.isClient) {
            if (target instanceof AbstractMinecartEntity) {


                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return super.use(world, user, hand);
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return Items.KNOWLEDGE_BOOK;
    }
}
