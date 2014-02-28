package algorithm.tools;

import java.util.List;

import algorithm.runner.Runner;
import algorithm.structure.NodePoint;
import algorithm.structure.ObstacleCollection;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Polygon;

public class VisibilityTester
{

	ObstacleCollection obstacles;
	Coordinate source;
	GeometryFactory fact;

	private double epsilon = 0.1d;

	public VisibilityTester(ObstacleCollection obs, Coordinate source)
	{
		obstacles = obs;
		this.source = source;
		fact = Runner.FACTORY;
	}

	public Coordinate getSource()
	{
		return source;
	}

	public void setSource(Coordinate source)
	{
		this.source = source;
	}

	public boolean isVisible(NodePoint coord)
	{
		// bordering nodes are always visible from each other
		if (coord.getPrevNode().equals2D(source) || coord.getNextNode().equals2D(source))
		{
			return true;
		}

		Coordinate[] coords = new Coordinate[] { source, coord };

		LineString line = fact.createLineString(coords);

		// if centroid of line is inside the polygon coord belongs to its not
		// visible in any case
		if (coord.getPolygon() != null && coord.getPolygon().contains(line.getCentroid()))
		{
			return false;
		}

		ObstacleCollection obsSelection = obstacles.queryObstacles(line.getEnvelopeInternal());

		List<Polygon> obsList = obsSelection.getAllObstacles();
		boolean noIntersection = true;
		int i = 0;
		while (noIntersection && i < obsList.size())
		{
			if (obsList.get(i).intersects(line))
			{
				Geometry inters = obsList.get(i).getExteriorRing().intersection(line);
				int numInters = inters.getNumGeometries();
				if (numInters > 0)
				{
					for (int j = 0; j < inters.getNumGeometries(); j++)
					{
						if (coord.isBorderNode())
						{
							// because Border nodes are calculated through
							// intersection with an approximated
							// circle the are not exact
							if (source.distance(inters.getGeometryN(j).getCoordinate()) < epsilon)
							{
								numInters--;
							}
							if (coord.distance(inters.getGeometryN(j).getCoordinate()) < epsilon)
							{
								numInters--;
							}
						} else
						{
							if (source.equals2D(inters.getGeometryN(j).getCoordinate()))
							{
								numInters--;
							}
							if (coord.equals2D(inters.getGeometryN(j).getCoordinate()))
							{
								numInters--;
							}
						}
					}
					if (numInters > 0)
					{
						noIntersection = false;
					}
				}
			}
			i++;
		}
		return noIntersection;
	}
}
