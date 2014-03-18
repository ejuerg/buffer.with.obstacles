package algorithm.structure.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import algorithm.structure.NodePoint;
import algorithm.structure.NodePointCollection;

import com.vividsolutions.jts.geom.Coordinate;

public class NodePointCollectionTest
{

	private NodePointCollection nodePointCollection;
	private final Coordinate origin = new Coordinate(0, 0);
	private final NodePoint nodeAtDeg0 = new NodePoint(1, 0);
	private final NodePoint nodeAtDeg180 = new NodePoint(-1, 0);
	private final NodePoint nodeAtDeg45 = new NodePoint(1, 1);
	private final NodePoint nodeAtDegMinus135 = new NodePoint(-1, -1);
	private final NodePoint nodeAtDeg90 = new NodePoint(0, 1);

	@Test
	public void testNodePointCollection()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testNodePointCollectionVectorOfNodePoint()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testNodePointCollectionNodePointArray()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testAddPoint()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testSortByAngleAlreadySorted()
	{
		NodePoint[] nodePoints = new NodePoint[] { nodeAtDeg0, nodeAtDeg45, nodeAtDeg90 };
		nodePointCollection = new NodePointCollection(nodePoints);

		nodePointCollection = nodePointCollection.sortByAngle(origin);

		assertEquals(nodeAtDeg0, nodePointCollection.get(0));
		assertEquals(nodeAtDeg45, nodePointCollection.get(1));
		assertEquals(nodeAtDeg90, nodePointCollection.get(2));
	}

	@Test
	public void testSortByAngle()
	{
		NodePoint[] nodePoints = new NodePoint[] { nodeAtDeg180, nodeAtDeg45, nodeAtDeg90, nodeAtDegMinus135 };
		nodePointCollection = new NodePointCollection(nodePoints);

		nodePointCollection = nodePointCollection.sortByAngle(origin);

		assertEquals(nodeAtDegMinus135, nodePointCollection.get(0));
		assertEquals(nodeAtDeg45, nodePointCollection.get(1));
		assertEquals(nodeAtDeg90, nodePointCollection.get(2));
		assertEquals(nodeAtDeg180, nodePointCollection.get(3));
	}

	@Test
	public void testGetNodesBetweenAngles()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testSortByDistance()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testSize()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetNodesInCircle()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testAddCollection()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testRemovePoint()
	{
		fail("Not yet implemented");
	}

}
