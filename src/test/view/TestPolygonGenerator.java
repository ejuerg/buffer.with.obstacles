package test.view;

import java.awt.Color;
import java.util.Vector;

import test.util.PolygonGenerator;

import algorithm.structure.NodePoint;
import algorithm.structure.NodePointCollection;
import algorithm.structure.ObstacleCollection;
import algorithm.tools.NodePointUtils;
import algorithm.tools.VisibilityTester;
import algorithm.view.VisualPoint;
import algorithm.view.VisualPolygon;
import algorithm.view.Visualizer;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Polygon;

public class TestPolygonGenerator
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
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
			visPols.add(new VisualPolygon(polys.get(i).getExteriorRing().getCoordinates(), Color.darkGray, null, 1f));
		}

		Vector<VisualPoint> visPoints = new Vector<VisualPoint>();

		NodePointCollection nodes = new NodePointCollection();
		for (int i = 0; i < polys.size(); i++)
		{
			nodes.addCollection(NodePointUtils.generateNodePoints(polys.get(i)));
		}

		Coordinate source = new Coordinate(500, 300);

		VisibilityTester visTest = new VisibilityTester(obs, source);

		visPoints.add(new VisualPoint(source, Color.red));

		for (int i = 0; i < nodes.size(); i++)
		{
			NodePoint p = nodes.get(i);
			Color c;
			if (visTest.isVisible(p))
			{
				c = Color.green;
			} else
			{
				c = Color.blue;
			}
			visPoints.add(new VisualPoint(p, c));
		}

		new Visualizer(visPols, visPoints);
	}
}
