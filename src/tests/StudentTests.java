package tests;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import graphs.Graph;
import junit.framework.TestCase;

/**
 * Please put your own test cases into this file.
 */

public class StudentTests extends TestCase {

	@Test
	public void testDijkstras1() {
		Graph<Double> graph = new Graph<Double>();
		String[] vertices = new String[] { "ST", "A", "B", "C", "D" };
		for (int i = 0; i < vertices.length; i++) {
			graph.addVertex(vertices[i], new Double(i + 0.001));
		}

		graph.addDirectedEdge("ST", "A", 5);
		graph.addDirectedEdge("ST", "B", 8);
		graph.addDirectedEdge("A", "C", 10);
		graph.addDirectedEdge("B", "C", 3);
		graph.addDirectedEdge("A", "B", 1);
		graph.addDirectedEdge("C", "D", 9);

		String startVertex = "ST";
		String answer = runDijkstras(graph, startVertex);

		answer += TestingSupport.hardCodingPrevention;

		assertTrue(TestingSupport.correctResults("studentTestDijkstras1.txt", answer));
	}

	@Test
	public void testDijkstras2() {
		Graph<Double> graph = createGraph();
		String startVertex = "ST";
		String answer = runDijkstras(graph, startVertex);

		answer += TestingSupport.hardCodingPrevention;

		assertTrue(TestingSupport.correctResults("studentTestDijkstras2.txt", answer));

	}

	@Test
	public void testDijkstras3() {
		Graph<Double> graph = new Graph<Double>();
		String[] vertices = new String[] { "ST", "A", "B", "C", "D" };
		for (int i = 0; i < vertices.length; i++) {
			graph.addVertex(vertices[i], new Double(i + 0.001));
		}

		graph.addDirectedEdge("B", "A", 4);
		graph.addDirectedEdge("B", "D", 3);
		graph.addDirectedEdge("A", "C", 2);
		graph.addDirectedEdge("A", "D", 5);
		graph.addDirectedEdge("C", "D", 7);

		String startVertex = "ST";
		String answer = runDijkstras(graph, startVertex);

		answer += TestingSupport.hardCodingPrevention;

		assertTrue(TestingSupport.correctResults("studentTestDijkstras3.txt", answer));
	}

	@Test
	public void testDijkstras4() {
		Graph<Double> graph = new Graph<Double>();
		String[] vertices = new String[] { "ST", "A", "B", "C", "D" };
		for (int i = 0; i < vertices.length; i++) {
			graph.addVertex(vertices[i], new Double(i + 0.001));
		}
		graph.addDirectedEdge("ST", "A", 5);
		graph.addDirectedEdge("ST", "B", 8);
		graph.addDirectedEdge("B", "A", 4);
		graph.addDirectedEdge("B", "D", 3);
		graph.addDirectedEdge("A", "D", 5);
		graph.addDirectedEdge("C", "D", 7);

		String startVertex = "ST";
		String answer = runDijkstras(graph, startVertex);

		answer += TestingSupport.hardCodingPrevention;

		assertTrue(TestingSupport.correctResults("studentTestDijkstras4.txt", answer));
	}

	private Graph<Double> createGraph() {
		Graph<Double> graph = new Graph<Double>();

		/* Adding vertices to the graph */
		String[] vertices = new String[] { "ST", "A", "B", "C", "D", "M" };
		for (int i = 0; i < vertices.length; i++) {
			graph.addVertex(vertices[i], new Double(i + 1000.50));
		}

		/* Adding directed edges */
		graph.addDirectedEdge("ST", "A", 13);
		graph.addDirectedEdge("ST", "B", 2);
		graph.addDirectedEdge("A", "C", 1);
		graph.addDirectedEdge("C", "A", 1);
		graph.addDirectedEdge("B", "D", 2);
		graph.addDirectedEdge("C", "D", 3);
		graph.addDirectedEdge("D", "C", 2);

		return graph;
	}

	private static String runDijkstras(Graph<Double> graph, String startVertex) {
		ArrayList<String> shortestPath = new ArrayList<String>();
		StringBuffer results = new StringBuffer();

		Set<String> vertices = graph.getVertices();
		TreeSet<String> sortedVertices = new TreeSet<String>(vertices);
		for (String endVertex : sortedVertices) {
			int shortestPathCost = graph.doDijkstras(startVertex, endVertex, shortestPath);
			results.append("Shortest path cost between " + startVertex + " " + endVertex + ": " + shortestPathCost);
			results.append(", Path: " + shortestPath + "\n");
			shortestPath.clear();
		}

		return results.toString();
	}
}