package me.ajh123.sams_bits.content.registry.network;

import java.util.List;

import io.netty.buffer.Unpooled;
import me.ajh123.sams_bits.SamsBits;
import me.ajh123.sams_bits.content.registry.network.DataSyncCustomPayload.DataSyncAction;
import me.ajh123.sams_bits.content.registry.network.DataSyncCustomPayload.DataSyncStatus;
import me.ajh123.sams_bits.content.roads.RoadNodeBlockEntity;
import me.ajh123.sams_bits.maths.Position;
import me.ajh123.sams_bits.roads.RoadManager;
import me.ajh123.sams_bits.roads.RoadNode;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.Context;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class DataManager {
	// Send a request to the client (server-side)
	public static void sendToClient(DataSyncAction action, DataSyncStatus status, int messageID, PacketByteBuf content, ServerPlayerEntity player) {
		// Create the payload to send
		DataSyncCustomPayload payload = new DataSyncCustomPayload(action, status, messageID, content);

		// Send the payload to the client
		ServerPlayNetworking.send(player, payload);
	}

	// Initialize server-side networking
	public static void onInitializeServer() {
		PayloadTypeRegistry.playS2C().register(DataSyncCustomPayload.ID, DataSyncCustomPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(DataSyncCustomPayload.ID, DataSyncCustomPayload.CODEC);

		// Register a handler for incoming requests from clients
		ServerPlayNetworking.registerGlobalReceiver(DataSyncCustomPayload.ID, (payload, context) -> {
			// The server automatically runs this on the main server thread
			// Process the incoming request here
			SamsBits.LOGGER.info("Received request on server: " + payload.action() + " with message ID: " + payload.messageID());
	
			PacketByteBuf responseContent = new PacketByteBuf(Unpooled.buffer());

			switch (payload.action()) {
				case GET_DESTINATIONS_OF_NODE: {
					DataSyncStatus status;

					try {
						status = DataManager.onServerGetDestinationsOfNodeHandler(responseContent, payload, context);
					} catch (Exception e) {
						status = DataSyncStatus.INTERNAL_ERROR;
						responseContent.writeString(e.getMessage());
						SamsBits.LOGGER.error("Error whilst handling node destination packet:", e);
					}
					sendToClient(payload.action(), status, payload.messageID(), responseContent, context.player());
				}
				case GET_NODE_POSITION: {
					DataSyncStatus status;

					try {
						status = DataManager.onServerGetNodePosition(responseContent, payload, context);
					} catch (Exception e) {
						status = DataSyncStatus.INTERNAL_ERROR;
						responseContent.writeString(e.getMessage());
						SamsBits.LOGGER.error("Error whilst handling node destination packet:", e);
					}
					sendToClient(payload.action(), status, payload.messageID(), responseContent, context.player());
				}
				case GET_NODE_ID: {
					DataSyncStatus status;

					try {
						status = DataManager.onServerGetNodeID(responseContent, payload, context);
					} catch (Exception e) {
						status = DataSyncStatus.INTERNAL_ERROR;
						responseContent.writeString(e.getMessage());
						SamsBits.LOGGER.error("Error whilst handling node destination packet:", e);
					}
					sendToClient(payload.action(), status, payload.messageID(), responseContent, context.player());
				}
				case INVALID:
					sendToClient(payload.action(), DataSyncStatus.INVALID, payload.messageID(), responseContent, context.player());
			}
		});
	}

	private static DataSyncStatus onServerGetDestinationsOfNodeHandler(PacketByteBuf responseContent,
			DataSyncCustomPayload payload, Context context) {
		long nodeID = payload.content().readLong();
		ServerWorld world = context.player().getServerWorld();
		RoadManager manager = SamsBits.getRoadManager(world);
		RoadNode node = manager.getNode(nodeID);

		DataSyncStatus status;

		if (node == null) {
			status = DataSyncStatus.NOT_FOUND;
			responseContent.writeString("The given node could not be found.");
		} else {
			BlockPos pos = new BlockPos(node.getPosition().getX(), node.getPosition().getY(), node.getPosition().getZ());
			BlockEntity blockEntity = world.getBlockEntity(pos);

			if (blockEntity instanceof RoadNodeBlockEntity rEntity) {
				status = DataSyncStatus.SUCCESS;

				List<BlockPos> destinations = rEntity.getDestinations();
				responseContent.writeInt(destinations.size());
				for (BlockPos id : destinations) {
					responseContent.writeBlockPos(id);
				}
			} else {
				status = DataSyncStatus.NOT_FOUND;
				responseContent.writeString("The given node does not have a block entity, try rebuilding the node.");
			}
		}

		return status;
	}

	private static DataSyncStatus onServerGetNodePosition(PacketByteBuf responseContent,
			DataSyncCustomPayload payload, Context context) {
		long nodeID = payload.content().readLong();
		ServerWorld world = context.player().getServerWorld();
		RoadManager manager = SamsBits.getRoadManager(world);
		RoadNode node = manager.getNode(nodeID);

		DataSyncStatus status;

		if (node == null) {
			status = DataSyncStatus.NOT_FOUND;
			responseContent.writeString("The given node could not be found.");
		} else {
			BlockPos pos = new BlockPos(node.getPosition().getX(), node.getPosition().getY(), node.getPosition().getZ());
			BlockEntity blockEntity = world.getBlockEntity(pos);

			if (blockEntity instanceof RoadNodeBlockEntity) {
				status = DataSyncStatus.SUCCESS;

				responseContent.writeBlockPos(pos);
			} else {
				status = DataSyncStatus.NOT_FOUND;
				responseContent.writeString("The given node does not have a block entity, try rebuilding the node.");
			}
		}

		return status;
	}

	private static DataSyncStatus onServerGetNodeID(PacketByteBuf responseContent,
			DataSyncCustomPayload payload, Context context) {
		BlockPos pos = payload.content().readBlockPos();
		ServerWorld world = context.player().getServerWorld();
		RoadManager manager = SamsBits.getRoadManager(world);
		RoadNode node = manager.getNode(new Position(pos.getX(), pos.getY(), pos.getZ()));

		DataSyncStatus status;

		if (node == null) {
			status = DataSyncStatus.NOT_FOUND;
			responseContent.writeString("The given node could not be found.");
		} else {
			BlockEntity blockEntity = world.getBlockEntity(pos);

			if (blockEntity instanceof RoadNodeBlockEntity) {
				status = DataSyncStatus.SUCCESS;

				responseContent.writeLong(node.getId());
			} else {
				status = DataSyncStatus.NOT_FOUND;
				responseContent.writeString("The given node does not have a block entity, try rebuilding the node.");
			}
		}

		return status;
	}

	public static interface ResponseListener {
		void onResponse(DataSyncCustomPayload response);
	}
}
