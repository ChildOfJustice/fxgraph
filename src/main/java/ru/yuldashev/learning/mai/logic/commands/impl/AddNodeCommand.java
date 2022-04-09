package ru.yuldashev.learning.mai.logic.commands.impl;

import ru.yuldashev.learning.mai.exceptions.DuplicatedNodeException;
import ru.yuldashev.learning.mai.logic.commands.Command;
import ru.yuldashev.learning.mai.logic.graphLogic.GraphEventsHandler;

public class AddNodeCommand implements Command {

    private final String nodeName;
    private final GraphEventsHandler graphEventsHandler;

    public AddNodeCommand(String nodeName, GraphEventsHandler graphEventsHandler) {
        this.nodeName = nodeName;
        this.graphEventsHandler = graphEventsHandler;
    }

    @Override
    public void execute() throws DuplicatedNodeException {
        graphEventsHandler.addNode(nodeName);
    }

    @Override
    public void undo() {
        graphEventsHandler.removeNode(nodeName);
    }
}
