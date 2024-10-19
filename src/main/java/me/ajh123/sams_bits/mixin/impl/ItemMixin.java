package me.ajh123.sams_bits.mixin.impl;

import me.ajh123.sams_bits.mixin.IItemMixin;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public class ItemMixin implements IItemMixin {

    // Add a custom field for properties
    @Unique
    private Item.Properties sams_bits$properties;

    // Inject into the constructor
    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(Item.Properties properties, CallbackInfo ci) {
        this.sams_bits$properties = properties;
    }

    @Unique
    public Item.Properties sams_bits$getCustomProperties() {
        return this.sams_bits$properties;
    }
}