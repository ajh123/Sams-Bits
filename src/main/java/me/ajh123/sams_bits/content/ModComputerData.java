package me.ajh123.sams_bits.content;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import li.cil.oc2r.api.util.Registries;
import li.cil.oc2r.common.bus.device.data.BuildrootBlockDeviceData;
import li.cil.oc2r.common.bus.device.data.BuildrootFirmware;
import me.ajh123.sams_bits.SamsBits;
import li.cil.oc2r.api.bus.device.data.Firmware;
import li.cil.oc2r.api.bus.device.data.BlockDeviceData;

public class ModComputerData {
    public static final DeferredRegister<BlockDeviceData> BLOCK_DEVICE_DATA = DeferredRegister.create(Registries.BLOCK_DEVICE_DATA, SamsBits.MODID);
    public static final DeferredRegister<Firmware> FIRMWARE = DeferredRegister.create(Registries.FIRMWARE , SamsBits.MODID);

    public static final RegistryObject<BlockDeviceData> SEDNA_IMPROVED_BLOCK_DEVICE_DATA = BLOCK_DEVICE_DATA.register("sedna_improved", BuildrootBlockDeviceData::new);
    public static final RegistryObject<Firmware> SEDNA_IMPROVED_FIRMWARE= FIRMWARE.register("sedna_improved", BuildrootFirmware::new);

    public static void register(IEventBus modEventBus) {
        BLOCK_DEVICE_DATA.register(modEventBus);
        FIRMWARE.register(modEventBus);
    }
}
