package algorithm.structure;

import algorithm.runner.Runner;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Polygon;

public class NodePoint extends Coordinate
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6758771633538842444L;

	private Polygon polygon;
	private NodePoint nextNode;
	private NodePoint previousNode;
	private NodePoint shadowNode;
	private boolean isBorderNode = false;
	private boolean isShadowNode = false;
	private static GeometryFactory factory;

	public NodePoint(double x, double y)
	{
		super(x, y);
	}

	public NodePoint(double x, double y, NodePoint prev, NodePoint next, Polygon p)
	{
		this(x, y);
		this.setNextNode(next);
		this.setPreviousNode(prev);
		this.polygon = p;

		if (factory == null)
		{
			factory = Runner.FACTORY;
		}
	}

	public Polygon getPolygon()
	{
		return polygon;
	}

	public void setPolygon(Polygon polygon)
	{
		this.polygon = polygon;
	}

	public NodePoint getNextNode()
	{
		return nextNode;
	}

	public void setNextNode(NodePoint nextNode)
	{
		this.nextNode = nextNode;

		if (nextNode == null)
		{
			return;
		}

		if (nextNode.getNextNode() == null || !nextNode.getNextNode().equals(this))
		{
			nextNode.setPreviousNode(this);
		}
	}

	public NodePoint getPreviousNode()
	{
		return previousNode;
	}

	public void setPreviousNode(NodePoint previousNode)
	{
		this.previousNode = previousNode;

		if (previousNode == null)
		{
			return;
		}

		if (previousNode.getNextNode() == null || !previousNode.getNextNode().equals(this))
		{
			previousNode.setNextNode(this);
		}
	}

	public LineString getLineToPreviousNode()
	{
		return createLine(this, this.getPreviousNode());
	}

	public LineString getLineToNextNode()
	{
		return createLine(this, this.getNextNode());
	}

	private static LineString createLine(Coordinate from, Coordinate to)
	{
		if (factory == null)
		{
			factory = new GeometryFactory();
		}
		return factory.createLineString(new Coordinate[] { to, from });
	}

	public NodePoint getShadowNode()
	{
		return shadowNode;
	}

	public void setShadowNode(NodePoint shadowNode)
	{
		this.shadowNode = shadowNode;
	}

	public boolean isBorderNode()
	{
		return isBorderNode;
	}

	public void setBorderNode(boolean isBorderNode)
	{
		this.isBorderNode = isBorderNode;
	}

	public boolean hasShadow()
	{
		return (this.shadowNode != null);
	}

	public boolean isShadow()
	{
		return isShadowNode;
	}

	public void setShadow(boolean isShadow)
	{
		isShadowNode = isShadow;
	}

	public boolean hasNeighbour(Coordinate other)
	{
		return (this.getPreviousNode().equals2D(other) || this.getNextNode().equals2D(other));
	}

}
