package ncl.areaplanning.optimization;

public class PossibleSol implements Comparable<PossibleSol>
{
	int[] obj;
	int value;
	int area;

	public PossibleSol(int no)
	{
		this.obj = new int[no];
		for (int i = 0; i < no; i++)
		{
			obj[i] = 0;
		}
		this.value = 0;
		this.area = 0;
	}
	public PossibleSol(PossibleSol sol)
	{
		if (sol != null)
		{
			this.obj = new int[sol.obj.length];
			for (int i = 0; i < sol.obj.length; i++)
			{
				this.obj[i] = sol.obj[i];
			}
			this.value = sol.value;
			this.area = sol.area;
		}
	}
	@Override
	public String toString()
	{
		String temp = "{ ";
		for (int i = 0; i < obj.length; i++)
		{
			temp = temp + obj[i] + ", ";
		}
		temp = temp + " }";
		return "Sol [rez=" + temp + ", value=" + value + ", area=" + area + "]";
	}
	@Override
	public int compareTo(PossibleSol obj)
	{
		if (this.value > obj.value)
		{
			return -1;
		}
		else if (this.value < obj.value)
		{
			return 1;
		}

		if (this.area > obj.area)
		{
			return 1;
		}
		else if (this.area < obj.area)
		{
			return -1;
		}
		return 0;

	}
}
