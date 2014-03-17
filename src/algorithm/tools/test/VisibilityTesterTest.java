package algorithm.tools.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import algorithm.structure.NodePoint;
import algorithm.structure.ObstacleCollection;
import algorithm.tools.VisibilityTester;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;

public class VisibilityTesterTest
{

	private VisibilityTester visibilityTester;
	private Coordinate source;

	@Before
	public void setUp() throws Exception
	{
		source = new Coordinate(0, 0);
		
	}

	@Test
	public void testGetSource()
	{
		visibilityTester = new VisibilityTester(null, source);
		
		assertTrue(visibilityTester.getSource().equals(source));
	}

	@Test
	public void testSetSource()
	{
		visibilityTester = new VisibilityTester(null, source);
		
		Coordinate newSource = new Coordinate(1, 1);
		visibilityTester.setSource(newSource);
		
		assertTrue(visibilityTester.getSource().equals(newSource));
	}

	@Test
	public void testIsVisible()
	{
		ObstacleCollection obstacles = new ObstacleCollection();
		
		Polygon obstacle = createObstacle();
		
		obstacles.addObstacle(obstacle);
		
		visibilityTester = new VisibilityTester(obstacles, source);
		
		assertTrue(visibilityTester.isVisible(new NodePoint(0.3, 0.3)));
	}
	
	@Test
	public void testPointOnBoundaryIsVisible()
	{
		ObstacleCollection obstacles = new ObstacleCollection();
		
		Polygon obstacle = createObstacle();
		
		obstacles.addObstacle(obstacle);
		
		visibilityTester = new VisibilityTester(obstacles, source);
		
		assertTrue(visibilityTester.isVisible(new NodePoint(0.5, 0.5)));
	}
	
	@Test
	public void testPointInsideObstacleIsNotVisible()
	{
		ObstacleCollection obstacles = new ObstacleCollection();
		
		Polygon obstacle = createObstacle();
		
		obstacles.addObstacle(obstacle);
		
		visibilityTester = new VisibilityTester(obstacles, source);
		
		assertFalse(visibilityTester.isVisible(new NodePoint(0.7, 0.7)));
	}
	
	@Test
	public void testPointBehindObstacleIsNotVisible()
	{
		ObstacleCollection obstacles = new ObstacleCollection();
		
		Polygon obstacle = createObstacle();
		
		obstacles.addObstacle(obstacle);
		
		visibilityTester = new VisibilityTester(obstacles, source);
		
		assertFalse(visibilityTester.isVisible(new NodePoint(1.5, 1.5)));
	}
	
	@Test @Ignore
	//TODO what would be correct here?
	public void testPointBehindBoundaryOfObstacleIsVisible()
	{
		ObstacleCollection obstacles = new ObstacleCollection();
		
		Polygon obstacle = createObstacle();
		
		obstacles.addObstacle(obstacle);
		
		visibilityTester = new VisibilityTester(obstacles, source);
		
		assertTrue(visibilityTester.isVisible(new NodePoint(5, 0)));
	}

	private Polygon createObstacle()
	{
		GeometryFactory factory = new GeometryFactory();
		
		Coordinate[] obstacleCoordinates = new Coordinate[] {new Coordinate(1, 0), new Coordinate(1, 1), new Coordinate(0, 1), new Coordinate(1,0)};
		
		Polygon obstacle = factory.createPolygon(obstacleCoordinates);
		return obstacle;
	}
}
