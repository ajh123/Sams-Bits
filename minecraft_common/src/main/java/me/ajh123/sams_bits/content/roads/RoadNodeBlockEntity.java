package me.ajh123.sams_bits.content.roads;

import java.util.ArrayList;
import java.util.List;

import me.ajh123.sams_bits.content.registry.ModBlockEntities;
import me.ajh123.sams_bits.roads.RoadNode;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
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
    }

    public void removeDestination(RoadNode node) {
        this.destinations.remove(node.getId());
    }

    public void addSource(RoadNode node) {
        this.sources.add(node.getId());
    }

    public void removeSource(RoadNode node) {
        this.sources.remove(node.getId());
    }

    public List<Long> getDestinations() {
        return this.destinations;
    }

    public List<Long> getSources() {
        return this.sources;
    }


}
