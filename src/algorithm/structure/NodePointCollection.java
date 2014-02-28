package algorithm.structure;

import java.util.Vector;

import com.vividsolutions.jts.algorithm.Angle;
import com.vividsolutions.jts.geom.Coordinate;

public class NodePointCollection
{

	private Vector<NodePoint> points;

	public NodePointCollection()
	{
		this(new Vector<NodePoint>());
	}

	public NodePointCollection(Vector<NodePoint> points)
	{
		this.points = points;
	}

	public NodePointCollection(NodePoint[] pointArray)
	{
		this();
		for (int i = 0; i < pointArray.length; i++)
		{
			this.addPoint(pointArray[i]);
		}
	}

	public void addPoint(NodePoint p)
	{
		points.add(p);
	}

	@SuppressWarnings("unchecked")
	public NodePointCollection sortByAngle(Coordinate origin)
	{
		Vector<NodePoint> copyPoints = (Vector<NodePoint>) points.clone();
		Vector<NodePoint> newPoints = new Vector<NodePoint>();
		while (copyPoints.size() > 0)
		{
			double minValue = Double.POSITIVE_INFINITY;
			int minPosition = -1;
			for (int i = 0; i < copyPoints.size(); i++)
			{
				double currentValue = Angle.angle(origin, copyPoints.get(i));
				if (currentValue < minValue)
				{
					minValue = currentValue;
					minPosition = i;
				}
			}
			newPoints.add(copyPoints.remove(minPosition));
		}
		return new NodePointCollection(newPoints);
	}

	public NodePointCollection getNodesBetweenAngles(Coordinate origin, double start, double end)
	{
		Vector<NodePoint> newPoints = new Vector<NodePoint>();

		for (int i = 0; i < points.size(); i++)
		{
			NodePoint current = points.get(i);
			double angle = Angle.angle(origin, current);
			if (start < end && angle >= start && angle <= end)
			{
				newPoints.add(current);
			} else if (start > end && angle >= start || angle <= end)
			{
				newPoints.add(current);
			}
		}
		return new NodePointCollection(newPoints).sortByAngle(origin);
	}

	@SuppressWarnings("unchecked")
	public NodePointCollection sortByDistance(Coordinate origin)
	{
		Vector<NodePoint> copyPoints = (Vector<NodePoint>) points.clone();
		Vector<NodePoint> newPoints = new Vector<NodePoint>();
		while (copyPoints.size() > 0)
		{
			double minValue = Double.POSITIVE_INFINITY;
			int minPosition = -1;
			for (int i = 0; i < copyPoints.size(); i++)
			{
				double currentValue = copyPoints.get(i).distance(origin);
				if (currentValue < minValue)
				{
					minValue = currentValue;
					minPosition = i;
				}
			}
			newPoints.add(copyPoints.remove(minPosition));
		}
		return new NodePointCollection(newPoints);
	}

	public NodePoint get(int index)
	{
		return points.get(index);
	}

	public int size()
	{
		return points.size();
	}

	public NodePointCollection getNodesInCircle(Coordinate centre, double radius)
	{
		Vector<NodePoint> newPoints = new Vector<NodePoint>();
		for (int i = 0; i < points.size(); i++)
		{
			if (points.get(i).distance(centre) <= radius)
			{
				newPoints.add(points.get(i));
			}
		}
		return new NodePointCollection(newPoints);
	}

	public void addCollection(NodePointCollection nodes)
	{
		for (int i = 0; i < nodes.size(); i++)
		{
			this.addPoint(nodes.get(i));
		}
	}

	public boolean removePoint(Coordinate coord)
	{
		boolean pointRemoved = false;

		for (int i = 0; i < points.size() && !pointRemoved; i++)
		{
			if (points.get(i).equals2D(coord))
			{
				points.remove(i);
				pointRemoved = true;
			}
		}
		if (pointRemoved)
		{
			removePoint(coord);
			return true;
		} else
		{
			return false;
		}
	}
}
