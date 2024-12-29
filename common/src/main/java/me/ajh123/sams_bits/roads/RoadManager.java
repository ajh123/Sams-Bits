package me.ajh123.sams_bits.roads;

import java.nio.file.Path;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import me.ajh123.sams_bits.SamsBitsCommon;
import me.ajh123.sams_bits.data.JSON_Exporter;
import me.ajh123.sams_bits.maths.Position;

public class RoadManager {
    private Path save_path = Path.of(".");
    private final SamsBitsCommon common;
    private final Graph<RoadNode, RoadWay> graph;

    public RoadManager(SamsBitsCommon common) {
        //disable instantiation
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
        boolean res = graph.addVertex(node);
        if (res) {
            common.log_debug(String.format("Added node %s\n", node));
        }
        return res;
    }

    public boolean addRoadNode(Position position) {
        return addRoadNode(new RoadNode(position));
    }

    public boolean roadNodeExists(RoadNode node) {
        return graph.containsVertex(node);
    }

    public boolean roadNodeExists(Position position) {
        return graph.containsVertex(new RoadNode(position));
    }

    public boolean removeNode(RoadNode node) {
        boolean res = graph.removeVertex(node);
        if (res) {
            common.log_debug(String.format("Removed node %s\n", node));
        }
        return res;
    }

    public boolean removeNode(Position position) {
        return removeNode(new RoadNode(position));
    }

    public RoadNode getNode(Position pos) {
        return graph
            .vertexSet().stream().filter(node -> node.getPosition().equals(pos)).findAny()
            .get();
    }

    public RoadWay connectNodes(RoadNode source, RoadNode destination) {
        RoadWay edge = graph.addEdge(source, destination);
        graph.setEdgeWeight(edge, source.getPosition().distanceTo(destination.getPosition()));
        common.log_debug(String.format("Connected nodes %s, %s\n", source, destination));
        return edge;
    }

    public RoadWay connectNodes(Position source, Position destination) {
        RoadNode rSource = new RoadNode(source);
        RoadNode rDest = new RoadNode(destination);
        return connectNodes(rSource, rDest);
    }

    public Graph<RoadNode, RoadWay> getGraph() {
        return graph;
    }

    public void save() {
        JSON_Exporter exporter = new JSON_Exporter(this.save_path);
        exporter.export(this);
    }

    public void load() {
        // File roads = SAVE_PATH.resolve("graph.json").toFile();
        // if (roads.exists()) {
        //     final var jsonImporter = new JSONImporter<RoadNode, RoadWay>();
        //     jsonImporter.importGraph(graph, roads);
        // }
    }
}
