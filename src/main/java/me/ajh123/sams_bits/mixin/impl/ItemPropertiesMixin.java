package me.ajh123.sams_bits.mixin.impl;


import me.ajh123.sams_bits.mixin.IItemPropertiesMixin;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Item.Properties.class)
public class ItemPropertiesMixin implements IItemPropertiesMixin {
    // Add a custom field for hiddenInCreative
    @Unique // Ensures that the field doesn't conflict with fields from other mods
    private boolean sams_bits$hiddenInCreative;

    // Inject the custom method into Item.Properties
    @Unique
    public Item.Properties sams_bits$hiddenInCreative() {
        // Set the field to true when this method is called
        this.sams_bits$hiddenInCreative = true;
        // Return this to allow method chaining
        return (Item.Properties) (Object) this;
    }

    @Unique
    public boolean sams_bits$isHiddenInCreative() {
        return this.sams_bits$hiddenInCreative;
    }
}