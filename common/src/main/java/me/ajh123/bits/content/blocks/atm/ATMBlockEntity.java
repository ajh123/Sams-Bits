package me.ajh123.bits.content.blocks.atm;

import me.ajh123.bits.foundation.device.GenericDeviceBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


public class ATMBlockEntity extends GenericDeviceBlockEntity {
	public ATMBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
		super(type, pos, blockState);
	}
}
