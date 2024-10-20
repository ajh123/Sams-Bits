package me.ajh123.sams_bits.mixin.impl;

import me.ajh123.sams_bits.mixin.IOptionsMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.controls.ControlsScreen;
import net.minecraft.network.chat.CommonComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ControlsScreen.class)
public abstract class ControlsScreenMixin {
    @Inject(method = "init", at = @At("TAIL"))
    public void addCustomButton(CallbackInfo ci) {
        ControlsScreen screen = (ControlsScreen) (Object) this;
        ScreenAccessor accessor = (ScreenAccessor) screen;

        int i = screen.width / 2 - 155;
        int j = i + 160;

        // Variables to track row positions and count
        int rowCount = 0;
        int lastY = Integer.MIN_VALUE; // Initialize with a very low value

        // Loop through each button and count rows manually
        for (Object child : screen.children()) {
            if (child instanceof AbstractWidget button) {
                // Skip the "Done" button
                if (button.getMessage().contains(CommonComponents.GUI_DONE)) {
                    continue;
                }

                // If the current button's Y position is different from the last, it's a new row
                if (button.getY() != lastY) {
                    rowCount++;
                    lastY = button.getY(); // Update lastY to the current button's Y
                }
            }
        }

        // Reflect the current vertical position (k) based on the number of existing rows
        int k = (screen.height / 6 - 12) + (rowCount * 24); // One row is 24 units high

        // Add the custom button at the calculated position
        accessor.invokeAddRenderableWidget(((IOptionsMixin) Minecraft.getInstance().options).sams_bits$getOperatorItems().createButton(Minecraft.getInstance().options, j, k, 150));

        // Move the existing "Done" button down by another row
        k += 24;
        int finalK = k;
        screen.children().stream()
                .filter(widget -> widget instanceof Button)
                .filter(widget -> ((Button) widget).getMessage().contains(CommonComponents.GUI_DONE)) // Only the "Done" button
                .forEach(doneButton -> ((Button) doneButton).setY(finalK)); // Move the "Done" button down
    }

}