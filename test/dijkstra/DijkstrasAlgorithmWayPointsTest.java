package dijkstra;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import graph.*;

public class DijkstrasAlgorithmWayPointsTest {

	AdjacencyMatrix am;
	Vertex a;
	Vertex b;
	Vertex c;
	Vertex d;
	Vertex e;
	GraphNode<Vertex> aNode;
	GraphNode<Vertex> bNode;
	GraphNode<Vertex> cNode;
	GraphNode<Vertex> dNode;
	GraphNode<Vertex> eNode;
	LinkData aToB;
	LinkData bToD;
	LinkData aToC;
	LinkData cToD;
	LinkData eToC;
	/*A Representation of the graph being tested
	 * (0,0)
	 * (A)----20---(B)(20,0)
	 *    \_		|
	 *(10,10)(C)    |20
	 *     _/   \_  |
	 *	 _/       \_|
	 *(E)(0,20)	   (D)(20,20)
	 */
	@Before
	public void setup() {
		am = new AdjacencyMatrix(5);
		a = new Vertex("WinterFell", 0, 0);
		b = new Vertex("CastleBlack", 20, 0);
		c = new Vertex("DreadFort", 10, 10);
		d = new Vertex("WhiteHarbour", 20, 20);
		e = new Vertex("BearIsland", 0, 20);
		aNode = new GraphNode<Vertex>(a,am);
		bNode = new GraphNode<Vertex>(b,am);
		cNode = new GraphNode<Vertex>(c,am);
		dNode = new GraphNode<Vertex>(d,am);
		eNode = new GraphNode<Vertex>(e,am);
		aToB = new LinkData("A<->B", 20, 20, 20);
		bToD = new LinkData("B<->D", 20, 20, 20);
		aToC = new LinkData("A<->C", 14, 14, 14);
		cToD = new LinkData("C<->D", 14, 14, 14);
		eToC = new LinkData("E<->C", 14, 14, 14);
		aNode.connectUndirected(bNode, aToB);
		bNode.connectUndirected(dNode, bToD);
		aNode.connectUndirected(cNode, aToC);
		cNode.connectUndirected(dNode, cToD);
		eNode.connectUndirected(cNode, eToC);
	}
	@Test
	public void test() {
		DijkstrasAlgorithmWayPoints daw = new DijkstrasAlgorithmWayPoints();
		DijkstrasAlgorithm da = new DijkstrasAlgorithm();
		ArrayList<Integer> wpl = new ArrayList<>();
		wpl.add(4);
		int[][] distanceGraph = da.retriveSpecificLinkData(am.getLinkData(), "Distance");
		ArrayList<Integer> path = daw.dijkstrasAlgorithmWayPoints(distanceGraph,0, 3,wpl,null);
		assertEquals("Should only contain 6 indexes",6,path.size());
		int start = path.get(0);
		assertEquals("Start Point",0,start);
		int middle0 = path.get(1);
		assertEquals("Middle",2,middle0);
		int WayPoint = path.get(2);
		assertEquals("Arrived at WayPoint",4,WayPoint);
		int WayPoint0 = path.get(3);
		assertEquals("Leaving WayPoint",4,WayPoint0);
		int middle2 = path.get(4);
		assertEquals("Middle",2,middle2);
		int destination = path.get(5);
		assertEquals("Destination",3,destination);
	}

}