package ncl.areaplanning.mapobjects;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Polygon;

public class MapObject 
{
	private Polygon place;
	private Dimension tileSize;
	private Point size;
	private Point endPos;
	private boolean painted;
	//Top point 0
	//Left point 1
	//Bottom point 2
	//Right point 3

	//height segments
	
	private Point startPos;
	

	public MapObject(Polygon place, Dimension tileSize, Point size, Point startPos)
	{
		this.endPos = new Point(startPos.x + tileSize.height - 1, startPos.y + tileSize.width - 1);
		this.size = size;
		this.startPos = startPos;
		this.tileSize = tileSize;
		this.painted = false;
		this.calcPlace(place);
	}
	public void calcPlace(Polygon place)
	{
		this.place = new Polygon();
		this.place.addPoint(place.xpoints[0], place.ypoints[0]);

		Point dist = new Point(Math.abs(place.xpoints[0] - place.xpoints[1]), Math.abs(place.ypoints[0] - place.ypoints[1]));
		this.place.addPoint(place.xpoints[0] - (dist.x * this.getTileSize().width) , place.ypoints[0] + (dist.y * this.getTileSize().width));
		this.getTileSize();
		dist = new Point(Math.abs(place.xpoints[1] - place.xpoints[2]), Math.abs(place.ypoints[1] - place.ypoints[2]));
		this.place.addPoint(this.place.xpoints[1] + (dist.x * this.getTileSize().height) ,this.place.ypoints[1] + (dist.y * this.getTileSize().height));

		dist = new Point(Math.abs(place.xpoints[2] - place.xpoints[3]), Math.abs(place.ypoints[2] - place.ypoints[3]));
		this.place.addPoint(this.place.xpoints[2] + (dist.x * this.getTileSize().width) , this.place.ypoints[2] - (dist.y * this.getTileSize().width));
	}
	public Polygon getPlace()
	{
		return this.place;
	}
	public Point getSize()
	{
		return this.size;
	}
	public Dimension getTileSize()
	{
		return tileSize;
	}
	public boolean isPainted()
	{
		return painted;
	}
	public Point getStartPos()
	{
		return startPos;
	}
	public Point getEndPos()
	{
		return endPos;
	}
	public void setPainted(boolean painted)
	{
		this.painted = painted;
	}
	public void zoom(Tile[][]grid)
	{
		this.calcPlace(grid[this.getStartPos().x][this.getStartPos().y]);
	}
	
}
