package me.ajh123.sams_bits;

import org.joml.Matrix3f;
import org.joml.Matrix4f;

import me.ajh123.sams_bits.content.roads.RoadNodeBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public class RoadNodeRenderer implements BlockEntityRenderer<RoadNodeBlockEntity >{

    public RoadNodeRenderer(BlockEntityRendererFactory.Context ctx) {}
 
    @Override
    public void render(RoadNodeBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        // TODO: loop though all connected nodes.

        // Get the position of the current block entity
        float startX = blockEntity.getPos().getX() + 0.5f;
        float startY = blockEntity.getPos().getY() + 0.5f;
        float startZ = blockEntity.getPos().getZ() + 0.5f;

        // TODO: Replace with the position of the other block
        float endX = 0 + 0.5f;
        float endY = 64 + 0.5f;
        float endZ = 0 + 0.5f;

        // Translate to the block position
        matrices.translate(-blockEntity.getPos().getX(), -blockEntity.getPos().getY(), -blockEntity.getPos().getZ());

        // Get the rendering layer for lines
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getLines());
        MatrixStack.Entry matrixEntry = matrices.peek();

        // Make normals go up
        float normalX = 0;
        float normalY = 1;
        float normalZ = 0;

        // Define the vertices for the line
        vertexConsumer.vertex(matrixEntry.getPositionMatrix(), startX, startY, startZ)
                    .color(0, 0, 255, 255)
                    .normal(matrixEntry, normalX, normalY, normalZ);

        vertexConsumer.vertex(matrixEntry.getPositionMatrix(), endX, endY, endZ)
                    .color(0, 0, 255, 255)
                    .normal(matrixEntry, normalX, normalY, normalZ);

        matrices.pop();
    }
}
