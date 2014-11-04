package ncl.areaplanning.mapobjects;

import java.awt.Polygon;
import java.awt.Rectangle;

public class Tile extends Polygon{
	private boolean available;
	private MapObject object;

	public Tile()
	{
		super();
		this.object = null;
		this.available = false;
	}
	public Tile(boolean available)
	{
		super();
		this.object = null;
		this.available = available;
	}
	public Tile(Tile tile, boolean available, MapObject object)
	{
		super(tile.xpoints,tile.ypoints, tile.npoints);
		this.available = available;
		this.object = object;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public MapObject getObject()
	{
		return object;
	}
	public void setObject(MapObject object)
	{
		this.object = object;
	}
	public Tile copyTile()
	{
		return new Tile(this, this.available, this.object);
	}
	public Tile[][] setAreaUnavailable(Tile[][] grid, Rectangle area)
	{
		if (area.x >= 0 && area.y >= 0 && area.width < grid.length && area.height < grid[0].length)
		{
			for (int i = area.x; i < area.width; i++)
			{
				for (int j = area.y; j < area.height; j++)
				{
					grid[i][j].setAvailable(false);
				}
			}
		}
		return copyGrid(grid);
	}
	public static Tile[][] copyGrid(Tile[][] grid)
	{
		Tile[][] copy = new Tile[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[i].length; j++)
			{
				copy[i][j] = grid[i][j].copyTile();
			}
		}
		return copy;
	}
	public static String printGrid(Tile[][] grid)
	{
		String rez = "";
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[i].length; j++)
			{
				if(grid[i][j].getObject() == null && grid[i][j].available)
				{
					rez = rez + "1"; 
				}
				else
				{
					rez = rez + "0";
				}
			}
			rez = rez + "\n";
		}
		return rez;
	}
}
