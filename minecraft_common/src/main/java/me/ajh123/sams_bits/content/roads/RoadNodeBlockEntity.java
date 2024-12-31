package me.ajh123.sams_bits.content.roads;

import java.util.ArrayList;
import java.util.List;

import me.ajh123.sams_bits.content.registry.ModBlockEntities;
import me.ajh123.sams_bits.roads.RoadNode;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.util.math.BlockPos;

public class RoadNodeBlockEntity extends BlockEntity {
    public RoadNodeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ROAD_NODE, pos, state);

        destinations = new ArrayList<>();
        sources = new ArrayList<>();
    }

    private List<Long> destinations;
    private List<Long> sources;

    public void addDestination(RoadNode node) {
        this.destinations.add(node.getId());
        this.markDirty();
    }

    public void removeDestination(RoadNode node) {
        this.destinations.remove(node.getId());
        this.markDirty();
    }

    public void addSource(RoadNode node) {
        this.sources.add(node.getId());
        this.markDirty();
    }

    public void removeSource(RoadNode node) {
        this.sources.remove(node.getId());
        this.markDirty();
    }

    public List<Long> getDestinations() {
        return this.destinations;
    }

    public List<Long> getSources() {
        return this.sources;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

        nbt.putLongArray("destinations", destinations);
        nbt.putLongArray("sources", sources);
    }

    @Override
    protected void readNbt(NbtCompound nbt, WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        for (long destination : nbt.getLongArray("destinations")) {
            destinations.add(destination);
        }

        for (long source : nbt.getLongArray("sources")) {
            sources.add(source);
        }
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}
