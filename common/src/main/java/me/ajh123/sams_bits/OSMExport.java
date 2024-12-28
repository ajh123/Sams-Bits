package me.ajh123.sams_bits;

import de.topobyte.osm4j.core.model.impl.Node;
import de.topobyte.osm4j.core.model.impl.Way;
import de.topobyte.osm4j.core.model.impl.Tag;
import de.topobyte.osm4j.xml.output.OsmXmlOutputStream;
import me.ajh123.sams_bits.roads.RoadManager;

import com.slimjars.dist.gnu.trove.list.array.TLongArrayList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OSMExport {
    public static void main(RoadManager manager) throws IOException {
        // Create nodes with unique IDs and coordinates
        Node node1 = new Node(1L, 51.5074, -0.1278); // Example coordinates (London)
        Node node2 = new Node(2L, 51.5075, -0.1279);
        Node node3 = new Node(3L, 51.5076, -0.1280);

        // Create a TLongList to hold node IDs
        TLongArrayList nodeIds = new TLongArrayList();
        nodeIds.add(node1.getId());
        nodeIds.add(node2.getId());
        nodeIds.add(node3.getId());

        // Create a way with a unique ID and a list of node IDs
        Way way = new Way(1L, nodeIds); // Pass the node list in the constructor

        // Add tags to define the highway type and name
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("highway", "primary"));
        tags.add(new Tag("name", "Example Road"));
        way.setTags(tags);

        // Setup OsmXmlOutputStream to write OSM XML to an OutputStream
        File file = new File("highway.osm");

        // Open the output stream
        FileOutputStream fos = new FileOutputStream(file);
        OsmXmlOutputStream osmOutput = new OsmXmlOutputStream(fos, true); // true enables XML declaration

        try {
            // Write nodes to the OSM XML
            osmOutput.write(node1);
            osmOutput.write(node2);
            osmOutput.write(node3);

            // Write the way (highway) to the OSM XML
            osmOutput.write(way);

            // Complete the output (closes the stream and finalizes the document)
            osmOutput.complete();
        } finally {
            // Ensure that the OsmXmlOutputStream is closed properly
            osmOutput.complete();  // this is necessary to finish writing and clean up
            fos.close();  // close the file output stream
        }

        System.out.println("OSM file created: " + file.getAbsolutePath());
    }
}