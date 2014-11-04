package ncl.areaplanning.navigation;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import ncl.areaplanning.SystemForAreaPlanning;

public class MyMenu extends JMenu {
	
	private SystemForAreaPlanning sfap;
	private NavigationContainer container;
	private JMenuItem[] items;
	private String[] itemsId;
	

	public MyMenu(SystemForAreaPlanning sfap, NavigationContainer container, String id, String[] itemId) {
		super(id);
		this.sfap = sfap;
		this.container = container;
		this.itemsId = itemId;
		this.items = new JMenuItem[this.itemsId.length];

		for (int i = 1; i < this.itemsId.length; i++) {
			if(this.getText().equals("Options"))
			{
				this.items[i] = new MyCheckBoxMenuItem(this.sfap, container, this, this.itemsId[i]);
				this.add(items[i]);
			}
			else
			{
				this.items[i] = new MyMenuItem(this.sfap, container, this, this.itemsId[i]);
				this.add(items[i]);
			}
		}
	}
}
