package ru.yuldashev.learning.mai.logic.snapshotPattern.impl;

import com.fxgraph.cells.RectangleCell;
import com.fxgraph.edges.Edge;
import com.fxgraph.graph.Graph;
import com.fxgraph.graph.ICell;
import com.fxgraph.graph.Model;
import ru.yuldashev.learning.mai.exceptions.DuplicatedNodeException;
import ru.yuldashev.learning.mai.exceptions.GraphCommandException;
import ru.yuldashev.learning.mai.logic.snapshotPattern.impl.testObjects.EdgeInfo;
import ru.yuldashev.learning.mai.utils.GraphUtils;
import ru.yuldashev.learning.mai.logic.snapshotPattern.GraphStateSnapshot;
import ru.yuldashev.learning.mai.logic.snapshotPattern.Originator;

import java.util.*;

public class GraphStateManager implements Originator {

    private Graph graph = GraphUtils.createGraph();
    private Map<String, ICell> cells = new HashMap<>();
    private ArrayList<Edge> edges = new ArrayList<>();
    private ArrayList<EdgeInfo> edgesInfo = new ArrayList<>();

    public Graph getGraph(){
        return graph;
    }

    public void pushNewNode(String nodeName) throws DuplicatedNodeException {
        ICell existingCell = cells.get(nodeName);
        if(existingCell == null){
            ICell cell = new RectangleCell(nodeName);
            cells.put(cell.getCellName(), cell);
            Model model = graph.getModel();

            model.addCell(cell);

            graph.update();
        } else {
            throw new DuplicatedNodeException("Name " + nodeName + " is already defined");
        }
    }

    public void removeNode(String nodeName) {
        ICell removedCell = cells.remove(nodeName);
        if(removedCell == null){
            System.err.println("NO SUCH NODE!");
        }
        Model model = graph.getModel();
        model.removeCell(removedCell);

        graph.update();
    }

    @Override
    public GraphStateSnapshotImpl save() {

        HashMap<String, RectangleCell> copiedNonAbstractCells = new HashMap<>();
        cells.forEach((cellName, cell) -> {
            System.out.println("Adding cell to snapshot: " + cellName);
            RectangleCell rectangleCell = new RectangleCell(cell);
            copiedNonAbstractCells.put(cellName, rectangleCell);
        });

        ArrayList<EdgeInfo> copiedEdges = new ArrayList<>(edgesInfo);

        GraphState state = new GraphState(
                Collections.unmodifiableMap(copiedNonAbstractCells),
                Collections.unmodifiableList(copiedEdges)
//                graph.copy()
        );
        
        return new GraphStateSnapshotImpl(
                state,
                "Last action description"
        );
    }

    private void setState(GraphState state) throws GraphCommandException {
        cells = new HashMap<>(state.getCells());
        edgesInfo = new ArrayList<>(state.getEdges());
        graph = GraphUtils.createGraph();
        Model model = graph.getModel();
        state.getCells().forEach((cellName, cell) -> {
            model.addCell(cell);
        });
        for (EdgeInfo edgeInfo : state.getEdges()) {
            ICell cell1 = cells.get(edgeInfo.getCellName1());
            ICell cell2 = cells.get(edgeInfo.getCellName2());

            if(cell1 == null){
                throw new GraphCommandException("No such cell: " + edgeInfo.getCellName1());
            }
            if(cell2 == null){
                throw new GraphCommandException("No such cell: " + edgeInfo.getCellName2());
            }

            Edge edge = new Edge(cell1, cell2, true, edgeInfo.getWeight());

            model.addEdge(edge);
        }
        graph.update();
//        graph = state.getGraph().copy();
    }

    public void restore(GraphState graphState) throws GraphCommandException {
        setState(graphState);
    }

    public class GraphStateSnapshotImpl implements GraphStateSnapshot {

        public final GraphState graphState;
        private final String description;

        public GraphStateSnapshotImpl(GraphState graphState, String description) {

            this.graphState = graphState;
            this.description = description;
        }


        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public void restore() throws GraphCommandException {
            setState(graphState);
        }
    }

    public void addEdge(String cellName1, String cellName2, String edgeWeight) throws GraphCommandException {
        ICell cell1 = cells.get(cellName1);
        ICell cell2 = cells.get(cellName2);

        if(cell1 == null){
            throw new GraphCommandException("No such cell: " + cellName1);
        }
        if(cell2 == null){
            throw new GraphCommandException("No such cell: " + cellName2);
        }

        Edge edge = new Edge(cell1, cell2, true, edgeWeight);

        Model model = graph.getModel();
        model.addEdge(edge);

        edges.add(edge);
        edgesInfo.add(new EdgeInfo(cellName1, cellName2, edgeWeight));

        graph.update();
    }

    public void removeEdge(String edgeWeight) {
        ListIterator<Edge> iterator = edges.listIterator(edges.size());
        ListIterator<EdgeInfo> iterator2 = edgesInfo.listIterator(edges.size());
        while(iterator.hasPrevious()){
            Edge edge = iterator.previous();
            iterator2.previous();
            if(edge.textProperty().get().equals(edgeWeight)){
                Model model = graph.getModel();
                model.removeEdge(edge);
                iterator.remove();
                iterator2.remove();

                graph.update();

                break;
            }
        }
    }
}
