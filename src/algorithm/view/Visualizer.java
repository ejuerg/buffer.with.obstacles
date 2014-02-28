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
import java.util.Vector;

import javax.swing.JFrame;

import com.vividsolutions.jts.geom.Coordinate;

public class Visualizer extends Applet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4781540162102036203L;
	private Image buffer;
	private Graphics2D gBuffer;

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

	public void init()
	{
		setBackground(new Color(255, 255, 255));
		this.setSize(xSize, ySize);
	}

	public void paint(Graphics g)
	{
		if (buffer == null)
		{
			buffer = createImage(this.getSize().width, this.getSize().height);
			gBuffer = (Graphics2D) buffer.getGraphics();
		}
		gBuffer.clearRect(0, 0, this.getSize().width, this.getSize().height);

		// draw all polygons
		for (int i = 0; i < polygons.size(); i++)
		{
			Coordinate[] coords = polygons.get(i).getCoordinates();
			double[] xCoords = new double[coords.length];
			double[] yCoords = new double[coords.length];

			for (int j = 0; j < coords.length; j++)
			{
				xCoords[j] = (coords[j].x - xCorrection) / divisor;
				yCoords[j] = ySize - ((coords[j].y - yCorrection) / divisor);
			}

			GeneralPath.Double polyline = new GeneralPath.Double(GeneralPath.WIND_EVEN_ODD, xCoords.length);
			polyline.moveTo(xCoords[0], yCoords[0]);
			for (int index = 1; index < xCoords.length; index++)
			{
				polyline.lineTo(xCoords[index], yCoords[index]);
			}
			if (polygons.get(i).getFill() != null)
			{
				gBuffer.setColor(polygons.get(i).getFill());
				gBuffer.fill(polyline);
			}
			gBuffer.setPaint(polygons.get(i).getOutline());
			gBuffer.setStroke(polygons.get(i).getStroke());
			gBuffer.draw(polyline);
		}

		// test points
		// createPoints();

		// draw all points
		for (int i = 0; i < points.size(); i++)
		{
			Coordinate coord = points.get(i).getPoint();
			Ellipse2D.Double point = new Ellipse2D.Double((coord.x - xCorrection) / divisor, ySize
					- ((coord.y - yCorrection) / divisor), 1, 1);
			gBuffer.setPaint(points.get(i).getOutline());
			gBuffer.setStroke(points.get(i).getStroke());
			gBuffer.draw(point);
			gBuffer.fill(point);
		}

		g.drawImage(buffer, 0, 0, this);
	}
}
