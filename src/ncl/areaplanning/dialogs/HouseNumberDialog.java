package ncl.areaplanning.dialogs;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class HouseNumberDialog extends JPanel
{
	private JSpinner numberSelector;
	private JPanel panelNumber;
	private JLabel lblNumber;
	
	public HouseNumberDialog()
	{
		super();
		SpinnerNumberModel modelNumber = new SpinnerNumberModel(1, 1, 10, 1);
		
		panelNumber = new JPanel();
		add(panelNumber);
		lblNumber = new JLabel("Number of houses: ");
		panelNumber.add(lblNumber);
		numberSelector = new JSpinner(modelNumber);
		panelNumber.add(numberSelector);
	}

	public Integer getNumberSelectorValue()
	{
		return (Integer) numberSelector.getValue();
	}
	
}
