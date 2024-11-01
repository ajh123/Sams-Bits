package me.ajh123.sams_bits;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SamsBits implements ModInitializer {
	public static final String MOD_ID = "sams_bits";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing content ...");
		ModItems.initialize();
	}
}