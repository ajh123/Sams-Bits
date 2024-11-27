package me.ajh123.sams_bits;

import me.ajh123.sams_bits.content.entities.VehicleEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.MinecartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

public class VehicleEntityRenderer extends EntityRenderer<VehicleEntity> {
    protected final EntityModel<VehicleEntity> model;

    protected VehicleEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new MinecartEntityModel<>(null);
    }

    @Override
    public Identifier getTexture(VehicleEntity entity) {
        return Identifier.ofVanilla("textures/entity/minecart.png");
    }

    public void render(VehicleEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.push();

        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        this.model.setAngles(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(this.getTexture(entity)));
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
        matrixStack.pop();
    }
}
