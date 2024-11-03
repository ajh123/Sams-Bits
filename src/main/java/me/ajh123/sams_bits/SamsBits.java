package me.ajh123.sams_bits;

import me.ajh123.sams_bits.api.permissions.PermissionContainer;
import me.lucko.fabric.api.permissions.v0.OfflinePermissionCheckEvent;
import me.lucko.fabric.api.permissions.v0.PermissionCheckEvent;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class SamsBits implements ModInitializer {
	public static final String MOD_ID = "sams_bits";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing content ...");
		ModItems.initialize();

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