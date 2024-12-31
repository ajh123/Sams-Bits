package me.ajh123.sams_bits;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.ajh123.sams_bits.content.registry.network.DataSyncCustomPayload;
import me.ajh123.sams_bits.content.registry.network.DataSyncCustomPayload.DataSyncAction;
import me.ajh123.sams_bits.content.registry.network.DataSyncCustomPayload.DataSyncStatus;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;

public class ClientDataManager {
	// This map will track pending requests by their message ID
	private static final Map<Integer, ResponseListener> pendingRequests = new ConcurrentHashMap<>();
	private static int NEXT_MESSAGE_ID = 0;

	// Initialize client-side networking
	public static void onInitializeClient() {
		// Register a single handler to process all incoming responses
		ClientPlayNetworking.registerGlobalReceiver(DataSyncCustomPayload.ID, (payload, context) -> {
			// Find the corresponding listener for the received message ID
			ResponseListener listener = pendingRequests.remove(payload.messageID());

			// If a listener was found, invoke it with the response
			if (listener != null) {
				listener.onResponse(payload);
			} else {
				SamsBits.LOGGER.debug("No listener found for message ID: " + payload.messageID());
			}
		});
	}

	// Send a request to the server and handle the response asynchronously using a callback
	public static void sendToServer(DataSyncAction action, PacketByteBuf content, ResponseListener listener) {
		int messageID = NEXT_MESSAGE_ID++;

		// Register the listener for this message ID
		pendingRequests.put(messageID, listener);

		// Create the payload to send
		DataSyncCustomPayload payload = new DataSyncCustomPayload(action, DataSyncStatus.SUCCESS, messageID, content);

		// Send the payload to the server
		ClientPlayNetworking.send(payload);
	}

	public static interface ResponseListener {
		void onResponse(DataSyncCustomPayload response);
	}
}
