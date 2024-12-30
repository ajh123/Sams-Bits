package me.ajh123.sams_bits.content.registry;

import me.ajh123.sams_bits.SamsBits;
import me.ajh123.sams_bits.content.roads.RoadNodeBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<RoadNodeBlockEntity> ROAD_NODE = register("road_node", BlockEntityType.Builder.create(RoadNodeBlockEntity::new, ModBlocks.ROAD_NODE).build()); 

    public static void initialize() {
        //do nothing expect initialize static
    }

    public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(SamsBits.MOD_ID, path), blockEntityType);
    }
}
