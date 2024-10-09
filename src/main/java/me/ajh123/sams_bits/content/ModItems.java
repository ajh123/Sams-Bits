package me.ajh123.sams_bits.content;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final RegistryObject<Item> EXAMPLE_ITEM = Registers.ITEMS.register("example_item", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().alwaysEat().nutrition(1).saturationMod(2f).build())));


    public static void register(IEventBus modEventBus) {
        Registers.ITEMS.register(modEventBus);
    }
}
