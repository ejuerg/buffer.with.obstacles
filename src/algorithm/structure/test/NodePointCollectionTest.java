package algorithm.structure.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import algorithm.structure.NodePoint;
import algorithm.structure.NodePointCollection;

import com.vividsolutions.jts.geom.Coordinate;

public class NodePointCollectionTest
{

	private NodePointCollection nodePointCollection;
	private Coordinate origin;

	@Before
	public void setUp() throws Exception
	{
		origin = new Coordinate(0, 0);
	}

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
		NodePoint[] nodePoints = new NodePoint[] { new NodePoint(1, 0), new NodePoint(1, 1), new NodePoint(0, 1) };
		nodePointCollection = new NodePointCollection(nodePoints);

		nodePointCollection = nodePointCollection.sortByAngle(origin);

		assertEquals(nodePoints[0], nodePointCollection.get(0));
		assertEquals(nodePoints[1], nodePointCollection.get(1));
		assertEquals(nodePoints[2], nodePointCollection.get(2));
	}

	@Test
	public void testSortByAngle()
	{
		NodePoint[] nodePoints = new NodePoint[] { new NodePoint(-1, 0), new NodePoint(1, 1), new NodePoint(0, 1),
				new NodePoint(-1, -1) };
		nodePointCollection = new NodePointCollection(nodePoints);

		nodePointCollection = nodePointCollection.sortByAngle(origin);

		assertEquals(nodePoints[3], nodePointCollection.get(0));
		assertEquals(nodePoints[1], nodePointCollection.get(1));
		assertEquals(nodePoints[2], nodePointCollection.get(2));
		assertEquals(nodePoints[0], nodePointCollection.get(3));
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
	public void testGet()
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
