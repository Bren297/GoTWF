package dijkstra;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import graph.AdjacencyMatrix;
import graph.LinkData;
import graph.GraphNode;
import graph.Vertex;

public class DijkstrasAlgorithmTest {
	AdjacencyMatrix am;
	Vertex a;
	Vertex b;
	Vertex c;
	Vertex d;
	GraphNode<Vertex> aNode;
	GraphNode<Vertex> bNode;
	GraphNode<Vertex> cNode;
	GraphNode<Vertex> dNode;
	LinkData aToB;
	LinkData bToD;
	LinkData aToC;
	LinkData cToD;
	/*A Representation of the graph being tested
	 * (0,0)
	 * (A)----20---(B)(0,20)
	 *    \_		|
	 *(10,10)(C)    |20
	 *        \_    |
	 *			\_  |
	 *			  (D)(20,20)
	 */
	@Before
	public void setUp() {
		am = new AdjacencyMatrix(4);
		a = new Vertex("WinterFell", 0, 0);
		b = new Vertex("CastleBlack", 20, 0);
		c = new Vertex("DreadFort", 10, 10);
		d = new Vertex("WhiteHarbour", 20, 20);
		aNode = new GraphNode<Vertex>(a,am);
		bNode = new GraphNode<Vertex>(b,am);
		cNode = new GraphNode<Vertex>(c,am);
		dNode = new GraphNode<Vertex>(d,am);
		aToB = new LinkData("A<->B", 20, 20, 20);
		bToD = new LinkData("B<->D", 20, 20, 20);
		aToC = new LinkData("A<->C", 14, 14, 14);
		cToD = new LinkData("C<->D", 14, 14, 14);
		aNode.connectUndirected(bNode, aToB);
		bNode.connectUndirected(dNode, bToD);
		aNode.connectUndirected(cNode, aToC);
		cNode.connectUndirected(dNode, cToD);
	}
	@Test
	public void testDijkstrasAlgorithm() {
		DijkstrasAlgorithm da = new DijkstrasAlgorithm();
		int[][] distanceGraph = da.retriveSpecificLinkData(am.getLinkData(), "Distance");
		ArrayList<Integer> path = da.dijkstrasAlgorithm(distanceGraph, 0, 3,null);
		assertEquals("Should only contain 3 indexes",3,path.size());
		int start = path.get(0);
		assertEquals("Start Point",0,start);
		int middle = path.get(1);
		assertEquals("Middle",2,middle);
		int destination = path.get(2);
		assertEquals("End Point",3,destination);
	}

}