package me.ajh123.bits.content.blocks.bank_management_server;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.shared.computer.blocks.ComputerBlockEntity;
import dan200.computercraft.shared.computer.blocks.ComputerPeripheral;
import dan200.computercraft.shared.computer.core.ComputerFamily;
import dan200.computercraft.shared.computer.core.ServerComputer;
import dan200.computercraft.shared.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class BankManagementServerBlockEntity extends ComputerBlockEntity {
	private @Nullable IPeripheral peripheral;

	public BankManagementServerBlockEntity(BlockEntityType<? extends ComputerBlockEntity> type, BlockPos pos, BlockState blockState) {
		super(type, pos, blockState, ComputerFamily.ADVANCED);
	}

	@Override
	protected ServerComputer createComputer(int id) {
		var computer = new ServerComputer(
				(ServerLevel) getLevel(), getBlockPos(), id, label,
				getFamily(), Config.computerTermWidth,
				Config.computerTermHeight
		);
		computer.addAPI(new BankManagementAPI());
		return computer;
	}

	@Nullable
	public IPeripheral peripheral() {
		if (peripheral != null) return peripheral;
		return peripheral = new ComputerPeripheral("bank_management_server", this);
	}
}
