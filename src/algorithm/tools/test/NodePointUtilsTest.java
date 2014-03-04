package algorithm.tools.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import algorithm.structure.NodePointCollection;
import algorithm.tools.NodePointUtils;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;

public class NodePointUtilsTest
{

	private GeometryFactory factory;
	private Coordinate[] coordinates;
	private Polygon triangle;

	@Before
	public void setUp() throws Exception
	{
		factory = new GeometryFactory();
		
		coordinates = new Coordinate[] { new Coordinate(0,0), new Coordinate(0,1), new Coordinate(1,0), new Coordinate(0,0)};
		
		triangle = factory.createPolygon(coordinates);
		
		
		
	}

	@Test
	public void testSize()
	{
		NodePointCollection result = NodePointUtils.generateNodePoints(triangle);

		assertTrue(result.size() == 3);
	}
}