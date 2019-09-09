package utilities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GraphUtils {

	public Line connectNodes(int x1, int y1, int x2, int y2, Color color) {
		Line line = new Line(x1 + 10, y1 + 10, x2 + 10, y2 + 10);
		line.setStroke(color);
		line.setStrokeWidth(5);
		line.setOpacity(0.5);
		return line;
	}

	public Rectangle drawNodes(int x, int y, Color color) {
		Rectangle rec = new Rectangle(x, y, 20, 20);
		rec.setStroke(Color.TRANSPARENT);
		rec.setFill(color);
		rec.setOpacity(0.5);
		return rec;
	}

	public Text nodeNames(int x, int y, String name, Color color) {
		Text text = new Text(x - 15, y, name);
		text.setStroke(color);
		return text;
	}

}