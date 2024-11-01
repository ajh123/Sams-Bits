package me.ajh123.sams_bits.mixin;

import me.ajh123.sams_bits.utils.UseOnEntityEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({MinecartEntity.class, CommandBlockMinecartEntity.class, FurnaceMinecartEntity.class, ChestMinecartEntity.class, StorageMinecartEntity.class, Entity.class})
public class EntityMixin {
	@Inject(method = "interact", at = @At("HEAD"), cancellable = true)
	private void onInteract(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack heldItem = player.getStackInHand(hand);

		if (heldItem.getItem() instanceof UseOnEntityEvent event) {
			cir.setReturnValue(event.useOnAnyEntity(player.getWorld(), (Entity) (Object) this, player));
		}
	}
}