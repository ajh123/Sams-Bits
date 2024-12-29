package me.ajh123.sams_bits.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.ajh123.sams_bits.SamsBitsCommon;
import me.ajh123.sams_bits.roads.RoadNode;
import me.ajh123.sams_bits.roads.RoadWay;

public class JSON_Exporter extends Exporter {
    private ObjectMapper objectMapper;

    public JSON_Exporter(Path save_path) {
        super(save_path);
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
            Files.createDirectories(file.toPath().getParent());
            file.createNewFile();
            objectMapper.writeValue(file, node);
        } catch (IOException e) {
            SamsBitsCommon.INSTANCE.log_warn(e.getMessage());
        }
    }

    @Override
    protected void write(RoadWay way) {
        try {
            File file = this.getSavePath().resolve("ways").resolve(String.valueOf(node.getId())+".json").toFile();
            Files.createDirectories(file.toPath().getParent());
            file.createNewFile();
            objectMapper.writeValue(file, way);
        } catch (IOException e) {
            SamsBitsCommon.INSTANCE.log_warn(e.getMessage());
        }
    }

    @Override
    protected void complete() {
        // TODO Auto-generated method stub
    }

}
