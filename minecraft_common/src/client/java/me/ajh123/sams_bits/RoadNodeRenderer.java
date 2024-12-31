package me.ajh123.sams_bits;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.Unpooled;
import me.ajh123.sams_bits.content.registry.network.DataSyncCustomPayload.DataSyncAction;
import me.ajh123.sams_bits.content.registry.network.DataSyncCustomPayload.DataSyncStatus;
import me.ajh123.sams_bits.content.roads.RoadNodeBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class RoadNodeRenderer implements BlockEntityRenderer<RoadNodeBlockEntity >{
    private Map<Long, BlockPos> ids_to_pos = new HashMap<>();

    public RoadNodeRenderer(BlockEntityRendererFactory.Context ctx) {}
 
    @Override
    public void render(RoadNodeBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockPos myPos = blockEntity.getPos();

        for (long id : blockEntity.getDestinations()) {
            if (!ids_to_pos.keySet().contains(id)) {
                PacketByteBuf requestContent = new PacketByteBuf(Unpooled.buffer());
                requestContent.writeLong(id);
                ClientDataManager.sendToServer(DataSyncAction.GET_NODE_POSITION, requestContent, (response) -> {
                    DataSyncStatus status = response.status();
                    if (status == DataSyncStatus.SUCCESS) {
                        BlockPos pos = response.content().readBlockPos();
                        ids_to_pos.put(id, pos);
                    }
                });
            } else {
                matrices.push();

                // Get the position of the current block entity
                float startX = myPos.getX() + 0.5f;
                float startY = myPos.getY() + 0.5f;
                float startZ = myPos.getZ() + 0.5f;

                // Get the target's position
                BlockPos endPos = ids_to_pos.get(id);

                float endX = endPos.getX() + 0.5f;
                float endY = endPos.getY() + 0.5f;
                float endZ = endPos.getZ() + 0.5f;

                // Translate to the block position
                matrices.translate(-myPos.getX(), -myPos.getY(), -myPos.getZ());

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
    }
}
