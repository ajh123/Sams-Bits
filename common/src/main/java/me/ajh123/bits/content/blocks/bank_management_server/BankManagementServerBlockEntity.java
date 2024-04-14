package me.ajh123.bits.content.blocks.bank_management_server;

import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class BankManagementServerBlockEntity extends BlockEntity {
	private @Nullable IPeripheral peripheral;

	public BankManagementServerBlockEntity(BlockEntityType<? extends BlockEntity> type, BlockPos pos, BlockState blockState) {
		super(type, pos, blockState);
	}

	@Nullable
	public IPeripheral peripheral() {
		if (peripheral != null) return peripheral;
		return peripheral = new BankManagementAPI(this);
	}
}
