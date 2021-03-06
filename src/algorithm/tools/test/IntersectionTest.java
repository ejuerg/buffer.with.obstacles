package algorithm.tools.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import algorithm.tools.Intersection;

import com.vividsolutions.jts.geom.Coordinate;

public class IntersectionTest
{

	private Intersection intersection;

	@Before
	public void setUp() throws Exception
	{
		intersection = new Intersection(new Coordinate(0, 0), 0);
	}

	@Test
	public void testCoordinate()
	{
		assertTrue(intersection.getCoordinate().equals(new Coordinate(0, 0)));
	}

	@Test
	public void testIndex()
	{
		assertTrue(intersection.getNodeIndex() == 0);
	}
}
