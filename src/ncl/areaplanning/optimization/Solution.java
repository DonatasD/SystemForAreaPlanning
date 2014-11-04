package ncl.areaplanning.optimization;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;

import ncl.areaplanning.collision.Collision;
import ncl.areaplanning.mapobjects.Building;
import ncl.areaplanning.mapobjects.MapObject;
import ncl.areaplanning.mapobjects.Tile;

public class Solution
{
	private ArrayList<MapObject> mapObjects;
	private Tile[][] grid;
	private ArrayList<MapObject> mapObjectsCopy;
	private Tile[][] gridCopy;
	public int count;
	private Stack<Building> previousChoices;
	private Stack<House> previousHouse;
	private boolean foundSolution;
	private boolean impossible;
	private int value;

	public Solution(ArrayList<MapObject> mapObjects, Tile[][] grid, int value)
	{
		this.value = value;
		this.mapObjects = mapObjects;
		this.grid = grid;
		this.foundSolution = false;
		this.impossible = false;
		this.previousChoices = new Stack<Building>();
		this.previousHouse = new Stack<House>();
	}
	private void calcCont(Optimum opt, Point TILE_SIZE, LinkedList<House> myHouses,int[] currentSol,int[] tempSol, int index, boolean cont)
	{
		while (!myHouses.isEmpty() && !foundSolution && !impossible)
		{

			House house = myHouses.get(0);
			boolean found = false;
			while (!house.getPossiblePos().isEmpty() && !foundSolution)
			{
				Rectangle pos = house.getPossiblePos().get(0);
				Point start = new Point(pos.x, pos.y);
				Point end = new Point(pos.x + pos.width, pos.y + pos.height);
				house.getPossiblePos().remove(pos);
				

				if (Collision.insideBounds(gridCopy, start, end) && Collision.noCollision(gridCopy, start, end))
				{
					found = true;
					this.value += house.getValue();
					Building obj = new Building(gridCopy[start.x][start.y], new Dimension(house.getHeight(), house.getWidth()),
							TILE_SIZE, start, house.getValue(), house.getColor());
					this.mapObjectsCopy.add(obj);
					for (int k = start.x; k < start.x + house.getWidth(); k++)
					{
						for (int l = start.y; l < start.y + house.getHeight(); l++)
						{
							this.gridCopy[k][l].setObject(obj);
						}
					}
					if (found)
					{
						/*for (int i = 0; i < myHouses.size(); i++)
						{
							if (myHouses.get(i).equals(house))
							{
								if (myHouses.get(i).getPossiblePos().contains(pos))
								{
									myHouses.get(i).removePosition(pos);
								}
							}
						}*/
						int temp = calcCurrentSol(currentSol); 
						if (index == temp)
						{
							currentSol[index]--;
							tempSol[index]++;
						}
						else
						{
							index = temp;
							currentSol[index]--;
							tempSol[index]++;
						}
						this.previousChoices.push(obj);
						this.previousHouse.push(myHouses.get(0));
						myHouses.remove(0);
						if (myHouses.isEmpty() && this.value > opt.getBestSolution().value)
						{
							foundSolution = true;
							opt.setBestSolution(new Solution(new ArrayList<MapObject>(this.mapObjectsCopy), Tile.copyGrid(this.gridCopy), this.value));
						}
					}
				}
				if (found)
				{
					if (!myHouses.isEmpty())
					{
						this.calcCont(opt,TILE_SIZE, myHouses,currentSol,tempSol, index,true);
					}
					else
					{
						if (this.value > opt.getBestSolution().value)
						{
							foundSolution = true;
							opt.setBestSolution(new Solution(new ArrayList<MapObject>(this.mapObjectsCopy), Tile.copyGrid(this.gridCopy), this.value));
						}
					}
				}
				else if (!found && house.getPossiblePos().isEmpty())
				{
					int temp = calcCurrentSol(currentSol); 
					index = temp;
					this.value = 0;
					opt.removeSolutions(tempSol, index);
					impossible = true;	
				}
			}
			if (!found && !impossible)
			{
				this.value = 0;
				opt.removeSolutions(tempSol, index);
				impossible = true;
			}
		}
		if (!previousChoices.empty() && !previousHouse.empty() && !myHouses.isEmpty() && !impossible && !foundSolution)
		{
			House tempHouse = previousHouse.pop();
			Building tempBuilding = previousChoices.pop();
			mapObjectsCopy.remove(tempBuilding);
			this.value -= tempBuilding.getValue();
			Point start = new Point (tempBuilding.getStartPos().x, tempBuilding.getStartPos().y);
			Point end = new Point (tempBuilding.getEndPos().x, tempBuilding.getEndPos().y);
			for (int i = start.x; i <= end.x; i++)
			{
				for (int j = start.y; j <= end.y; j++)
				{
					if (gridCopy[i][j].getObject() != null)
					{
						gridCopy[i][j].setObject(null);
					}
				}
			}
			myHouses.addFirst(tempHouse);
			tempBuilding = null;
			System.gc();
		}
		else if(cont && !myHouses.isEmpty() && !impossible && !foundSolution)
		{
			this.calcCont(opt,TILE_SIZE, myHouses,currentSol,tempSol, index, false);
		}
	}
	public void calcNext(Optimum opt, Point TILE_SIZE, ArrayList<House> houses)
	{
		while (!opt.getPossibleSol().isEmpty() && !this.foundSolution)
		{		
			LinkedList<House> myHouses = new LinkedList<House>();
			for (int i = 0; i < opt.getPossibleSol().get(0).obj.length; i++)
			{
				for (int j = 0; j < opt.getPossibleSol().get(0).obj[i]; j++)
				{
					myHouses.add(House.copy(houses.get(i)));

				}
			}
			int[] currentSol = opt.getPossibleSol().get(0).obj; 
			System.out.println(opt.getPossibleSol().get(0));
			opt.getPossibleSol().remove(0);
			this.mapObjectsCopy = new ArrayList<MapObject>(mapObjects);
			this.gridCopy = Tile.copyGrid(grid);
			this.impossible = false;
			this.calcCont(opt, TILE_SIZE, myHouses,currentSol,new int[currentSol.length],0, true);
		}
	}

	public int calcCurrentSol(int[] currentSol)
	{
		for (int i = 0; i < currentSol.length; i++)
		{
			if (currentSol[i] > 0)
			{
				return i;
			}
		}
		return -1;
	}

	public ArrayList<MapObject> getMapObjects()
	{
		return mapObjects;
	}

	public Tile[][] getGrid()
	{
		return grid;
	}
	public int getValue()
	{
		return this.value;
	}


}
