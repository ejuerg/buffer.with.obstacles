package algorithm.tools;

import algorithm.runner.Runner;
import algorithm.structure.NodePoint;
import algorithm.structure.NodePointCollection;
import algorithm.structure.ObstacleCollection;

import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Polygon;

public class ShadowNodeConstructor
{

	private Coordinate origin;
	private NodePointCollection nodes;
	private double radius;
	private static GeometryFactory fact;

	private double epsilon = 1d;

	public ShadowNodeConstructor(Coordinate origin, NodePointCollection nodes, double radius)
	{
		this.nodes = new NodePointCollection();
		this.nodes.addCollection(nodes);
		this.origin = origin;
		this.radius = radius;
		if (fact == null)
		{
			fact = Runner.FACTORY;
		}
	}

	private NodePoint getShadowNode(NodePoint node, VisibilityTester vis)
	{
		if (node.isShadow())
		{
			return null;
		}

		double angle = Angle.angle(origin, node);

		// projection onto circle
		double circleX = origin.x + radius * Math.cos(angle);
		double circleY = origin.y + radius * Math.sin(angle);

		// ray from node to projecton on circle
		LineString ray = fact.createLineString(new Coordinate[] { node, new Coordinate(circleX, circleY) });

		// The closest intersection is the result
		Coordinate closestInters = new Coordinate(0, 0);

		double minDistance = Double.MAX_VALUE;
		int minIndex = -1;

		// find closest intersection by testing against all segments
		for (int i = 0; i < nodes.size(); i++)
		{
			LineString segmentNext = nodes.get(i).getLineToNextNode();
			if (segmentNext.intersects(ray))
			{
				Coordinate intersection = segmentNext.intersection(ray).getCoordinate();
				if (!node.equals2D(intersection))
				{
					double distance = intersection.distance(origin);
					if (distance < minDistance)
					{
						closestInters = intersection;
						minDistance = distance;
						minIndex = i;
					}
				}
			}
		}

		NodePoint result = null;
		NodePoint prev = null;
		NodePoint next = null;
		Polygon polygon = null;
		if (minIndex == -1)
		{
			// noIntersection found
			result = new NodePoint(circleX, circleY);
			prev = result;
			next = result;
			result.setBorderNode(true);
		} else
		{
			next = nodes.get(minIndex).getNextNode();
			prev = nodes.get(minIndex);
			polygon = node.getPolygon();
			result = new NodePoint(closestInters.x, closestInters.y);
		}

		LineString testLine = fact.createLineString(new Coordinate[] { node, result });

		if (testLine.intersects(node.getPolygon()))
		{
			Coordinate centroid = testLine.intersection(node.getPolygon()).getGeometryN(0).getCentroid()
					.getCoordinate();

			if ((node.distance(centroid) < epsilon) || (result.distance(centroid) < epsilon))
			{
				if (minIndex > -1)
				{
					nodes.addPoint(result);
				}
				node.setShadowNode(result);
				result.setNextNode(next);
				result.setPreviousNode(prev);
				result.setPolygon(polygon);
				result.setShadow(true);
				return result;
			}
		} else
		{
			node.setShadowNode(result);
			result.setNextNode(next);
			result.setPreviousNode(prev);
			result.setPolygon(polygon);
			result.setShadow(true);
			return result;
		}
		return null;
	}

	public NodePointCollection getAllShadowNodes(ObstacleCollection obs)
	{
		VisibilityTester vis = new VisibilityTester(obs, origin);

		NodePointCollection result = new NodePointCollection();

		for (int i = 0; i < nodes.size(); i++)
		{
			NodePoint current = nodes.get(i);
			NodePoint next = current.getNextNode();
			NodePoint prev = current.getPreviousNode();
			if (vis.isVisible(current) && (!vis.isVisible(next) || !vis.isVisible(prev)))
			{
				NodePoint inters = this.getShadowNode(current, vis);

				if (inters != null)
				{
					result.addPoint(inters);
				}
			}
		}
		return result;
	}
}
