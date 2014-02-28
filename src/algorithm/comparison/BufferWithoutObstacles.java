package algorithm.comparison;

import java.util.List;

import algorithm.runner.Runner;
import algorithm.structure.ObstacleCollection;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;

public class BufferWithoutObstacles {
	
	GeometryFactory fact = Runner.FACTORY;

	private Geometry buff;
	
	public BufferWithoutObstacles(Coordinate origin, double radius, ObstacleCollection obs) {
		
		buff = new GeometryFactory().createPoint(origin).buffer(radius, 100);
		
		ObstacleCollection obstacles = obs.queryObstacles(buff.getEnvelopeInternal());
		
		List<Polygon> obsList = obstacles.getAllObstacles();
		
		for(int i = 0; i < obsList.size(); i++) {
			buff = buff.difference(obsList.get(i));
		}		
	}
	
	public Geometry getResult() {
		return buff;
	}
	

}
