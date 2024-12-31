package me.ajh123.sams_bits.content.roads;

import java.util.ArrayList;
import java.util.List;

import me.ajh123.sams_bits.content.registry.ModBlockEntities;
import me.ajh123.sams_bits.maths.Position;
import me.ajh123.sams_bits.roads.RoadNode;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.util.math.BlockPos;

public class RoadNodeBlockEntity extends BlockEntity {
    private static final String NBT_DESTINATIONS = "destinations";
    private static final String NBT_SOURCES = "sources";

    public RoadNodeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ROAD_NODE, pos, state);

        destinations = new ArrayList<>();
        sources = new ArrayList<>();
    }

    private List<BlockPos> destinations;
    private List<BlockPos> sources;

    private void sync() {
        this.markDirty();
        BlockPos me = this.getPos();
        BlockState state = this.world.getBlockState(me);
        this.world.updateListeners(me, state, state, 0);
    }

    public void addDestination(RoadNode node) {
        Position position = node.getPosition();
        BlockPos pos = new BlockPos(position.getX(), position.getY(), position.getZ());
        this.destinations.add(pos);
        this.sync();
    }

    public void removeDestination(RoadNode node) {
        Position position = node.getPosition();
        BlockPos pos = new BlockPos(position.getX(), position.getY(), position.getZ());
        this.destinations.remove(pos);
        this.sync();
    }

    public void addSource(RoadNode node) {
        Position position = node.getPosition();
        BlockPos pos = new BlockPos(position.getX(), position.getY(), position.getZ());
        this.sources.add(pos);
        this.sync();
    }

    public void removeSource(RoadNode node) {
        Position position = node.getPosition();
        BlockPos pos = new BlockPos(position.getX(), position.getY(), position.getZ());
        this.sources.remove(pos);
        this.sync();
    }

    public List<BlockPos> getDestinations() {
        return this.destinations;
    }

    public List<BlockPos> getSources() {
        return this.sources;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

        NbtList dests = new NbtList();
        for (BlockPos pos : destinations) {
            NbtCompound position = new NbtCompound();
            position.putInt("x", pos.getX());
            position.putInt("y", pos.getY());
            position.putInt("z", pos.getZ());
            dests.add(position);
        }
        nbt.put(NBT_DESTINATIONS, dests);

        NbtList socs = new NbtList();
        for (BlockPos pos : sources) {
            NbtCompound position = new NbtCompound();
            position.putInt("x", pos.getX());
            position.putInt("y", pos.getY());
            position.putInt("z", pos.getZ());
            socs.add(position);
        }
        nbt.put(NBT_SOURCES, socs);
    }

    @Override
    protected void readNbt(NbtCompound nbt, WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        NbtList dests = nbt.getList(NBT_DESTINATIONS, NbtElement.COMPOUND_TYPE);
        destinations.clear();
        for (int i = 0; i < dests.size(); i++) {
            NbtCompound position = dests.getCompound(i);
            BlockPos loc = new BlockPos(position.getInt("x"), position.getInt("y"), position.getInt("z"));
            destinations.add(loc);
        }

        NbtList socs = nbt.getList(NBT_SOURCES, NbtElement.COMPOUND_TYPE);
        sources.clear();
        for (int i = 0; i < socs.size(); i++) {
            NbtCompound position = dests.getCompound(i);
            BlockPos loc = new BlockPos(position.getInt("x"), position.getInt("y"), position.getInt("z"));
            sources.add(loc);
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
