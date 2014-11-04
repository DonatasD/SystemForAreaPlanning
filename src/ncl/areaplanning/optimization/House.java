package ncl.areaplanning.optimization;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import ncl.areaplanning.collision.Collision;
import ncl.areaplanning.map.MapScrollPane;
import ncl.areaplanning.mapobjects.Tile;

public class House implements Comparable<House>
{
	private int width;
	private int height;
	private int value;
	private int area;
	private Color color;
	private ArrayList<Rectangle> possiblePos;

	public House(int width, int height, int value, Color color)
	{
		this.width = width;
		this.height = height;
		this.area = width * height;
		this.value = value;
		this.color = color;
		this.possiblePos = new ArrayList<Rectangle>();
	}
	public House(int width, int height, int value,Color color, ArrayList<Rectangle> possiblePos)
	{
		this.width = width;
		this.height = height;
		this.area = width * height;
		this.value = value;
		this.color = color;
		this.possiblePos = possiblePos;
	}
	public House rotate()
	{
		return new House(this.height, this.width, this.value, this.color);
	}
	public void calcPossiblePos(MapScrollPane msp)
	{
		Tile[][] grid = msp.getGrid();
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[i].length; j++)
			{
				if (Collision.noCollision(grid, new Point(i,j), new Point(i + width - 1,j + height - 1)))
				{
					this.possiblePos.add(new Rectangle(i,j,width - 1,height - 1));
				}
			}
		}
	}

	public ArrayList<Rectangle> getPossiblePos()
	{
		return possiblePos;
	}
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
	public int getArea()
	{
		return area;
	}
	public int getValue()
	{
		return value;
	}
	public Color getColor()
	{
		return this.color;
	}
	public static House copy(House house)
	{
		return new House(house.width, house.height, house.value,house.color, new ArrayList<Rectangle>(house.possiblePos));
	}
	public void removePosition(Rectangle pos)
	{
		this.possiblePos.remove(pos);
	}
	@Override
	public boolean equals(Object object)
	{
		boolean result = false;
		if (object != null && object instanceof House)
		{
			result = (this.width == ((House) object).width && this.height == ((House) object).height && this.value == ((House) object).value);
		}
		return result;
	}
	@Override
	public String toString()
	{
		String pos = "";
		for (Rectangle temp: possiblePos)
		{
			pos = pos + "(" +temp.x + ", " + temp.y + ") - (" + (temp.x + temp.width) + ", " + (temp.y + temp.height) + ")" + "\n";
		}
		String rez =  "Width: " + this.width + ", Height: " + this.height + ", Value: " + this.value + "\n" + pos;
		/*if (rotated != null)
		{
			pos = "";
			for (Rectangle temp: rotated.possiblePos)
			{
				pos = pos + "(" +temp.x + ", " + temp.y + ") - (" + (temp.x + temp.width) + ", " + (temp.y + temp.height) + ")" + "\n";
			}
			rez = rez + "Width: " + rotated.width + ", Height: " + rotated.height + ", Value: " + rotated.value + "\n" + pos;
		}*/
		return rez;
	}

	@Override
	public int compareTo(House obj)
	{
		if ((double) this.value /  (double) this.area > (double) obj.value / (double) obj.area)
		{
			return -1;
		}
		else if ((double) this.value / (double) this.area < (double) obj.value / (double) obj.area)
		{
			return 1;
		}
		else
		{
			if (this.area > obj.area)
			{
				return 1;
			}
			else if (this.area < obj.area)
			{
				return -1;
			}
		}
		return 0;
	}

}
