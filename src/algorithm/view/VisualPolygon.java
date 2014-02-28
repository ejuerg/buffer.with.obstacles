package algorithm.view;

import java.awt.BasicStroke;
import java.awt.Color;

import com.vividsolutions.jts.geom.Coordinate;

public class VisualPolygon extends VisualGeometry {

	private Coordinate[] coordinates;
	
	private Color fill;
	
	//Constructor
	public VisualPolygon(Coordinate[] coord) {
		this(coord, Color.BLACK, null, 1.0f);
	}

	public VisualPolygon(Coordinate[] coord, Color outline, Color fill, float stroke) {
		this(outline, stroke);
		this.fill = fill;
		this.coordinates = coord;
	}
	
	protected VisualPolygon(Color outline, float stroke) {
		super(outline, stroke);
	}

	//Getter and setter
	public Color getFill() {
		return fill;
	}

	public void setFill(Color fill) {
		this.fill = fill;
	}

	public Color getOutline() {
		return outline;
	}

	public void setOutline(Color outline) {
		this.outline = outline;
	}

	public Coordinate[] getCoordinates() {
		return coordinates;
	}

	public BasicStroke getStroke() {
		return stroke;
	}

	public void setStroke(BasicStroke stroke) {
		this.stroke = stroke;
	}	
}
