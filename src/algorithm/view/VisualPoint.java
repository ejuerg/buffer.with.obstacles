package algorithm.view;

import java.awt.Color;

import com.vividsolutions.jts.geom.Coordinate;

public class VisualPoint extends VisualGeometry
{

	private Coordinate point;

	protected VisualPoint(Color outline, float stroke)
	{
		super(outline, stroke);
	}

	public VisualPoint(Coordinate coord)
	{
		this(Color.black, 2.5f);
		this.point = coord;
	}

	public VisualPoint(Coordinate coord, Color outline)
	{
		this(outline, 2.5f);
		this.point = coord;
	}

	public Coordinate getPoint()
	{
		return point;
	}
}
