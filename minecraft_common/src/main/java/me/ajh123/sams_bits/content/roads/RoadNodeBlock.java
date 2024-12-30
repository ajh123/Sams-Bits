package me.ajh123.sams_bits.content.roads;

import com.mojang.serialization.MapCodec;

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
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class RoadNodeBlock extends BlockWithEntity {
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

                RoadNode node = roadManager.getNode(new Position(pos.getX(), pos.getY(), pos.getZ()));
                RoadNodeBlockEntity be = getEntity(world, node);
                
                for (long sourceI : be.getSources()) {
                    RoadNode source = roadManager.getNode(sourceI);
                    RoadNodeBlockEntity sE = getEntity(world, source);
                    if (sE == null) {
                        continue;
                    }
                    sE.removeDestination(node);
                }
                for (long destinationI : be.getDestinations()) {
                    RoadNode destination = roadManager.getNode(destinationI);
                    RoadNodeBlockEntity dE = getEntity(world, destination);
                    if (dE == null) {
                        continue;
                    }
                    dE.removeSource(node);
                }

                roadManager.removeNode(node);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            RoadManager roadManager = SamsBits.getRoadManager((ServerWorld) world);
            Position position = new Position(pos.getX(), pos.getY(), pos.getZ());
            RoadNode node = roadManager.getNode(position);
            if (player.isSneaking()) {
                if (node == null) {
                    player.sendMessage(Text.translatable("interaction.road_node.not_defined"));
                } else {
                    player.sendMessage(Text.translatable("interaction.road_node.get_info", node.getId()));
                }
                return ActionResult.SUCCESS;           
            }
        }
        return super.onUse(state, world, pos, player, hit);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
            PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            RoadManager roadManager = SamsBits.getRoadManager((ServerWorld) world);

            Position position = new Position(pos.getX(), pos.getY(), pos.getZ());
            roadManager.addRoadNode(position);
            RoadNode node = roadManager.getNode(position);

            if (player.isSneaking()) {
                player.sendMessage(Text.translatable("interaction.road_node.get_info", node.getId()));
                return ItemActionResult.SUCCESS;
            } else {
                if (stack.getItem() instanceof RoadConnectorItem) {
                    LinkingComponent linking = stack.getOrDefault(ModComponents.LINKING_COMPONENT, LinkingComponent.empty());
                    if (linking.getSource() == null) {
                        linking.setSource(new Position(node.getPosition()));
                        stack.set(ModComponents.LINKING_COMPONENT, linking);
                    } else {
                        RoadNode source = roadManager.getNode(linking.getSource());

                        if (source == node) {
                            player.sendMessage(Text.translatable("interaction.road_node.link_loop").formatted(Formatting.RED), true);
                            linking.setSource(null);
                            stack.set(ModComponents.LINKING_COMPONENT, null);
                            return ItemActionResult.FAIL;
                        }

                        RoadNodeBlockEntity sE = getEntity(world, source);
                        RoadNodeBlockEntity dE = getEntity(world, node);

                        if (sE == null || dE == null) {
                            player.sendMessage(Text.translatable("interaction.road_node.invalid_nodes").formatted(Formatting.RED), true);
                            return ItemActionResult.FAIL;
                        }

                        sE.addDestination(node);
                        dE.addSource(source);

                        var way = roadManager.connectNodes(linking.getSource(), node.getPosition());

                        if (way == null) {
                            player.sendMessage(Text.translatable("interaction.road_node.already_linked").formatted(Formatting.RED), true);
                            return ItemActionResult.FAIL;
                        }

                        player.sendMessage(Text.translatable("interaction.road_node.linked", source.getId(), node.getId()).formatted(Formatting.GREEN), true);
                        player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP);

                        linking.setSource(null);
                        stack.set(ModComponents.LINKING_COMPONENT, null);
                        return ItemActionResult.SUCCESS;
                    }
                }
            }
        }
        return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RoadNodeBlockEntity(pos, state);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(RoadNodeBlock::new);
    }

    private static RoadNodeBlockEntity getEntity(World world, RoadNode node) {
        if (node == null) {
            return null;
        }

        Position position = node.getPosition();
        BlockEntity entity = world.getBlockEntity(new BlockPos(position.getX(), position.getY(), position.getZ()));
        if (entity instanceof RoadNodeBlockEntity roadNodeBlockEntity) {
            return roadNodeBlockEntity;
        }

        return null;
    }
}
