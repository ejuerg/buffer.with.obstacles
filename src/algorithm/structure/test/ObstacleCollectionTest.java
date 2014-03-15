package algorithm.structure.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import algorithm.structure.ObstacleCollection;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;

public class ObstacleCollectionTest
{

	private ObstacleCollection obstacleCollection;

	private GeometryFactory geometryFactory;

	private Polygon square;

	@Before
	public void setUp() throws Exception
	{
		geometryFactory = new GeometryFactory();

		Coordinate[] squareCoords = new Coordinate[] { new Coordinate(0, 0), new Coordinate(0, 5),
				new Coordinate(5, 5), new Coordinate(5, 0), new Coordinate(0, 0) };

		square = geometryFactory.createPolygon(squareCoords);
	}

	@Test
	public void testCanAddOneObstacle()
	{
		obstacleCollection = new ObstacleCollection();

		obstacleCollection.addObstacle(square);

		assertTrue(obstacleCollection.getAllObstacles().size() == 1);
	}

	@Test
	public void testNewCollectionIsEmpty()
	{
		obstacleCollection = new ObstacleCollection();

		assertTrue(obstacleCollection.getAllObstacles().size() == 0);
	}

	@Test
	public void testQueryObstacles()
	{
		obstacleCollection = new ObstacleCollection();

		obstacleCollection.addObstacle(square);

		ObstacleCollection result = obstacleCollection.queryObstacles(new Envelope(new Coordinate(1, 1)));

		Polygon polygonResult = result.getAllObstacles().get(0);

		assertEquals(square, polygonResult);
	}

	@Test
	public void testGetAllObstacles()
	{
		Coordinate[] coords1 = new Coordinate[] { new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(1, 0),
				new Coordinate(0, 0) };
		Coordinate[] coords2 = new Coordinate[] { new Coordinate(0, 0), new Coordinate(0, 2), new Coordinate(2, 0),
				new Coordinate(0, 0) };
		Coordinate[] coords3 = new Coordinate[] { new Coordinate(0, 0), new Coordinate(0, 3), new Coordinate(3, 0),
				new Coordinate(0, 0) };

		Polygon polygon1 = geometryFactory.createPolygon(coords1);
		Polygon polygon2 = geometryFactory.createPolygon(coords2);
		Polygon polygon3 = geometryFactory.createPolygon(coords3);

		obstacleCollection = new ObstacleCollection();

		obstacleCollection.addObstacle(polygon1);
		obstacleCollection.addObstacle(polygon2);
		obstacleCollection.addObstacle(polygon3);

		assertTrue(obstacleCollection.getAllObstacles().size() == 3);
	}

	@Test
	public void testPointInObstacle()
	{
		Coordinate point = new Coordinate(2, 2);

		obstacleCollection = new ObstacleCollection();

		obstacleCollection.addObstacle(square);

		assertTrue(obstacleCollection.pointInObstacle(point));
	}

	@Test
	public void testPointOutsideObstacle()
	{
		Coordinate point = new Coordinate(10, 10);

		obstacleCollection = new ObstacleCollection();

		obstacleCollection.addObstacle(square);

		assertFalse(obstacleCollection.pointInObstacle(point));
	}

	@Test
	public void testPointOnBorderIsOutsideObstacle()
	{
		Coordinate point = new Coordinate(0, 2);

		obstacleCollection = new ObstacleCollection();

		obstacleCollection.addObstacle(square);

		assertFalse(obstacleCollection.pointInObstacle(point));
	}

	@Test
	public void testPointOnCornerIsOutsideObstacle()
	{
		Coordinate point = new Coordinate(5, 5);

		obstacleCollection = new ObstacleCollection();

		obstacleCollection.addObstacle(square);

		assertFalse(obstacleCollection.pointInObstacle(point));
	}

}
