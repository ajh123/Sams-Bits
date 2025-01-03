package me.ajh123.sams_bits.mixin.client;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.GameMode;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.ajh123.sams_bits.content.registry.ModBlocks;
import me.ajh123.sams_bits.content.registry.ModItems;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {

    @Inject(method = "getBlockParticle", at = @At("HEAD"), cancellable = true)
    private void injectGetBlockParticle(CallbackInfoReturnable<Block> cir) {
        MinecraftClient client = MinecraftClient.getInstance();
        ItemStack itemStack = client.player.getMainHandStack();
        Item item = itemStack.getItem();

        // Check if the item is your custom item
        if (client.interactionManager.getCurrentGameMode() == GameMode.CREATIVE &&
            item == ModBlocks.ROAD_NODE.asItem() || item == ModItems.ROAD_CONNECTOR) {
            cir.setReturnValue(ModBlocks.ROAD_NODE);
        }
    }
}