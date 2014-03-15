package test.view;

import java.awt.Color;
import java.util.Random;
import java.util.Vector;

import test.util.PolygonGenerator;
import algorithm.runner.Runner;
import algorithm.structure.ObstacleCollection;
import algorithm.view.VisualPoint;
import algorithm.view.VisualPolygon;
import algorithm.view.Visualizer;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;

public class TestAlgorithm
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Random rand = new Random();
		double radius = rand.nextInt(200) + 100;
		System.out.println("Radius is " + radius);

		Vector<Polygon> polys = new Vector<Polygon>();
		ObstacleCollection obs = new ObstacleCollection();

		PolygonGenerator gen = new PolygonGenerator(new Envelope(new Coordinate(0, 0), new Coordinate(1000, 600)));

		for (int i = 0; i < 20; i++)
		{
			Polygon p = gen.createPolygon();
			polys.add(p);
			obs.addObstacle(p);

		}

		Vector<VisualPolygon> visPols = new Vector<VisualPolygon>();
		for (int i = 0; i < polys.size(); i++)
		{
			visPols.add(new VisualPolygon(polys.get(i).getExteriorRing().getCoordinates(), Color.darkGray,
					Color.lightGray, 1f));
		}

		Coordinate origin = new Coordinate(500, 300);

		// Coordinate origin = PolygonToNodePoints.run(
		// obs.getAllObstacles().get(rand.nextInt(20))).get(rand.nextInt(8));

		// Coordinate origin2 =
		// PolygonToNodePoints.run(obs.getAllObstacles().get(0)).get(1);;

		try
		{
			Runner run = new Runner(origin, obs, radius);

			// Runner run2 = new Runner(origin2, obs, radius);

			// run.getResult().addGeometry(run2.getResult().getPolygon());
			// Geometry result = run.getResult().getResultingGeometry();
			Geometry result = run.getResult().getResultingGeometry();

			Polygon buff = (Polygon) new GeometryFactory().createPoint(origin).buffer(radius, 100);
			visPols.add(new VisualPolygon(buff.getExteriorRing().getCoordinates(), Color.pink, null, 1f));

			if (result != null)
			{
				for (int i = 0; i < result.getNumGeometries(); i++)
				{
					visPols.add(new VisualPolygon(
							((Polygon) result.getGeometryN(i)).getExteriorRing().getCoordinates(), Color.green,
							Color.yellow, 1f));
				}
			}
			if (result != null)
			{
				for (int i = 0; i < result.getNumGeometries(); i++)
				{
					Polygon p = (Polygon) result.getGeometryN(i);
					System.out.println("Fläche: " + p.getArea());
					for (int j = 0; j < p.getNumInteriorRing(); j++)
					{
						visPols.add(new VisualPolygon(p.getInteriorRingN(j).getCoordinates(), Color.black,
								Color.LIGHT_GRAY, 1f));
					}

				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		Vector<VisualPoint> visPoints = new Vector<VisualPoint>();

		new Visualizer(visPols, visPoints);
		//
		// NodePointCollection nodes = run.getVisibleNodes();
		//
		// for (int i = 0; i < nodes.size(); i++) {
		// visPoints.add(new VisualPoint(nodes.get(i), Color.green));
		// }
		//
		// NodePointCollection shadows = run.getShadowNodes();
		//
		// for (int i = 0; i < shadows.size(); i++) {
		// visPoints.add(new VisualPoint(shadows.get(i), Color.magenta));
		// }

	}

}
