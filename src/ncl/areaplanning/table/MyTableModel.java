package ncl.areaplanning.table;

import java.awt.Color;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel
{
	private String[] columnNames =
		{	" ",
			"Id",
			"Color",
			"Width",
			"Height",
			"Value",
			"Rotate" };

	private Object[][] data =
		{
			{	new String("+"),
				new String(" "),
				new String(" "),
				new String(" "),
				new String(" "),
				new String(" "),
				new String(" ") } };
	
	public Object[][] getData()
	{
		return data;
		
	}
	
	public int getColumnCount()
	{
		return columnNames.length;
	}

	public int getRowCount()
	{
		return data.length;
	}

	public String getColumnName(int col)
	{
		return columnNames[col];
	}

	public Object getValueAt(int row, int col)
	{
		return data[row][col];
	}

	public Class getColumnClass(int c)
	{
		if (getValueAt(0, c) == null)
		{
			return String.class;
		}

		return getValueAt(0, c).getClass();
	}

	public boolean isCellEditable(int row, int col)
	{
		if (col > 1 && row != getRowCount() - 1 || col == 0)
		{
			return true;
		}
		return false;
	}

	public void setValueAt(Object value, int row, int col)
	{
		data[row][col] = value;
		fireTableCellUpdated(row, col);
	}

	public void removeRow(int row)
	{
		if (getRowCount() > 0)
		{
			Object[][] temp = new Object[data.length - 1][data[0].length];
			for (int i = 0; i < temp.length - 1; i++)
			{
				if (i < row)
				{
					temp[i] = data[i];
				}
				else
				{
					temp[i] = data[i + 1];
					
				}
				temp[i][1] = new Integer(i + 1);
			}
			Object[] newData =
				{	new String("+"),
					new String(" "),
					new String(" "),
					new String(" "),
					new String(" "),
					new String(" "),
					null };
			temp[temp.length - 1] = newData;
			this.data = temp;
		}
		fireTableRowsDeleted(row, row);
	}

	public void addRow(Object[] data)
	{
		if (getRowCount() > 0 && getRowCount() < 21)
		{
			Object[][] temp = new Object[this.data.length + 1][this.data[0].length];
			for (int i = 0; i < temp.length - 1; i++)
			{
				temp[i] = this.data[i];
			}
			Object[] newData =
				{	new String("-"),
					new Integer(temp.length - 1),
					Color.GRAY,
					new Integer(1),
					new Integer(1),
					new Integer(1),
					new Boolean(false) };
			temp[temp.length - 2] = newData;
			if (getRowCount() != 20)
			{
				temp[temp.length - 1] = data;
			}
			this.data = temp;
			int id = getRowCount() - 1;
			fireTableDataChanged();
			fireTableRowsInserted(id, id);
		}
	}
}
