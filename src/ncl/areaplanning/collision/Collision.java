package ncl.areaplanning.collision;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Line2D;

import ncl.areaplanning.mapobjects.Tile;

public class Collision
{
	public static boolean intersects(Polygon obj, Polygon gridBoundary)
	{
		Line2D[] objLines = new Line2D[obj.npoints];
		Line2D[] gridLines = new Line2D[gridBoundary.npoints];

		objLines[0] = new Line2D.Double(obj.xpoints[0], obj.ypoints[0] + 1,
				obj.xpoints[1] + 1, obj.ypoints[1]);
		objLines[1] = new Line2D.Double(obj.xpoints[1] + 1, obj.ypoints[1],
				obj.xpoints[2], obj.ypoints[2] - 1);
		objLines[2] = new Line2D.Double(obj.xpoints[2], obj.ypoints[2] - 1,
				obj.xpoints[3] - 1, obj.ypoints[3]);
		objLines[3] = new Line2D.Double(obj.xpoints[3] - 1, obj.ypoints[3],
				obj.xpoints[0], obj.ypoints[0] + 1);

		for (int i = 0; i < gridLines.length; i++)
		{
			gridLines[i] = new Line2D.Double(gridBoundary.xpoints[i],
					gridBoundary.ypoints[i], gridBoundary.xpoints[(i + 1)
					                                              % gridBoundary.npoints],
					                                              gridBoundary.ypoints[(i + 1) % gridBoundary.npoints]);
		}
		for (int i = 0; i < objLines.length; i++)
		{
			for (int j = 0; j < gridLines.length; j++)
			{
				if (objLines[i].intersectsLine(gridLines[j]))
				{
					return true;
				}
			}
		}
		return false;
	}
	public static boolean insideBounds(Tile[][] grid, Point start, Point end)
	{
		return (start.x < grid.length && start.y < grid[start.x].length
				&& end.x < grid.length && end.y < grid[end.x].length);
	}
	public static boolean noCollision(Tile[][] grid, Point start, Point end)
	{
		for (int i = start.x; i <= end.x; i++ )
		{
			for (int j = start.y; j <= end.y; j++)
			{
				if (grid.length <= i || grid[i].length <= j)
				{
					return false;

				}
				else if (grid[i][j].getObject() != null)
				{
					return false;
				}
				else if (!grid[i][j].isAvailable())
				{
					return false;
				}
			}
		}
		return true;
	}
	
}
