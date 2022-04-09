package ru.yuldashev.learning.mai;

import com.fxgraph.cells.RectangleCell;
import com.fxgraph.edges.CorneredEdge;
import com.fxgraph.edges.CorneredLoopEdge;
import com.fxgraph.edges.DoubleCorneredEdge;
import com.fxgraph.edges.Edge;
import com.fxgraph.graph.Graph;
import com.fxgraph.graph.ICell;
import com.fxgraph.graph.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.yuldashev.learning.mai.utils.GraphUtils;
import ru.yuldashev.learning.mai.gui.ViewController;
import ru.yuldashev.learning.mai.logic.graphLogic.GraphEventsHandler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;

public class Core extends Application {

    public static Stage mainStage;
    public static ViewController mainViewController;

//    public static void main(String[] args) {
//
//        LinkedList<String> states = new LinkedList<String>();
//        states.add("Germany");
//        states.add("France");
//        states.add("Italy");
//        states.add("Spain");
//
//        ListIterator<String> listIter = states.listIterator();
//
//        while(listIter.hasNext()){
//
//            System.out.println(listIter.next());
//        }
//        // сейчас текущий элемент - Испания
//        // изменим значение этого элемента
//        listIter.set("Португалия");
//        // пройдемся по элементам в обратном порядке
//        while(listIter.hasPrevious()){
//
//            System.out.println(listIter.previous());
//        }
//    }



    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {

        Graph graph = GraphUtils.createGraph();
//        populateGraph(graph);
        GraphEventsHandler graphEventsHandler = new GraphEventsHandler();

        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root;
        try {
            root = fxmlLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("GraphScene.fxml")).openStream());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        mainViewController = fxmlLoader.getController();
        mainViewController.setGraphEventsHandler(graphEventsHandler);
        mainViewController.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Graph with patterns");
        primaryStage.setScene(new Scene(root, 1000, 500));
        mainStage = primaryStage;

//        Platform.runLater(() -> mainViewController.init());

        primaryStage.show();
    }

    private void populateGraph(Graph graph) {
        final Model model = graph.getModel();
        graph.clear();
        final ICell cellA = new RectangleCell("A");
        final ICell cellB = new RectangleCell("B");
        final ICell cellC = new RectangleCell("C");
        final ICell cellD = new RectangleCell("D");
        final ICell cellE = new RectangleCell("E");
        final ICell cellF = new RectangleCell("F");
        final ICell cellG = new RectangleCell("G");

        model.addCell(cellA);
        model.addCell(cellB);
        model.addCell(cellC);
        model.addCell(cellD);
        model.addCell(cellE);
        model.addCell(cellF);
        model.addCell(cellG);

        final Edge edgeAB = new Edge(cellA, cellB, true);
        edgeAB.textProperty().set("Directed Edge");
        model.addEdge(edgeAB);

        final CorneredEdge edgeAC = new CorneredEdge(cellA, cellC, true, Orientation.HORIZONTAL);
        edgeAC.textProperty().set("Directed CorneredEdge");
        model.addEdge(edgeAC);

        final DoubleCorneredEdge edgeBE = new DoubleCorneredEdge(cellB, cellE, true, Orientation.HORIZONTAL);
        edgeBE.textProperty().set("Directed DoubleCorneredEdge");
        model.addEdge(edgeBE);

        final Edge edgeCF = new Edge(cellC, cellF, true);
        edgeCF.textProperty().set("Directed Edge");
        model.addEdge(edgeCF);

        final CorneredLoopEdge loopFTop = new CorneredLoopEdge(cellF, CorneredLoopEdge.Position.TOP);
        loopFTop.textProperty().set("Loop top");
        model.addEdge(loopFTop);

        model.addEdge(cellC, cellG);

        model.addEdge(cellB, cellD);

        graph.update();
    }
}
