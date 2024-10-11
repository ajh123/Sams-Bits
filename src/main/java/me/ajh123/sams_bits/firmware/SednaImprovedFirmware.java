package me.ajh123.sams_bits.firmware;

import java.io.IOException;

import javax.annotation.Nonnull;

import li.cil.oc2r.api.bus.device.data.Firmware;
import li.cil.sedna.api.memory.MemoryMap;
import li.cil.sedna.memory.MemoryMaps;
import net.minecraft.network.chat.Component;
import me.ajh123.sedna_improved.buildroot.Buildroot;

public final class SednaImprovedFirmware implements Firmware {
    public boolean run(@Nonnull MemoryMap memory, long startAddress) {
        try {
            MemoryMaps.store(memory, startAddress, Buildroot.getFirmware());
            MemoryMaps.store(memory, startAddress + 2097152L, Buildroot.getLinuxImage());
            return true;
        } catch (IOException var5) {
            return false;
        }
    }

    public Component getDisplayName() {
        return Component.literal("Improved Sedna Linux");
    }
}
