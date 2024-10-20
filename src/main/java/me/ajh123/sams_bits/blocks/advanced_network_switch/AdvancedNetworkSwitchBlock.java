package me.ajh123.sams_bits.blocks.advanced_network_switch;

import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImString;
import li.cil.oc2r.common.blockentity.NetworkHubBlockEntity;
import me.ajh123.sams_bits.content.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import xyz.breadloaf.imguimc.Imguimc;
import xyz.breadloaf.imguimc.interfaces.Renderable;

import javax.annotation.Nullable;

public class AdvancedNetworkSwitchBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public AdvancedNetworkSwitchBlock() {
        super(Properties
                .of()
                .mapColor(MapColor.METAL)
                .sound(SoundType.METAL)
                .strength(1.5f, 6.0f));
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        return super.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(final @NotNull BlockState state, final Level level, final @NotNull BlockPos pos, final @NotNull Block changedBlock, final @NotNull BlockPos changedBlockPos, final boolean isMoving) {
        final BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof final NetworkHubBlockEntity networkHub) {
            networkHub.handleNeighborChanged();
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(final @NotNull BlockPos pos, final @NotNull BlockState state) {
        return ModBlocks.ADVANCED_NETWORK_SWITCH_BLOCK_ENTITY.get().create(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (level.isClientSide) {
            windowOpen.set(true);
            if (!windowOpen.get()) {
                Imguimc.pushRenderable(window);
            }
        }
        return InteractionResult.SUCCESS;
    }

    private static final ImBoolean windowOpen = new ImBoolean(false);
    private static final SwitchScreenRenderable window = new SwitchScreenRenderable();

    public static class SwitchScreenRenderable implements Renderable {

        @Override
        public String getName() {
            return "Advanced Network Switch";
        }

        @Override
        public void render() {
            ImString string = new ImString();
            ImGui.begin(getName(), windowOpen);
            ImGui.text("AAAAAAAA");
            ImGui.inputText("Test", string);
            ImGui.end();

            if (!windowOpen.get()) {
                Imguimc.toRemove.add(this);
            }
        }
    }
}