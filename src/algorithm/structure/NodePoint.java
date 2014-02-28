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
	private NodePoint prevNode;
	private NodePoint shadowNode;
	private boolean isBorderNode = false;
	private boolean isShadowNode = false;
	private static GeometryFactory fact;

	public NodePoint(double x, double y)
	{
		super(x, y);
	}

	public NodePoint(double x, double y, NodePoint prev, NodePoint next, Polygon p)
	{
		this(x, y);
		this.setNextNode(next);
		this.setPrevNode(prev);
		this.polygon = p;

		if (fact == null)
		{
			fact = Runner.FACTORY;
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
		if (nextNode.getNextNode() == null || !nextNode.getNextNode().equals(this))
		{
			nextNode.setPrevNode(this);
		}
	}

	public NodePoint getPrevNode()
	{
		return prevNode;
	}

	public void setPrevNode(NodePoint prevNode)
	{
		this.prevNode = prevNode;
		if (prevNode.getNextNode() == null || !prevNode.getNextNode().equals(this))
		{
			prevNode.setNextNode(this);
		}
	}

	public LineString getLineToPrev()
	{
		return getLine(this, this.getPrevNode());
	}

	public LineString getLineToNext()
	{
		return getLine(this, this.getNextNode());
	}

	private static LineString getLine(Coordinate from, Coordinate to)
	{
		if (fact == null)
		{
			fact = new GeometryFactory();
		}
		return fact.createLineString(new Coordinate[] { to, from });
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
		return (this.getPrevNode().equals2D(other) || this.getNextNode().equals2D(other));
	}

}
