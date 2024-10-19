package me.ajh123.sams_bits;

import me.ajh123.sams_bits.mixin.IItemMixin;
import me.ajh123.sams_bits.mixin.IItemPropertiesMixin;
import net.minecraft.world.item.Item;

public class ItemExtras {
    public static boolean isHiddenInCreative(Item item) {
        return ((IItemPropertiesMixin) ((IItemMixin) item).sams_bits$getCustomProperties()).sams_bits$isHiddenInCreative();
    }

    public static Item.Properties hideItem(Item.Properties properties) {
        return ((IItemPropertiesMixin) properties).sams_bits$hiddenInCreative();
    }
}
