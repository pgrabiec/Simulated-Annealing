package pgrabiec.mownit2.simulatedAnnealing.algo.simulations.salesman.visualization;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class GraphPointVisualization {
    private final Graph graph;

    private int count = 0;

    private Map<Point, Node> nodeMap = new HashMap<Point, Node>();

    private Set<Edge> edges = new LinkedHashSet<Edge>();

    public GraphPointVisualization(String title) {
        graph = new SingleGraph("Graph", false, false);

        graph.setAttribute("ui.title", title);

        graph.display(false);
    }

    public void addNodes(Point[] points) {
        Node node;

        for (Point point : points) {
            node = graph.addNode(String.valueOf(++count));
            node.setAttribute("x", point.x);
            node.setAttribute("y", point.y);

            nodeMap.put(point, node);
        }
    }

    public void setEdges(Point[] permutation) {
        Point p1, p2;
        Node n1, n2;

        for (Edge e : edges) {
            graph.removeEdge(e);
        }

        edges = new LinkedHashSet<Edge>();

        Edge edge;
        for (int i=0; i<permutation.length; i++) {
            p1 = permutation[i];
            p2 = permutation[(i+1)%permutation.length];

            n1 = nodeMap.get(p1);
            n2 = nodeMap.get(p2);

            edge = graph.addEdge(n1.getId() + " " + n2.getId(), n1, n2);

            edges.add(edge);
        }
    }
}
