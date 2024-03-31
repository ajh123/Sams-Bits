package me.ajh123.bits.registration;

import com.tterrag.registrate.util.entry.ItemEntry;
import me.ajh123.bits.SamsBits;
import net.minecraft.world.item.Item;

public class ModItems {
	public static final ItemEntry<Item> COPPER_COIN = Registration.REGISTRATE.item("copper_coin", Item::new).register();
	public static final ItemEntry<Item> IRON_COIN = Registration.REGISTRATE.item("iron_coin", Item::new).register();
	public static final ItemEntry<Item> GOLD_COIN = Registration.REGISTRATE.item("gold_coin", Item::new).register();
	public static final ItemEntry<Item> EMERALD_COIN = Registration.REGISTRATE.item("emerald_coin", Item::new).register();

	public static void init() {
		// load the class and register everything
		SamsBits.LOGGER.info("Registering items for " + SamsBits.NAME);
	}
}
