package algorithm.runner;

import java.util.Hashtable;

import com.vividsolutions.jts.geom.Coordinate;

public class FinishedNodesList
{

	private Hashtable<Coordinate, Double> nodeTable;

	public FinishedNodesList()
	{
		nodeTable = new Hashtable<Coordinate, Double>();
	}

	public void addNode(Coordinate coord, double radius)
	{
		if (nodeTable.containsKey(coord))
		{
			nodeTable.remove(coord);
		}
		nodeTable.put(coord, radius);
	}

	public boolean isNodeDone(Coordinate coord, double radius)
	{
		if (nodeTable.containsKey(coord) && nodeTable.get(coord) > radius)
		{
			return true;
		}
		return false;
	}
}
