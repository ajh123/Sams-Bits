package me.ajh123.sams_bits.content;

import com.mrcrayfish.furniture.refurbished.block.CeilingLightBlock;
import com.mrcrayfish.furniture.refurbished.block.MetalType;
import com.mrcrayfish.furniture.refurbished.item.PoweredItem;
import li.cil.oc2r.common.block.NetworkSwitchBlock;
import li.cil.oc2r.common.blockentity.NetworkSwitchBlockEntity;
import me.ajh123.sams_bits.ItemExtras;
import me.ajh123.sams_bits.blocks.ceiling_light_panel.CeilingLightPanelBlock;
import me.ajh123.sams_bits.blocks.cfe_transformer.CFETransformerBlock;
import me.ajh123.sams_bits.blocks.cfe_transformer.CFETransformerBlockEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final RegistryObject<CFETransformerBlock> CFE_TRANSFORMER_BLOCK = Registers.BLOCKS.register("cfe_transformer", () -> new CFETransformerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    public static final RegistryObject<BlockItem> CFE_TRANSFORMER_ITEM = Registers.ITEMS.register("cfe_transformer", () -> new BlockItem(CFE_TRANSFORMER_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<CFETransformerBlockEntity>> CFE_TRANSFORMER_BLOCK_ENTITY = Registers.BLOCK_ENTITIES.register("cfe_transformer",  () -> BlockEntityType.Builder.of(CFETransformerBlockEntity::new, CFE_TRANSFORMER_BLOCK.get()).build(null));

    public static final RegistryObject<CeilingLightBlock> LIGHT_CEILING_LIGHT_PANEL_BLOCK = Registers.BLOCKS.register("light_ceiling_light_panel", () -> new CeilingLightPanelBlock(MetalType.LIGHT, BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    public static final RegistryObject<PoweredItem> LIGHT_CEILING_LIGHT_PANEL_ITEM = Registers.ITEMS.register("light_ceiling_light_panel", () -> new PoweredItem(LIGHT_CEILING_LIGHT_PANEL_BLOCK.get(), ItemExtras.hideItem(new Item.Properties())));
    public static final RegistryObject<CeilingLightBlock> DARK_CEILING_LIGHT_PANEL_BLOCK = Registers.BLOCKS.register("dark_ceiling_light_panel", () -> new CeilingLightPanelBlock(MetalType.DARK, BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    public static final RegistryObject<PoweredItem> DARK_CEILING_LIGHT_PANEL_ITEM = Registers.ITEMS.register("dark_ceiling_light_panel", () -> new PoweredItem(DARK_CEILING_LIGHT_PANEL_BLOCK.get(), ItemExtras.hideItem(new Item.Properties())));

    public static final RegistryObject<NetworkSwitchBlock> ADVANCED_NETWORK_SWITCH_BLOCK = Registers.BLOCKS.register("advanced_network_switch", NetworkSwitchBlock::new);
    public static final RegistryObject<BlockItem> ADVANCED_NETWORK_SWITCH_ITEM = Registers.ITEMS.register("advanced_network_switch", () -> new BlockItem(ADVANCED_NETWORK_SWITCH_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockEntityType<NetworkSwitchBlockEntity>> ADVANCED_NETWORK_SWITCH_BLOCK_ENTITY = Registers.BLOCK_ENTITIES.register("advanced_network_switch",  () -> BlockEntityType.Builder.of(NetworkSwitchBlockEntity::new, ADVANCED_NETWORK_SWITCH_BLOCK.get()).build(null));


    public static void register(IEventBus modEventBus) {
        Registers.BLOCKS.register(modEventBus);
        Registers.BLOCK_ENTITIES.register(modEventBus);
    }
}
