package me.ajh123.bits.content.blocks.atm;

import com.simibubi.create.foundation.gui.ScreenOpener;
import com.tterrag.registrate.fabric.EnvExecutor;
import me.ajh123.bits.foundation.device.GenericDeviceBlock;
import me.ajh123.bits.utilities.registration.ModBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;


public class ATMBlock extends GenericDeviceBlock<ATMBlockEntity> {
	public ATMBlock(Properties properties) {
		super(properties);
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
}
