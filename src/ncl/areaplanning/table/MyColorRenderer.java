package ncl.areaplanning.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

public class MyColorRenderer extends JTextArea implements TableCellRenderer
{
	public MyColorRenderer()
	{
		super();
		setOpaque(true);
	}
	public Component getTableCellRendererComponent(	JTable table,
	                                               	Object object,
	                                               	boolean isSelected,
	                                               	boolean hasFocus,
	                                               	int row,
	                                               	int column)
	{
		if (row != table.getRowCount() - 1)
		{
			if (object instanceof Color)
			{
				Color color = (Color) object;
				this.setBackground(color);
				setBorder(BorderFactory.createMatteBorder(2,2,2,2,
						table.getBackground()));
			}
		}
		else
		{
			this.setBackground(table.getBackground());
		}
		return this;
	}
}
