package graph;

import java.lang.reflect.Array;

public class AdjacencyMatrix {
    private LinkData[][] adjacencyMatrixLinkData;
    private GraphNode<Vertex>[] graphVertexArray;
    private int numberOfNodes;

    /**
     * Class constructor
     * 
     * @param size The size of the matrix;
     */
    @SuppressWarnings("unchecked")
	public AdjacencyMatrix(int size) {
        adjacencyMatrixLinkData = new LinkData[size][size];
        graphVertexArray = (GraphNode<Vertex>[])Array.newInstance(GraphNode.class, size);
    }

    // getters and setters
    public LinkData[][] getLinkData() {
        return adjacencyMatrixLinkData;
    }

    public void setLinkData(LinkData[][] adjacencyMatrixLinkData) {
        this.adjacencyMatrixLinkData = adjacencyMatrixLinkData;
    }

    public GraphNode<Vertex>[] getGraphNodes() {
        return graphVertexArray;
    }

    public void setArrayGraphNodes(GraphNode<Vertex>[] graphVertexArray) {
        this.graphVertexArray = graphVertexArray;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public void setNumberOfNodes(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
    }
}