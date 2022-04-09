package ru.yuldashev.learning.mai.logic.commands.impl;

import ru.yuldashev.learning.mai.exceptions.GraphCommandException;
import ru.yuldashev.learning.mai.logic.commands.Command;
import ru.yuldashev.learning.mai.logic.graphLogic.GraphEventsHandler;

public class AddEdgeCommand implements Command {

    private final String cellName1;
    private final String cellName2;
    private final String weight;
    private final GraphEventsHandler graphEventsHandler;

    public AddEdgeCommand(String cellName1, String cellName2, String edgeWeight, GraphEventsHandler graphEventsHandler) {
        this.weight = edgeWeight;
        this.cellName1 = cellName1;
        this.cellName2 = cellName2;
        this.graphEventsHandler = graphEventsHandler;
    }

    @Override
    public void execute() throws GraphCommandException {
        graphEventsHandler.addEdge(cellName1, cellName2, weight);
    }

    @Override
    public void undo() {
        graphEventsHandler.removeEdge(weight);
    }
}
