package algorithm.tools;

import algorithm.structure.NodePoint;
import algorithm.structure.NodePointCollection;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Polygon;

public class NodePointUtils
{

	/**
	 * Makes a double linked list of NodePoints from a Polygon
	 * 
	 * @param p
	 *            Polygon to make NodePoints from
	 * @return A NodePointCollection containing the resulting nodes
	 */
	public static NodePointCollection generateNodePoints(Polygon p)
	{
		Coordinate[] coords = p.getExteriorRing().getCoordinates();

		NodePointCollection nodes = new NodePointCollection();

		createNodes(coords, nodes);

		connectNeighbours(p, nodes);

		return nodes;
	}

	private static void createNodes(Coordinate[] coords, NodePointCollection nodes)
	{
		for (int i = 0; i < coords.length - 1; i++)
		{
			nodes.addPoint(new NodePoint(coords[i].x, coords[i].y));
		}
	}

	private static void connectNeighbours(Polygon p, NodePointCollection nodes)
	{
		int prevI = nodes.size() - 1;
		for (int i = 0; i < nodes.size(); i++)
		{
			NodePoint prev = nodes.get(prevI);
			NodePoint current = nodes.get(i);

			current.setPreviousNode(prev);
			current.setPolygon(p);
			prevI = i;
		}
	}

}