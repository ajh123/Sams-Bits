package me.ajh123.sams_bits.mixin.impl.imguimc;

import imgui.ImGui;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.breadloaf.imguimc.imgui.ImguiLoader;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    @Inject(method = "onPress", at = @At("HEAD"), cancellable = true)
    public void onScroll(long l, int button, int action, int mods, CallbackInfo ci) {
        ImguiLoader.imGuiGlfw.mouseButtonCallback(l, button, action, mods);
        if (ImGui.getIO().getWantCaptureMouse()) {
            ci.cancel();
        }
    }

    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    public void onScroll(long l, double x, double y, CallbackInfo ci) {
        ImguiLoader.imGuiGlfw.scrollCallback(l, x, y);
        if (ImGui.getIO().getWantCaptureMouse()) {
            ci.cancel();
        }
    }

    @Inject(method = "onMove", at = @At("HEAD"), cancellable = true)
    public void onMove(long l, double x, double y, CallbackInfo ci) {
        ImguiLoader.imGuiGlfw.cursorPosCallback(l, x, y);
        if (ImGui.getIO().getWantCaptureMouse()) {
            ci.cancel();
        }
    }
}
