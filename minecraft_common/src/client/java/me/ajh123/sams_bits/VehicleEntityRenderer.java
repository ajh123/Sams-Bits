package me.ajh123.sams_bits;

import me.ajh123.sams_bits.content.vehicles.VehicleEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.MinecartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

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
    }
}
