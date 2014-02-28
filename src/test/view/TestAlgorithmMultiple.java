package test.view;

import java.awt.Color;
import java.util.Vector;

import test.util.PolygonGenerator;
import algorithm.comparison.BufferWithoutObstacles;
import algorithm.runner.Runner;
import algorithm.structure.ObstacleCollection;
import algorithm.view.VisualPoint;
import algorithm.view.VisualPolygon;
import algorithm.view.Visualizer;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;

public class TestAlgorithmMultiple
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		double radius = 200;

		Vector<Polygon> polys = new Vector<Polygon>();
		ObstacleCollection obs = new ObstacleCollection();

		PolygonGenerator gen = new PolygonGenerator(new Envelope(new Coordinate(0, 0), new Coordinate(1000, 600)));

		for (int i = 0; i < 7; i++)
		{
			Polygon p = gen.createPolygon();
			polys.add(p);
			obs.addObstacle(p);

		}

		Vector<VisualPolygon> visPols = new Vector<VisualPolygon>();
		Vector<VisualPolygon> visPols2 = new Vector<VisualPolygon>();
		for (int i = 0; i < polys.size(); i++)
		{
			visPols.add(new VisualPolygon(polys.get(i).getExteriorRing().getCoordinates(), Color.darkGray,
					Color.lightGray, 1f));
			visPols2.add(new VisualPolygon(polys.get(i).getExteriorRing().getCoordinates(), Color.darkGray,
					Color.lightGray, 1f));
		}

		Coordinate origin = new Coordinate(500, 300);

		int color = 255;
		while (radius >= 40)
		{
			System.out.println("Radius is " + radius);

			Geometry poly = new BufferWithoutObstacles(origin, radius, obs).getResult();
			if (poly != null)
			{
				for (int i = 0; i < poly.getNumGeometries(); i++)
				{
					visPols2.add(new VisualPolygon(((Polygon) poly.getGeometryN(i)).getExteriorRing().getCoordinates(),
							new Color(0, color, color - 50), new Color(0, color, color - 50), 1f));
				}
				System.out.println("FlächeOBSD: " + poly.getArea());
			}

			if (poly != null)
			{
				for (int i = 0; i < poly.getNumGeometries(); i++)
				{
					Polygon p = (Polygon) poly.getGeometryN(i);
					for (int j = 0; j < p.getNumInteriorRing(); j++)
					{
						visPols2.add(new VisualPolygon(p.getInteriorRingN(j).getCoordinates(), new Color(0, color,
								color - 50), Color.LIGHT_GRAY, 1f));
					}
				}
			}

			try
			{

				Runner run = new Runner(origin, obs, radius);

				Geometry result = run.getResult().getResultingGeometry();

				if (result != null)
				{
					for (int i = 0; i < result.getNumGeometries(); i++)
					{
						visPols.add(new VisualPolygon(((Polygon) result.getGeometryN(i)).getExteriorRing()
								.getCoordinates(), new Color(0, color, color - 50), new Color(0, color, color - 50), 1f));

					}
					System.out.println("FlächeWA: " + result.getArea());
				}
				if (result != null)
				{
					for (int i = 0; i < result.getNumGeometries(); i++)
					{
						Polygon p = (Polygon) result.getGeometryN(i);
						for (int j = 0; j < p.getNumInteriorRing(); j++)
						{
							visPols.add(new VisualPolygon(p.getInteriorRingN(j).getCoordinates(), new Color(0, color,
									color - 50), Color.LIGHT_GRAY, 1f));
						}
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			radius -= 40;
			color -= 35;
		}

		Vector<VisualPoint> visPoints = new Vector<VisualPoint>();

		new Visualizer(visPols, visPoints);
		new Visualizer(visPols2, visPoints);
	}
}
