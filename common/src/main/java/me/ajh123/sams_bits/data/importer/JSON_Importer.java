package me.ajh123.sams_bits.data.importer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import me.ajh123.sams_bits.SamsBitsCommon;
import me.ajh123.sams_bits.roads.RoadManager;
import me.ajh123.sams_bits.roads.RoadNode;
import me.ajh123.sams_bits.roads.RoadWay;

public class JSON_Importer extends Importer {
    private ObjectMapper objectMapper;

    public JSON_Importer(Path load_path, RoadManager manager) {
        super(load_path, manager);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void importNodes() {
        File nodesDir = this.getLoadPath().resolve("nodes").toFile();
        if (!nodesDir.exists()) {
            SamsBitsCommon.INSTANCE.log_warn("Directory for nodes does not exist.");
            return;
        }

        try {
            for (File nodeFile : nodesDir.listFiles()) {
                if (nodeFile.isFile()) {
                    RoadNode node = readRoadNode(nodeFile);
                    if (node != null) {
                        if (!node.isDeleted()) {
                            // Add the node to the graph directly
                            getRoadManager().getGraph().addVertex(node);
                            SamsBitsCommon.INSTANCE.log_debug(String.format("Added node %s\n", node));
                        } else {
                            nodeFile.delete();
                        }
                    }
                }
            }
        } catch (IOException e) {
            SamsBitsCommon.INSTANCE.log_warn("Error reading node files: " + e.getMessage());
        }
    }

    @Override
    protected void importWays() {
        File waysDir = this.getLoadPath().resolve("ways").toFile();
        if (!waysDir.exists()) {
            SamsBitsCommon.INSTANCE.log_warn("Directory for ways does not exist.");
            return;
        }

        try {
            for (File wayFile : waysDir.listFiles()) {
                if (wayFile.isFile()) {
                    RoadWay way = readRoadWay(wayFile);
                    if (way != null) {
                        RoadNode source = getRoadManager().getNode(way.source_id);
                        RoadNode target = getRoadManager().getNode(way.target_id);

                        if (source.isDeleted() || target.isDeleted()) {
                            wayFile.delete();
                        } else {
                            getRoadManager().getGraph().addEdge(source, target, way);
                            SamsBitsCommon.INSTANCE.log_debug(String.format("Added way %s\n", way));
                        }
                    }
                }
            }
        } catch (IOException e) {
            SamsBitsCommon.INSTANCE.log_warn("Error reading way files: " + e.getMessage());
        }
    }

    @Override
    protected void complete() {
        // For JSON, this method does not need to do anything.
    }

    private RoadNode readRoadNode(File file) throws IOException {
        JsonNode nodeJson = objectMapper.readTree(file);
        return objectMapper.treeToValue(nodeJson, RoadNode.class);
    }

    private RoadWay readRoadWay(File file) throws IOException {
        JsonNode wayJson = objectMapper.readTree(file);
        return objectMapper.treeToValue(wayJson, RoadWay.class);
    }
}