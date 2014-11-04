package ncl.areaplanning.navigation;

import javax.swing.JMenuBar;

import ncl.areaplanning.SystemForAreaPlanning;

public class NavigationContainer extends JMenuBar {
	
	private SystemForAreaPlanning sfap;
	private MyMenu[] menu;
	private String[] menuId;

	public NavigationContainer(SystemForAreaPlanning sfap, String[][] menuId) {
		super();
		this.sfap = sfap;
		this.menuId = new String[menuId.length];
		this.menu = new MyMenu[menuId.length];

		for (int i = 0; i < menuId.length; i++) {
			this.menuId[i] = menuId[i][0];
			this.menu[i] = new MyMenu(this.sfap, this, this.menuId[i], menuId[i]);
			this.add(this.menu[i]);
		}
	}
}
