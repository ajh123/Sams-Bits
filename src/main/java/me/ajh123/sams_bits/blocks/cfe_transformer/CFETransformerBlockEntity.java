package me.ajh123.sams_bits.blocks.cfe_transformer;

import com.mrcrayfish.furniture.refurbished.blockentity.ElectricitySourceBlockEntity;
import me.ajh123.sams_bits.Config;
import me.ajh123.sams_bits.content.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CFETransformerBlockEntity extends ElectricitySourceBlockEntity {
    private final EnergyStorage energyStorage = new EnergyStorage(10000); // Storage for 10,000 FE
    private final LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);

    public CFETransformerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.CFE_TRANSFORMER_BLOCK_ENTITY.get(), pos, state);
    }

    // Forge energy

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        if (tag.contains("energy")) {
            energyStorage.deserializeNBT(tag.get("energy"));
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("energy", energyStorage.serializeNBT());
        super.saveAdditional(tag);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return energy.cast(); // Provide the energy capability for Forge Energy handling
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energy.invalidate(); // Clean up capabilities when invalidating
    }

    public int getEnergyStored() {
        return energyStorage.getEnergyStored();
    }

    public int getMaxEnergyStored() {
        return energyStorage.getMaxEnergyStored();
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        boolean powered = false;
        if (!level.isClientSide) {
            BlockEntity entity = level.getBlockEntity(blockPos);
            if (entity instanceof  CFETransformerBlockEntity cfeEntity) {
                if (cfeEntity.energyStorage.getEnergyStored() > 0) {
                    level.setBlockAndUpdate(blockPos, blockState.setValue(CFETransformerBlock.POWERED, true));
                    powered = true;
                } else {
                    level.setBlockAndUpdate(blockPos, blockState.setValue(CFETransformerBlock.POWERED, false));
                }

                if (powered) {
                    cfeEntity.energyStorage.extractEnergy(Config.CFEExtrationRate, false);
                }
            }
        }
    }

    // Crayfish energy

    @Override
    public boolean isNodePowered() {
        BlockState state = this.getBlockState();
        return state.hasProperty(BlockStateProperties.POWERED) && state.getValue(BlockStateProperties.POWERED);
    }

    @Override
    public void setNodePowered(boolean powered) {
        BlockState state = this.getBlockState();
        if (state.hasProperty(BlockStateProperties.POWERED)) {
            this.level.setBlock(this.worldPosition, state.setValue(BlockStateProperties.POWERED, powered), Block.UPDATE_ALL);
        }
    }

    @Override
    public int getNodeMaximumConnections() {
        return Config.maximumLinksPerCFETransformer;
    }
}
