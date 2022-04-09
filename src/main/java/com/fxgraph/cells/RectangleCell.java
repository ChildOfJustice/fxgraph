package com.fxgraph.cells;

import com.fxgraph.graph.Graph;
import com.fxgraph.graph.ICell;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RectangleCell extends AbstractCell {

	public RectangleCell(String cellName) {
		super(cellName);
	}

	public RectangleCell(ICell cell) {
		super(cell.getCellName());
	}

	@Override
	public Region getGraphic(Graph graph) {
		final Rectangle view = new Rectangle(50, 50);

		view.setStroke(Color.DODGERBLUE);
		view.setFill(Color.DODGERBLUE);

		final Pane pane = new Pane(view);


		Text t = new Text(20, 0, getCellName());
		t.setFont(Font.font ("Verdana", 20));
		t.setFill(Color.RED);
		pane.getChildren().add(t);


		pane.setPrefSize(50, 50);
		view.widthProperty().bind(pane.prefWidthProperty());
		view.heightProperty().bind(pane.prefHeightProperty());
//		CellGestures.makeResizable(graph, pane);

		return pane;
	}

}
