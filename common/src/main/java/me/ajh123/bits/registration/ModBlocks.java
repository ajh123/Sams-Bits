package me.ajh123.bits.registration;

import com.tterrag.registrate.util.entry.BlockEntry;
import me.ajh123.bits.SamsBits;
import me.ajh123.bits.blocks.atm.ATMBlock;
import net.minecraft.world.level.block.Blocks;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class ModBlocks {
	public static final BlockEntry<ATMBlock> ATM_BLOCK = Registration.REGISTRATE.block("atm", ATMBlock::new)
			.initialProperties(() -> Blocks.IRON_BLOCK)
			.properties(p -> p.strength(5f).requiresCorrectToolForDrops())
			.simpleItem()
			.transform(pickaxeOnly())
			.register();

	public static void init() {
		// load the class and register everything
		SamsBits.LOGGER.info("Registering blocks for " + SamsBits.NAME);
	}
}
