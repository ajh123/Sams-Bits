package me.ajh123.sams_bits.content;

import java.util.function.Supplier;

import li.cil.oc2r.common.item.FlashMemoryWithExternalDataItem;
import li.cil.oc2r.common.item.HardDriveWithExternalDataItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final RegistryObject<HardDriveWithExternalDataItem> SEDNA_IMPROVED_HARD_DRIVE = register("sedna_improved_hard_drive", () ->
        new HardDriveWithExternalDataItem(ModComputerData.SEDNA_IMPROVED_BLOCK_DEVICE_DATA.getId(), DyeColor.BROWN));
    public static final RegistryObject<FlashMemoryWithExternalDataItem> SEDNA_IMPROVED_FLASH = register("sedna_improved_flash", () ->
        new FlashMemoryWithExternalDataItem(ModComputerData.SEDNA_IMPROVED_FIRMWARE.getId()));

    public static void register(IEventBus modEventBus) {
        Registers.ITEMS.register(modEventBus);
    }

    private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> factory) {
        return Registers.ITEMS.register(name, factory);
    }
}
