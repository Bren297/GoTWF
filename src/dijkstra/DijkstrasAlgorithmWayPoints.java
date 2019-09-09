package dijkstra;

import java.util.ArrayList;

public class DijkstrasAlgorithmWayPoints {

	private ArrayList<Integer> dijkstras_Shortest_Path_With_WayPoints;
	private DijkstrasAlgorithm da = new DijkstrasAlgorithm();

	/**
	 * Using Dijkstras algorithm but you can specify indexes thats the resulting
	 * path must include
	 * 
	 * @param adjacemcyMatrix The matrix to be traversed
	 * @param start           The begining index for the path
	 * @param destination     The end index for the path
	 * @param wpl             A list of indexes that must be included
	 * @param avl             A list of indexes to be avoided
	 * @return The shortest path but with waypoints
	 */
	public ArrayList<Integer> dijkstrasAlgorithmWayPoints(int[][] adjacencyMatrix, int start, int destination,
			ArrayList<Integer> wpl, ArrayList<Integer> avl) {

		dijkstras_Shortest_Path_With_WayPoints = new ArrayList<>();
		ArrayList<ArrayList<Integer>> allPaths = new ArrayList<>();
		if (wpl.size() > 0) {
			// start to the first way point
			ArrayList<Integer> firstWapPoint = da.dijkstrasAlgorithm(adjacencyMatrix, start, wpl.get(0), avl);
			allPaths.add(firstWapPoint);

			// first way point to all others except the last one
			for (int i = 0; i < wpl.size(); i++) {
				if (i < wpl.size() - 1) {
					ArrayList<Integer> middleWayPoints = da.dijkstrasAlgorithm(adjacencyMatrix, wpl.get(i),
							wpl.get(i + 1), avl);

					allPaths.add(middleWayPoints);
				}
			}
			// last way point to destination
			ArrayList<Integer> lastWayPoints = da.dijkstrasAlgorithm(adjacencyMatrix, wpl.get(wpl.size() - 1),
					destination, avl);
			allPaths.add(lastWayPoints);
		} else {
			ArrayList<Integer> path = da.dijkstrasAlgorithm(adjacencyMatrix, start, destination, avl);
			allPaths.add(path);
		}

		// extract the correct path
		for (ArrayList<Integer> path : allPaths) {
			for (int k : path) {
				dijkstras_Shortest_Path_With_WayPoints.add(k);
			}
		}

		// return the complete path with all way points
		return dijkstras_Shortest_Path_With_WayPoints;
	}

}