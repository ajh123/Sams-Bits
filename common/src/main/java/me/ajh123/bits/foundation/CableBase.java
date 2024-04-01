package me.ajh123.bits.foundation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public abstract class CableBase extends CrossCollisionBlock {
	private final VoxelShape post = Block.box(6, 0, 6, 10, 4, 10);
	private final VoxelShape side_north = Block.box(6, 0, 0, 10, 4, 9);
	private final VoxelShape side_east = Block.box(7, 0, 6, 16, 4, 10);
	private final VoxelShape side_south = Block.box(6, 0, 7, 10, 4, 16);
	private final VoxelShape side_west = Block.box(0, 0, 6, 9, 4, 10);
	private final VoxelShape vertical = Block.box(6, 0, 6, 10, 16, 10);
	public static final BooleanProperty VERTICAL = BooleanProperty.create("vertical");

	public CableBase(Properties properties) {
		super(2.0F, 2.0F, 3.0F, 3.0F, 3.0F, properties);
		this.registerDefaultState(this.stateDefinition.any()
				.setValue(NORTH, false)
				.setValue(EAST, false)
				.setValue(SOUTH, false)
				.setValue(WEST, false)
				.setValue(VERTICAL, false)
				.setValue(WATERLOGGED, false)
		);
	}

	@Override
	@ParametersAreNonnullByDefault
	public @NotNull VoxelShape getOcclusionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return this.getShape(blockState, blockGetter, blockPos, CollisionContext.empty());
	}

	@Override
	@ParametersAreNonnullByDefault
	public @NotNull VoxelShape getVisualShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return this.getShape(blockState, blockGetter, blockPos, collisionContext);
	}

	@Override
	@ParametersAreNonnullByDefault
	public boolean isPathfindable(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, PathComputationType pathComputationType) {
		return false;
	}

	public abstract boolean connectsTo(BlockState blockState);

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
		BlockGetter blockGetter = blockPlaceContext.getLevel();
		BlockPos blockPos = blockPlaceContext.getClickedPos();
		FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
		BlockPos blockPos2 = blockPos.north();
		BlockPos blockPos3 = blockPos.east();
		BlockPos blockPos4 = blockPos.south();
		BlockPos blockPos5 = blockPos.west();
		BlockPos blockPos6 = blockPos.above();
		BlockState blockState = blockGetter.getBlockState(blockPos2);
		BlockState blockState2 = blockGetter.getBlockState(blockPos3);
		BlockState blockState3 = blockGetter.getBlockState(blockPos4);
		BlockState blockState4 = blockGetter.getBlockState(blockPos5);
		BlockState blockState5 = blockGetter.getBlockState(blockPos6);
		return super.getStateForPlacement(blockPlaceContext)
				.setValue(NORTH, this.connectsTo(blockState))
				.setValue(EAST, this.connectsTo(blockState2))
				.setValue(SOUTH, this.connectsTo(blockState3))
				.setValue(WEST, this.connectsTo(blockState4))
				.setValue(VERTICAL, this.connectsTo(blockState5))
				.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
	}

	@Override
	@ParametersAreNonnullByDefault
	public @NotNull BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
		if (blockState.getValue(WATERLOGGED)) {
			levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
		}
		return updateConnection(blockState, direction, blockState2);
	}

	@Override
	@ParametersAreNonnullByDefault
	public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		VoxelShape shape = post;
		if (blockState.getValue(VERTICAL)) {
			shape = vertical;
		}

		if (blockState.getValue(NORTH)) {
			shape = Shapes.or(shape, side_north);
		}
		if (blockState.getValue(EAST)) {
			shape = Shapes.or(shape, side_east);
		}
		if (blockState.getValue(SOUTH)) {
			shape = Shapes.or(shape, side_south);
		}
		if (blockState.getValue(WEST)) {
			shape = Shapes.or(shape, side_west);
		}
		return shape;
	}

	@Override
	@ParametersAreNonnullByDefault
	public @NotNull VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return this.getShape(blockState, blockGetter, blockPos, collisionContext);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(NORTH, EAST, WEST, SOUTH, VERTICAL, WATERLOGGED);
	}

	public BlockState updateConnection(BlockState state, Direction direction, BlockState blockState2){
		if (direction == Direction.NORTH) {
			state = state.setValue(NORTH, false);
			if (this.connectsTo(blockState2)) {
				state = state.setValue(NORTH, true);
			}
		}
		if (direction == Direction.EAST) {
			state = state.setValue(EAST, false);
			if (this.connectsTo(blockState2)) {
				state = state.setValue(EAST, true);
			}
		}
		if (direction == Direction.SOUTH) {
			state = state.setValue(SOUTH, false);
			if (this.connectsTo(blockState2)) {
				state = state.setValue(SOUTH, true);
			}
		}
		if (direction == Direction.WEST) {
			state = state.setValue(WEST, false);
			if (this.connectsTo(blockState2)) {
				state = state.setValue(WEST, true);
			}
		}
		if (direction == Direction.UP) {
			state = state.setValue(VERTICAL, false);
			if (this.connectsTo(blockState2)) {
				state = state.setValue(VERTICAL, true);
			}
		}
		return state;
	}
}