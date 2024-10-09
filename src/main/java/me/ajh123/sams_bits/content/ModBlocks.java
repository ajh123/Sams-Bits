package me.ajh123.sams_bits.content;

import me.ajh123.sams_bits.blocks.cfe_transformer.CFETransformerBlock;
import me.ajh123.sams_bits.blocks.cfe_transformer.CFETransformerBlockEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final RegistryObject<Block> CFE_TRANSFORMER_BLOCK = Registers.BLOCKS.register("cfe_transformer", () -> new CFETransformerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    public static final RegistryObject<Item> CFE_TRANSFORMER_ITEM = Registers.ITEMS.register("cfe_transformer", () -> new BlockItem(CFE_TRANSFORMER_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<CFETransformerBlockEntity>> CFE_TRANSFORMER_BLOCK_ENTITY = Registers.BLOCK_ENTITIES.register("cfe_transformer",  () -> BlockEntityType.Builder.of(CFETransformerBlockEntity::new, CFE_TRANSFORMER_BLOCK.get()).build(null));

    public static void register(IEventBus modEventBus) {
        Registers.BLOCKS.register(modEventBus);
        Registers.BLOCK_ENTITIES.register(modEventBus);
    }
}
