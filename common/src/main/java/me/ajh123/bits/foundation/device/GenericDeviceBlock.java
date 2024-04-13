package me.ajh123.bits.foundation.device;

import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.gui.ScreenOpener;
import com.simibubi.create.foundation.item.ItemHelper;
import com.tterrag.registrate.fabric.EnvExecutor;
import me.ajh123.bits.content.blocks.atm.ATMScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public abstract class GenericDeviceBlock<E extends BlockEntity> extends HorizontalDirectionalBlock implements IBE<E> {

	protected GenericDeviceBlock(Properties properties) {
		super(properties);
		this.registerDefaultState((this.stateDefinition.any()).setValue(FACING, Direction.NORTH));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction direction = context.getHorizontalDirection().getOpposite();
		BlockPos blockPos = context.getClickedPos();
		BlockPos blockPos2 = blockPos.relative(direction);
		Level level = context.getLevel();
		return level.getBlockState(blockPos2).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(blockPos2)
				? this.defaultBlockState().setValue(FACING, direction)
				: null;
	}

	@Override
	public abstract Class<E> getBlockEntityClass();

	@Override
	public abstract BlockEntityType<? extends E> getBlockEntityType();

}
