package algorithm.structure;

import java.math.BigDecimal;
import java.util.Vector;

import algorithm.runner.Runner;

import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;

public class WalkAroundBuffer
{
	private GeometryFactory fact;

	private Vector<Polygon> polys;

	public WalkAroundBuffer()
	{
		fact = Runner.FACTORY;
		polys = new Vector<Polygon>();
	}

	// 400 segments for the full circle
	private double angleStepSize = 2 * Math.PI / 400;

	public void addTriangle(Coordinate a, Coordinate b, Coordinate c)
	{
		if (a.equals(b) || a.equals(c) || b.equals(c))
		{
			return;
		}
		else
		{
			Coordinate[] coords = new Coordinate[] { a, b, c, a };
			add(coords);
		}
	}

	private void add(Coordinate[] coords)
	{
		LinearRing shell = fact.createLinearRing(coords);
		Polygon newPoly = fact.createPolygon(shell, null);

		polys.add(newPoly);
	}

	/**
	 * Adds a segment of a circle to this WalkAroundBuffer The has the angle
	 * between two given coordinates.
	 * 
	 * @param origin
	 * @param start
	 * @param end
	 * @param radius
	 */
	public void addArcSegment(Coordinate origin, Coordinate start, Coordinate end, double radius)
	{
		if (!(start.equals2D(origin) && start.equals2D(end) && origin.equals2D(end)))
		{

			double angleFrom = Angle.angle(origin, start);
			double angleTo = Angle.angle(origin, end);

			// when the segment crosses
			if (angleFrom > angleTo)
			{
				angleTo += (2 * Math.PI);
			}

			Vector<Coordinate> circlePoints = new Vector<Coordinate>();

			circlePoints.add(origin);

			double angle = angleFrom;

			while (angle < angleTo)
			{
				circlePoints.add(this.getPointOnCircle(origin, radius, angle));
				angle += angleStepSize;
			}
			circlePoints.add(this.getPointOnCircle(origin, radius, angleTo));
			circlePoints.add(origin);

			Coordinate[] coord = new Coordinate[circlePoints.size()];
			for (int i = 0; i < circlePoints.size(); i++)
			{
				coord[i] = circlePoints.get(i);
			}

			this.add(coord);
		}
	}

	public Vector<Polygon> getPolygon()
	{
		return polys;
	}

	public Geometry getResultingGeometry()
	{
		Geometry[] polyArray = new Geometry[polys.size()];
		for (int i = 0; i < polys.size(); i++)
		{
			polyArray[i] = polys.get(i);
		}

		// return CascadedPolygonUnion.union(polys);
		return fact.createGeometryCollection(polyArray).buffer(0);
	}

	private Coordinate getPointOnCircle(Coordinate origin, double radius, double angle)
	{
		double x = origin.x + radius * Math.cos(angle);
		double y = origin.y + radius * Math.sin(angle);

		int decimalPlace = 5;
		BigDecimal bdx = new BigDecimal(x);
		bdx = bdx.setScale(decimalPlace, BigDecimal.ROUND_UP);
		x = bdx.doubleValue();
		BigDecimal bdy = new BigDecimal(y);
		bdy = bdy.setScale(decimalPlace, BigDecimal.ROUND_UP);
		y = bdy.doubleValue();

		return new Coordinate(x, y);
	}

	public void addGeometry(Vector<Polygon> other)
	{
		if (other != null)
		{
			for (int i = 0; i < other.size(); i++)
			{
				polys.add(other.get(i));
			}
		}
	}

	public void addCircle(Coordinate origin, double radius)
	{
		Vector<Coordinate> circlePoints = new Vector<Coordinate>();

		double angle = -Math.PI;

		while (angle < Math.PI)
		{
			circlePoints.add(this.getPointOnCircle(origin, radius, angle));
			angle += angleStepSize;
		}
		circlePoints.add(this.getPointOnCircle(origin, radius, Math.PI));

		Coordinate[] coord = new Coordinate[circlePoints.size()];
		for (int i = 0; i < circlePoints.size(); i++)
		{
			coord[i] = circlePoints.get(i);
		}

		this.add(coord);

	}
}
