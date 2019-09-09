package graph;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class AdjacencyMatrixTest {

	@Test
	public void testAdjacencyMatrix() {
		AdjacencyMatrix am = null;
		Vertex a;
		Vertex b;
		GraphNode<Vertex> aNode;
		GraphNode<Vertex> bNode;
		a = new Vertex("WinterFell", 10, 10);
		b = new Vertex("CastleBlack", 20, 20);
		assertEquals("Matrix should be empty", null, am);
		am = new AdjacencyMatrix(2);
		assertEquals("Matrix shold be size 2", 2, am.getLinkData().length);
		aNode = new GraphNode<Vertex>(a, am);
		bNode = new GraphNode<Vertex>(b, am);
		GraphNode<Vertex>[] nodeArray = am.getGraphNodes();
		assertEquals("Index 0 should be aNode", aNode.getData(), nodeArray[0].getData());
		assertEquals("Index 1 should be bNode", bNode.getData(), nodeArray[1].getData());
	}

}
