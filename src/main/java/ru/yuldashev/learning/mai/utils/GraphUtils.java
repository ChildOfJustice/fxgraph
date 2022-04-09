package ru.yuldashev.learning.mai.utils;

import com.fxgraph.graph.Graph;
import com.fxgraph.layout.AbegoTreeLayout;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.abego.treelayout.Configuration;

public class GraphUtils {
    public static Graph createGraph(){
        Graph graph = new Graph();

        // Layout nodes
        AbegoTreeLayout layout = new AbegoTreeLayout(200, 200, Configuration.Location.Top);
        graph.layout(layout);

        // Configure interaction buttons and behavior
        graph.getViewportGestures().setPanButton(MouseButton.SECONDARY);
        graph.getNodeGestures().setDragButton(MouseButton.PRIMARY);

        return graph;
    }

    public static void updateGraphOnAnchorPane(Graph graph, AnchorPane pane){

//        mainStage.setTitle("REMOVED");
//        mainStage.setScene(new Scene(new BorderPane(), 1000, 800));

//        if(delete)
            pane.getChildren().clear();
//        else {
            // Display the graph
            Node PaneWithGraph = new BorderPane(graph.getCanvas());
            //Retrieving the observable list of the Anchor Pane
            ObservableList<Node> list = pane.getChildren();
            list.addAll(PaneWithGraph);
//        }

    }
}
