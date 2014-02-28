package algorithm.tools;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Polygon;

import algorithm.structure.NodePoint;
import algorithm.structure.NodePointCollection;

public class HelperNodeConstructor {

	private Coordinate origin;
	private double radius;
	private GeometryFactory fact;
	private Polygon buffer;

	private int precision = 50;

	public HelperNodeConstructor(Coordinate origin, double radius) {
		this.origin = origin;
		this.radius = radius;
		this.fact = new GeometryFactory();
		this.buffer = (Polygon) fact.createPoint(origin).buffer(radius,
				precision);
	}

	public Coordinate getOrigin() {
		return origin;
	}

	public void setOrigin(Coordinate origin) {
		this.origin = origin;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	/**
	 * Constructs helper nodes from the intersection of line segments of
	 * obstacle polygons with the circle Needs all the nodes of segments that
	 * possibly intersect, even if the nodes themselves lie outside
	 * 
	 * @param nodes
	 * @return
	 */
	public NodePointCollection getBorderNodes(NodePointCollection nodes) {
		NodePointCollection borderNodes = new NodePointCollection();
		for (int i = 0; i < nodes.size(); i++) {
			NodePoint current = nodes.get(i);
			NodePoint oldPrev = current.getPrevNode();

			LineString linePrev = current.getLineToPrev();

			if (linePrev.intersects(buffer.getExteriorRing())) {
				Geometry intersPrev = linePrev.intersection(buffer
						.getExteriorRing());
				if (intersPrev.getNumGeometries() > 1) {
					constructTwoBorderNodes(borderNodes, current, oldPrev,
							intersPrev);
				} else {
					constructOneBorderNode(borderNodes, current, oldPrev,
							intersPrev);
				}
			}

		}
		return borderNodes;
	}

	private void constructOneBorderNode(NodePointCollection borderNodes,
			NodePoint current, NodePoint oldPrev, Geometry intersPrev) {
		// one intersection
		Coordinate inters = intersPrev.getGeometryN(0)
				.getCoordinate();
		NodePoint border = new NodePoint(intersPrev
				.getGeometryN(0).getCoordinate().x, intersPrev
				.getGeometryN(0).getCoordinate().y);
		if (current.equals2D(inters) || oldPrev.equals2D(inters)) {
			// TODO intersection with either end, do nothing?
		} else {

			current.setPrevNode(border);
			border.setPrevNode(oldPrev);
			borderNodes.addPoint(border);
			border.setPolygon(current.getPolygon());
			border.setBorderNode(true);
		}
	}

	private void constructTwoBorderNodes(NodePointCollection borderNodes,
			NodePoint current, NodePoint oldPrev, Geometry intersPrev) {
		// two intersections, three or more not possible with line
		// and circle
		// this is possible if both nodes lie outside the circle,
		// but the line
		// connecting the node goes through the circle
		NodePoint border0 = new NodePoint(intersPrev
				.getGeometryN(0).getCoordinate().x, intersPrev
				.getGeometryN(0).getCoordinate().y);
		NodePoint border1 = new NodePoint(intersPrev
				.getGeometryN(1).getCoordinate().x, intersPrev
				.getGeometryN(1).getCoordinate().y);

		// find out which node comes first
		if (border0.distance(current) < border1.distance(current)) {
			current.setPrevNode(border0);
			border0.setPrevNode(border1);
			border1.setPrevNode(oldPrev);
		} else {
			current.setPrevNode(border1);
			border1.setPrevNode(border0);
			border0.setPrevNode(oldPrev);
		}
		// add to collection
		border0.setPolygon(current.getPolygon());
		border1.setPolygon(current.getPolygon());
		border0.setBorderNode(true);
		border1.setBorderNode(true);
		borderNodes.addPoint(border0);
		borderNodes.addPoint(border1);
	}
}
