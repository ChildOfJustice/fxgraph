package ru.yuldashev.learning.mai.logic.graphLogic;

import com.google.gson.Gson;
import javafx.application.Platform;
import ru.yuldashev.learning.mai.exceptions.DuplicatedNodeException;
import ru.yuldashev.learning.mai.exceptions.GraphCommandException;
import ru.yuldashev.learning.mai.logic.snapshotPattern.GraphStateSnapshot;
import ru.yuldashev.learning.mai.utils.GraphUtils;
import ru.yuldashev.learning.mai.logic.snapshotPattern.impl.GraphStateManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static ru.yuldashev.learning.mai.Core.mainViewController;

public class GraphEventsHandler {
    private final GraphStateManager graphStateManager = new GraphStateManager();

    public void addNode(String nodeName) throws DuplicatedNodeException {
        graphStateManager.pushNewNode(nodeName);
        Platform.runLater(() -> GraphUtils.updateGraphOnAnchorPane(graphStateManager.getGraph(), mainViewController.mainPane));
    }

    public void removeNode(String nodeName) {
        graphStateManager.removeNode(nodeName);
        Platform.runLater(() -> GraphUtils.updateGraphOnAnchorPane(graphStateManager.getGraph(), mainViewController.mainPane));
    }

    public void addEdge(String cellName1, String cellName2, String edgeWeight) throws GraphCommandException {
        graphStateManager.addEdge(cellName1, cellName2, edgeWeight);
        Platform.runLater(() -> GraphUtils.updateGraphOnAnchorPane(graphStateManager.getGraph(), mainViewController.mainPane));
    }

    public void removeEdge(String weight) {
        graphStateManager.removeEdge(weight);
        Platform.runLater(() -> GraphUtils.updateGraphOnAnchorPane(graphStateManager.getGraph(), mainViewController.mainPane));
    }

    public void save(File saveFile) throws GraphCommandException {
        GraphStateManager.GraphStateSnapshotImpl snapshot = graphStateManager.save();

        Gson gson = new Gson();
        String snapshotJson = gson.toJson(snapshot);

        try {
            FileWriter fileWriter = new FileWriter(saveFile);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(snapshotJson);
            printWriter.close();
        } catch (IOException e) {
            throw new GraphCommandException("Couldn't save to the file with name: " + saveFile.getName() + ", reason: " + e.getMessage());
        }
    }

    public void load(File loadFile) throws GraphCommandException {

        try {
            String loadedGraph = new String(Files.readAllBytes(loadFile.toPath()), StandardCharsets.UTF_8);
            Gson gson = new Gson();
            GraphStateManager.GraphStateSnapshotImpl snapshot = gson.fromJson(loadedGraph, GraphStateManager.GraphStateSnapshotImpl.class);

            graphStateManager.restore(snapshot.graphState);

            Platform.runLater(() -> GraphUtils.updateGraphOnAnchorPane(graphStateManager.getGraph(), mainViewController.mainPane));

        } catch (IOException e) {
            throw new GraphCommandException("Couldn't load from the file with name: " + loadFile.getName() + ", reason: " + e.getMessage());
        }
    }


}
