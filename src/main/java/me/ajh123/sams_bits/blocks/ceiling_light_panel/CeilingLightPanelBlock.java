package me.ajh123.sams_bits.blocks.ceiling_light_panel;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.block.CeilingLightBlock;
import com.mrcrayfish.furniture.refurbished.block.MetalType;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Collectors;

public class CeilingLightPanelBlock extends CeilingLightBlock {
    public CeilingLightPanelBlock(MetalType type, Properties properties) {
        super(type, properties);
    }

    protected ImmutableMap<BlockState, VoxelShape>  generateShapes(ImmutableList<BlockState> states) {
        VoxelShape wallShape = Block.box(13.0, 5.0, 5.0, 16.0, 11.0, 11.0);
        VoxelShape ceilingShape = Block.box(5.0, 13.0, 5.0, 11.0, 16.0, 11.0);
        VoxelShape floorShape = Block.box(5.0, 0.0, 5.0, 11.0, 3.0, 11.0);
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap((state) -> state, (state) -> {
            Direction facing = state.getValue(FACING);
            AttachFace face = state.getValue(FACE);
            VoxelShape var10000;
            switch (face) {
                case FLOOR -> var10000 = VoxelShapeHelper.rotateHorizontally(floorShape, facing.getOpposite());
                case WALL -> var10000 = VoxelShapeHelper.rotateHorizontally(wallShape, facing.getOpposite());
                case CEILING -> var10000 = VoxelShapeHelper.rotateHorizontally(ceilingShape, facing.getOpposite());
                default -> throw new IncompatibleClassChangeError();
            }

            return var10000;
        })));
    }
}
