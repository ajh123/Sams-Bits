package me.ajh123.sams_bits.content.roads;

import me.ajh123.sams_bits.SamsBits;
import me.ajh123.sams_bits.content.registry.ModBlocks;
import me.ajh123.sams_bits.content.registry.ModComponents;
import me.ajh123.sams_bits.content.registry.ModItems;
import me.ajh123.sams_bits.maths.Position;
import me.ajh123.sams_bits.roads.RoadManager;
import me.ajh123.sams_bits.roads.RoadNode;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class RoadNodeBlock extends Block {
    public RoadNodeBlock(Block.Settings settings) {
        super(settings);
    }

    @Override
    protected boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    protected VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    protected float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return 
            context.isHolding(ModBlocks.ROAD_NODE.asItem()) ||
            context.isHolding(ModItems.ROAD_CONNECTOR) 
        ? VoxelShapes.fullCube() : VoxelShapes.empty();
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            if (!world.isClient) {
                RoadManager roadManager = SamsBits.getRoadManager((ServerWorld) world);
                roadManager.removeNode(new Position(pos.getX(), pos.getY(), pos.getZ()));
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
            PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            RoadManager roadManager = SamsBits.getRoadManager((ServerWorld) world);

            Position position = new Position(pos.getX(), pos.getY(), pos.getZ());
            roadManager.addRoadNode(position);
            if (stack.getItem() instanceof RoadConnectorItem) {
                RoadNode node = roadManager.getNode(position);
                LinkingComponent linking = stack.getOrDefault(ModComponents.LINKING_COMPONENT, LinkingComponent.empty());
                if (linking.getSource() == null) {
                    linking.setSource(new Position(node.getPosition()));
                    stack.set(ModComponents.LINKING_COMPONENT, linking);
                } else {
                    roadManager.connectNodes(linking.getSource(), node.getPosition());
                    linking.setSource(null);
                    stack.set(ModComponents.LINKING_COMPONENT, null);
                }

            }
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }
}
