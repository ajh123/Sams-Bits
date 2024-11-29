package me.ajh123.sams_bits.content;

import me.ajh123.sams_bits.SamsBits;
import me.ajh123.sams_bits.content.roads.RoadNodeBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static RoadNodeBlock ROAD_NODE = register_with_item("road_node", new RoadNodeBlock(Block.Settings.create()));

    public static void initialize() {
        //do nothing expect initialize static
    }

    public static <T extends Block> T register(String id, T block) {
        Identifier itemID = Identifier.of(SamsBits.MOD_ID, id);
        return Registry.register(Registries.BLOCK, itemID, block);
    }

    public static <T extends Block> T register_with_item(String id, T block) {
        ModItems.register(id, new BlockItem(block, new Item.Settings()));
        return register(id, block);
    }
}
