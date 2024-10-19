package me.ajh123.sams_bits.mixin;

import net.minecraft.world.item.Item;

public interface IItemPropertiesMixin {
    Item.Properties sams_bits$hiddenInCreative();
    boolean sams_bits$isHiddenInCreative();
}
