package ncl.areaplanning.navigation;

import javax.swing.JCheckBoxMenuItem;

import ncl.areaplanning.SystemForAreaPlanning;
import ncl.areaplanning.handlers.ActionHandler;

public class MyCheckBoxMenuItem extends JCheckBoxMenuItem {
	
	private NavigationContainer navContainer;
	private MyMenu container;
	private SystemForAreaPlanning sfap;

	public MyCheckBoxMenuItem(SystemForAreaPlanning sfap, NavigationContainer navContainer, MyMenu container, String id)
	{
		super(id);
		this.sfap = sfap;
		this.navContainer = navContainer;
		this.container = container;

		this.addActionListener(new ActionHandler(this.sfap));
	}
}
