package me.ajh123.sams_bits.content.items;

import eu.pb4.sgui.api.gui.BookGui;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class TrainScheduleGUI extends BookGui {
    public TrainScheduleGUI(ServerPlayerEntity player, ItemStack book) {
        super(player, book);
    }
}
