package application;

import java.io.*;
import java.net.*;
import java.util.*;
import dijkstra.*;
import graph.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import utilities.*;

public class FXController implements Initializable {

    @FXML
    private ImageView mapView;
    @FXML
    private ScrollPane sp;
    @FXML
    private Pane ContolPane, mapPane;
    @FXML
    private TextArea textFeedBackRoute, waypointTextFeedBack, avoidTextFeedBack;
    @FXML
    private ComboBox<Vertex> start, destination, waypoints, avoidence;
    @FXML
    private ComboBox<String> routeOption;
    @FXML
    private Slider maxRoutes;
    @FXML
    private Label sliderValue, distanceLabel, difficultyLabel, dangerLabel;

    private AdjacencyMatrix am;
    private GraphUtils gu;
    private DijkstrasAlgorithm da;
    private DijkstrasAlgorithmWayPoints daw;
    private ArrayList<Integer> avoidList;
    private ArrayList<Integer> waypointList;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        mapView.setImage(new Image(FXController.class.getResource("map.jpg").toExternalForm()));
        am = new AdjacencyMatrix(300);
        da = new DijkstrasAlgorithm();
        daw = new DijkstrasAlgorithmWayPoints();
        avoidList = new ArrayList<>();
        waypointList = new ArrayList<>();
        loadCSVFiles();
        gu = new GraphUtils();
        drawGraphOnMap();
        routeOption.setItems(FXCollections.observableArrayList("Difficulty", "Danger", "Distance"));
        maxRoutes.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> source, Boolean oldValue, Boolean newValue) {
                sliderValue.textProperty().setValue(String.valueOf((int) maxRoutes.getValue()));
            }
        });
        setItems(start);
        setItems(destination);
        setItems(waypoints);
        setItems(avoidence);
    }

    // Handler Methods
    @FXML
    private void handleFindRouteButton() {
        try {
            textFeedBackRoute.setText("");
            int startChoice = start.getSelectionModel().getSelectedIndex();
            int destinationChoice = destination.getSelectionModel().getSelectedIndex();
            String searchOption = routeOption.getSelectionModel().getSelectedItem();
            switch (searchOption) {
            case "Difficulty":
                LinkData[][] difficultylinks = am.getLinkData();
                int[][] difficultyMartix = da.retriveSpecificLinkData(difficultylinks, "Difficulty");
                ArrayList<Integer> dpDifficulty;
                if (waypointList.isEmpty()) {
                    dpDifficulty = da.dijkstrasAlgorithm(difficultyMartix, startChoice, destinationChoice, avoidList);
                    path(dpDifficulty, textFeedBackRoute, true);
                } else {
                    dpDifficulty = daw.dijkstrasAlgorithmWayPoints(difficultyMartix, startChoice, destinationChoice,
                            waypointList, avoidList);
                    path(dpDifficulty, textFeedBackRoute, true);
                }
                Cost.cost(dpDifficulty, difficultylinks);
                distanceLabel.setText("Distance: " + Cost.distanceCost);
                difficultyLabel.setText("Difficulty: " + Cost.difficultyCost);
                dangerLabel.setText("Danger: " + Cost.dangerCost);
                break;
            case "Danger":
                LinkData[][] dangerlinks = am.getLinkData();
                int[][] dangerMartix = da.retriveSpecificLinkData(dangerlinks, "Danger");
                ArrayList<Integer> dpDanger;
                if (waypointList.isEmpty()) {
                    dpDanger = da.dijkstrasAlgorithm(dangerMartix, startChoice, destinationChoice, avoidList);
                    path(dpDanger, textFeedBackRoute, true);
                } else {
                    dpDanger = daw.dijkstrasAlgorithmWayPoints(dangerMartix, startChoice, destinationChoice,
                            waypointList, avoidList);
                    path(dpDanger, textFeedBackRoute, true);
                }
                Cost.cost(dpDanger, dangerlinks);
                distanceLabel.setText("Distance: " + Cost.distanceCost);
                difficultyLabel.setText("Difficulty: " + Cost.difficultyCost);
                dangerLabel.setText("Danger: " + Cost.dangerCost);
                break;
            case "Distance":
                LinkData[][] distancelinks = am.getLinkData();
                int[][] distanceMartix = da.retriveSpecificLinkData(distancelinks, "Distance");
                ArrayList<Integer> dpDistance;
                if (waypointList.isEmpty()) {
                    dpDistance = da.dijkstrasAlgorithm(distanceMartix, startChoice, destinationChoice, avoidList);
                    path(dpDistance, textFeedBackRoute, true);
                } else {
                    dpDistance = daw.dijkstrasAlgorithmWayPoints(distanceMartix, startChoice, destinationChoice,
                            waypointList, avoidList);
                    path(dpDistance, textFeedBackRoute, true);
                }
                Cost.cost(dpDistance, distancelinks);
                distanceLabel.setText("Distance: " + Cost.distanceCost);
                difficultyLabel.setText("Difficulty: " + Cost.difficultyCost);
                dangerLabel.setText("Danger: " + Cost.dangerCost);
                break;

            }

        } catch (Exception e) {
            textFeedBackRoute.setText("Please select a valid start point, destination and waypoint");
        }
    }

    private void path(ArrayList<Integer> dijkstrasPath, TextArea ta, boolean resetGraph) {
        if (resetGraph) {
            mapView.toFront();
        }
        int size = am.getGraphNodes().length;
        int[] Xconnections = new int[size];
        int[] Yconnections = new int[size];
        GraphNode<Vertex>[] nodeArray = am.getGraphNodes();
        for (int index : dijkstrasPath) {
            int x = nodeArray[index].getData().getX();
            int y = nodeArray[index].getData().getY();
            String name = nodeArray[index].getData().getName();
            Xconnections[index] = x;
            Yconnections[index] = y;
            mapPane.getChildren().addAll(gu.drawNodes(x, y, Color.DEEPPINK), gu.nodeNames(x, y, name, Color.BLACK));
            ta.appendText(nodeArray[index].getData().toString());
        }
        int j = 0;
        int k = j + 1;
        for (int i = 0; i < dijkstrasPath.size(); i++) {
            if (k < dijkstrasPath.size()) {
                int x1 = nodeArray[dijkstrasPath.get(j)].getData().getX();
                int y1 = nodeArray[dijkstrasPath.get(j)].getData().getY();
                int x2 = nodeArray[dijkstrasPath.get(k)].getData().getX();
                int y2 = nodeArray[dijkstrasPath.get(k)].getData().getY();
                mapPane.getChildren().add(gu.connectNodes(x1, y1, x2, y2, Color.BLUE));
            }
            j++;
            k++;
        }

    }

    @FXML
    private void handleAddWaypointButton() {
        waypointTextFeedBack.setText("");
        int waypoint = waypoints.getSelectionModel().getSelectedIndex();
        GraphNode<Vertex>[] nodeArray = am.getGraphNodes();
        if (!avoidList.contains(waypoint)) {
            if (!waypointList.contains(waypoint)) {
                waypointList.add(waypoint);
            }
            for (int point : waypointList) {
                waypointTextFeedBack.appendText(point + ":" + nodeArray[point].getData().toString());
            }
        } else {
            waypointTextFeedBack.setText("You cant go to a place you are avoiding.");
        }

    }

    @FXML
    private void handleAddAvoidepointButton() {
        avoidTextFeedBack.setText("");
        int avoidencePoint = avoidence.getSelectionModel().getSelectedIndex();
        GraphNode<Vertex>[] nodeArray = am.getGraphNodes();
        if (!waypointList.contains(avoidencePoint)) {
            if (!avoidList.contains(avoidencePoint)) {
                avoidList.add(avoidencePoint);
            }
            for (int point : avoidList) {
                avoidTextFeedBack.appendText(point + ":" + nodeArray[point].getData().toString());
            }
        } else {
            avoidTextFeedBack.setText("You cant avoid a place you have to go to.");
        }
    }

    @FXML
    private void handleShowAllRoutesButton() {
        textFeedBackRoute.setText("");
        int startChoice = start.getSelectionModel().getSelectedIndex();
        int destinationChoice = destination.getSelectionModel().getSelectedIndex();
        GraphNode<Vertex>[] nodeArray = am.getGraphNodes();
        Random rand = new Random();
        LinkData[][] links = am.getLinkData();
        int[][] randLinks = new int[nodeArray.length][nodeArray.length];
        int max = (int) maxRoutes.getValue();// max number of routes

        // set the link to random weights between 1 - 10
        for (int i = 0; i < nodeArray.length; i++) {
            for (int j = 0; j < nodeArray.length; j++) {
                if (links[i][j] != null) {
                    randLinks[i][j] = rand.nextInt(10);
                }
            }
        }
        // ArrayList to hold all paths
        ArrayList<ArrayList<Integer>> allPaths = new ArrayList<>();
        // boolean to cut the loop if it keeps returning the same paths
        boolean stop = false;
        for (int k = 0; k < max; k++) {
            if (!allPaths.isEmpty()) {
                // sets the previous paths weights to a bigger number so the next
                // path will be different
                for (int q = 0; q < allPaths.get(k - 1).size() - 1; q++) {
                    int one = allPaths.get(k - 1).get(q);
                    int two = allPaths.get(k - 1).get(q + 1);
                    randLinks[one][two] = rand.nextInt(1000);
                }
            }
            // a random path that should be different from the last
            ArrayList<Integer> randPath = da.dijkstrasAlgorithm(randLinks, startChoice, destinationChoice, null);
            allPaths.add(randPath);
            if (allPaths.size() > 1) {
                // checks to see if the previous path was the same as the currrent path
                for (int w = 0; w < allPaths.size() - 1; w++) {
                    if (allPaths.get(w).equals(allPaths.get(w + 1))) {
                        stop = true;
                        break;
                    }
                }
            }
            if (stop) {
                break;
            }
            textFeedBackRoute.appendText("\nRoute: " + k + "\n");
            textFeedBackRoute.appendText("--------------\n");
            path(randPath, textFeedBackRoute, false);
        }

    }

    @FXML
    private void handleRestartButton(ActionEvent e) {
        try {
            Stage dialogStage = new Stage();
            Node node = (Node) e.getSource();
            dialogStage = (Stage) node.getScene().getWindow();
            dialogStage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Gui.fxml")));
            dialogStage.setScene(scene);
            dialogStage.sizeToScene();
            dialogStage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    // load in the csv files that contain the edges and the vertices
    private void loadCSVFiles() {
        String line = "";
        BufferedReader br = null;
        BufferedReader br2 = null;
        Random rand = new Random();
        try {
            // TODO may need to use absolute path
            br = new BufferedReader(new FileReader("Vertices.txt"));
            while ((line = br.readLine()) != null) {

                String[] vertices = line.split(",");

                String name = vertices[0];
                int x = Integer.valueOf(vertices[1]);
                int y = Integer.valueOf(vertices[2]);
                new GraphNode<Vertex>(new Vertex(name, x, y), am);
            }
            // TODO may need to use absolute path
            br2 = new BufferedReader(new FileReader("Edges.txt"));
            line = "";
            while ((line = br2.readLine()) != null) {
                String[] edges = line.split(",");

                int danger = rand.nextInt(1000);
                int difficulty = rand.nextInt(1000);
                String con1 = edges[0];
                String con2 = edges[1];
                GraphNode<Vertex>[] nodeArray = am.getGraphNodes();

                int vertex1 = -1;
                int vertex2 = -1;
                boolean found1 = false;
                boolean found2 = false;
                for (GraphNode<Vertex> v : nodeArray) {
                    if (v != null) {
                        if (v.getData().getName().equals(con1)) {
                            vertex1 = v.getIndex();
                            found1 = true;
                        } else if (v.getData().getName().equals(con2)) {
                            vertex2 = v.getIndex();
                            found2 = true;
                        }
                    }
                }
                if (vertex1 != -1 || vertex2 != -1) {
                    if (found1 && found2) {
                        LinkData link = new LinkData("",
                                calcDistance(nodeArray[vertex1].getData().getX(), nodeArray[vertex1].getData().getY(),
                                        nodeArray[vertex2].getData().getX(), nodeArray[vertex2].getData().getY()),
                                difficulty, danger);

                        nodeArray[vertex1].connectUndirected(nodeArray[vertex2], link);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    // The disatnce formula for calculating the distance between two points
    private static int calcDistance(int node1X, int nod1Y, int node2X, int node2Y) {
        double distance = Math.sqrt((node2Y - nod1Y) * (node2Y - nod1Y) + (node2X - node1X) * (node2X - node1X));
        return (int) distance;
    }

    // draws the initial graph on the map
    private void drawGraphOnMap() {
        GraphNode<Vertex>[] nodeArray = am.getGraphNodes();
        LinkData[][] links = am.getLinkData();
        for (int i = 0; i < am.getNumberOfNodes(); i++) {
            for (int j = 0; j < am.getNumberOfNodes(); j++) {
                if (links[i][j] != null) {
                    int x1 = nodeArray[i].getData().getX();
                    int y1 = nodeArray[i].getData().getY();
                    int x2 = nodeArray[j].getData().getX();
                    int y2 = nodeArray[j].getData().getY();
                    Line line = gu.connectNodes(x1, y1, x2, y2, Color.BLACK);
                    mapPane.getChildren().add(line);
                }
            }
        }
        for (int i = 0; i < am.getNumberOfNodes(); i++) {
            if (nodeArray[i] != null) {
                int x = nodeArray[i].getData().getX();
                int y = nodeArray[i].getData().getY();
                String name = nodeArray[i].getData().getName();
                Rectangle rect = gu.drawNodes(x, y, Color.BLACK);
                mapPane.getChildren().addAll(rect, gu.nodeNames(x, y, name, Color.BLACK));
            }
        }
    }

    private ObservableList<Vertex> observableList() {
        List<Vertex> comboBoxNodes = new ArrayList<Vertex>();
        GraphNode<Vertex>[] nodeArray = am.getGraphNodes();
        for (int i = 0; i < am.getNumberOfNodes(); i++) {
            if (nodeArray[i] != null) {
                comboBoxNodes.add(nodeArray[i].getData());
            }
        }
        return FXCollections.observableArrayList(comboBoxNodes);
    }

    private void setItems(ComboBox<Vertex> cb) {
        cb.setItems(observableList());
        cb.setVisibleRowCount(4);
        cb.setTooltip(new Tooltip(""));
        new ComboBoxSearch<Vertex>(cb);
    }

    public static class Cost {
        public static int distanceCost;
        public static int difficultyCost;
        public static int dangerCost;

        public static void cost(ArrayList<Integer> path, LinkData[][] linkData) {
            distanceCost = 0;
            difficultyCost = 0;
            dangerCost = 0;
            int j = 0;
            for (int i = 0; i < path.size(); i++) {
                j = i + 1;
                if (j < path.size() && path.get(i) != path.get(j)) {
                    distanceCost += linkData[path.get(i)][path.get(j)].getDistance();
                    difficultyCost += linkData[path.get(i)][path.get(j)].getDifficulty();
                    dangerCost += linkData[path.get(i)][path.get(j)].getDanger();
                }
            }
        }
    }
}