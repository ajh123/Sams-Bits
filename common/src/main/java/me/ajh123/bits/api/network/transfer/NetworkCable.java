package me.ajh123.bits.api.network.transfer;

import me.ajh123.bits.api.network.NetworkPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface NetworkCable {
	void addPacket(NetworkPacket packet, Direction from, BlockState me, Level level, BlockPos pos);
}
