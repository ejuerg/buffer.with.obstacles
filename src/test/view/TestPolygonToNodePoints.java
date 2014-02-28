package test.view;

import java.awt.Color;
import java.util.Random;
import java.util.Vector;

import test.util.PolygonGenerator;

import algorithm.structure.NodePoint;
import algorithm.structure.NodePointCollection;
import algorithm.tools.PolygonToNodePoints;
import algorithm.view.VisualPoint;
import algorithm.view.VisualPolygon;
import algorithm.view.Visualizer;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequenceFactory;

public class TestPolygonToNodePoints {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GeometryFactory fact = new GeometryFactory();

		LinearRing r1;
		LinearRing r2;
		// triangles
		r1 = generateLinearRing(7);
		r2 = generateLinearRing(5);
		while (r1.intersects(r2)) {
			r2 = generateLinearRing(5);
		}

		Polygon p1 = new Polygon(r1, null, fact);
		Polygon p2 = new Polygon(r2, null, fact);

		// buffer around point -> circle
		Point point = fact.createPoint(new Coordinate(300, 300));
		Polygon buff = (Polygon) point.buffer(250d, 20);

		// go and get nodes of polygons
		NodePointCollection nodes1 = PolygonToNodePoints.run(p1);
		NodePointCollection nodes2 = PolygonToNodePoints.run(p2);
		NodePointCollection nodes3 = PolygonToNodePoints.run(buff);

		// Only select nodes close to point
		nodes1 = nodes1.getNodesInCircle(point.getCoordinate(), 250d);
		nodes2 = nodes2.getNodesInCircle(point.getCoordinate(), 250d);

		// Add neighbouring nodes for selected nodes
		Vector<VisualPoint> vNode = new Vector<VisualPoint>();
		for (int i = 0; i < nodes1.size(); i++) {
			NodePoint current = nodes1.get(i);
			vNode.add(new VisualPoint(current.getNextNode(), Color.pink));
			vNode.add(new VisualPoint(current.getPrevNode(), Color.yellow));
		}
		for (int i = 0; i < nodes2.size(); i++) {
			NodePoint current = nodes2.get(i);
			vNode.add(new VisualPoint(current.getNextNode(), Color.pink));
			vNode.add(new VisualPoint(current.getPrevNode(), Color.yellow));
		}

		// make visual points from nodes
		for (int i = 0; i < nodes1.size(); i++) {
			vNode.add(new VisualPoint(nodes1.get(i), Color.green));
		}
		for (int i = 0; i < nodes2.size(); i++) {
			vNode.add(new VisualPoint(nodes2.get(i), Color.blue));
		}
		for (int i = 0; i < nodes3.size(); i++) {
			vNode.add(new VisualPoint(nodes3.get(i), Color.black));
		}
		vNode.add(new VisualPoint(point.getCoordinate(), Color.pink));

		// make visual polygons
		Vector<VisualPolygon> vPol = new Vector<VisualPolygon>();
		vPol.add(new VisualPolygon(p1.getExteriorRing().getCoordinates(),
				Color.darkGray, null, 1f));
		vPol.add(new VisualPolygon(p2.getExteriorRing().getCoordinates(),
				Color.darkGray, null, 1f));
		vPol.add(new VisualPolygon(buff.getExteriorRing().getCoordinates(),
				Color.darkGray, null, 1f));
		vPol.add(new VisualPolygon(new PolygonGenerator(new Envelope(
				new Coordinate(0, 0), new Coordinate(1000, 600)))
				.createPolygon().getExteriorRing().getCoordinates(),
				Color.orange, null, 1f));

		// visualize intersection points
		GeometryCollection inters = (GeometryCollection) r1.intersection(r2);
		for (int i = 0; i < inters.getNumGeometries(); i++) {
			vNode.add(new VisualPoint(inters.getGeometryN(i).getCoordinate(),
					Color.red));
		}
		GeometryCollection inters2 = (GeometryCollection) r1.intersection(buff
				.getBoundary());
		for (int i = 0; i < inters2.getNumGeometries(); i++) {
			vNode.add(new VisualPoint(inters2.getGeometryN(i).getCoordinate(),
					Color.red));
		}
		GeometryCollection inters3 = (GeometryCollection) r2.intersection(buff
				.getBoundary());
		for (int i = 0; i < inters3.getNumGeometries(); i++) {
			vNode.add(new VisualPoint(inters3.getGeometryN(i).getCoordinate(),
					Color.red));
		}

		new Visualizer(vPol, vNode);
	}

	/**
	 * @param size
	 * @return
	 */
	public static LinearRing generateLinearRing(int size) {
		Random rand = new Random();
		Coordinate[] coords = new Coordinate[size];

		for (int i = 0; i < coords.length - 1; i++) {
			coords[i] = new Coordinate(rand.nextInt(1000), rand.nextInt(500));
		}
		coords[coords.length - 1] = coords[0];

		LinearRing r = new LinearRing(CoordinateArraySequenceFactory.instance()
				.create(coords), new GeometryFactory());
		if (r.isValid()) {
			return r;
		} else {
			return generateLinearRing(size);
		}
	}
}
