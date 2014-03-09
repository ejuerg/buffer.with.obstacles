package algorithm.structure.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import algorithm.structure.NodePoint;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;

public class NodePointTest
{

	private NodePoint nextNodePoint;
	private NodePoint previousNodePoint;
	private NodePoint replacementNodePoint;

	private GeometryFactory factory;

	@Before
	public void setUp() throws Exception
	{
		nextNodePoint = new NodePoint(1, 1);
		previousNodePoint = new NodePoint(-1, -1);
		replacementNodePoint = new NodePoint(0, 5);

		factory = new GeometryFactory();
	}

	@Test
	public void testGetLineToPrev()
	{
		NodePoint nodePoint = new NodePoint(0, 0, previousNodePoint, nextNodePoint, null);

		LineString line = nodePoint.getLineToPreviousNode();

		LineString comparisonLine = factory
				.createLineString(new Coordinate[] { previousNodePoint, new NodePoint(0, 0) });

		assertTrue(line.equals(comparisonLine));
	}

	@Test
	public void testGetLineToNext()
	{
		NodePoint nodePoint = new NodePoint(0, 0, previousNodePoint, nextNodePoint, null);

		LineString line = nodePoint.getLineToNextNode();

		LineString comparisonLine = factory.createLineString(new Coordinate[] { new NodePoint(0, 0), nextNodePoint });

		assertTrue(line.equals(comparisonLine));
	}

	@Test
	public void testNextNode()
	{
		NodePoint nodePoint = new NodePoint(0, 0, null, nextNodePoint, null);

		assertTrue(nodePoint.getNextNode().equals(nextNodePoint));
	}

	@Test
	public void testPreviousNode()
	{
		NodePoint nodePoint = new NodePoint(0, 0, previousNodePoint, null, null);

		assertTrue(nodePoint.getPreviousNode().equals(previousNodePoint));
	}

	@Test
	public void testReplaceNextNode()
	{
		NodePoint nodePoint = new NodePoint(0, 0, previousNodePoint, nextNodePoint, null);

		nodePoint.setNextNode(replacementNodePoint);

		assertTrue(nodePoint.getNextNode().equals(replacementNodePoint));
	}

	@Test
	public void testReplacePreviousNode()
	{
		NodePoint nodePoint = new NodePoint(0, 0, previousNodePoint, nextNodePoint, null);

		nodePoint.setPreviousNode(replacementNodePoint);

		assertTrue(nodePoint.getPreviousNode().equals(replacementNodePoint));
	}

	@Test
	public void testBacklinkNextNode()
	{
		NodePoint nodePoint = new NodePoint(0, 0, previousNodePoint, nextNodePoint, null);

		assertTrue(nodePoint.getNextNode().getPreviousNode().equals(nodePoint));
	}

	@Test
	public void testBacklinkPreviousNode()
	{
		NodePoint nodePoint = new NodePoint(0, 0, previousNodePoint, nextNodePoint, null);

		assertTrue(nodePoint.getPreviousNode().getNextNode().equals(nodePoint));
	}

}
