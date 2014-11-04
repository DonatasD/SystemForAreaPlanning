package ncl.areaplanning.dialogs;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class HouseDialog extends JPanel
{
	private JSpinner HSelector;
	private JSpinner WSelector;
	private JSpinner ValueSelector;
	private JLabel lblHeight;
	private JLabel lblWidth;
	private JLabel lblValue;
	private JPanel panelWidth;
	private JPanel panelHeight;
	private JPanel panelValue;
	
	public HouseDialog()
	{
		super();
		SpinnerNumberModel modelWidth = new SpinnerNumberModel(1, 1, 200, 1);
		SpinnerNumberModel modelHeight = new SpinnerNumberModel(1, 1, 200, 1);
		SpinnerNumberModel modelValue = new SpinnerNumberModel(1, 1, 999, 1);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		panelWidth = new JPanel();
		add(panelWidth);
		this.lblWidth  = new JLabel("Select Width");
		panelWidth.add(lblWidth);
		this.WSelector = new JSpinner(modelWidth);
		panelWidth.add(WSelector);
		
		panelHeight = new JPanel();
		add(panelHeight);
		this.lblHeight = new JLabel("Select Height");
		panelHeight.add(lblHeight);
		this.HSelector = new JSpinner(modelHeight);
		panelHeight.add(HSelector);
		
		panelValue = new JPanel();
		add(panelValue);
		this.lblValue = new JLabel("Select Value");
		panelValue.add(lblValue);
		this.ValueSelector = new JSpinner(modelValue);
		panelValue.add(ValueSelector);
	}

	public Integer getHSelector()
	{
		return (Integer) HSelector.getValue();
	}

	public Integer getWSelector()
	{
		return (Integer) WSelector.getValue();
	}

	public Integer getValueSelector()
	{
		return (Integer) ValueSelector.getValue();
	}
	
	
	
	
}
