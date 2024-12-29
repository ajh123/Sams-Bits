package me.ajh123.sams_bits;

import com.mojang.blaze3d.systems.RenderSystem;

import me.ajh123.sams_bits.content.registry.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;


public class SamsBitsClient implements ClientModInitializer {
	private static final MinecraftClient client = MinecraftClient.getInstance();

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		EntityRendererRegistry.register(ModEntities.VEHICLE_ENTITY, VehicleEntityRenderer::new);

		WorldRenderEvents.LAST.register(this::onWorldRenderLast);
	}

    private void onWorldRenderLast(WorldRenderContext context) {
        // Example start and end blocks
        BlockPos start = new BlockPos(0, 64, 0);  // Example start block
        BlockPos end = new BlockPos(10, 64, 10);   // Example end block

        // Draw the line between the two blocks
        drawLineBetweenBlocks(context.matrixStack(), start, end);
    }

    public static void drawLineBetweenBlocks(MatrixStack matrices, BlockPos start, BlockPos end) {
        // Get the player's position
        Vec3d playerPos = client.player.getCameraPosVec(1.0F);

        // Get the coordinates of the start and end blocks
        Vec3d startPos = new Vec3d(start.getX(), start.getY(), start.getZ());
        Vec3d endPos = new Vec3d(end.getX(), end.getY(), end.getZ());

        // Prepare the line rendering
        RenderSystem.lineWidth(2.0F); // Set the line width
        RenderSystem.disableDepthTest(); // Disable depth test to ensure the line is drawn in front
        RenderSystem.enableBlend(); // Enable blending for smoother lines
        RenderSystem.defaultBlendFunc(); // Use the default blending function

        // Draw the line
        drawLine(startPos.subtract(playerPos), endPos.subtract(playerPos));

        // Reset the state
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
    }

    private static void drawLine(Vec3d start, Vec3d end) {
        // Use Tessellator and BufferBuilder to render the line
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.begin(DrawMode.LINES, VertexFormats.POSITION);

        VertexConsumer vertexConsumer = bufferBuilder.color(255, 0, 0, 255);

        // Add the start and end points to the buffer
        vertexConsumer.vertex((float) start.x, (float) start.y, (float) start.z).color(1.0F, 0.0F, 0.0F, 1.0F);
        vertexConsumer.vertex((float) end.x, (float) end.y, (float) end.z).color(1.0F, 0.0F, 0.0F, 1.0F);

        // Finalize the buffer and send the vertices to the GPU
        bufferBuilder.end();
    }
}