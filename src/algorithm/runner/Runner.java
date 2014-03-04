package algorithm.runner;

import java.util.List;

import algorithm.structure.NodePoint;
import algorithm.structure.NodePointCollection;
import algorithm.structure.ObstacleCollection;
import algorithm.structure.WalkAroundBuffer;
import algorithm.tools.HelperNodeConstructor;
import algorithm.tools.PolygonToNodePoints;
import algorithm.tools.ShadowNodeConstructor;
import algorithm.tools.VisibilityTester;

import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class Runner
{

	NodePointCollection nodes;
	ObstacleCollection obstacles;
	Coordinate origin;
	double radius;

	private double epsilon = 1.5;

	NodePointCollection shadowNodes;

	WalkAroundBuffer result;
	private FinishedNodesList finished;

	public static final GeometryFactory FACTORY = new GeometryFactory();

	public Runner(Coordinate origin, ObstacleCollection obs, double radius) throws PointInObstacleException
	{
		this(origin, obs, radius, new FinishedNodesList());
	}

	protected Runner(Coordinate origin, ObstacleCollection obs, double radius, FinishedNodesList finished)
			throws PointInObstacleException
	{

		System.out.println("constructing visibility for: " + origin + ", radius: " + radius);

		this.finished = finished;
		this.result = new WalkAroundBuffer();
		this.nodes = new NodePointCollection();
		this.origin = origin;
		this.radius = radius;
		this.obstacles = obs.queryObstacles(new Envelope(origin.x - radius, origin.x + radius, origin.y - radius, origin.y
				+ radius));

		if (!obs.pointInObstacle(origin))
		{
			this.getNodePoints();

			HelperNodeConstructor helpNoder = new HelperNodeConstructor(origin, radius);

			NodePointCollection helperNodes = helpNoder.getBorderNodes(nodes);

			nodes = nodes.getNodesInCircle(origin, radius + epsilon);

			nodes.removePoint(origin);

			nodes.addCollection(helperNodes);

			ShadowNodeConstructor shadowNoder = new ShadowNodeConstructor(origin, nodes, radius);

			shadowNodes = shadowNoder.getAllShadowNodes(obs);

			constructAlt();

			constructArcSegments();

			recursion();
		} else
		{
			throw new PointInObstacleException();
		}
	}

	public WalkAroundBuffer getResult()
	{
		return result;
	}

	public NodePointCollection getNodes()
	{
		return nodes;
	}

	public NodePointCollection getShadowNodes()
	{
		return shadowNodes;
	}

	private void getNodePoints()
	{
		List<Polygon> polyList = obstacles.getAllObstacles();

		for (int i = 0; i < polyList.size(); i++)
		{
			nodes.addCollection(PolygonToNodePoints.run(polyList.get(i)));
		}
	}

	public NodePointCollection getVisibleNodes()
	{
		NodePointCollection visibleNodes = new NodePointCollection();
		VisibilityTester visTest = new VisibilityTester(obstacles, origin);
		for (int i = 0; i < nodes.size(); i++)
		{
			if (visTest.isVisible(nodes.get(i)))
			{
				visibleNodes.addPoint(nodes.get(i));
			}
		}
		return visibleNodes;
	}

	private void constructAlt()
	{
		NodePointCollection allNodes = new NodePointCollection();
		allNodes.addCollection(nodes);
		allNodes.addCollection(shadowNodes);
		VisibilityTester vis = new VisibilityTester(obstacles, origin);
		for (int i = 0; i < allNodes.size(); i++)
		{
			NodePoint current = allNodes.get(i);
			NodePoint next = current.getNextNode();
			NodePoint prev = current.getPrevNode();
			if (vis.isVisible(next) && current.isShadow())
			{
				result.addTriangle(origin, next, current);
				// System.out.println("next & shadow");
			}
			if (vis.isVisible(prev) && current.isShadow())
			{
				result.addTriangle(origin, prev, current);
				// System.out.println("prev & shadow");
			}
			if (vis.isVisible(current) && vis.isVisible(next) && !current.equals(next)
					&& next.distance(origin) <= radius + epsilon)
			{
				result.addTriangle(origin, next, current);
				// System.out.println("next");
			}
			if (vis.isVisible(current) && vis.isVisible(prev) && !current.equals(prev)
					&& prev.distance(origin) <= radius + epsilon)
			{
				result.addTriangle(origin, prev, current);
				// System.out.println("prev");
			}
		}
		// constructArcSegments();
	}

	private void constructArcSegments()
	{
		NodePointCollection visible = getVisibleNodes();

		if (visible.size() > 0)
		{
			visible = visible.sortByAngle(origin);
			NodePoint current = null;
			NodePoint prev = visible.get(visible.size() - 1);
			for (int i = 0; i < visible.size(); i++)
			{
				current = visible.get(i);

				if (((current.hasShadow() && current.getShadowNode().isBorderNode()) || current.isBorderNode())
						&& ((prev.hasShadow() && prev.getShadowNode().isBorderNode()) || prev.isBorderNode()))
				{

					if (prev.getPrevNode().equals2D(current) || prev.getNextNode().equals2D(current))
					{
						// a segment with a border node on both ends
						// we already handled this - it is a triangle
					} else if (!isSpecialCase(current, prev))
					{
						// we need an arc
						result.addArcSegment(origin, prev, current, radius);
					}
				} else if ((current.hasShadow() && !current.getShadowNode().isBorderNode())
						&& (prev.hasShadow() && !prev.getShadowNode().isBorderNode()))
				{
					// We may need a triangle
					if (current.getPrevNode().equals(prev) || current.getNextNode().equals(prev))
					{
						// no triangle needed
					} else if (current.getShadowNode().getPrevNode().equals(prev)
							|| current.getShadowNode().getNextNode().equals(prev))
					{
						// no triangle
					} else if (prev.getShadowNode().getPrevNode().equals(current)
							|| prev.getShadowNode().getNextNode().equals(current))
					{
						// no triangle
					} else if (!isSpecialCase(current, prev))
					{
						// neighbors with a visible connection in between
						result.addTriangle(origin, prev.getShadowNode(), current.getShadowNode());
					}

				}

				prev = current;
			}
		} else
		{
			result.addCircle(origin, radius);
		}

	}

	/**
	 * Tests for the special case where the origin is a bordering node of both
	 * the current and the previous node. Tests if between the two nodes there
	 * is the inside of the polygon no triangle or arc needs to be constructed
	 * 
	 * @param current
	 * @param prev
	 * @return
	 */
	private boolean isSpecialCase(NodePoint current, NodePoint prev)
	{
		if (current.hasNeighbour(origin) && prev.hasNeighbour(origin))
		{
			Point testPoint = FACTORY.createPoint(new Coordinate(
					(origin.x + (current.x - origin.x) / 100 + (prev.x - origin.x) / 100), (origin.y
							+ (current.y - origin.y) / 100 + (prev.y - origin.y) / 100)));
			if (current.getPolygon() != null && current.getPolygon().contains(testPoint))
			{
				// System.out.println("contain");
				if (Angle.angleBetweenOriented(current, origin, prev) < 0)
				{
					// System.out.println("special case");
					return true;
				} else
				{
					// System.out.println("not special case: angle");
				}
			} else
			{
				// System.out.println("not contain");
				if (Angle.angleBetweenOriented(current, origin, prev) < 0)
				{
					// System.out.println("not special case: angle");
				} else
				{
					// System.out.println("special case");
					return true;
				}
			}
		}
		return false;
	}

	private void recursion()
	{
		NodePointCollection visible = getVisibleNodes();

		if (visible.size() > 0)
		{
			visible = visible.sortByAngle(origin);
			NodePoint current = null;
			for (int i = 0; i < visible.size(); i++)
			{
				current = visible.get(i);

				if (current.hasShadow())
				{
					double distance = current.distance(origin);

					try
					{
						if (radius - distance > epsilon)
						{
							if (!finished.isNodeDone(current, radius - distance))
							{
								finished.addNode(current, radius - distance);
								Runner recRun = new Runner(current, obstacles, radius - distance, finished);
								this.result.addGeometry(recRun.getResult().getPolygon());
							}
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}
}
