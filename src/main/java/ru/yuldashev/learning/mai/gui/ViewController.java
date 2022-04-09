package ru.yuldashev.learning.mai.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.yuldashev.learning.mai.exceptions.DuplicatedNodeException;
import ru.yuldashev.learning.mai.exceptions.GraphCommandException;
import ru.yuldashev.learning.mai.logic.commands.Command;
import ru.yuldashev.learning.mai.logic.commands.impl.AddEdgeCommand;
import ru.yuldashev.learning.mai.logic.commands.impl.AddNodeCommand;
import ru.yuldashev.learning.mai.logic.commands.impl.LoadCommand;
import ru.yuldashev.learning.mai.logic.commands.impl.SaveCommand;
import ru.yuldashev.learning.mai.logic.graphLogic.GraphEventsHandler;

import java.io.File;
import java.util.LinkedList;
import java.util.ListIterator;

public class ViewController {

    public MenuItem addNodeMenuItem;

    public AnchorPane mainPane;
    public MenuItem undoButton;
    public MenuItem redoButton;

    private Stage primaryStage;
    private GraphEventsHandler graphEventsHandler;

    private final LinkedList<Command> commandsHistory = new LinkedList<>();
    private final ListIterator<Command> commandIterator = commandsHistory.listIterator();

    public void setGraphEventsHandler(GraphEventsHandler graphEventsHandler) {
        this.graphEventsHandler = graphEventsHandler;
    }

    public void handleAddNode() {

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("Set the node name"));
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);

        TextField textField = new TextField();
        dialogVbox.getChildren().add(textField);

        Button button = new Button("Submit");
        EventHandler<ActionEvent> event =
                e -> {

                    Command command = new AddNodeCommand(textField.getText(), graphEventsHandler);
                    try {
                        command.execute();
                        commandIterator.add(command);

                        printHistoryState();

                    } catch (GraphCommandException exception) {
                        handleException(exception);
                    }

                    dialog.close();
                };

        button.setOnAction(event);
        dialogVbox.getChildren().add(button);

        dialog.show();
    }

    private void printHistoryState() {
        System.out.println("||||HISTORY: ");
        for (Command command : commandsHistory) {
            System.out.println(command.toString());
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void handeUndo() {
        if(commandIterator.hasPrevious()){
            commandIterator.previous().undo();
        }
    }

    public void handeRedo() {
        if(commandIterator.hasNext()){
            try {
                commandIterator.next().execute();
            } catch (GraphCommandException e) {
                handleException(e);
            }
        }
    }

    public void handleSaveButton(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file where to save the graph");
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            Command command = new SaveCommand(file, graphEventsHandler);
            try {
                command.execute();
            } catch (GraphCommandException e) {
                handleException(e);
            }
        }
    }

    public void handleOpenButton(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the file with saved Graph");
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            Command command = new LoadCommand(file, graphEventsHandler);
            try {
                command.execute();
            } catch (GraphCommandException e) {
                handleException(e);
            }
        }
    }

    private void handleException(GraphCommandException e){
        System.out.println("Got the error: " + e.getMessage());
    }

    public void handleAddEdgeButton(ActionEvent actionEvent) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("Set the node name"));
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);

        TextField cell1 = new TextField();
        dialogVbox.getChildren().add(cell1);
        TextField cell2 = new TextField();
        dialogVbox.getChildren().add(cell2);
        TextField weight = new TextField();
        dialogVbox.getChildren().add(weight);

        Button button = new Button("Submit");
        EventHandler<ActionEvent> event =
                e -> {

                    Command command = new AddEdgeCommand(cell1.getText(), cell2.getText(), weight.getText(), graphEventsHandler);
                    try {
                        command.execute();
                        commandIterator.add(command);

                        printHistoryState();

                    } catch (GraphCommandException exception) {
                        handleException(exception);
                    }

                    dialog.close();
                };

        button.setOnAction(event);
        dialogVbox.getChildren().add(button);

        dialog.show();
    }
}
