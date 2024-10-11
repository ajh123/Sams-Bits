package me.ajh123.sams_bits.firmware;

import java.io.IOException;
import li.cil.oc2r.api.bus.device.data.BlockDeviceData;
import li.cil.sedna.api.device.BlockDevice;
import me.ajh123.sedna_improved.buildroot.Buildroot;
import li.cil.sedna.device.block.ByteBufferBlockDevice;
import net.minecraft.network.chat.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class SednaImprovedBlockDeviceData implements BlockDeviceData {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ByteBufferBlockDevice INSTANCE;
 
    public BlockDevice getBlockDevice() {
        return INSTANCE;
    }
 
    public Component getDisplayName() {
        return Component.literal("Improved Sedna Linux");
    }
 
    static {
        ByteBufferBlockDevice instance;
        try {
            instance = ByteBufferBlockDevice.createFromStream(Buildroot.getRootFilesystem(), true);
        } catch (IOException var2) {
            LOGGER.error(var2);
            instance = ByteBufferBlockDevice.create(0, true);
        }
    
        INSTANCE = instance;
    }
}
 