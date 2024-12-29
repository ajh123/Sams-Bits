package me.ajh123.sams_bits;

import me.ajh123.sams_bits.content.registry.ModContent;
import me.ajh123.sams_bits.roads.RoadManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.WorldSavePath;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SamsBits extends SamsBitsCommon implements ModInitializer {
	public static final String MOD_ID = "sams_bits";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing content ...");
		ModContent.initialize();

		ServerWorldEvents.LOAD.register(this::onWorldLoad);
		ServerWorldEvents.UNLOAD.register(this::onWorldUnload);
	}

	public static String getWorldName(ServerWorld world) {
		String worldName = world.getChunkManager().chunkLoadingManager.getSaveDir();
		return worldName;
	}

	public static RoadManager getRoadManager(ServerWorld world) {
		String worldName = SamsBits.getWorldName((ServerWorld) world);
		SamsBitsCommon common = SamsBitsCommon.getInstance();
		RoadManager roadManager = common.getRoadManager(worldName);
		return roadManager;
	}

    private void onWorldLoad(MinecraftServer server, ServerWorld world) {
		Path dir = server.getSavePath(WorldSavePath.ROOT).resolve(MOD_ID);
		String worldName = getWorldName(world);
		Path worldDir = dir.resolve("roads").resolve(worldName);

		RoadManager manager = this.getRoadManager(worldName);
		manager.setSavePath(worldDir);
		manager.load();
		LOGGER.info("Road storage (%s): All data loaded".formatted(worldName));
    }

	private void onWorldUnload(MinecraftServer server, ServerWorld world) {
		Path dir = server.getSavePath(WorldSavePath.ROOT).resolve(MOD_ID);
		String worldName = getWorldName(world);
		Path worldDir = dir.resolve("roads").resolve(worldName);

		RoadManager manager = this.getRoadManager(worldName);
		manager.setSavePath(worldDir);
		manager.save();
		LOGGER.info("Road storage (%s): All data saved".formatted(worldName));
	}

	@Override
	public void log_warn(String message) {
		LOGGER.warn(message);
	}

	@Override
	public void log_info(String message) {
		LOGGER.info(message);
	}

	@Override
	public void log_debug(String message) {
		LOGGER.debug(message);
	}
}