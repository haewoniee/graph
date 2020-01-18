package graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Implements a graph. We use two maps: one map for adjacency properties
 * (adjancencyMap) and one map (dataMap) to keep track of the data associated
 * with a vertex.
 * 
 * @author cmsc132
 * 
 * @param <E>
 */
public class Graph<E> {
	/* You must use the following maps in your implementation */
	private HashMap<String, HashMap<String, Integer>> adjacencyMap;
	private HashMap<String, E> dataMap;

	public Graph() {
		adjacencyMap = new HashMap<String, HashMap<String, Integer>>();
		dataMap = new HashMap<String, E>();
	}

	public void addVertex(String vertexName, E data) 
			throws IllegalArgumentException {
		if (!dataMap.containsKey(vertexName)) {
			dataMap.put(vertexName, data);
			adjacencyMap.put(vertexName, new HashMap<String, Integer>());
		} else {
			throw new IllegalArgumentException("ERROR: already existing vertex");
		}
	}

	public void addDirectedEdge(String startVertexName, 
			String endVertexName, int cost) {
		/*
		 * If any of the vertices are not part of the graph. Use any error
		 * message.
		 */

		if (!dataMap.containsKey(startVertexName) || 
				!dataMap.containsKey(endVertexName)) {
			throw new IllegalArgumentException();
		} else {
			adjacencyMap.get(startVertexName).put(endVertexName, cost);
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		TreeMap<String, E> sortedDataMap = new TreeMap<String, E>(dataMap);
		TreeMap<String, HashMap<String, Integer>> sortedAdjMap = 
				new TreeMap<String, HashMap<String, Integer>>(
				adjacencyMap);
		sb.append("Vertices: " + sortedDataMap.keySet().toString());
		sb.append("\nEdges:");
		for (String i : sortedAdjMap.keySet()) {
			sb.append("\nVertex(" + i + ")--->" + sortedAdjMap.get(i).toString());
		}
		return sb.toString();
	}

	public Map<String, Integer> getAdjacentVertices(String vertexName) {
		if (adjacencyMap.containsKey(vertexName)) {
			return adjacencyMap.get(vertexName);
		} else {
			throw new IllegalArgumentException("ERROR: No such vertex exists");
		}
	}

	public int getCost(String startVertexName, String endVertexName) {
		if (adjacencyMap.containsKey(startVertexName)) {
			if (adjacencyMap.get(startVertexName).containsKey(endVertexName)) {
				return adjacencyMap.get(startVertexName).get(endVertexName);
			}
		}
		throw new IllegalArgumentException("ERROR: No such vertex exists");
	}

	public Set<String> getVertices() {
		return dataMap.keySet();
	}

	public E getData(String vertex) {
		if (dataMap.containsKey(vertex)) {
			return dataMap.get(vertex);
		}
		throw new IllegalArgumentException("ERROR: No such vertex exists");
	}

	public void doDepthFirstSearch(String StartingVertex, CallBack<E> callback) {
		if (!dataMap.containsKey(StartingVertex)) {
			throw new IllegalArgumentException("ERROR: No such vertex exists");
		} else {
			// Creating a HashSet to check visited vertices
			HashSet<String> visited = new HashSet<String>();

			// Creating a Stack
			Stack<String> stack = new Stack<String>();

			// Using stack and HashSet to traverse
			stack.push(StartingVertex);
			while (!stack.isEmpty()) {
				String curr = stack.pop();
				if (!visited.contains(curr)) {
					callback.processVertex(curr, dataMap.get(curr));
					visited.add(curr);
					if (adjacencyMap.get(curr) != null) {
						// Using a temporary TreeSet to Sort out the successors
						// (alphabetical order)
						TreeSet<String> temp = new TreeSet<String>(
								adjacencyMap.get(curr).keySet());
						for (String x : temp) {
							stack.push(x);
						}
					}
				}
			}
		}
	}

	public void doBreadthFirstSearch(String StartingVertex, CallBack<E> callback) {
		if (!dataMap.containsKey(StartingVertex)) {
			throw new IllegalArgumentException("ERROR: No such vertex exists");
		} else {
			// Creating a HashSet to check visited vertices
			HashSet<String> visited = new HashSet<String>();

			// Creating a Queue
			Queue<String> queue = new LinkedList<String>();

			// Using Queue and Hashset to traverse
			queue.add(StartingVertex);
			while (!queue.isEmpty()) {
				String curr = queue.remove();
				if (!visited.contains(curr)) {
					callback.processVertex(curr, dataMap.get(curr));
					visited.add(curr);
					if (adjacencyMap.get(curr) != null) {
						TreeSet<String> temp = new TreeSet<String>(
								adjacencyMap.get(curr).keySet());
						for (String x : temp) {
							// Avoid adding unnecessary queues
							if (!visited.contains(x)) {
								queue.add(x);
							}
						}
					}
				}
			}
		}
	}

	public int doDijkstras(String startVertexName, String endVertexName, 
			ArrayList<String> shortestPath) {
		if (!dataMap.containsKey(startVertexName) ||
				!dataMap.containsKey(endVertexName)) {
			throw new IllegalArgumentException("ERROR: No such vertex exists");
		} else {
			HashSet<String> visited = new HashSet<String>();
			HashMap<String, ArrayList<String>> pathMap = 
					new HashMap<String, ArrayList<String>>();
			TreeMap<String, Integer> costMap = new TreeMap<String, Integer>();
			for (String i : dataMap.keySet()) {
				pathMap.put(i, new ArrayList<String>());
				costMap.put(i, Integer.MAX_VALUE);
			}
			costMap.put(startVertexName, 0);
			String minVertex = startVertexName;
			while (!visited.containsAll(dataMap.keySet())) {
				// find node K not in visited with smallest cost
				double min = Integer.MAX_VALUE;
				for (String k : dataMap.keySet()) {
					if (!visited.contains(k)) {
						if (costMap.get(k) <= min) {
							min = costMap.get(k);
							minVertex = k;
						}
					}
				}
				pathMap.get(minVertex).add(minVertex);
				if (minVertex.equals(endVertexName)) {
					break;
				} else {
					visited.add(minVertex);
					for (String j : adjacencyMap.get(minVertex).keySet()) {
						if (costMap.get(minVertex) + 
								adjacencyMap.get(minVertex).get(j) < costMap.get(j)
								&& pathMap.get(minVertex).contains(startVertexName)) {
							
							costMap.put(j, costMap.get(minVertex) + 
									adjacencyMap.get(minVertex).get(j));
							pathMap.put(j, new ArrayList<String>());
							pathMap.get(j).addAll(pathMap.get(minVertex));
						}
					}
				}
			}
			int cost = costMap.get(endVertexName);
			if (!pathMap.get(endVertexName).contains(startVertexName) || 
					cost == Integer.MAX_VALUE) {
				shortestPath.add("None");
				return -1;
			} else {
				for (String j : pathMap.get(endVertexName)) {
					shortestPath.add(j);
				}
				return cost;
			}
		}
	}

}