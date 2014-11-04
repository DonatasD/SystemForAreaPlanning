package ncl.areaplanning.navigation;

import javax.swing.JMenuItem;

import ncl.areaplanning.SystemForAreaPlanning;
import ncl.areaplanning.handlers.ActionHandler;

public class MyMenuItem extends JMenuItem {

	private NavigationContainer navContainer;
	private MyMenu container;
	private SystemForAreaPlanning sfap;
	

	public MyMenuItem(SystemForAreaPlanning sfap, NavigationContainer navContainer, MyMenu container, String id) {
		super(id);
		this.sfap = sfap;
		this.navContainer = navContainer;
		this.container = container;
		
		this.addActionListener(new ActionHandler(this.sfap));
	}

}
