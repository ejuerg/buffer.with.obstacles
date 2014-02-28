package test.view;

import java.awt.Color;
import java.util.Vector;

import test.util.PolygonGenerator;
import algorithm.structure.NodePoint;
import algorithm.structure.NodePointCollection;
import algorithm.structure.ObstacleCollection;
import algorithm.tools.HelperNodeConstructor;
import algorithm.tools.PolygonToNodePoints;
import algorithm.tools.ShadowNodeConstructor;
import algorithm.tools.VisibilityTester;
import algorithm.view.VisualPoint;
import algorithm.view.VisualPolygon;
import algorithm.view.Visualizer;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;

public class TestShadowNode
{

	static double radius = 200d;

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
			nodes.addCollection(PolygonToNodePoints.run(polys.get(i)));
		}

		Coordinate source = new Coordinate(500, 300);

		visPoints.add(new VisualPoint(source, Color.cyan));

		HelperNodeConstructor helpNoder = new HelperNodeConstructor(source, radius);

		NodePointCollection helpNodes = helpNoder.getBorderNodes(nodes);

		nodes = nodes.getNodesInCircle(source, radius);
		nodes.addCollection(helpNodes);

		ShadowNodeConstructor shadowNoder = new ShadowNodeConstructor(source, nodes, radius);

		NodePointCollection shadowNodes = shadowNoder.getAllShadowNodes(obs);
		nodes.addCollection(shadowNodes);

		/*
		 * for (int i = 0; i < helpNodes.size(); i++) { visPoints.add(new
		 * VisualPoint(helpNodes.get(i).getPrevNode(), Color.blue));
		 * visPoints.add(new VisualPoint(helpNodes.get(i).getNextNode(),
		 * Color.red)); }
		 */
		for (int i = 0; i < helpNodes.size(); i++)
		{
			visPoints.add(new VisualPoint(helpNodes.get(i), Color.green));
		}

		/*
		 * for (int i = 0; i < shadowNodes.size(); i++) { visPoints.add(new
		 * VisualPoint(shadowNodes.get(i).getPrevNode(), Color.blue));
		 * visPoints.add(new VisualPoint(shadowNodes.get(i).getNextNode(),
		 * Color.red)); }
		 */
		for (int i = 0; i < shadowNodes.size(); i++)
		{
			visPoints.add(new VisualPoint(shadowNodes.get(i), Color.magenta));
		}
		System.out.println("# shadow nodes: " + shadowNodes.size());

		Polygon buff = (Polygon) new GeometryFactory().createPoint(source).buffer(radius, 100);
		visPols.add(new VisualPolygon(buff.getExteriorRing().getCoordinates(), Color.pink, null, 1f));

		new Visualizer(visPols, visPoints);

		// Calculate visibility
		Vector<VisualPoint> visPoints2 = new Vector<VisualPoint>();
		visPoints2.add(new VisualPoint(source, Color.cyan));

		nodes.addCollection(shadowNodes);
		nodes.getNodesInCircle(source, radius);

		VisibilityTester visTest = new VisibilityTester(obs, source);
		for (int i = 0; i < nodes.size(); i++)
		{
			Color c = Color.red;
			NodePoint n = nodes.get(i);
			if (visTest.isVisible(n))
			{
				c = Color.green;
			}
			visPoints2.add(new VisualPoint(n, c));
		}

		// new Visualizer(visPols, visPoints2);

	}

}
