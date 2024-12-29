package me.ajh123.sams_bits.data.importer;

import java.nio.file.Path;

import me.ajh123.sams_bits.SamsBitsCommon;
import me.ajh123.sams_bits.roads.RoadManager;
import me.ajh123.sams_bits.roads.RoadNode;
import me.ajh123.sams_bits.roads.RoadWay;

public abstract class Importer {
    private Path load_path;

    protected Importer(Path load_path) {
        this.load_path = load_path;
    }

    protected Path getLoadPath() {
        return this.load_path;
    }

    protected abstract void importNodes(RoadManager manager);
    protected abstract void importWays(RoadManager manager);
    protected abstract void complete();
    
    public void importData(RoadManager manager) {
        try {
            try {
                importNodes(manager);
                RoadNode.nextId = manager.getGraph().vertexSet().size() + 1;
                importWays(manager);
                RoadWay.nextId = manager.getGraph().edgeSet().size() + 1;
            } finally {
                complete();
            }
        } catch (Exception e) {
            SamsBitsCommon.INSTANCE.log_warn(e.getMessage());
        }
    }
}