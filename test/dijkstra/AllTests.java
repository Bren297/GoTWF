package dijkstra;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DijkstrasAlgorithmTest.class, DijkstrasAlgorithmWayPointsTest.class,
		DijkstrasAlgorithmAvoidenceTest.class, graph.AdjacencyMatrixTest.class })
public class AllTests {

}
