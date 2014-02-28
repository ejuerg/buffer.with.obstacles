package algorithm.view;

import java.awt.BasicStroke;
import java.awt.Color;

public abstract class VisualGeometry
{

	protected Color outline;
	protected BasicStroke stroke;

	protected VisualGeometry(Color outline, float stroke)
	{
		this.outline = outline;
		this.stroke = new BasicStroke(stroke);
	}

	public Color getOutline()
	{
		return outline;
	}

	public void setOutline(Color outline)
	{
		this.outline = outline;
	}

	public BasicStroke getStroke()
	{
		return stroke;
	}

	public void setStroke(BasicStroke stroke)
	{
		this.stroke = stroke;
	}

}
