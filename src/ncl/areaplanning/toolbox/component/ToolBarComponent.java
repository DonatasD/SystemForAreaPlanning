package ncl.areaplanning.toolbox.component;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import ncl.areaplanning.SystemForAreaPlanning;
import ncl.areaplanning.dialogs.NewMapDialog;
import ncl.areaplanning.mapobjects.MapObject;
import ncl.areaplanning.mapobjects.Road;
import ncl.areaplanning.toolbox.ToolBar;

public class ToolBarComponent extends JToggleButton implements MouseListener, ActionListener{

	private SystemForAreaPlanning sfap;
	private ToolBar tb;
	private ImageIcon icon;
	private int id;
	private boolean selected;


	public ToolBarComponent(SystemForAreaPlanning sfap, ToolBar tb, String res, String descr, int id){
		super();
		icon = new ImageIcon(res, descr);
		this.setIcon(icon);
		this.id = id;
		this.sfap = sfap;
		this.tb = tb;
		this.selected = false;
		this.addActionListener(this);
		this.addMouseListener(this);
	}

	public int getId() {
		return id;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean global = tb.getSelected();
		boolean state = this.selected;
		boolean result = false;
		if (!global)
		{
			tb.setSelected();
			this.setSelected(true);
			result = true;
		}
		else
		{
			if (state)
			{
				this.setSelected(false);
				tb.setSelected();
				result = false;
			}
			else
			{
				ToolBarComponent[] temp = tb.getTbc();
				for (int i = 0; i < temp.length; i++)
				{
					temp[i].setSelected(false);
					temp[i].selected = false;
				}
				this.setSelected(true);
				result = true;
			}
		}
		sfap.hand = result ? this.getId() : 0;	
		sfap.objSize = new Dimension(1, 1);
		this.selected = result;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e))
		{
			if (sfap.hand == 1)
			{
				NewMapDialog dialog = new NewMapDialog();
				int result = JOptionPane.showConfirmDialog(null, dialog, "Select", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					sfap.objSize = new Dimension(dialog.getHSelectorValue(), dialog.getWSelectorValue());
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
