package me.ajh123.bits.content.blocks.atm;

import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import me.ajh123.bits.foundation.NetworkBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;


public class ATMBlockEntity extends NetworkBlockEntity {
	public ATMBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
		super(type, pos, blockState);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

	}
}
