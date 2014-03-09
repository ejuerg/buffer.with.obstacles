package algorithm.structure.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import algorithm.structure.NodePoint;

public class NodePointTest
{

	private NodePoint nextNodePoint;
	private NodePoint previousNodePoint;
	private NodePoint replacementNodePoint;

	@Before
	public void setUp() throws Exception
	{
		nextNodePoint = new NodePoint(1, 1);
		previousNodePoint = new NodePoint(-1, -1);
		replacementNodePoint = new NodePoint(0, 5);
	}

	@Test
	public void testGetLineToPrev()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testGetLineToNext()
	{
		fail("Not yet implemented");
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

		assertTrue(nodePoint.getPrevNode().equals(previousNodePoint));
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
		
		nodePoint.setPrevNode(replacementNodePoint);

		assertTrue(nodePoint.getPrevNode().equals(replacementNodePoint));
	}
	
	@Test
	public void testLinkNextNode()
	{
		NodePoint nodePoint = new NodePoint(0, 0, previousNodePoint, nextNodePoint, null);

		assertTrue(nodePoint.getNextNode().getPrevNode().equals(nodePoint));
	}
	
	@Test
	public void testLinkPreviousNode()
	{
		NodePoint nodePoint = new NodePoint(0, 0, previousNodePoint, nextNodePoint, null);

		assertTrue(nodePoint.getPrevNode().getNextNode().equals(nodePoint));
	}
	
}
