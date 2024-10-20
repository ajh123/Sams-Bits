package me.ajh123.sams_bits.mixin.impl;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.breadloaf.imguimc.Imguimc;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "pauseGame", at = @At("HEAD"), cancellable = true)
    public void pauseGame(boolean pause, CallbackInfo ci) {
        if (!Imguimc.renderstack.isEmpty()) {
            ci.cancel();
        }
    }
}
