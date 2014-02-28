package algorithm.structure;

import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.index.quadtree.Quadtree;

import algorithm.runner.Runner;

public class ObstacleCollection {
	
	private Quadtree obstacles;
	
	public ObstacleCollection() {
		this(new Quadtree());
	}
	
	private ObstacleCollection(Quadtree obstacles) {
		this.obstacles = obstacles;
	}
	
	public void addObstacle(Polygon p) {
		obstacles.insert(p.getEnvelopeInternal(), p);
	}
	
	public ObstacleCollection queryObstacles(Envelope envelope) {
		List<?> resultList =  obstacles.query(envelope);
		ObstacleCollection result = new ObstacleCollection();
		for(int i = 0; i < resultList.size(); i++) {
			result.addObstacle((Polygon) resultList.get(i));
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Polygon> getAllObstacles() {
		List<Polygon> queryAll = (List<Polygon>) obstacles.queryAll();
		return queryAll;
	}
	
	public boolean pointInObstacle(Coordinate coord) {
		Point p = Runner.FACTORY.createPoint(coord);
		List<?> resultList = obstacles.query(p.getEnvelopeInternal());
		if(resultList != null) {
			for(int i = 0; i < resultList.size(); i++) {
				if(((Polygon)resultList.get(i)).contains(p)) {
					return true;
				}
			}
		}
		
		return false;
	}
}
