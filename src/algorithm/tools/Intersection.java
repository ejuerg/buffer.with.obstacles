package algorithm.tools;

import com.vividsolutions.jts.geom.Coordinate;

public class Intersection
{
	Coordinate coordinate;

	int nodeIndex;

	public Intersection(Coordinate coordinate, int nodeIndex)
	{
		this.coordinate = coordinate;
		this.nodeIndex = nodeIndex;
	}

	public Coordinate getCoordinate()
	{
		return coordinate;
	}

	public int getNodeIndex()
	{
		return nodeIndex;
	}
}
