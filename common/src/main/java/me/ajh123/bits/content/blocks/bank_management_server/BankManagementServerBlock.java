package me.ajh123.bits.content.blocks.bank_management_server;

import me.ajh123.bits.foundation.device.GenericDeviceBlock;
import me.ajh123.bits.utilities.registration.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BankManagementServerBlock extends GenericDeviceBlock<BankManagementServerBlockEntity> {
	public BankManagementServerBlock(Properties properties) {
		super(properties);
	}

	@Override
	public Class<BankManagementServerBlockEntity> getBlockEntityClass() {
		return BankManagementServerBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends BankManagementServerBlockEntity> getBlockEntityType() {
		return ModBlocks.BANK_MANAGEMENT_SERVER_BLOCK_ENTITY.get();
	}
}
