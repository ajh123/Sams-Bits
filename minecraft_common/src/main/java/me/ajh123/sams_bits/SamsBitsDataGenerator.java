package me.ajh123.sams_bits;

import me.ajh123.sams_bits.content.registry.data.ItemTagProvider;
import me.ajh123.sams_bits.content.registry.data.ModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;


public class SamsBitsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();
		pack.addProvider(ItemTagProvider::new);
		pack.addProvider(ModelProvider::new);
	}
}
