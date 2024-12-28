// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class basic_car<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "basic_car"), "main");
	private final ModelPart body;

	public basic_car(ModelPart root) {
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(74, 35).addBox(-1.0F, -4.0F, 3.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(120, 58).addBox(-25.0F, -9.0F, 14.0F, 2.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(4, 84).addBox(-25.0F, -22.0F, -18.0F, 26.0F, 2.0F, 33.0F, new CubeDeformation(0.0F))
		.texOffs(138, 93).addBox(-25.0F, 0.0F, -29.0F, 26.0F, 2.0F, 48.0F, new CubeDeformation(0.0F))
		.texOffs(120, 10).addBox(-25.0F, -9.0F, -28.0F, 26.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(250, 78).addBox(-23.0F, -9.0F, 5.0F, 22.0F, 9.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(109, 28).addBox(-25.0F, -9.0F, -26.0F, 2.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(198, 38).addBox(-1.0F, -9.0F, -26.0F, 2.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(257, 48).addBox(-23.0F, -6.0F, -26.0F, 22.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(181, 65).addBox(-23.0F, -9.0F, -26.0F, 22.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(65, 5).addBox(-1.0F, -9.0F, 14.0F, 2.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(8, 50).addBox(-1.0F, -20.0F, 3.0F, 2.0F, 16.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(235, 14).addBox(-25.0F, -20.0F, 3.0F, 2.0F, 16.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(207, -1).addBox(-1.0F, -20.0F, -17.0F, 2.0F, 20.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(185, 0).addBox(-25.0F, -20.0F, -17.0F, 2.0F, 20.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(8, 26).addBox(-23.0F, -20.0F, 11.0F, 22.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(273, 15).addBox(-25.0F, -9.0F, -11.0F, 2.0F, 9.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(151, 31).addBox(-1.0F, -9.0F, -11.0F, 2.0F, 9.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(99, 10).addBox(-25.0F, -4.0F, 3.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(12.0F, 14.0F, 5.0F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(309, 0).addBox(-1.0F, -16.0F, -1.0F, 2.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-24.0F, -5.0F, -20.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(66, 58).addBox(-1.0F, -12.0F, -1.0F, 2.0F, 13.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(10, 5).addBox(-23.0F, -12.0F, -1.0F, 22.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, -19.0F, -0.2618F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 320, 320);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}