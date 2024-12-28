package me.ajh123.sams_bits;

import de.topobyte.osm4j.core.model.impl.Node;
import de.topobyte.osm4j.core.model.impl.Way;
import de.topobyte.osm4j.xml.output.OsmXmlOutputStream;
import me.ajh123.sams_bits.roads.RoadManager;
import me.ajh123.sams_bits.roads.RoadNode;
import me.ajh123.sams_bits.roads.RoadWay;

import com.slimjars.dist.gnu.trove.list.array.TLongArrayList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class OSMExport {
    public static void convert(RoadManager manager) throws IOException {
        var graph = manager.getGraph();

        // Set up OsmXmlOutputStream to write to an OSM XML file
        File file = new File("osm_export.osm");
        FileOutputStream fos = new FileOutputStream(file);
        OsmXmlOutputStream osmOutput = new OsmXmlOutputStream(fos, true);

        try {
            for (RoadNode osmNode : graph.vertexSet()) {
                osmOutput.write(osmNode.toOSMNode());
            }

             for (RoadWay edge : graph.edgeSet()) {
                TLongArrayList nodeIds = new TLongArrayList();
                
                // Get the source and target nodes and add their IDs
                nodeIds.add(graph.getEdgeSource(edge).getId());
                nodeIds.add(graph.getEdgeTarget(edge).getId());

                // Create a way with the node IDs and associated tags
                Way osmWay = new Way(edge.getId(), nodeIds);
                osmWay.setTags(edge.OSMTags());
                osmOutput.write(osmWay);
            }

        } finally {
            // Complete the output (close the stream and finalize the document)
            osmOutput.complete();  // Ensure the output is finalized
            fos.close();  // Close the file output stream
        }

        System.out.println("OSM file created: " + file.getAbsolutePath());
    }
}