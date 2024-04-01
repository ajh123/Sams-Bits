package me.ajh123.bits.blocks.atm;

import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.content.logistics.vault.ItemVaultBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.gui.ScreenOpener;
import com.simibubi.create.foundation.item.ItemHelper;
import com.tterrag.registrate.fabric.EnvExecutor;
import me.ajh123.bits.registration.ModBlocks;
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

public class ATMBlock extends Block implements IBE<ATMBlockEntity> {
	public static final DirectionProperty FACING = BlockStateProperties.FACING;

	public ATMBlock(Properties properties) {
		super(properties);
		this.registerDefaultState((this.stateDefinition.any()).setValue(FACING, Direction.NORTH));
	}

	@SuppressWarnings("deprecation") // calling this method is not recommended, but overriding is recommended.
	@Override
	public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		EnvExecutor.runWhenOn(EnvType.CLIENT,
				() -> () -> this.displayScreen(player));
		return InteractionResult.SUCCESS;
	}

	@Environment(value = EnvType.CLIENT)
	protected void displayScreen(Player player) {
		if (player instanceof LocalPlayer)
			ScreenOpener.open(new ATMScreen());
	}

	@SuppressWarnings("deprecation") // calling this method is not recommended, but overriding is recommended.
	@Override
	public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@SuppressWarnings("deprecation") // calling this method is not recommended, but overriding is recommended.
	@Override
	public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
	}

	@Override
	public Class<ATMBlockEntity> getBlockEntityClass() {
		return ATMBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends ATMBlockEntity> getBlockEntityType() {
		return ModBlocks.ATM_BLOCK_ENTITY.get();
	}

	@SuppressWarnings("deprecation") // calling this method is not recommended, but overriding is recommended.
	@Override
	public void onRemove(BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState newState, boolean pIsMoving) {
		if (state.hasBlockEntity() && (state.getBlock() != newState.getBlock() || !newState.hasBlockEntity())) {
			BlockEntity be = world.getBlockEntity(pos);
			if (!(be instanceof ATMBlockEntity atmBE))
				return;
			ItemHelper.dropContents(world, pos, atmBE.inventory);
			atmBE.onRemove();
			world.removeBlockEntity(pos);
		}
	}
}
