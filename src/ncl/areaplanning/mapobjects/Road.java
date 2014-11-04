package ncl.areaplanning.mapobjects;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.ImageIcon;

public class Road extends MapObject
{
	private Image texture;

	public Road(Polygon place, Dimension tileSize, Point size, Point startPos)
	{
		super(place, tileSize, size, startPos);
		this.setTexture("Basic");
	}
	public void setTexture(String key)
	{
		this.texture = new ImageIcon("res/objects/map/" + RoadTexture.textureMap.get(key)).getImage();
	}
	public Image getTexture()
	{
		return this.texture;
	}
	@Override
	public void zoom(Tile[][] grid)
	{
		super.zoom(grid);
	}

}
