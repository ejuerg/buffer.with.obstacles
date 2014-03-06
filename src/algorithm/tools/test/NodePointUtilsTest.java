package algorithm.tools.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import algorithm.structure.NodePoint;
import algorithm.structure.NodePointCollection;
import algorithm.tools.NodePointUtils;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;

public class NodePointUtilsTest
{

	private GeometryFactory factory;
	
	private Coordinate[] coordinatesTriangle;
	private Coordinate firstCoordinate;
	private Coordinate secondCoordinate;
	private Coordinate thirdCoordinate;
	
	private Polygon triangle;
	private NodePointCollection resultTriangle;

	@Before
	public void setUp() throws Exception
	{
		factory = new GeometryFactory();
		
		firstCoordinate = new Coordinate(0,0);
		secondCoordinate = new Coordinate(0,1);
		thirdCoordinate = new Coordinate(1,0);
		
		coordinatesTriangle = new Coordinate[] { firstCoordinate, secondCoordinate, thirdCoordinate, firstCoordinate};
		
		triangle = factory.createPolygon(coordinatesTriangle);
		
		resultTriangle = NodePointUtils.generateNodePoints(triangle);		
	}

	@Test
	public void testTriangleSize()
	{
		assertTrue(resultTriangle.size() == 3);
	}
	
	@Test
	public void testTriangleFirstCoord() 
	{
		assertTrue(resultTriangle.get(0).equals(firstCoordinate));
	}
	
	@Test
	public void testTriangleSecondCoord() 
	{
		assertTrue(resultTriangle.get(1).equals(secondCoordinate));
	}
	
	@Test
	public void testTriangleThirdCoord() 
	{
		assertTrue(resultTriangle.get(2).equals(thirdCoordinate));
	}
	
	@Test
	public void testTriangleFirstNodeSequence()
	{
		NodePoint node = resultTriangle.get(0);
		
		assertTrue(node.getNextNode().equals(secondCoordinate) && node.getPrevNode().equals(thirdCoordinate));
	}
	
	@Test
	public void testTriangleSecondNodeSequence()
	{
		NodePoint node = resultTriangle.get(1);
		
		assertTrue(node.getNextNode().equals(thirdCoordinate) && node.getPrevNode().equals(firstCoordinate));
	}
	
	@Test
	public void testTriangleThirdNodeSequence()
	{
		NodePoint node = resultTriangle.get(2);
		
		assertTrue(node.getNextNode().equals(firstCoordinate) && node.getPrevNode().equals(secondCoordinate));
	}
	
	
	
}