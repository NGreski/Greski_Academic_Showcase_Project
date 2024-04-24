import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * non-public class to encapsulate a distance and an edge for use 
 * as elements of the priority queue underlying Dijkstra's algorithm.
 *
 * @author Jim Teresco
 * @author Noah Greski
 */
class PQEntry implements Comparable<PQEntry> {

    /** the cumulative distance to the destination of the edge */
    protected double totalDist;
    /** the edge to be considered for addition to the collection
	of shortest paths */
    protected HighwayEdge lastEdge;

    /**
     * Construct a PQEntry
     *
     * @param totalDist the total distance should this edge be added
     * @param lastEdge the candidate edge for inclusion in the collection of
     *        shortest paths
     */
    public PQEntry(double totalDist, HighwayEdge lastEdge) {

	this.totalDist = totalDist;
	this.lastEdge = lastEdge;
    }

    /**
     * Compare this PQEntry to another for priority queue ordering.
     * Here, we want higher priority given to the smaller cumulative
     * distance.
     *
     * @param other the PQEntry to which this PQEntry is to be compared
     *
     * @return -1 if this PQEntry's cumulative distance is smaller that other's<br>
     * 1 if this PQEntry's cumulative distance is greater that other's<br>
     * 0 if the cumulative distances are the same
     */
    @Override
    public int compareTo(PQEntry other) {

	if (totalDist < other.totalDist) return -1;
	if (totalDist > other.totalDist) return 1;
	return 0;
    }
}

/**
 * Sample solution for Dijkstra's algorithm using METAL graph data.
 *
 * Please do not place this code in any publically accessible location,
 * as students will be required to write this for assignments.
 *
 * @author Jim Teresco
 */
public class Dijkstra {

    public static final boolean DEBUG = true;
    
    /**
     * The main method for the Dijkstra's algorithm driver program,
     * which will take command-line parameters of a graph to read,
     * starting and ending points for driving directions, and an
     * optional filename to write a METAL ".pth" file to display
     * the directions in HDX.
     *
     * @param args command-line parameters, which include<br>
     * args[0]: name of graph file<br>
     * args[1]: starting waypoint label<br>
     * args[2]: destination waypoint label<br>
     * args[3]: (optional) .pth file name for mappable route
     */
    public static void main(String args[]) throws IOException {

	// check for command-line parameters
	if (args.length != 3 && args.length != 4) {
	    System.err.println("Usage: java Dijkstra graphfile start destination [pthfile]");
	    System.exit(1);
	}
	
        // read in the file to construct the graph
        Scanner s = new Scanner(new File(args[0]));
        HighwayGraph g = new HighwayGraph(s);
        s.close();
	if (DEBUG) {
	    System.out.println("Successfully read graph " + args[0] + " with |V|=" + g.vertices.length + " |E|=" + g.numEdges);
	}

	// find the vertex objects for the starting and destination points
	HighwayVertex start = g.getVertexByName(args[1]);
	if (start == null) {
	    System.err.println("No vertex found with label " + args[1]);
	    System.exit(1);
	}
	if (DEBUG) {
	    System.out.println("Start: " + start.label + ", vertexNum=" + start.vNum);
	}

	HighwayVertex dest = g.getVertexByName(args[2]);
	if (dest == null) {
	    System.err.println("No vertex found with label " + args[2]);
	    System.exit(1);
	}
	if (DEBUG) {
	    System.out.println("Dest: " + dest.label + ", vertexNum=" + dest.vNum);
	}

	// perform Dijkstra's algorithm to build a Map of vertices to
	// HighwayEdge objects (which will be the last edge traversed
	// on the shortest path to this object
	Map<String,HighwayEdge> result = new HashMap<String,HighwayEdge>();

	// construct the priority queue
	PriorityQueue<PQEntry> pq = new PriorityQueue<PQEntry>();

	// mark all vertices as unvisited
	g.markAllUnvisited();

	// entry for the start vertex in the result map
	result.put(start.label, null);

	// mark as visited
	start.visited = true;

	// initialize PQ with the places we can get to directly from
	// the start vertex
	HighwayEdge e = start.head;
	while (e != null) {
		if(e.isSidewalk()){ //added
			if (DEBUG) {
			System.out.println("pq.add(" + e.length + ", edge to " + g.vertices[e.dest].label + " via " + e.label + ")");
			}
			pq.add(new PQEntry(e.length, e));
		}
			e = e.next;
	}

int reached = start.vNum;
while (reached != dest.vNum && !pq.isEmpty()) {
    PQEntry nextPQ = pq.remove();
    HighwayEdge nextEdge = nextPQ.lastEdge;
    
    if (g.vertices[nextEdge.dest].visited) {
        continue; 
    }
    
    g.vertices[nextEdge.dest].visited = true;
    reached = g.vertices[nextEdge.dest].vNum;

    result.put(g.vertices[nextEdge.dest].label, nextEdge);

    HighwayEdge neighborEdge = g.vertices[nextEdge.dest].head;
    while (neighborEdge != null) {
        if (!g.vertices[neighborEdge.dest].visited && neighborEdge.isSidewalk()) {
            double newDist = nextPQ.totalDist + neighborEdge.length;
            pq.add(new PQEntry(newDist, neighborEdge));
            
            if (DEBUG) {
                System.out.println("pq.add(" + newDist + ", edge to " + g.vertices[neighborEdge.dest].label + " via " + neighborEdge.label + ")");
            }
        }
        neighborEdge = neighborEdge.next;
    }
}

if (reached != dest.vNum) {
    System.err.println("No path found to the destination.");
} else {
    
	// map is populated, find the sequence of edges to traverse
	// for the shortest path
	// build ArrayList that we will traverse in reverse to get
	// forward driving directions
	ArrayList<HighwayEdge> path = new ArrayList<HighwayEdge>();
	String current = dest.label;
	while (!current.equals(start.label)) {
	    HighwayEdge hop = result.get(current);
	    path.add(hop);
	    current = g.vertices[hop.source].label;
	}
	
	DecimalFormat df = new DecimalFormat("0.00");

	System.out.println("Detailed directions:");
	// report results
	double totalLength = 0.0;
	for (int i = path.size() - 1; i >= 0; i--) {
	    HighwayEdge hop = path.get(i);
	    totalLength += hop.length;
	    System.out.println("Travel from " + g.vertices[hop.source].label + " to " + g.vertices[hop.dest].label + " for " + df.format(hop.length) + " along " + hop.label + ", total " + df.format(totalLength));
	}

	// if there was an output filename given, also write in .pth format
	if (args.length == 4) {
	    PrintWriter pw = new PrintWriter(args[3]);
	    pw.println("START " + start.label + " " + start.point);
	    for (int i = path.size() - 1; i >= 0; i--) {
		HighwayEdge hop = path.get(i);
		pw.print(hop.label + " ");
		if (hop.shapePoints != null) {
		    for (int pNum = 0; pNum < hop.shapePoints.length; pNum++) {
			pw.print(hop.shapePoints[pNum] + " ");
		    }
		}
		pw.println(g.vertices[hop.dest].label + " " + g.vertices[hop.dest].point);
	    }

	    pw.close();
	}
    }
}
}