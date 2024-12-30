package me.ajh123.sams_bits.data.exporter;

import java.nio.file.Path;

import org.jgrapht.Graph;

import me.ajh123.sams_bits.SamsBitsCommon;
import me.ajh123.sams_bits.roads.RoadManager;
import me.ajh123.sams_bits.roads.RoadNode;
import me.ajh123.sams_bits.roads.RoadWay;

public abstract class Exporter {
    private Path save_path;
    private RoadManager manager;

    protected Exporter(Path save_path, RoadManager manager) {
        this.save_path = save_path;
        this.manager = manager;
    }

    protected Path getSavePath() {
        return this.save_path;
    }

    protected RoadManager getRoadManager() {
        return this.manager;
    }

    protected abstract void write(RoadNode node);
    protected abstract void delete(RoadNode node);
    protected abstract void write(RoadWay way);
    protected abstract void complete();
    
    public void export() {
        Graph<RoadNode, RoadWay> graph = manager.getGraph();

        try {
            try {
                for (RoadNode node : manager.toDelete()) {
                    this.delete(node);
                }

                for (RoadNode osmNode : graph.vertexSet()) {
                    this.write(osmNode);
                }
    
                for (RoadWay edge : graph.edgeSet()) {
                    this.write(edge);
                }
            } finally {
                this.complete();
            }      
        } catch (Exception e) {
            SamsBitsCommon.INSTANCE.log_warn(e.getMessage());
        }
    }
}
