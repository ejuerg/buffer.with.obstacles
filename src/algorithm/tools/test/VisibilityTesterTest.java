package algorithm.tools.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.vividsolutions.jts.geom.Coordinate;

import algorithm.tools.VisibilityTester;

public class VisibilityTesterTest
{

	private VisibilityTester visibilityTester;
	private Coordinate source;

	@Before
	public void setUp() throws Exception
	{
		source = new Coordinate(0, 0);
		visibilityTester = new VisibilityTester(null, source);
	}

	@Test
	public void testGetSource()
	{
		assertTrue(visibilityTester.getSource().equals(source));
	}

	@Test
	public void testSetSource()
	{
		Coordinate newSource = new Coordinate(1, 1);
		visibilityTester.setSource(newSource);
		assertTrue(visibilityTester.getSource().equals(newSource));
	}

	@Test
	public void testIsVisible()
	{
		// TODO
	}
}