package graph;

public class Vertex {
    // variables
    private String vertexName;
    private int xCorodinate;
    private int yCorodinate;

    /**
     * Class constructor
     * @param vertexName The name of the vertex
     * @param xCorodinate The x position of the vertex
     * @param yCorodinate The y position of the vertex
     */
    public Vertex(String vertexName, int xCorodinate, int yCorodinate) {
        this.vertexName = vertexName;
        this.xCorodinate = xCorodinate;
        this.yCorodinate = yCorodinate;
    }

    // getters and setters
    public String getName() {
        return vertexName;
    }

    public void setName(String vertexName) {
        this.vertexName = vertexName;
    }

    public int getX() {
        return xCorodinate;
    }

    public void setX(int xCorodinate) {
        this.xCorodinate = xCorodinate;
    }

    public int getY() {
        return yCorodinate;
    }

    public void setY(int yCorodinate) {
        this.yCorodinate = yCorodinate;
    }

    @Override
    public String toString() {
        return vertexName + "\n";
    }
}
