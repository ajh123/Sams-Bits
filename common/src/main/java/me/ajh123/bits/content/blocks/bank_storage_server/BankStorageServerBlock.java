package me.ajh123.bits.content.blocks.bank_storage_server;

import me.ajh123.bits.foundation.device.GenericDeviceBlock;
import me.ajh123.bits.utilities.registration.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BankStorageServerBlock extends GenericDeviceBlock<BankStorageServerBlockEntity> {
	public BankStorageServerBlock(Properties properties) {
		super(properties);
	}

	@Override
	public Class<BankStorageServerBlockEntity> getBlockEntityClass() {
		return BankStorageServerBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends BankStorageServerBlockEntity> getBlockEntityType() {
		return ModBlocks.BANK_STORAGE_SERVER_BLOCK_ENTITY.get();
	}
}
