package ru.yuldashev.learning.mai.logic.snapshotPattern.impl;

import com.fxgraph.cells.RectangleCell;
import ru.yuldashev.learning.mai.logic.snapshotPattern.impl.testObjects.EdgeInfo;

import java.util.List;
import java.util.Map;

public class GraphState {
    private final Map<String, RectangleCell> cells;
    private final List<EdgeInfo> edges;

    public GraphState(Map<String, RectangleCell> cells, List<EdgeInfo> edges) {
        this.cells = cells;
        this.edges = edges;
    }

    public Map<String, RectangleCell> getCells() {
        return cells;
    }

    public List<EdgeInfo> getEdges() {
        return edges;
    }
}
