package me.ajh123.bits.forge;

import me.ajh123.bits.ModBlocks;
import me.ajh123.bits.SamsBits;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SamsBits.MOD_ID)
public class SamsBitsForge {
    public SamsBitsForge() {
        // registrate must be given the mod event bus on forge before registration
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.REGISTRATE.registerEventListeners(eventBus);
        SamsBits.init();
    }
}
