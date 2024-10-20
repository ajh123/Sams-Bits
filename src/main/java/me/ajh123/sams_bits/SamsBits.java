package me.ajh123.sams_bits;

import com.mojang.logging.LogUtils;
import com.mrcrayfish.furniture.refurbished.client.renderer.blockentity.ElectricBlockEntityRenderer;
import me.ajh123.sams_bits.content.ModBlocks;
import me.ajh123.sams_bits.content.Registers;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SamsBits.MODID)
@Mod.EventBusSubscriber(modid = SamsBits.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SamsBits {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "sams_bits";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public SamsBits() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        Registers.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlocks.CFE_TRANSFORMER_BLOCK_ENTITY.get(), ElectricBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void onCreativeTab(BuildCreativeModeTabContentsEvent event) {
        onBuildCreativeTab(event.getTabKey(), event.getParameters(), event);
    }

    public static void onBuildCreativeTab(ResourceKey<CreativeModeTab> key, CreativeModeTab.ItemDisplayParameters context, CreativeModeTab.Output out) {
        if (key == CreativeModeTabs.OP_BLOCKS && context.hasPermissions()) {
            for (RegistryObject<Block> block : Registers.BLOCKS.getEntries()) {
                if (ItemExtras.isHiddenInCreative(block.get().asItem())) {
                    out.accept(block.get().asItem());
                }
            }
            for (RegistryObject<Item> item : Registers.ITEMS.getEntries()) {
                if (ItemExtras.isHiddenInCreative(item.get())) {
                    out.accept(item.get());
                }
            }
        }
    }
}
