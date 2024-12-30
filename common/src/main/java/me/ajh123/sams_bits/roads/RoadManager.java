package me.ajh123.sams_bits.roads;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import me.ajh123.sams_bits.SamsBitsCommon;
import me.ajh123.sams_bits.data.exporter.JSON_Exporter;
import me.ajh123.sams_bits.data.importer.JSON_Importer;
import me.ajh123.sams_bits.maths.Position;

public class RoadManager {
    private Path save_path = Path.of(".");
    private final SamsBitsCommon common;
    private final Graph<RoadNode, RoadWay> graph;
    private List<RoadNode> toDelete = new ArrayList<>();

    public RoadManager(SamsBitsCommon common) {
        this.graph = new SimpleDirectedWeightedGraph<>(RoadWay.class);
        this.common = common;
    }

    public void setSavePath(Path save_path) {
        this.save_path = save_path;
    }

    public Path getSavePath() {
        return this.save_path;
    }

    public boolean addRoadNode(RoadNode node) {
        if (getNode(node.getPosition()) != null) {
            return false;
        }

        boolean res = graph.addVertex(node);
        if (res) {
            common.log_debug(String.format("Added node %s\n", node));
        }
        return res;
    }

    public boolean addRoadNode(Position position) {
        return addRoadNode(new RoadNode(position, RoadNode.nextId++));
    }

    public boolean removeNode(RoadNode node) {
        if (node == null) {
            return false;
        }
        boolean res = graph.removeVertex(node);
        node.setDelated(true);
        toDelete.add(node);

        if (res) {
            common.log_debug(String.format("Removed node %s\n", node));
        }
        return res;
    }

    public boolean removeNode(Position position) {
        return removeNode(getNode(position));
    }

    public List<RoadNode> toDelete() {
        return this.toDelete;
    }

    public RoadNode getNode(Position pos) {
        try {
            return graph
                .vertexSet().stream().filter(node -> node.getPosition().equals(pos)).findAny()
                .get();
        } catch (NoSuchElementException e) {
            SamsBitsCommon.INSTANCE.log_debug("Failed getting node: "+e.getMessage());
            return null;
        }
    }

    public RoadNode getNode(long id) {
        try {
            return graph
                .vertexSet().stream().filter(node -> node.getId() == id).findAny()
                .get();
        } catch (NoSuchElementException e) {
            SamsBitsCommon.INSTANCE.log_debug("Failed getting node: "+e.getMessage());
            return null;
        }
    }

    public RoadWay connectNodes(RoadNode source, RoadNode destination) {
        RoadWay edge = graph.addEdge(source, destination);
        if (edge == null) {
            return null;
        }
        edge.setId(RoadWay.nextId++);
        edge.source_id = source.getId();
        edge.target_id = destination.getId();
        graph.setEdgeWeight(edge, source.getPosition().distanceTo(destination.getPosition()));
        common.log_debug(String.format("Connected nodes %s, %s\n", source, destination));
        return edge;
    }

    public RoadWay connectNodes(Position source, Position destination) {
        RoadNode rSource = getNode(source);
        RoadNode rDest = getNode(destination);
        return connectNodes(rSource, rDest);
    }

    public Graph<RoadNode, RoadWay> getGraph() {
        return graph;
    }

    public void save() {
        JSON_Exporter exporter = new JSON_Exporter(this.save_path, this);
        exporter.export();
    }

    public void load() {
        JSON_Importer importer = new JSON_Importer(this.save_path, this);
        importer.importData();
    }
}
