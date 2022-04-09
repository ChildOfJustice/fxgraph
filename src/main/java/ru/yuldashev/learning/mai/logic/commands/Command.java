package ru.yuldashev.learning.mai.logic.commands;

import ru.yuldashev.learning.mai.exceptions.GraphCommandException;

public interface Command {
    void execute() throws GraphCommandException;
    void undo();
}
