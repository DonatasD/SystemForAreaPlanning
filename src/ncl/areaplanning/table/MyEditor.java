package ncl.areaplanning.table;

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellEditor;
import javax.swing.text.DefaultFormatter;

public class MyEditor extends AbstractCellEditor implements
		TableCellEditor
{

	final JSpinner spinner;

	public MyEditor(int min, int max)
	{
		spinner = new JSpinner(new SpinnerNumberModel(min, min, max, 1));
		spinner.setFocusable(true);
	    JComponent comp = spinner.getEditor();
	    JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
	    DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
	    formatter.setCommitsOnValidEdit(true);

		for (final Component tmpComponent : spinner.getComponents())
		{
			tmpComponent.setFocusable(true);
			tmpComponent.addFocusListener(new FocusAdapter()
			{
				@Override
				public void focusLost(FocusEvent fe)
				{
					spinner.setValue(getCellEditorValue());
					System.out.println(getCellEditorValue());
				}
			});
		}
	}

	public Component getTableCellEditorComponent(	JTable table,
													Object value,
													boolean isSelected,
													int row,
													int column)
	{
		spinner.setValue(value);
		return spinner;
	}

	public Object getCellEditorValue()
	{
		return spinner.getValue();
	}
}