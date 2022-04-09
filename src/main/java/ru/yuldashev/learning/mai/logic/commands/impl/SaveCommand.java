package ru.yuldashev.learning.mai.logic.commands.impl;

import ru.yuldashev.learning.mai.exceptions.DuplicatedNodeException;
import ru.yuldashev.learning.mai.exceptions.GraphCommandException;
import ru.yuldashev.learning.mai.logic.commands.Command;
import ru.yuldashev.learning.mai.logic.graphLogic.GraphEventsHandler;

import java.io.File;

public class SaveCommand implements Command {

    private final File saveFile;
    private final GraphEventsHandler graphEventsHandler;

    public SaveCommand(File file, GraphEventsHandler graphEventsHandler) {
        this.saveFile = file;
        this.graphEventsHandler = graphEventsHandler;
    }

    @Override
    public void execute() throws GraphCommandException {
        graphEventsHandler.save(saveFile);
    }

    @Override
    public void undo() {
        System.out.println("Cannot undo the Graph saving operation.");
    }
}