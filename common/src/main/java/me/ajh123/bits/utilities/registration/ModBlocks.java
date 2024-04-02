package me.ajh123.bits.utilities.registration;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import me.ajh123.bits.SamsBits;
import me.ajh123.bits.content.blocks.atm.ATMBlock;
import me.ajh123.bits.content.blocks.atm.ATMBlockEntity;
import me.ajh123.bits.content.blocks.bank_management_server.BankManagementServerBlock;
import me.ajh123.bits.content.blocks.bank_management_server.BankManagementServerBlockEntity;
import me.ajh123.bits.content.blocks.bank_storage_server.BankStorageServerBlock;
import me.ajh123.bits.content.blocks.bank_storage_server.BankStorageServerBlockEntity;
import me.ajh123.bits.content.blocks.data_transfer_cable.DataTransferCableBlock;
import net.minecraft.world.level.block.Blocks;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class ModBlocks {
	public static final BlockEntry<ATMBlock> ATM_BLOCK = Registration.REGISTRATE.block("atm", ATMBlock::new)
			.initialProperties(() -> Blocks.IRON_BLOCK)
			.properties(p -> p.strength(5f).requiresCorrectToolForDrops())
			.simpleItem()
			.transform(pickaxeOnly())
			.register();

	public static final BlockEntityEntry<ATMBlockEntity> ATM_BLOCK_ENTITY = Registration.REGISTRATE.blockEntity("atm", ATMBlockEntity::new)
			.validBlocks(ATM_BLOCK)
			.register();

	public static final BlockEntry<DataTransferCableBlock> DATA_TRANSFER_CABLE = Registration.REGISTRATE.block("data_transfer_cable", DataTransferCableBlock::new)
			.initialProperties(() -> Blocks.IRON_BLOCK)
			.properties(p -> p.strength(5f).requiresCorrectToolForDrops())
			.simpleItem()
			.transform(pickaxeOnly())
			.register();

	public static final BlockEntry<BankStorageServerBlock> BANK_STORAGE_SERVER_BLOCK = Registration.REGISTRATE.block("bank_storage_server", BankStorageServerBlock::new)
			.initialProperties(() -> Blocks.IRON_BLOCK)
			.properties(p -> p.strength(5f).requiresCorrectToolForDrops())
			.simpleItem()
			.transform(pickaxeOnly())
			.register();

	public static final BlockEntityEntry<BankStorageServerBlockEntity> BANK_STORAGE_SERVER_BLOCK_ENTITY = Registration.REGISTRATE.blockEntity("bank_storage_server", BankStorageServerBlockEntity::new)
			.validBlocks(BANK_STORAGE_SERVER_BLOCK)
			.register();

	public static final BlockEntry<BankManagementServerBlock> BANK_MANAGEMENT_SERVER_BLOCK = Registration.REGISTRATE.block("bank_management_server", BankManagementServerBlock::new)
			.initialProperties(() -> Blocks.IRON_BLOCK)
			.properties(p -> p.strength(5f).requiresCorrectToolForDrops())
			.simpleItem()
			.transform(pickaxeOnly())
			.register();

	public static final BlockEntityEntry<BankManagementServerBlockEntity> BANK_MANAGEMENT_SERVER_BLOCK_ENTITY = Registration.REGISTRATE.blockEntity("bank_management_server", BankManagementServerBlockEntity::new)
			.validBlocks(BANK_MANAGEMENT_SERVER_BLOCK)
			.register();

	public static void init() {
		// load the class and register everything
		SamsBits.LOGGER.info("Registering blocks for " + SamsBits.NAME);
	}
}
