package ncl.areaplanning.map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ncl.areaplanning.SystemForAreaPlanning;
import ncl.areaplanning.collision.Collision;
import ncl.areaplanning.handlers.MouseHandler;
import ncl.areaplanning.handlers.MouseMotionHandler;
import ncl.areaplanning.mapobjects.Building;
import ncl.areaplanning.mapobjects.MapObject;
import ncl.areaplanning.mapobjects.Road;
import ncl.areaplanning.mapobjects.Tile;
import ncl.areaplanning.optimization.House;
import ncl.areaplanning.optimization.Optimum;
import ncl.areaplanning.optimization.Solution;

public class MapScrollPane extends JScrollPane
{

	private MapView mapView;
	private SystemForAreaPlanning sfap;

	private Dimension area;

	private Line2D[] horLines;
	private Line2D[] verLines;
	private Tile[][] grid;
	private int gridArea;
	private ArrayList<MapObject> mapObjects;
	private MapObject currentObject;
	private Polygon gridBoundary;

	// min 12-6
	// max 192-96
	public Point TILE_SIZE = new Point(48, 24);
	private int TILE_COUNT_WIDTH;
	private int TILE_COUNT_HEIGHT;

	private final static Point MAX_TILE_SIZE = new Point(192, 96);
	private final static Point MIN_TILE_SIZE = new Point(6, 3);

	private int startX = TILE_SIZE.x * 2;
	private int startY = TILE_SIZE.y * 4;

	private boolean created = false;
	private boolean showGrid = false;
	private boolean showCenter = false;

	public MapScrollPane(SystemForAreaPlanning sfap)
	{

		this.sfap = sfap;
		this.mapView = new MapView();
		this.area = new Dimension(0, 0);
		this.gridBoundary = new Polygon();
		this.mapObjects = new ArrayList<MapObject>();

		this.setViewportView(this.mapView);

		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		getVerticalScrollBar().setUnitIncrement(TILE_SIZE.x);
		getHorizontalScrollBar().setUnitIncrement(TILE_SIZE.y);

		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

	}

	public class MapView extends JPanel
	{

		public MapView()
		{
			this.addMouseListener(new MouseHandler(sfap));
			this.addMouseMotionListener(new MouseMotionHandler(sfap));
		}

		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			// 2.5D

			// Showing grids
			if (grid != null)
			{
				if (showGrid)
				{
					Graphics2D g2d = (Graphics2D) g.create();
					g2d.setColor(new Color(224, 189, 132));
					g2d.fill(gridBoundary);
					g2d.setColor(Color.BLACK);
					g2d.draw(gridBoundary);
					g2d.dispose();
					for (int i = 0; i < verLines.length; i++)
					{
						g2d = (Graphics2D) g.create();
						g2d.setColor(Color.BLACK);
						g2d.draw(verLines[i]);
						g2d.dispose();
					}
					for (int i = 0; i < horLines.length; i++)
					{
						g2d = (Graphics2D) g.create();
						g2d.setColor(Color.BLACK);
						g2d.draw(horLines[i]);
						g2d.dispose();
					}
				}
				else
				{
					Graphics2D g2d = (Graphics2D) g.create();
					g2d.setColor(new Color(224, 189, 132));
					g2d.fill(gridBoundary);
					g2d.setColor(Color.BLACK);
					g2d.drawPolygon(gridBoundary);
					g2d.dispose();
				}

				for (int i = 0; i < grid.length; i++)
				{
					for (int j = 0; j < grid[i].length; j++)
					{

						if (!grid[i][j].isAvailable())
						{
							Graphics2D g2d = (Graphics2D) g.create();
							g2d.setColor(new Color(238, 238, 238));
							g2d.draw(grid[i][j]);
							g2d.fill(grid[i][j]);
						}
					}
				}
				for (int i = 0; i < grid.length; i++)
				{
					for (int j = 0; j < grid[i].length; j++)
					{
						MapObject temp = grid[i][j].getObject();

						if (temp != null)
						{
							preparePaintObject(g, temp);
						}
					}
				}
				if (currentObject != null)
				{
					paintObject(g, currentObject);
					currentObject.setPainted(false);
				}
				resetObjects();
			}

