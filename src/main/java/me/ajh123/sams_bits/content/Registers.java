package me.ajh123.sams_bits.content;

import me.ajh123.sams_bits.SamsBits;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Registers {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SamsBits.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, SamsBits.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SamsBits.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SamsBits.MODID);

    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register("main_tab", () -> CreativeModeTab.builder()
    .title(Component.translatable("itemGroup." + SamsBits.MODID + ".main_tab"))
    .icon(() -> ModBlocks.CFE_TRANSFORMER_ITEM.get().getDefaultInstance())
    .displayItems((parameters, output) -> {
        for (RegistryObject<Block> block : BLOCKS.getEntries()) {
            output.accept(block.get().asItem());
        }
        for (RegistryObject<Item> item : ITEMS.getEntries()) {
            output.accept(item.get());
        }
    }).build());


    public static void register(IEventBus modEventBus) {
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
