package me.ajh123.sams_bits.mixin.impl;

import me.ajh123.sams_bits.mixin.IOptionsMixin;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Options.class)
public class OptionsMixin implements IOptionsMixin {
    @Unique
    private final OptionInstance<Boolean> sams_bits$operatorItems = OptionInstance.createBoolean("options.sams_bits.operatorItems", false);

    @Inject(method = "processOptions", at = @At("HEAD"))
    public void addCustomButton(Options.FieldAccess access, CallbackInfo ci) {
        access.process("sams_bits.operatorItems", this.sams_bits$operatorItems);
    }

    @Override
    public OptionInstance<Boolean> sams_bits$getOperatorItems() {
        return sams_bits$operatorItems;
    }
}
