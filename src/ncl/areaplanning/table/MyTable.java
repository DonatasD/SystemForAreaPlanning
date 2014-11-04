package ncl.areaplanning.table;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

public class MyTable extends JTable
{
	Color[] colors =

		{	Color.GRAY,
			Color.WHITE,
			Color.PINK,
			Color.RED,
			Color.ORANGE,
			Color.YELLOW,
			Color.GREEN,
			Color.CYAN,
			Color.BLUE,
			Color.MAGENTA };
	JComboBox<Color> colorBox = new JComboBox<Color>(colors);

	public MyTable()
	{
		super(new MyTableModel());
		putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		initSelectionMode();
		initVisual();
		initSize();
		initEditting();
		Action delete = new AbstractAction()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        JTable table = (JTable)e.getSource();
		        int modelRow = Integer.valueOf( e.getActionCommand() );
		        if (modelRow != table.getRowCount()-1)
		        {
		        	System.out.println(table.getRowCount() + ", " +modelRow);
		        	((MyTableModel) table.getModel()).removeRow(modelRow);
		        }
		        else
		        {
		        	
		        	Object[] temp = {	new String("+"),
		        	    				new String(" "),
		        	    				new String(" "),
		        	    				new String(" "),
		        	    				new String(" "),
		        	    				new String(" "),
		        	    				null };
		        	((MyTableModel)table.getModel()).addRow(temp);
		       }
		    }
		};
		 
		ButtonColumn buttonColumn = new ButtonColumn(this, delete, 0);
		buttonColumn.setMnemonic(KeyEvent.VK_D);
		
	}

	private void initEditting()
	{

		TableColumn colorColumn = getColumnModel().getColumn(2);
		colorBox.setRenderer(new ColorComboRenderer());
		colorColumn.setCellEditor(new DefaultCellEditor(colorBox));
		colorColumn.setCellRenderer(new MyColorRenderer());

		getColumnModel().getColumn(3).setCellEditor(new MyEditor(1, 100));
		getColumnModel().getColumn(4).setCellEditor(new MyEditor(1, 100));
		getColumnModel().getColumn(5).setCellEditor(new MyEditor(1, 1000));

	}

	private void initSelectionMode()
	{
		getTableHeader().setReorderingAllowed(false);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setRowSelectionAllowed(false);
		setColumnSelectionAllowed(false);
	}

	private void initVisual()
	{
		setBackground(new Color(238, 238, 238));
		getTableHeader().setBackground(new Color(160, 173, 164));
		getTableHeader().setForeground(new Color(209, 83, 60));
	}

	private void initSize()
	{
		setAutoResizeMode(AUTO_RESIZE_OFF);
		for (int i = 0; i < getColumnCount(); i++)
		{
			TableColumn col = getColumnModel().getColumn(i);
			int size = 0;
			switch (i)
			{
				case 0:
					size = 45;
					break;
				case 1:
					size = 20;
					break;
				case 2:
					size = 50;
					break;
				case 3:
					size = 50;
					break;
				case 4:
					size = 50;
					break;
				case 5:
					size = 50;
					break;
				case 6:
					size = 50;
					break;
			}
			col.setMinWidth(size);
			col.setMaxWidth(size);
			col.setPreferredWidth(size);
		}
	}
}
