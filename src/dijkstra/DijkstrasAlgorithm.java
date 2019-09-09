package dijkstra;

import java.util.ArrayList;

import graph.LinkData;

public class DijkstrasAlgorithm {

	private static final int NO_PARENT = -1;

	private ArrayList<Integer> dijkstras_Shortest_Path;

	/**
	 * An implementation of Dijkstra's Algorithm
	 * 
	 * @param adjacemcyMatrix The matrix to be traversed
	 * @param start           The begining index for the path
	 * @param end             The end index for the path
	 * @param avoidList       A list of indexes to be avoided
	 */
	public ArrayList<Integer> dijkstrasAlgorithm(int[][] adjacencyMatrix, int start, int end,
			ArrayList<Integer> avoidList) {

		int nVertices = adjacencyMatrix[0].length;
		// array to hold the shortest distance for start to i
		int[] shortestDistances = new int[nVertices];

		boolean[] added = new boolean[nVertices];
		// set all distances to infinity
		for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
			shortestDistances[vertexIndex] = Integer.MAX_VALUE;
			added[vertexIndex] = (avoidList != null && avoidList.contains(vertexIndex));
		}
		// the distance from start to itself is 0
		shortestDistances[start] = 0;
		// array to store the shortest path
		int[] parents = new int[nVertices];
		// the start does not have a parent
		parents[start] = NO_PARENT;
		// find the shortest path for all vertex
		for (int i = 1; i < nVertices; i++) {

			int nearestVertex = -1;
			int shortestDistance = Integer.MAX_VALUE;
			for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
				if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
					nearestVertex = vertexIndex;
					shortestDistance = shortestDistances[vertexIndex];
				}
			}
			if (nearestVertex != -1) {
				added[nearestVertex] = true;
			}
			for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
				if (nearestVertex != -1) {
					int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

					if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) {
						parents[vertexIndex] = nearestVertex;
						shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
					}
				}
			}
		}
		// search the results for the single shortest path to the target
		searchPath(start, shortestDistances, parents, end);
		return dijkstras_Shortest_Path;
	}

	/**
	 * searches for the correct path that was specified out of all paths
	 * 
	 * @param startVertex
	 * @param distances
	 * @param endVertex
	 */
	private void searchPath(int startVertex, int[] distances, int[] parents, int endVertex) {
		int nVertices = distances.length;

		for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
			if (vertexIndex != startVertex && vertexIndex == endVertex) {
				createPath(vertexIndex, parents);
			}
		}
	}

	/**
	 * Adds each index for the selected path to an array list
	 * 
	 * @param currentVertex
	 * @param parents
	 */
	private void createPath(int currentVertex, int[] parents) {

		if (currentVertex == NO_PARENT) {
			dijkstras_Shortest_Path = new ArrayList<>();
			return;
		}

		createPath(parents[currentVertex], parents);

		dijkstras_Shortest_Path.add(currentVertex);

	}

	/**
	 * Retrieves the specified data from the three possibilities
	 * 
	 * @param am        The linkData matrix you wish to retrive the data from
	 * @param selection
	 * @return The extracted adjacency matrix or else null
	 */
	public int[][] retriveSpecificLinkData(LinkData[][] am, String selection) {

		int size = am.length;
		if (selection.equals("Difficulty")) {
			int[][] difficulty = new int[size][size];
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (am[i][j] != null) {
						difficulty[i][j] = am[i][j].getDifficulty();
					}
				}
			}
			return difficulty;
		} else if (selection.equals("Danger")) {
			int[][] danger = new int[size][size];
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (am[i][j] != null) {
						danger[i][j] = am[i][j].getDanger();
					}
				}
			}
			return danger;
		} else if (selection.equals("Distance")) {
			int[][] distance = new int[size][size];
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (am[i][j] != null) {
						distance[i][j] = am[i][j].getDistance();
					}
				}
			}
			return distance;
		} else {
			return null;
		}
	}

}