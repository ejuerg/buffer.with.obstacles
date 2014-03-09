package test.view;

import java.awt.Color;
import java.util.Vector;

import test.util.PolygonGenerator;
import algorithm.structure.NodePointCollection;
import algorithm.structure.ObstacleCollection;
import algorithm.tools.HelperNodeConstructor;
import algorithm.tools.NodePointUtils;
import algorithm.view.VisualPoint;
import algorithm.view.VisualPolygon;
import algorithm.view.Visualizer;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;

public class TestHelperNodes
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
			visPols.add(new VisualPolygon(polys.get(i).getExteriorRing().getCoordinates(), Color.darkGray,
					Color.lightGray, 1f));
		}

		Vector<VisualPoint> visPoints = new Vector<VisualPoint>();

		NodePointCollection nodes = new NodePointCollection();
		for (int i = 0; i < polys.size(); i++)
		{
			nodes.addCollection(NodePointUtils.generateNodePoints(polys.get(i)));
		}

		Coordinate source = new Coordinate(500, 300);

		visPoints.add(new VisualPoint(source, Color.cyan));

		HelperNodeConstructor helpNoder = new HelperNodeConstructor(source, 200);

		NodePointCollection helpNodes = helpNoder.getBorderNodes(nodes);

		for (int i = 0; i < helpNodes.size(); i++)
		{
			visPoints.add(new VisualPoint(helpNodes.get(i).getPreviousNode(), Color.blue));
			visPoints.add(new VisualPoint(helpNodes.get(i).getNextNode(), Color.red));
		}
		for (int i = 0; i < helpNodes.size(); i++)
		{
			visPoints.add(new VisualPoint(helpNodes.get(i), Color.green));
		}

		Polygon buff = (Polygon) new GeometryFactory().createPoint(source).buffer(200d, 100);
		visPols.add(new VisualPolygon(buff.getExteriorRing().getCoordinates(), Color.pink, null, 1f));

		new Visualizer(visPols, visPoints);

	}

}
