package ru.yuldashev.learning.mai.logic.commands.impl;

import ru.yuldashev.learning.mai.exceptions.GraphCommandException;
import ru.yuldashev.learning.mai.logic.commands.Command;
import ru.yuldashev.learning.mai.logic.graphLogic.GraphEventsHandler;

import java.io.File;

public class LoadCommand implements Command {

    private final File loadFile;
    private final GraphEventsHandler graphEventsHandler;

    public LoadCommand(File file, GraphEventsHandler graphEventsHandler) {
        this.loadFile = file;
        this.graphEventsHandler = graphEventsHandler;
    }

    @Override
    public void execute() throws GraphCommandException {
        graphEventsHandler.load(loadFile);
    }

    @Override
    public void undo() {
        System.out.println("Cannot undo the Graph loading operation.");
    }
}