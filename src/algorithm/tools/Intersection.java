package algorithm.tools;

import com.vividsolutions.jts.geom.Coordinate;

class Intersection
{
	Coordinate coordinate;

	int nodeIndex;

	public Intersection(Coordinate coordinate, int nodeIndex)
	{
		this.coordinate = coordinate;
		this.nodeIndex = nodeIndex;
	}

	Coordinate getCoordinate()
	{
		return coordinate;
	}

	int getNodeIndex()
	{
		return nodeIndex;
	}
}
