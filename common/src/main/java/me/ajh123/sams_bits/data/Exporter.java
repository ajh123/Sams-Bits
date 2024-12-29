package me.ajh123.sams_bits.data;

import java.nio.file.Path;

import org.jgrapht.Graph;

import me.ajh123.sams_bits.SamsBitsCommon;
import me.ajh123.sams_bits.roads.RoadManager;
import me.ajh123.sams_bits.roads.RoadNode;
import me.ajh123.sams_bits.roads.RoadWay;

public abstract class Exporter {
    private Path save_path;

    protected Exporter(Path save_path) {
        this.save_path = save_path;
    }

    protected Path getSavePath() {
        return this.save_path;
    }

    protected abstract void write(RoadNode node);
    protected abstract void write(RoadWay way);
    protected abstract void complete();
    
    public void export(RoadManager manager) {
        Graph<RoadNode, RoadWay> graph = manager.getGraph();

        try {
            try {
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
