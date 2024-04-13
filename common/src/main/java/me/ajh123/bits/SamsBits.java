package me.ajh123.bits;

import com.simibubi.create.Create;
import dan200.computercraft.shared.ModRegistry;
import me.ajh123.bits.content.blocks.bank_management_server.BankManagementServerBlock;
import me.ajh123.bits.utilities.registration.Registration;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SamsBits {
    public static final String MOD_ID = "sams_bits";
    public static final String NAME = "Sam's Bits";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);


    public static void init() {
        LOGGER.info("{} initializing! Create version: {} on platform: {}", NAME, Create.VERSION, ExampleExpectPlatform.platformName());
        Registration.init(); // hold registrate in a separate class to avoid loading early on forge
    }

    public static void postInit() {
        BankManagementServerBlock.REGISTRY_ENTRY.register();
    }

    public static ResourceLocation modLoc(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
