package algorithm.tools.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.vividsolutions.jts.geom.Coordinate;

import algorithm.tools.HelperNodeConstructor;

public class HelperNodeConstructorTest
{

	private Coordinate origin;

	@Before
	public void setUp() throws Exception
	{
		origin = new Coordinate(0, 0);
	}

	@Test
	public void testHelperNodeConstructor()
	{
		HelperNodeConstructor helperNodeConstructor = new HelperNodeConstructor(origin, 1);
		
		assertEquals(origin, helperNodeConstructor.getOrigin());
		assertEquals(1, helperNodeConstructor.getRadius(), 0.00001);
	}

	@Test
	public void testSetOrigin()
	{
		HelperNodeConstructor helperNodeConstructor = new HelperNodeConstructor(origin, 1);
		
		Coordinate newOrigin = new Coordinate(1, 1);		
		helperNodeConstructor.setOrigin(newOrigin);
		
		assertEquals(newOrigin, helperNodeConstructor.getOrigin());
	}

	@Test
	public void testSetRadius()
	{
		HelperNodeConstructor helperNodeConstructor = new HelperNodeConstructor(origin, 1);
		
		double newRadius = 2;
		helperNodeConstructor.setRadius(newRadius);
		
		assertEquals(newRadius, helperNodeConstructor.getRadius(), 0.00001);
	}

	@Test
	public void testGetBorderNodes()
	{
		fail("Not yet implemented");
	}

}
