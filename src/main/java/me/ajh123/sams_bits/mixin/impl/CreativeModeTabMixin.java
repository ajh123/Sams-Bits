package me.ajh123.sams_bits.mixin.impl;

import me.ajh123.sams_bits.ItemExtras;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mixin(CreativeModeTab.class)
public class CreativeModeTabMixin {
    @Inject(method = "getDisplayItems", at = @At("TAIL"), cancellable = true)
    public void getDisplayItems(CallbackInfoReturnable<Collection<ItemStack>> cir) {
        Collection<ItemStack> originalItems = cir.getReturnValue();

        List<ItemStack> filteredItems = originalItems.stream()
                .filter(itemStack -> !ItemExtras.isHiddenInCreative(itemStack.getItem()))
                .collect(Collectors.toList());

        // Set the filtered items back to the return value
        cir.setReturnValue(filteredItems);
    }

    @Inject(method = "getSearchTabDisplayItems", at = @At("TAIL"), cancellable = true)
    public void getSearchTabDisplayItems(CallbackInfoReturnable<Collection<ItemStack>> cir) {
        Collection<ItemStack> originalItems = cir.getReturnValue();

        List<ItemStack> filteredItems = originalItems.stream()
                .filter(itemStack -> !ItemExtras.isHiddenInCreative(itemStack.getItem()))
                .collect(Collectors.toList());

        // Set the filtered items back to the return value
        cir.setReturnValue(filteredItems);
    }
}
