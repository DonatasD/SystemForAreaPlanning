package ncl.areaplanning.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class ColorComboRenderer extends DefaultListCellRenderer
{
	public Component getListCellRendererComponent(	JList<?> list,
	                                              	Object object,
	                                              	int index,
	                                              	boolean isSelected,
	                                              	boolean cellHasFocus)
	{
		if (object instanceof Color)
		{
			setPreferredSize(new Dimension(15, 15));
			setOpaque(true);
			setBackground((Color) object);
		}

		return this;

	}
}
