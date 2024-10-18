package me.ajh123.sams_bits.content;

import java.util.function.Supplier;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static void register(IEventBus modEventBus) {
        Registers.ITEMS.register(modEventBus);
    }

    private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> factory) {
        return Registers.ITEMS.register(name, factory);
    }
}
