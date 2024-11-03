package me.ajh123.sams_bits.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;


public interface UseOnEntityEvent {
    ActionResult useOnAnyEntity(World world, Entity target, PlayerEntity player, ItemStack heldItem, Hand hand);
}
