package test.view;

import java.awt.Color;
import java.util.Random;
import java.util.Vector;

import algorithm.structure.NodePoint;
import algorithm.structure.NodePointCollection;
import algorithm.view.VisualPoint;
import algorithm.view.VisualPolygon;
import algorithm.view.Visualizer;

import com.vividsolutions.jts.geom.Coordinate;

public class TestNodeSort
{
	public static void main(String[] args)
	{
		int numberPoints = 150;
		int topX = 1000;
		int topY = 600;
		Random rand = new Random();

		double oX = rand.nextDouble() * topX;
		double oY = rand.nextDouble() * topY;

		Coordinate origin = new Coordinate(oX, oY);

		NodePointCollection nodes = new NodePointCollection();

		for (int i = 0; i < numberPoints; i++)
		{
			double x = rand.nextDouble() * topX;
			double y = rand.nextDouble() * topY;
			nodes.addPoint(new NodePoint(x, y));
		}

		Vector<VisualPoint> visPoints = new Vector<VisualPoint>();

		// test angle sorting
		nodes = nodes.sortByDistance(origin);
		makeVisPoints(nodes, visPoints);
		visPoints.add(new VisualPoint(origin, Color.red));
		Vector<VisualPolygon> polygons = new Vector<VisualPolygon>();
		new Visualizer(polygons, visPoints);

		// test distance sort
		// nodes = nodes.sortByAngle(origin);
		nodes = nodes.getNodesBetweenAngles(origin, 3, 2);
		Vector<VisualPoint> visPoints2 = new Vector<VisualPoint>();
		makeVisPoints(nodes, visPoints2);
		visPoints2.add(new VisualPoint(origin, Color.red));
		new Visualizer(polygons, visPoints2);

		NodePointCollection nodeSubset = nodes.getNodesInCircle(origin, 220d);
		Vector<VisualPoint> visPoints3 = new Vector<VisualPoint>();
		makeVisPoints(nodeSubset, visPoints3);
		visPoints3.add(new VisualPoint(origin, Color.red));
		// new Visualizer(polygons, visPoints3);

	}

	private static void makeVisPoints(NodePointCollection nodes, Vector<VisualPoint> visP)
	{
		for (int i = 0; i < nodes.size(); i++)
		{
			NodePoint node = nodes.get(i);
			Coordinate coord = new Coordinate(node.x, node.y);
			visP.add(new VisualPoint(coord, new Color(100, 255 - (255 / nodes.size() * i), 255 / nodes.size() * i)));
		}
	}
}
