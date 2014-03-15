package test.util;

import java.util.Random;
import java.util.Vector;

import algorithm.runner.Runner;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequenceFactory;

public class PolygonGenerator
{

	double minX;
	double maxX;
	double minY;
	double maxY;
	double extentX;
	double extentY;
	Random rand;
	Vector<Polygon> polys;

	GeometryFactory fact = Runner.FACTORY;

	public PolygonGenerator(Envelope env)
	{
		minX = env.getMinX();
		maxX = env.getMaxX();
		minY = env.getMinY();
		maxY = env.getMaxY();
		extentX = maxX - minX;
		extentY = maxY - minY;
		rand = new Random();
		polys = new Vector<Polygon>();
	}

	public Polygon createPolygon()
	{

		// extent of polygon: 1/1 to 1/8 of extent of area
		double width = extentX / (rand.nextDouble() * 7 + 1);
		double height = extentY / (rand.nextDouble() * 7 + 1);

		Coordinate[] coords = createCoordinates(width, height);

		LinearRing r = new LinearRing(CoordinateArraySequenceFactory.instance().create(coords), fact);

		if (r.isValid())
		{
			// create polygon
			Polygon result = fact.createPolygon(r, null);

			// test for intersection with already created polygons
			boolean noIntersection = true;
			int i = 0;
			while (noIntersection && i < polys.size())
			{
				if (result.intersects(polys.get(i)))
				{
					noIntersection = false;
				}
				i++;
			}
			if (noIntersection)
			{
				polys.add(result);
				return result;
			}
			else
			{
				return createPolygon();
			}
		}
		else
		{
			return createPolygon();
		}
	}

	private Coordinate[] createCoordinates(double width, double height)
	{
		// center point to create polygon around
		Coordinate centre = new Coordinate(rand.nextDouble() * (extentX - width) + minX + width / 2, rand.nextDouble()
				* (extentY - height) + minY + height / 2);

		Coordinate[] coords = new Coordinate[9];

		// Create 8 points with a particular distribution
		coords[0] = new Coordinate(rand.nextDouble() * width + centre.x - width / 2, rand.nextDouble() * height / 4
				+ centre.y + height / 4);
		coords[1] = new Coordinate(rand.nextDouble() * width / 2 + centre.x, rand.nextDouble() * height / 2 + centre.y);
		coords[2] = new Coordinate(rand.nextDouble() * width / 4 + centre.x + width / 4, rand.nextDouble() * height
				+ centre.y - height / 2);
		coords[3] = new Coordinate(rand.nextDouble() * width / 2 + centre.x, rand.nextDouble() * height / 2 + centre.y
				- height / 2);
		coords[4] = new Coordinate(rand.nextDouble() * width + centre.x - width / 2, rand.nextDouble() * height / 4
				+ centre.y - height / 2);
		coords[5] = new Coordinate(rand.nextDouble() * width / 2 + centre.x - width / 2, rand.nextDouble() * height / 2
				+ centre.y - height / 2);
		coords[6] = new Coordinate(rand.nextDouble() * width / 4 + centre.x - width / 2, rand.nextDouble() * height
				+ centre.y - height / 2);
		coords[7] = new Coordinate(rand.nextDouble() * width / 2 + centre.x - width / 2, rand.nextDouble() * height / 2
				+ centre.y - height / 4);

		// Close ring
		coords[coords.length - 1] = coords[0];

		return coords;
	}
}
