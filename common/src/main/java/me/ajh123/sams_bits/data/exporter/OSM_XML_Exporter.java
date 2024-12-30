package me.ajh123.sams_bits.data.exporter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.slimjars.dist.gnu.trove.list.array.TLongArrayList;

import de.topobyte.osm4j.core.model.impl.Way;
import de.topobyte.osm4j.xml.output.OsmXmlOutputStream;
import me.ajh123.sams_bits.SamsBitsCommon;
import me.ajh123.sams_bits.roads.RoadManager;
import me.ajh123.sams_bits.roads.RoadNode;
import me.ajh123.sams_bits.roads.RoadWay;

public class OSM_XML_Exporter extends Exporter {
    private FileOutputStream fos;
    private OsmXmlOutputStream osmOutput;

    public OSM_XML_Exporter(Path save_path, RoadManager manager) {
        super(save_path, manager);

        try {
            Files.createDirectories(save_path.getParent());
            
            File file = save_path.toFile();
            file.createNewFile();

            fos = new FileOutputStream(file);
            osmOutput = new OsmXmlOutputStream(fos, true);          
        } catch (IOException e) {
            SamsBitsCommon.INSTANCE.log_warn(e.getMessage());
        }
    }

    @Override
    protected void write(RoadNode node) {
        if (!node.isDeleted()) {
            osmOutput.write(node.toOSMNode());
        }
    }

    @Override
    protected void write(RoadWay way) {
        var graph = getRoadManager().getGraph();
        TLongArrayList nodeIds = new TLongArrayList();
        
        // Get the source and target nodes and add their IDs
        nodeIds.add(graph.getEdgeSource(way).getId());
        nodeIds.add(graph.getEdgeTarget(way).getId());

        // Create a way with the node IDs and associated tags
        Way osmWay = new Way(way.getId(), nodeIds);
        osmWay.setTags(way.OSMTags());
        osmOutput.write(osmWay);
    }

    @Override
    protected void complete() {
        try {
            // Complete the output (close the stream and finalize the document)
            osmOutput.complete();  // Ensure the output is finalized
            fos.close();  // Close the file output stream 
        } catch (IOException e) {
            SamsBitsCommon.INSTANCE.log_warn(e.getMessage());
        }
    }
}
