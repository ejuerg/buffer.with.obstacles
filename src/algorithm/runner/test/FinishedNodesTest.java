package algorithm.runner.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import algorithm.runner.FinishedNodes;
import algorithm.structure.NodePoint;

public class FinishedNodesTest
{

	private FinishedNodes finishedNodes;
	private NodePoint finishedNode;

	@Before
	public void setUp() throws Exception
	{
		finishedNode = new NodePoint(0, 0);
	}

	@Test
	public void testFinishedNodesList()
	{
		finishedNodes = new FinishedNodes();

		finishedNodes.addNode(finishedNode, 2);

		assertTrue(finishedNodes.isNodeDone(finishedNode, 1));
	}

	@Test
	public void testFinishedNodesListBiggerRadius()
	{
		finishedNodes = new FinishedNodes();

		finishedNodes.addNode(finishedNode, 2);

		assertFalse(finishedNodes.isNodeDone(finishedNode, 3));
	}

	@Test
	public void testFinishedNodesListEmpty()
	{
		finishedNodes = new FinishedNodes();

		assertFalse(finishedNodes.isNodeDone(finishedNode, 0));
	}

	@Test
	public void testFinishedNodesListReplace()
	{
		finishedNodes = new FinishedNodes();

		finishedNodes.addNode(finishedNode, 2);

		finishedNodes.addNode(finishedNode, 4);

		assertTrue(finishedNodes.isNodeDone(finishedNode, 3));
	}
}
