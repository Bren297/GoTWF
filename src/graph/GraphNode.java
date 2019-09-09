package graph;

public class GraphNode<T> {
    private T nodeData;
    private AdjacencyMatrix aMatrix;
    private int nodeIndex;

    /**
     *  
     */
    public GraphNode(T nodeData, AdjacencyMatrix aMatrix) {
        this.nodeData = nodeData;
        this.aMatrix = aMatrix;
        GraphNode<Vertex>[] nodes = aMatrix.getGraphNodes();
        int nodeCount = aMatrix.getNumberOfNodes();
        updateMatrix(nodeCount, nodes);
    }

    /**
     * Adds the new node into the desiered matrix
     * 
     * @param nodeCount
     * @param nodes
     */
    @SuppressWarnings("unchecked")
    private void updateMatrix(int nodeCount, GraphNode<Vertex>[] nodes) {
        for (int i = 0; i < nodes.length; i++) {
            // end of array
            if (i == nodeCount) {
                nodes[nodeIndex = nodeCount] = (GraphNode<Vertex>) this;
                aMatrix.setNumberOfNodes(nodeCount + 1);
                break;
            } // first null found
            else if (nodes[i] == null) {
                nodes[i] = (GraphNode<Vertex>) this;
                break;
            }
        }
    }

    /**
     * Connects two nodes/vertices on the graph so they can be traversed in both
     * directions
     * 
     * @param targetNode The node to be connected to this one
     * @param linkData   The matrix that these nodes/vertices are apart of
     */
    public void connectUndirected(GraphNode<T> targetNode, LinkData linkData) {
        LinkData[][] amat = aMatrix.getLinkData();
        amat[nodeIndex][targetNode.getIndex()] = amat[targetNode.getIndex()][nodeIndex] = linkData;
        aMatrix.setLinkData(amat);
    }

    // getters and setters
    public T getData() {
        return nodeData;
    }

    public void setData(T nodeData) {
        this.nodeData = nodeData;
    }

    public AdjacencyMatrix getAM() {
        return aMatrix;
    }

    public void setAM(AdjacencyMatrix aMatrix) {
        this.aMatrix = aMatrix;
    }

    public int getIndex() {
        return nodeIndex;
    }

    public void setIndex(int nodeIndex) {
        this.nodeIndex = nodeIndex;
    }
}