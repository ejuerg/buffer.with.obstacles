package algorithm.view;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.util.Vector;

import javax.swing.JFrame;

import com.vividsolutions.jts.geom.Coordinate;

public class Visualizer extends Applet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4781540162102036203L;
	private Image image;
	private Graphics2D graphics2D;

	// size of screen
	private int xSize = 1000;
	private int ySize = 600;

	// Correct the coordinates to fit screen
	private double xCorrection = 0;
	private double yCorrection = 0;
	private double divisor = 1;

	private Vector<VisualPolygon> polygons;
	private Vector<VisualPoint> points;

	public Visualizer(Vector<VisualPolygon> polygons, Vector<VisualPoint> points)
	{
		this.polygons = polygons;
		this.points = points;

		JFrame f = new JFrame("TestVisualizer");
		f.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		f.getContentPane().add("Center", this);
		this.init();
		f.pack();
		f.setSize(new Dimension(xSize + 50, ySize + 50));
		f.setVisible(true);
	}

	@Override
	public void init()
	{
		setBackground(new Color(255, 255, 255));
		this.setSize(xSize, ySize);
	}

	@Override
	public void paint(Graphics g)
	{
		if (image == null)
		{
			image = createImage(this.getSize().width, this.getSize().height);
			graphics2D = (Graphics2D) image.getGraphics();
		}

		clearGraphics();

		drawPolygons();

		drawPoints();

		g.drawImage(image, 0, 0, this);
	}

	private void clearGraphics()
	{
		graphics2D.clearRect(0, 0, this.getSize().width, this.getSize().height);
	}

	private void drawPoints()
	{
		for (int i = 0; i < points.size(); i++)
		{
			Coordinate coord = points.get(i).getPoint();
			Ellipse2D.Double point = new Ellipse2D.Double((coord.x - xCorrection) / divisor, ySize
					- ((coord.y - yCorrection) / divisor), 1, 1);
			graphics2D.setPaint(points.get(i).getOutline());
			graphics2D.setStroke(points.get(i).getStroke());
			graphics2D.draw(point);
			graphics2D.fill(point);
		}
	}

	private void drawPolygons()
	{
		for (int i = 0; i < polygons.size(); i++)
		{
			VisualPolygon polygon = polygons.get(i);

			Coordinate[] coords = polygon.getCoordinates();
			double[] xCoords = new double[coords.length];
			double[] yCoords = new double[coords.length];

			fillCoordinateArrays(coords, xCoords, yCoords);

			GeneralPath.Double polyline = new GeneralPath.Double(Path2D.WIND_EVEN_ODD, xCoords.length);
			polyline.moveTo(xCoords[0], yCoords[0]);
			for (int index = 1; index < xCoords.length; index++)
			{
				polyline.lineTo(xCoords[index], yCoords[index]);
			}
			if (polygon.getFill() != null)
			{
				graphics2D.setColor(polygon.getFill());
				graphics2D.fill(polyline);
			}
			graphics2D.setPaint(polygon.getOutline());
			graphics2D.setStroke(polygon.getStroke());
			graphics2D.draw(polyline);
		}
	}

	private void fillCoordinateArrays(Coordinate[] coords, double[] xCoords, double[] yCoords)
	{
		for (int j = 0; j < coords.length; j++)
		{
			xCoords[j] = (coords[j].x - xCorrection) / divisor;
			yCoords[j] = ySize - ((coords[j].y - yCorrection) / divisor);
		}
	}
}
