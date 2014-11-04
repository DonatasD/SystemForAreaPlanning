package ncl.areaplanning.dialogs;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class NewMapDialog extends JPanel implements ChangeListener {
	
	private JSpinner HSelector;
	private JSpinner WSelector;
	private JLabel lblHeight;
	private JLabel lblWidth;
	private JPanel panelWidth;
	private JPanel panelHeight;
	
	public NewMapDialog()
	{
		super();
		
		SpinnerNumberModel modelWidth = new SpinnerNumberModel(1, 1, 9999, 1);
		SpinnerNumberModel modelHeight = new SpinnerNumberModel(1, 1, 9999, 1);
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
		
		this.HSelector.addChangeListener(this);
		this.WSelector.addChangeListener(this);
	}
	
	public void stateChanged(ChangeEvent e){
		SpinnerModel hModel = HSelector.getModel();
		SpinnerModel wModel = WSelector.getModel();
		Integer hValue = this.getHSelectorValue();
		Integer wValue = this.getWSelectorValue();
		
		if (hModel instanceof SpinnerNumberModel || wModel instanceof SpinnerNumberModel){
			if (hValue <= 0 || hValue > 9999 || wValue <= 0 || wValue > 9999){
				JOptionPane.showMessageDialog(null, "Value must be between 0 and 9999", "Warning", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	public Integer getHSelectorValue() {
		return (Integer) HSelector.getValue();
	}

	public Integer getWSelectorValue() {
		return (Integer) WSelector.getValue();
	}
	
}
