package test.view;

import java.awt.Color;
import java.util.Vector;

import algorithm.view.VisualPoint;
import algorithm.view.VisualPolygon;
import algorithm.view.Visualizer;

import com.vividsolutions.jts.geom.Coordinate;

public class TestVisualizer {
	
	public static void main(String[] args) {
		
		Vector<VisualPoint> points = new Vector<VisualPoint>();
		points.add(new VisualPoint(new Coordinate(200,500), Color.darkGray));
		points.add(new VisualPoint(new Coordinate(300,300), Color.red));
		points.add(new VisualPoint(new Coordinate(400,200), Color.blue));
		points.add(new VisualPoint(new Coordinate(500,500), Color.green));
		points.add(new VisualPoint(new Coordinate(700,555), Color.darkGray));
		points.add(new VisualPoint(new Coordinate(100,100), Color.darkGray));	
		
		Vector<VisualPolygon> polygons = new Vector<VisualPolygon>();
		Coordinate[] coord1 = {
				new Coordinate(900, 5),
				new Coordinate(100, 5),
				new Coordinate(100, 555),
				new Coordinate(200, 500),
				new Coordinate(400, 200),
				new Coordinate(900, 5),
		};
		VisualPolygon vp1 = new VisualPolygon(coord1);
		polygons.add(vp1);
		Coordinate[] coord2 = {
				new Coordinate(900, 500),
				new Coordinate(800, 550),
				new Coordinate(700, 555),
				new Coordinate(850, 600),
				new Coordinate(1000, 400),
				new Coordinate(900, 500),
		};
		VisualPolygon vp2 = new VisualPolygon(coord2, Color.green, null, 1.0f);
		polygons.add(vp2);
		
		new Visualizer(polygons, points);		
	}

}
