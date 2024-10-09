package me.ajh123.sams_bits;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = SamsBits.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue MAXIUMUN_LINKS_PER_CFETRANSFORMER = BUILDER.comment("The maximum amount of links that can be connected to a Crayfish Energy Transformer.").defineInRange("blocks.cfe_transformer.maximumLinks", 12, 1, Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue CFE_EXTRACTION_RATE = BUILDER.comment("The rate of energy extration from a Crayfish Energy Transformer per tick").defineInRange("blocks.cfe_transformer.extrationRate", 30, 1, Integer.MAX_VALUE);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int maximumLinksPerCFETransformer;
    public static int CFEExtrationRate;

    private static boolean validateItemName(final Object obj) {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        maximumLinksPerCFETransformer = MAXIUMUN_LINKS_PER_CFETRANSFORMER.get();
        CFEExtrationRate = CFE_EXTRACTION_RATE.get();
    }
}
