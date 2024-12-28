package me.ajh123.sams_bits.roads;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import me.ajh123.sams_bits.maths.Position;

public class RoadManager {
    private static RoadManager INSTANCE = null;
    private final Graph<RoadNode, RoadWay> graph;

    private RoadManager() {
        //disable instantiation
        graph = new SimpleDirectedWeightedGraph<>(RoadWay.class);
    }

    public static RoadManager getInstance() {
        if (RoadManager.INSTANCE == null) {
            RoadManager.INSTANCE = new RoadManager();
        }
        return RoadManager.INSTANCE;
    }

    public boolean addRoadNode(RoadNode node) {
        boolean res = graph.addVertex(node);
        if (res) {
            System.out.printf("Added node %s\n", node);
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
            System.out.printf("Removed node %s\n", node);
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
        System.out.printf("Connected nodes %s, %s\n", source, destination);
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
}
