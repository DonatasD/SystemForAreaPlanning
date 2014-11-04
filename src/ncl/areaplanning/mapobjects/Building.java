package ncl.areaplanning.mapobjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Polygon;

public class Building extends MapObject
{
	private Polygon base;
	private Polygon roof;
	private Polygon[] walls;
	private int value;
	private Color color;
	
	
	public Building(Polygon place, Dimension tileSize, Point size, Point startPos, int value)
	{
		super(place, tileSize, size, startPos);
		this.value = value;
		this.color = new Color(150, 143, 145);
		this.calcBase();
		this.calcRoof();
		this.calcWalls();
	}
	public Building(Polygon place, Dimension tileSize, Point size, Point startPos, int value, Color color)
	{
		super(place, tileSize, size, startPos);
		this.value = value;
		this.color = color;
		this.calcBase();
		this.calcRoof();
		this.calcWalls();
	}
	
	@Override
	public void zoom(Tile[][] grid)
	{
		super.zoom(grid);
		this.calcBase();
		this.calcRoof();
		this.calcWalls();
	}
	private void calcBase()
	{
		this.base = new Polygon();
		Point move = new Point((int) (this.getSize().x * 0.4 ), (int) (this.getSize().y  * 0.4 ));
		//Top 0
		this.base.addPoint(this.getPlace().xpoints[0], this.getPlace().ypoints[0] + move.y);
		//Left 1
		this.base.addPoint(this.getPlace().xpoints[1] + move.x, this.getPlace().ypoints[1]);
		//Bottom 2
		this.base.addPoint(this.getPlace().xpoints[2], this.getPlace().ypoints[2] - move.y);
		//Right 3
		this.base.addPoint(this.getPlace().xpoints[3] - move.x, this.getPlace().ypoints[3]);
	}
	private void calcRoof()
	{
		this.roof = new Polygon();
		int move = (int) (this.getSize().x);

		for (int i = 0; i < this.base.npoints; i++)
		{
			Point point = new Point(this.base.xpoints[i], this.base.ypoints[i]);
			this.roof.addPoint(point.x, point.y - move);
		}
	}
	private void calcWalls()
	{
		this.walls = new Polygon[4];
		for (int i = 0; i < 4; i = i + 2)
		{
			this.walls[i] = new Polygon();
			this.walls[i].addPoint(this.base.xpoints[i], this.base.ypoints[i]);
			this.walls[i].addPoint(this.roof.xpoints[i], this.roof.ypoints[i]);
			this.walls[i].addPoint(this.roof.xpoints[i + 1], this.roof.ypoints[i + 1]);
			this.walls[i].addPoint(this.base.xpoints[i + 1], this.base.ypoints[i + 1]);

			int temp = (i + 3) % 4;
			this.walls[i + 1] = new Polygon();
			this.walls[i + 1].addPoint(this.base.xpoints[i], this.base.ypoints[i]);
			this.walls[i + 1].addPoint(this.roof.xpoints[i], this.roof.ypoints[i]);
			this.walls[i + 1].addPoint(this.roof.xpoints[temp], this.roof.ypoints[temp]);
			this.walls[i + 1].addPoint(this.base.xpoints[temp], this.base.ypoints[temp]);
		}
	}
	public int getValue()
	{
		return this.value;
	}
	public Polygon getBase()
	{
		return base;
	}

	public Polygon getRoof()
	{
		return roof;
	}

	public Polygon[] getWalls()
	{
		return walls;
	}
	public Color getColor()
	{
		return color;
	}

	public boolean equals(Object object)
	{
		boolean result = false;
		if (object != null && object instanceof Building)
		{
			result = (this.value == ((Building) object).value && 
					this.getStartPos().equals(((Building) object).getStartPos()) &&
					this.getEndPos().equals(((Building) object).getEndPos()) );
		}
		return result;
	}
	
	
	
}
