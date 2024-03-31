package me.ajh123.bits.registration;

import com.simibubi.create.foundation.data.CreateRegistrate;
import me.ajh123.bits.SamsBits;

public class Registration {
	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(SamsBits.MOD_ID);

	public static void init() {
		REGISTRATE.defaultCreativeTab("main_group").register();
		ModBlocks.init();
		ModItems.init();
	}
}
