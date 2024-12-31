package me.ajh123.sams_bits.content.registry;

public class ModContent {
    public static void initialize() {
        ModComponents.initialize();
        ModBlocks.initialize();
        ModItems.initialize();
        ModEntities.initialize();
        ModBlockEntities.initialize();
        ModCommands.initialize();
    }
}
