package me.ajh123.sams_bits.content.registry.data;

import me.ajh123.sams_bits.content.registry.ModBlocks;
import me.ajh123.sams_bits.content.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModelProvider extends FabricModelProvider {
	public ModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.ROAD_NODE, ModBlocks.ROAD_NODE.asItem());
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		itemModelGenerator.register(ModBlocks.ROAD_NODE.asItem(), Models.GENERATED);
		itemModelGenerator.register(ModItems.ROAD_CONNECTOR, Models.GENERATED);
	}

}
