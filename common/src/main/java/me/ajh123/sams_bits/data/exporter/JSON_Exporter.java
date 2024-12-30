package me.ajh123.sams_bits.data.exporter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.ajh123.sams_bits.SamsBitsCommon;
import me.ajh123.sams_bits.roads.RoadManager;
import me.ajh123.sams_bits.roads.RoadNode;
import me.ajh123.sams_bits.roads.RoadWay;

public class JSON_Exporter extends Exporter {
    private ObjectMapper objectMapper;

    public JSON_Exporter(Path save_path, RoadManager manager) {
        super(save_path, manager);
        try {
            Files.createDirectories(save_path);
            this.objectMapper = new ObjectMapper();
        } catch (IOException e) {
            SamsBitsCommon.INSTANCE.log_warn(e.getMessage());
        }
    }

    @Override
    protected void write(RoadNode node) {
        try {
            File file = this.getSavePath().resolve("nodes").resolve(String.valueOf(node.getId())+".json").toFile();
            if (!node.isDeleted()) {
                Files.createDirectories(file.toPath().getParent());
                file.createNewFile();
                objectMapper.writeValue(file, node);
            } else {
                file.delete();
            }
        } catch (IOException e) {
            SamsBitsCommon.INSTANCE.log_warn(e.getMessage());
        }
    }

    @Override
    protected void write(RoadWay way) {
        try {
            File file = this.getSavePath().resolve("ways").resolve(String.valueOf(way.getId())+".json").toFile();
            if (getRoadManager().getNode(way.source_id) == null || getRoadManager().getNode(way.target_id) == null) {
                file.delete();
            } else {
                Files.createDirectories(file.toPath().getParent());
                file.createNewFile();
                objectMapper.writeValue(file, way);
            }
        } catch (IOException e) {
            SamsBitsCommon.INSTANCE.log_warn(e.getMessage());
        }
    }

    @Override
    protected void complete() {
        // For JSON, this method does not need to do anything.
    }
}
