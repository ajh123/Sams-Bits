package me.ajh123.sams_bits.data.importer;

import java.nio.file.Path;

import me.ajh123.sams_bits.SamsBitsCommon;
import me.ajh123.sams_bits.roads.RoadManager;
import me.ajh123.sams_bits.roads.RoadNode;
import me.ajh123.sams_bits.roads.RoadWay;

public abstract class Importer {
    private Path load_path;
    private RoadManager manager;

    protected Importer(Path load_path, RoadManager manager) {
        this.load_path = load_path;
        this.manager = manager;
    }

    protected Path getLoadPath() {
        return this.load_path;
    }

    protected RoadManager getRoadManager() {
        return this.manager;
    }

    protected abstract void importNodes();
    protected abstract void importWays();
    protected abstract void complete();
    
    public void importData() {
        try {
            try {
                importNodes();
                RoadNode.nextId = manager.getGraph().vertexSet().size() + 1;
                importWays();
                RoadWay.nextId = manager.getGraph().edgeSet().size() + 1;
            } finally {
                complete();
            }
        } catch (Exception e) {
            SamsBitsCommon.INSTANCE.log_warn(e.getMessage());
        }
    }
}