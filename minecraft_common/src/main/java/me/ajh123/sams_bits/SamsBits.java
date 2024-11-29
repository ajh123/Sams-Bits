package me.ajh123.sams_bits;

import me.ajh123.sams_bits.content.ModContent;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SamsBits implements ModInitializer {
	public static final String MOD_ID = "sams_bits";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing content ...");
		ModContent.initialize();

//		PermissionCheckEvent.EVENT.register((source, permission) -> {
//			if (isSuperAdmin(source)) {
//				return TriState.TRUE;
//			}
//			return TriState.DEFAULT;
//		});
//
//		OfflinePermissionCheckEvent.EVENT.register((uuid, permission) -> {
//			return CompletableFuture.supplyAsync(() -> {
//				if (isSuperAdmin(uuid)) {
//					return TriState.TRUE;
//				}
//				return TriState.DEFAULT;
//			});
//		});
	}
}