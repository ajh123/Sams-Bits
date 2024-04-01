package me.ajh123.bits.fabric;

import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import me.ajh123.bits.utilities.registration.Registration;
import me.ajh123.bits.SamsBits;
import net.fabricmc.api.ModInitializer;

public class SamsBitsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SamsBits.init();
        SamsBits.LOGGER.info(EnvExecutor.unsafeRunForDist(
                () -> () -> "{} is accessing Porting Lib on a Fabric client!",
                () -> () -> "{} is accessing Porting Lib on a Fabric server!"
                ), SamsBits.NAME);
        // on fabric, Registrates must be explicitly finalized and registered.
        Registration.REGISTRATE.register();
    }
}
