package me.ajh123.sams_bits;

import me.ajh123.sams_bits.content.registry.ModContent;
import me.ajh123.sams_bits.content.registry.network.DataManager;
import me.ajh123.sams_bits.content.roads.RoadNodeBlock;
import me.ajh123.sams_bits.roads.RoadManager;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.block.BlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.math.BlockPos;

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

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            // Ensure the player is not in spectator mode
            if (player != null && !player.isSpectator() && player.isSneaking() && hitResult != null) {
                // Skip off-hand interaction to avoid duplicate events
                if (hand == Hand.OFF_HAND) {
                    return ActionResult.PASS; // Ignore off-hand interactions
                }

                BlockPos pos = hitResult.getBlockPos();
                BlockState state = world.getBlockState(pos);

                // Check if the block is an instance of your custom block
                if (state.getBlock() instanceof RoadNodeBlock block) {
                    // Call the block's `onUse` method
                    return block.onUse(state, world, pos, player, hitResult);
                }
            }

            // Pass to other interactions if conditions are not met
            return ActionResult.PASS;
        });

		DataManager.onInitializeServer();
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