			// Centering map
			if (showCenter)
			{
				showCenter = false;
				Rectangle bounds = getViewport().getViewRect();
				Dimension size = getViewport().getViewSize();
				int x = (size.width - bounds.width) / 2;
				int y = (size.height - bounds.height) / 2;
				getViewport().setViewPosition(new Point(x, y));
			}

		}
		private void resetObjects()
		{
			for (int i = 0; i < grid.length; i++)
			{
				for (int j = 0; j < grid[i].length; j++)
				{
					if (grid[i][j].getObject() != null)
					{
						grid[i][j].getObject().setPainted(false);
					}
				}
			}
		}
		private void preparePaintObject(Graphics g, MapObject mapObject)
		{
			if (!mapObject.isPainted())
			{
				if ((mapObject.getTileSize().height > 1) && mapObject.getStartPos().y != 0)
				{
					for (int i = 0; i < mapObject.getStartPos().y; i++)
					{
						for (int j = mapObject.getStartPos().x + 1; j <= mapObject.getEndPos().x; j++)
						{
							if (grid[j][i].getObject() != null)
							{
								preparePaintObject(g,grid[j][i].getObject());
							}
						}
					}
				}
				if ((mapObject.getTileSize().width > 1) && mapObject.getStartPos().x != 0)
				{
					for (int i = 0; i < mapObject.getStartPos().x; i++)
					{
						for (int j = mapObject.getStartPos().y + 1; j <= mapObject.getEndPos().y; j++)
						{
							if (grid[i][j].getObject() != null)
							{
								preparePaintObject(g,grid[i][j].getObject());
							}
						}
					}
				}
				paintObject(g,mapObject);
			}
		}
		private void paintObject(Graphics g, MapObject mapObject)
		{
			Graphics2D g2d = (Graphics2D) g.create();

			if (mapObject instanceof Building)
			{
				Building temp = (Building) mapObject;
				g2d.setColor(new Color(36, 32, 33));
				g2d.fill(temp.getPlace());

				g2d.setColor(Color.BLACK);
				g2d.draw(temp.getPlace());

				g2d.setColor(temp.getColor());
				g2d.fill(temp.getRoof());
				for (int k = 0; k < temp.getWalls().length; k++)
				{
					g2d.fill(temp.getWalls()[k]);
				}
				g2d.setColor(Color.BLACK);
				g2d.draw(temp.getRoof());
				for (int k = temp.getWalls().length - 1; k > 1; k--)
				{
					g2d.draw(temp.getWalls()[k]);
				}
				g2d.dispose();
			}
			else if (mapObject instanceof Road)
			{
				Road temp = (Road) mapObject;
				Polygon pol = temp.getPlace();
				g2d.drawImage(temp.getTexture(), pol.xpoints[1], pol.ypoints[0], Math.abs(pol.xpoints[3] - pol.xpoints[1]),Math.abs(pol.ypoints[2] - pol.ypoints[0]), this);
				g2d.dispose();
			}
			else
			{
				if (sfap.hand > 0 && sfap.hand < 3)
				{
					if (!Collision.intersects(mapObject.getPlace(), gridBoundary) && Collision.noCollision(grid, mapObject.getStartPos(), mapObject.getEndPos()))
					{
						g2d.setColor(Color.GREEN);
					}
					else
					{
						g2d.setColor(Color.RED);
					}
					g2d.fill(mapObject.getPlace());
					g2d.draw(mapObject.getPlace());
					g2d.dispose();
				}
				else if (sfap.hand == 3)
				{
					if (!Collision.noCollision(grid, mapObject.getStartPos(), mapObject.getEndPos()))
					{
						g2d.setColor(new Color(224, 189, 132));
						g2d.draw(mapObject.getPlace());
						g2d.fill(mapObject.getPlace());
						g2d.setColor(Color.RED);
						g2d.drawLine(mapObject.getPlace().xpoints[0],mapObject.getPlace().ypoints[0], mapObject.getPlace().xpoints[2], mapObject.getPlace().ypoints[2]);
						g2d.drawLine(mapObject.getPlace().xpoints[1],mapObject.getPlace().ypoints[1], mapObject.getPlace().xpoints[3], mapObject.getPlace().ypoints[3]);
						g2d.dispose();
					}
					else
					{
						g2d.setColor(Color.RED);
						g2d.draw(mapObject.getPlace());
						g2d.fill(mapObject.getPlace());
						g2d.dispose();
					}
				}
				else if (sfap.hand == 4)
				{
					if (!grid[mapObject.getStartPos().x][mapObject.getStartPos().y].isAvailable())
					{
						g2d.setColor(new Color(224, 189, 132));
						g2d.draw(mapObject.getPlace());
						g2d.fill(mapObject.getPlace());
						g2d.dispose();
					}
					else
					{
						g2d.setColor(Color.RED);
						g2d.draw(mapObject.getPlace());
						g2d.fill(mapObject.getPlace());
						g2d.dispose();
					}
				}
				else if (sfap.hand == 5)
				{
					if (Collision.noCollision(grid, mapObject.getStartPos(), mapObject.getEndPos()))
					{
						g2d.setColor(new Color(238, 238, 238));
						g2d.draw(mapObject.getPlace());
						g2d.fill(mapObject.getPlace());
						g2d.setColor(Color.RED);
						g2d.drawLine(mapObject.getPlace().xpoints[0],mapObject.getPlace().ypoints[0], mapObject.getPlace().xpoints[2], mapObject.getPlace().ypoints[2]);
						g2d.drawLine(mapObject.getPlace().xpoints[1],mapObject.getPlace().ypoints[1], mapObject.getPlace().xpoints[3], mapObject.getPlace().ypoints[3]);
						g2d.dispose();
					}
					else
					{
						g2d.setColor(Color.RED);
						g2d.draw(mapObject.getPlace());
						g2d.fill(mapObject.getPlace());
						g2d.dispose();
					}
				}
			}
			mapObject.setPainted(true);
		}

	}
	public void createMap(Integer width, Integer height)
	{
		TILE_COUNT_WIDTH = width;
		TILE_COUNT_HEIGHT = height;

		this.gridBoundary = new Polygon();
		this.gridArea = width * height;
		this.mapObjects.clear();
		this.grid = new Tile[width][height];
		this.verLines = new Line2D[width - 1];
		this.horLines = new Line2D[height - 1];
		createGrid();
		this.created = true;
	}
	public void removeMap()
	{
		if (created)
		{
			this.gridBoundary = null;
			this.grid = null;
			this.gridArea = 0;
			this.verLines = null;
			this.horLines = null;
			this.mapObjects.clear();
			area = new Dimension(0, 0);
			this.TILE_COUNT_HEIGHT = 0;
			this.TILE_COUNT_WIDTH = 0;
			this.mapView.repaint();
			created = false;
		}
	}
	public void updateMap()
	{
		if (created)
		{
			this.gridBoundary = new Polygon();
			this.grid = new Tile[TILE_COUNT_WIDTH][TILE_COUNT_HEIGHT];
			this.verLines = new Line2D[TILE_COUNT_WIDTH - 1];
			this.horLines =  new Line2D[TILE_COUNT_HEIGHT - 1];
			createGrid();
		}
	}
	public void createGrid()
	{
		this.area = new Dimension(0, 0);
		this.showCenter = true;

		boolean changed = false;
		int dist;
		if (TILE_COUNT_WIDTH >= TILE_COUNT_HEIGHT)
		{
			dist = TILE_COUNT_WIDTH;
		}
		else
		{
			dist = TILE_COUNT_HEIGHT;
		}
		for (int x = 0; x < TILE_COUNT_WIDTH; x++)
		{
			for (int y = 0; y < TILE_COUNT_HEIGHT; y++)
			{
				this.grid[x][y] = new Tile(true);
				// Top point 0
				this.grid[x][y].addPoint(TILE_SIZE.x * (dist - y + x) + startX,
						TILE_SIZE.y * (y + x) + startY);
				// Left point 1
				this.grid[x][y].addPoint(TILE_SIZE.x * (dist - y - 1 + x)
						+ startX, TILE_SIZE.y * (y + 1 + x) + startY);
				// Bottom point 2
				this.grid[x][y].addPoint(TILE_SIZE.x * (dist - y + x) + startX,
						TILE_SIZE.y * (y + 2 + x) + startY);
				// Right point 3
				this.grid[x][y].addPoint(TILE_SIZE.x * (dist - y + 1 + x)
						+ startX, TILE_SIZE.y * (y + 1 + x) + startY);
			}
		}
		for (int i = 0; i < TILE_COUNT_WIDTH - 1; i++)
		{
			Point start = new Point(grid[i + 1][0].xpoints[0],
					grid[i + 1][0].ypoints[0]);
			Point end = new Point(
					grid[i + 1][TILE_COUNT_HEIGHT - 1].xpoints[1],
					grid[i + 1][TILE_COUNT_HEIGHT - 1].ypoints[1]);
			this.verLines[i] = new Line2D.Double(start, end);
		}
		for (int i = 0; i < TILE_COUNT_HEIGHT - 1; i++)
		{
			Point start = new Point(grid[0][i + 1].xpoints[0],
					grid[0][i + 1].ypoints[0]);
			Point end = new Point(grid[TILE_COUNT_WIDTH - 1][i + 1].xpoints[3],
					grid[TILE_COUNT_WIDTH - 1][i + 1].ypoints[3]);
			this.horLines[i] = new Line2D.Double(start, end);
		}
		this.gridBoundary.addPoint(TILE_SIZE.x * dist + startX, startY);
		this.gridBoundary.addPoint(TILE_SIZE.x * (dist - TILE_COUNT_HEIGHT)
				+ startX, TILE_SIZE.y * TILE_COUNT_HEIGHT + startY);
		this.gridBoundary.addPoint(TILE_SIZE.x
				* (dist - (TILE_COUNT_HEIGHT - 1) + (TILE_COUNT_WIDTH - 1))
				+ startX, TILE_SIZE.y * (TILE_COUNT_WIDTH + TILE_COUNT_HEIGHT)
				+ startY);
		this.gridBoundary.addPoint(TILE_SIZE.x * (dist + TILE_COUNT_WIDTH)
				+ startX, TILE_SIZE.y * TILE_COUNT_WIDTH + startY);

		int current_width = (startX * 2 + TILE_SIZE.x * (dist * 2));
		if (current_width > area.width)
		{
			area.width = current_width;
			changed = true;
		}

		int current_height = (startY * 2 + TILE_SIZE.y * (dist * 2));
		if (current_height > area.height)
		{
			area.height = current_height;
			changed = true;
		}

		if (changed)
		{
			mapView.setPreferredSize(area);
			mapView.revalidate();
		}
		mapView.repaint();
	}
	public void showGrid()
	{
		this.showGrid = !this.showGrid;
		mapView.repaint();
	}
	public void addObject(int id, int x, int y)
	{
		Point p = new Point(x, y);
		if (gridBoundary.contains(p))
		{
			boolean found = false;
			for (int i = 0; i < grid.length && !found; i++)
			{
				for (int j = 0; j < grid[i].length; j++)
				{
					if (grid[i][j].contains(p))
					{
						Tile temp = grid[i][j];
						Point position = new Point(i, j);
						switch(id) 
						{
							case 1:
								addHouse(temp, position);
								break;
							case 2:
								addRoad(temp, position);
								break;
							case 3:
								deleteObject(temp);
								break;
							case 4:
								recreateTile(position);
								break;
							case 5:
								deleteTile(position);
								break;
						}

					}
				}
			}
		}
	}
	private void recreateTile(Point position)
	{
		if (grid[position.x][position.y] != null && grid[position.x][position.y].getObject() == null)
		{
			grid[position.x][position.y].setAvailable(true);
			gridArea++;
		}
	}
	private void deleteTile(Point position)
	{
		if (grid[position.x][position.y] != null && grid[position.x][position.y].getObject() == null)
		{
			grid[position.x][position.y].setAvailable(false);
			gridArea--;
		}
	}
	private void deleteObject(Tile temp)
	{
		if (temp.getObject() != null)
		{
			boolean road = false;
			if (temp.getObject() instanceof Road)
			{
				road = true;
			}
			mapObjects.remove(temp.getObject());
			Point start = new Point (temp.getObject().getStartPos().x, temp.getObject().getStartPos().y);
			Point end = new Point (temp.getObject().getEndPos().x, temp.getObject().getEndPos().y);
			for (int i = start.x; i <= end.x; i++)
			{
				for (int j = start.y; j <= end.y; j++)
				{
					if (grid[i][j].getObject() != null)
					{
						grid[i][j].setObject(null);
						gridArea++;
					}
				}
			}
			System.out.println(gridArea);
			if (road)
			{
				MapObject[] nearObjects = getNearObjects(start.x, start.y);
				for (int i = 0; i < nearObjects.length; i++)
				{
					if (nearObjects[i] != null && nearObjects[i] instanceof Road)
					{
						findTexture(nearObjects[i], nearObjects[i].getStartPos().x, nearObjects[i].getStartPos().y, "LRTB", true);
					}
				}
			}

		}

	}
	private void addRoad(Tile temp, Point position)
	{
		MapObject obj = new Road(temp, sfap.objSize, this.TILE_SIZE, position);
		if (Collision.noCollision(grid, obj.getStartPos(), obj.getEndPos()))
		{
			if (!Collision.intersects(obj.getPlace(), gridBoundary))
			{
				this.mapObjects.add(obj);
				for (int index = 0; index < mapObjects.size(); index++)
				{
					if (mapObjects.get(index).equals(obj))
					{
						findTexture(obj , obj.getStartPos().x, obj.getStartPos().y, "LRTB", true);
						this.grid[position.x][position.y].setObject(this.mapObjects.get(index));
						gridArea--;
						break;
					}
				}
			}

		}
	}
	private void addHouse(Tile temp, Point position)
	{
		MapObject obj = new Building(temp, sfap.objSize,
				this.TILE_SIZE, position, 0);
		if (Collision.noCollision(grid, obj.getStartPos(), obj.getEndPos()))
		{
			if (!Collision.intersects(obj.getPlace(), gridBoundary))
			{
				for (int k = position.x; k < position.x + sfap.objSize.height; k++)
				{
					for (int l = position.y; l < position.y + sfap.objSize.width; l++)
					{
						gridArea--;
						this.mapObjects.add(obj);
						this.grid[k][l].setObject(obj);
					}
				}
			}
		}
	}
	private void findTexture(MapObject obj, int x, int y, String text, boolean iterate)
	{
		if (obj instanceof Road)
		{
			String result = text;
			// 0 - Bottom, 1- Top, 2 - Right, 3 - Left
			MapObject[] nearObjects = getNearObjects(x, y);

			result = nearObjects[0] != null && (nearObjects[0] instanceof Road) ? result.replace("B", "") : result;
			result = nearObjects[1] != null && (nearObjects[1] instanceof Road) ? result.replace("T", "") : result;
			result = nearObjects[2] != null && (nearObjects[2] instanceof Road) ? result.replace("R", "") : result;
			result = nearObjects[3] != null && (nearObjects[3] instanceof Road) ? result.replace("L", "") : result;

			switch (result)
			{
				case "":
					result = "C";
					break;
				case "LRTB":
					result = "Basic";
					break;
				case "RTB":
					result = "TB";
					break;
				case "LTB":
					result = "TB";
					break;
				case "LRT":
					result = "LR";
					break;
				case "LRB":
					result = "LR";
					break;
			}
			Road temp = (Road) obj;
			temp.setTexture(result);
			if (iterate)
			{
				for (int i = 0; i < nearObjects.length; i++)
				{
					if (nearObjects[i] != null)
					{
						if (nearObjects[i] instanceof Road)
						{
							Point cord = nearObjects[i].getStartPos();
							String tempText = text;
							tempText = i == 0 ? tempText.replace("T", "") : tempText;
							tempText = i == 1 ? tempText.replace("B", "") : tempText;
							tempText = i == 2 ? tempText.replace("L", "") : tempText;
							tempText = i == 3 ? tempText.replace("R", "") : tempText;
							findTexture(nearObjects[i], cord.x, cord.y,
									tempText, false);
						}
					}
				}
			}
		}
	}
	private MapObject[] getNearObjects(int x, int y)
	{
		MapObject[] nearObjects = new MapObject[4];

		nearObjects[0] = x < grid.length-1 ? grid[x+1][y].getObject() : null;
		nearObjects[1] = x > 0 ? grid[x-1][y].getObject() : null;
		nearObjects[2] = y > 0 ? grid[x][y-1].getObject() : null;
		nearObjects[3] = y < grid[x].length-1 ? grid[x][y+1].getObject() : null;

		return nearObjects;
	}
	public void zoomIn() { 
		if (created)
		{
			if (TILE_SIZE.x < MAX_TILE_SIZE.x && TILE_SIZE.y < MAX_TILE_SIZE.y) 
			{ 
				this.TILE_SIZE.x = this.TILE_SIZE.x * 2;
				this.TILE_SIZE.y = this.TILE_SIZE.y * 2; 
				zoom();
			} 
		}
	} 	
	public void zoomOut() 
	{ 
		if (created)
		{
			if (TILE_SIZE.x > MIN_TILE_SIZE.x && TILE_SIZE.y > MIN_TILE_SIZE.y) 
			{ 
				this.TILE_SIZE.x = this.TILE_SIZE.x / 2;
				this.TILE_SIZE.y = this.TILE_SIZE.y / 2; 
				zoom();
			} 
		}
	}
	public void zoom()
	{
		MapObject[][] objRef = new MapObject[TILE_COUNT_WIDTH][TILE_COUNT_HEIGHT];
		boolean[][] availability = new boolean[TILE_COUNT_WIDTH][TILE_COUNT_HEIGHT]; 

		for (int i = 0; i < objRef.length; i++)
		{
			for (int j = 0; j < objRef[i].length; j++)
			{
				objRef[i][j] = this.grid[i][j].getObject();
				availability[i][j] = this.grid[i][j].isAvailable();
			}
		}
		updateMap();
		for (MapObject obj : this.mapObjects)
		{
			if (obj instanceof Building)
			{
				Building temp = (Building) obj;
				temp.zoom(this.grid);
			}
			else if (obj instanceof Road)
			{
				Road temp = (Road) obj;
				temp.zoom(this.grid);
			}
		}
		for (int i = 0; i < this.grid.length; i++)
		{
			for (int j = 0; j < this.grid[i].length; j++)
			{
				this.grid[i][j].setObject(objRef[i][j]);
				this.grid[i][j].setAvailable(availability[i][j]);
			}
		}
	}
	public void optimize(ArrayList<House> houses)
	{
		if (created)
		{
			int start = (int) System.currentTimeMillis();
			Optimum opt = new Optimum(this, houses);
			this.grid = opt.getBestSolution().getGrid();
			this.mapObjects = opt.getBestSolution().getMapObjects();
			int area = 0;
			for (int i = 0; i < grid.length; i++)
			{
				for (int j = 0; j < grid[i].length; j++)
				{
					if (grid[i][j].isAvailable() && grid[i][j].getObject() == null)
					{
						area++;
					}
				}
			}
			this.gridArea = area;
			opt = new Optimum(this, houses);
			this.grid = opt.getBestSolution().getGrid();
			this.mapObjects = opt.getBestSolution().getMapObjects();
			area = 0;
			for (int i = 0; i < grid.length; i++)
			{
				for (int j = 0; j < grid[i].length; j++)
				{
					if (grid[i][j].isAvailable() && grid[i][j].getObject() == null)
					{
						area++;
					}
				}
			}
			System.out.println(opt.getBestSolution().getValue());
			int end = (int) System.currentTimeMillis();
			System.out.println((end - start) / 1000);
			repaint();
		}
	}

	public int getGridArea()
	{
		return gridArea;
	}
	public ArrayList<MapObject> getMapObjects()
	{
		return mapObjects;
	}
	public boolean isCreated()
	{
		return created;
	}
	public Tile[][] getGrid()
	{
		return grid;
	}
	public Polygon getGridBoundary()
	{
		return gridBoundary;
	}
	public MapView getMapView()
	{
		return this.mapView;
	}
	public void setCurrentObject(MapObject currentObject)
	{
		this.currentObject = currentObject;
	}

	public void printGrid()
	{
		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid.length; j++)
			{
				if (grid[i][j].getObject() != null)
				{
					System.out.println(grid[i][j].getObject().getStartPos() + ", " + grid[i][j].getObject().getEndPos() + ", " + grid[i][j].getObject());
				}
			}
		}
	}

}
