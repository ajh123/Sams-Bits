package me.ajh123.sams_bits;

import me.ajh123.sams_bits.content.registry.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;


public class SamsBitsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		EntityRendererRegistry.register(ModEntities.VEHICLE_ENTITY, VehicleEntityRenderer::new);

	}
}