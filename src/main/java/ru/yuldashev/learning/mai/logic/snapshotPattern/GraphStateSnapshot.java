package ru.yuldashev.learning.mai.logic.snapshotPattern;

import ru.yuldashev.learning.mai.exceptions.GraphCommandException;

public interface GraphStateSnapshot {
    String getDescription();
    void restore() throws GraphCommandException;
}
