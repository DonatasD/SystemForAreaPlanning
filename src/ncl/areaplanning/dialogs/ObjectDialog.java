package ncl.areaplanning.dialogs;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class ObjectDialog extends JPanel {

	private JSpinner HSelector;
	private JSpinner WSelector;
	private JLabel lblHeight;
	private JLabel lblWidth;
	private JPanel panelWidth;
	private JPanel panelHeight;
	
	
	public ObjectDialog(){
		super();
		
		SpinnerNumberModel modelWidth = new SpinnerNumberModel(1, 1, 200, 1);
		SpinnerNumberModel modelHeight = new SpinnerNumberModel(1, 1, 200, 1);
		
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
	}
}
