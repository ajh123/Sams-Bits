package me.ajh123.bits.forge;

import dan200.computercraft.api.ForgeComputerCraftAPI;
import me.ajh123.bits.utilities.registration.Registration;
import me.ajh123.bits.SamsBits;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SamsBits.MOD_ID)
public class SamsBitsForge {
    public SamsBitsForge() {
        // registrate must be given the mod event bus on forge before registration
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::onCommonSetup);
        Registration.REGISTRATE.registerEventListeners(eventBus);
        SamsBits.init();
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        ForgeComputerCraftAPI.registerPeripheralProvider(new ForgePeripheralProvider());
    }
}
