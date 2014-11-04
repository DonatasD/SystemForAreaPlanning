package ncl.areaplanning.optimization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;
import java.awt.Point;
import java.awt.Rectangle;

import ncl.areaplanning.map.MapScrollPane;
import ncl.areaplanning.mapobjects.MapObject;
import ncl.areaplanning.mapobjects.Tile;

public class Optimum
{
	private Solution startSolution;
	private ArrayList<PossibleSol> possibleSol;
	private ArrayList<House> houses;
	private Solution bestSolution;
	private MapScrollPane msp;
	private int[] max;

	public Optimum(MapScrollPane msp, ArrayList<House> houses)
	{
		this.msp = msp;
		this.houses = houses;
		Collections.sort(this.houses);
		
		this.possibleSol = new ArrayList<PossibleSol>();
		this.max = new int[this.houses.size()];
		for (int i = 0; i < max.length; i++)
		{
			this.max[i] = msp.getGridArea() / this.houses.get(i).getArea();
		}
		calcPossibleSol();
		Collections.sort(this.possibleSol);

		this.startSolution = new Solution(new ArrayList<MapObject>(msp.getMapObjects()), Tile.copyGrid(msp.getGrid()), 0);
		this.bestSolution = new Solution(new ArrayList<MapObject>(msp.getMapObjects()), Tile.copyGrid(msp.getGrid()), 0);
		this.startSolution.calcNext(this, msp.TILE_SIZE, houses);
	}
	private void calcPossibleSol()
	{
		for (int i = 0; i <= max[0]; i++)
		{
			PossibleSol sol = new PossibleSol(max.length);
			sol.obj[0] = i;
			sol.value = i * this.houses.get(0).getValue();
			sol.area = i * this.houses.get(0).getArea();
			if (max.length > 1)
			{
				int step = 1;
				calcPossibleSol(step, sol);
			}
			else
			{
				if (!possibleSol.contains(sol))
				{
					possibleSol.add(sol);
				}
			}
		}
	}
	private void calcPossibleSol(int step, PossibleSol prev)
	{
		for (int i = 0; i <= max[step]; i++)
		{
			PossibleSol sol = new PossibleSol(prev);
			sol.obj[step] = i;
			sol.value = sol.value + i * houses.get(step).getValue();
			sol.area = sol.area + i * houses.get(step).getArea();
			if (sol.area > msp.getGridArea())
			{
				break;
			}
			if (step < max.length - 1)
			{
				calcPossibleSol(step + 1, sol);
			}
			else
			{
				if (!possibleSol.contains(sol))
				{
					possibleSol.add(sol);
				}
			}
		}
	}
	public Solution getBestSolution()
	{
		return bestSolution;
	}
	public void setBestSolution(Solution bestSolution)
	{
		this.bestSolution = bestSolution;
	}
	public ArrayList<PossibleSol> getPossibleSol()
	{
		return this.possibleSol;
	}
	public void removeSolutions(int[] sol, int index)
	{
		ArrayList<PossibleSol> temp = new ArrayList<PossibleSol>();
		for (int i= 0; i < sol.length; i++)
		{
			System.out.print(sol[i] + " ");
		}
		System.out.println();
		for (PossibleSol current: this.possibleSol)
		{
			boolean found = true;
			for (int j = 0; j < index; j++)
			{
				if (current.obj[j] != sol[j])
				{
					found = false;
					break;
				}
			}
			if (found)
			{
				if (current.obj[index] > sol[index])
				{
					System.out.println(current);
					temp.add(current);
				}
			}
		}
		this.possibleSol.removeAll(temp);
		System.out.println("Done");
	}
	public MapScrollPane getMsp()
	{
		return msp;
	}
	
	
}
