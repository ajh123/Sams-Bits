package me.ajh123.sams_bits.content.roads;

import me.ajh123.sams_bits.content.ModItems;
import me.ajh123.sams_bits.maths.Position;
import me.ajh123.sams_bits.roads.RoadManager;
import me.ajh123.sams_bits.roads.RoadNode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RoadNodeBlock extends Block {
    public RoadNodeBlock(Block.Settings settings) {
        super(settings);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            if (!world.isClient) {
                RoadManager.getInstance().removeNode(new Position(pos.getX(), pos.getY(), pos.getZ()));
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
            PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            Position position = new Position(pos.getX(), pos.getY(), pos.getZ());
            boolean added = RoadManager.getInstance().addRoadNode(position);
            if (player.getStackInHand(hand).getItem() == ModItems.ROAD_CONNECTOR) {
                if (!added) {
                    RoadNode node = RoadManager.getInstance().getNode(position);
                    System.out.println(node);
                }
            }
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }
}
