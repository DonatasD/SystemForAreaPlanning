package ncl.areaplanning.toolbox;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import ncl.areaplanning.SystemForAreaPlanning;
import ncl.areaplanning.handlers.MouseMotionHandler;
import ncl.areaplanning.toolbox.component.ToolBarComponent;

public class ToolBar extends JToolBar {

	private static final String RESDIR = "res/objects/icons/";


	private String[] res;
	private String[] descr;
	private SystemForAreaPlanning sfap;
	private ToolBarComponent[] tbc;
	private boolean selected;

	public ToolBar(SystemForAreaPlanning sfap, String[] res, String[] descr) {
		super("Tool Bar");
		this.sfap = sfap;
		this.res = res;
		this.descr = descr;
		this.selected = false;
		this.tbc = new ToolBarComponent[res.length];
		

		this.addMouseMotionListener(new MouseMotionHandler(sfap));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setFloatable(false);
		this.setBackground(new Color(240,240,240));

		for(int i = 0; i < res.length; i++){
			if (descr[i].equals("House"))
			{
				JLabel temp = new JLabel("Objects ");
				this.add(temp);
				this.addSeparator();
			}
			else if (descr[i].equals("Recreate Grid Tile"))
			{
				this.addSeparator();
				JLabel temp = new JLabel("Map ");
				this.add(temp);
				this.addSeparator();
			}
			ToolBarComponent temp = new ToolBarComponent(sfap, this, RESDIR + this.res[i], this.descr[i], i+1);
			this.add(temp, i);
		}
	}
	public Component add(ToolBarComponent comp, int i)
	{
		super.add(comp);
		this.tbc[i] = comp;
		return comp;
		
	}
	public void setSelected()
	{
		this.selected = !selected;
	}
	public boolean getSelected()
	{
		return this.selected;
	}
	public ToolBarComponent[] getTbc()
	{
		return this.tbc;
	}
}