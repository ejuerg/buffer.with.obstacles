package algorithm.structure.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
		fail("Not yet implemented");
	}

	@Test
	public void testPointInObstacle()
	{
		fail("Not yet implemented");
	}

}
