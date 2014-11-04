package ncl.areaplanning;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ncl.areaplanning.dialogs.HouseDialog;
import ncl.areaplanning.dialogs.HouseNumberDialog;
import ncl.areaplanning.dialogs.NewMapDialog;
import ncl.areaplanning.footer.FooterContainer;
import ncl.areaplanning.handlers.ActionHandler;
import ncl.areaplanning.handlers.MouseHandler;
import ncl.areaplanning.handlers.MouseMotionHandler;
import ncl.areaplanning.map.MapScrollPane;
import ncl.areaplanning.mapobjects.MapObject;
import ncl.areaplanning.mapobjects.Tile;
import ncl.areaplanning.navigation.NavigationContainer;
import ncl.areaplanning.optimization.House;
import ncl.areaplanning.table.MyTableContainer;
import ncl.areaplanning.table.MyTableModel;
import ncl.areaplanning.toolbox.ToolBar;

/**
 * SystemForAreaPlanning is a Panel placed inside a frame that contains all
 * other containers.
 * SystemForAreaPlanning object contains all the information of the main panels
 * placed inside it. It as well has predefined Navigation menu field names, 
 * Toolbar's image resources names and descriptions for them. Moreover, this object
 * contains the initial and minimum frame size that it is placed in.
 * 
 * 
 * @author 		Donatas Daubaras
 * @version 	%I%, %G%
 * @since 		1.0
 * 
 */
public class SystemForAreaPlanning extends JPanel {



	/**
	 * Constants for setting initial frame width {@link #INITFRAMEWIDTH}, height
	 * {@link #INITFRAMEHEIGHT} and minimum frame width {@link #MINFRAMEWIDTH},
	 * height {@link #MINFRAMEHEIGHT}.
	 */

	private static final String[][] MENUNAMES = {
	                                             { "File", "New", "Remove" }, { "Options", "Show Grid"},{"Optimisation", "Optimise"}, {"View", "Zoom In", "Zoom Out"},
	                                             { "Help", "help" } };

	private static final String[] TOOLBARRES = {"family-house-icon.png","road-icon.png", "delete-map-object-icon.png", "grid-tile-icon.png", "delete-grid-tile-icon.png"};
	private static final String[] TOOLBARRESDESCR = {"House", "Road", "Delete Object", "Recreate Grid Tile", "Delete Grid"};

	private static final int INITFRAMEWIDTH = 800;
	private static final int INITFRAMEHEIGHT = 600;

	private static final int MINFRAMEWIDTH = 800;
	private static final int MINFRAMEHEIGHT = 600;

	private FooterContainer footerContainer;

	private NavigationContainer navigationContainer;

	private MapScrollPane mapScrollPane;

	private ToolBar toolbar;

	private MyTableContainer tableContainer;

	public Dimension objSize = new Dimension(1,1);
	public int hand = 0;
	public boolean handInView = false;
	public int handXPos;
	public int handYPos;

	/**
	 * Constructor
	 */
	public SystemForAreaPlanning() {
		// Creates Frame
		super(new BorderLayout());

		this.tableContainer = new MyTableContainer(this);
		add(tableContainer, BorderLayout.WEST);

		this.footerContainer = new FooterContainer(this, handXPos, handYPos);
		add(footerContainer, BorderLayout.SOUTH);

		this.toolbar = new ToolBar(this, TOOLBARRES, TOOLBARRESDESCR);
		add(toolbar, BorderLayout.EAST);

		this.mapScrollPane = new MapScrollPane(this);
		this.mapScrollPane.setOpaque(true);

		add(mapScrollPane, BorderLayout.CENTER);

	}
	/**
	 * Creates the initial JFrame that is placed with needed containers/
	 * 
	 * @see		SystemForAreaPlanning
	 * @since	1.0
	 */
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Area Planning");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(INITFRAMEWIDTH, INITFRAMEHEIGHT);
		frame.setMinimumSize(new Dimension(INITFRAMEWIDTH, INITFRAMEHEIGHT));

		JComponent newContentPane = new SystemForAreaPlanning();
		NavigationContainer navigationContainer = new NavigationContainer((SystemForAreaPlanning) newContentPane, MENUNAMES);

		frame.setJMenuBar(navigationContainer);
		newContentPane.setOpaque(true);
		frame.setContentPane(newContentPane);


		frame.setVisible(true);
	}
	/**
	 * SystemForAreaPlanning object getter to retrieve mapScrollPane.
	 * 
	 * @return 	return mapScrollPane object that contains the actual map.			
	 * 
	 * @see 	SystemForAreaPlanning
	 * @see 	MapScrollPane
	 * @since	1.0
	 * 
	 */
	public MapScrollPane getMapScrollPane()
	{
		return mapScrollPane;
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				createAndShowGUI();
			}
		});

	}
	/**
	 * MouseHeld is a class, which is used to do an event, which involves pressing a mouse.
	 * 
	 * @author 	Donatas Daubaras
	 * @see 	MouseHandler
	 * 
	 */
	public class MouseHeld {

		/**
		 * Calls addObject method (if object is selected from toolbar) inside MapscrollPane class,
		 * which tries to add a new object.
		 * 
		 * @param e		Current mouse event
		 * @see			MapScrollPane
		 * @see			ToolBar
		 * @see			MouseHandler
		 */
		public void mouseDown(MouseEvent e) {
			if (hand != 0)
			{
				mapScrollPane.addObject(hand, e.getX(), e.getY());
			}
		}
	}

	/**
	 * MouseMoved is a class, which is used to do and event, which involves moving the mouse.
	 * 
	 * @author 	Donatas Daubaras
	 * @see		MouseMotionHandler
	 */
	public class MouseMoved {

		/**
		 * This method allows to track the position of the mouse and show the user
		 * where the selected object will be placed or if the object does not fit in inside the map.
		 * 
		 * @param e		Current mouse event
		 * @see			MouseMotionHandler
		 * @see			MapScrollPane
		 */
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			handXPos = e.getX();
			handYPos = e.getY();
			handInView = e.getSource().equals(mapScrollPane.getMapView());
			footerContainer.updateHand(handXPos, handYPos);
			boolean found = false;
			if (hand != 0)
			{
				//Check that map is created
				if (mapScrollPane.isCreated())
				{
					//Check if the map is inside the map
					if (mapScrollPane.getGridBoundary().contains(handXPos, handYPos))
					{
						//Look up in which tile the cursor is
						Tile[][] grid = mapScrollPane.getGrid();
						for (int i = 0; i < grid.length && !found; i++)
						{
							for (int j = 0; j < grid[i].length && !found; j++)
							{
								if (grid[i][j].contains(handXPos, handYPos))
								{
									found = true;
									mapScrollPane.setCurrentObject(new MapObject(grid[i][j], objSize, mapScrollPane.TILE_SIZE, new Point(i,j))) ;
								}
							}
						}

					}
				}
			}
			if (!found)
			{
				mapScrollPane.setCurrentObject(null);
			}
			mapScrollPane.getMapView().repaint();
		}
	}

	/**
	 * 
	 * @author 	Donatas Daubaras
	 * @see		ActionHandler
	 */
	public class ActionPerformed {

		private void actionOptimise()
		{

			Object[][] data = ((MyTableModel)(tableContainer.getTable().getModel())).getData();
			if (data.length > 1 && mapScrollPane.isCreated())
			{
				ArrayList<House> houses = new ArrayList<House>();
				for (int i = 0; i < data.length - 1; i++)
				{
					System.out.println(data[i][2]);
					House house = new House((Integer)data[i][3],(Integer)data[i][4], (Integer)data[i][5],(Color)data[i][2]);
					Boolean rotate = (Boolean)data[i][6];
					System.out.println(house);
					house.calcPossiblePos(mapScrollPane);
					if (!houses.contains(house)) 
					{
						houses.add(house);
						if (rotate)
						{
							House rotatedHouse = house.rotate();
							rotatedHouse.calcPossiblePos(mapScrollPane);
							if (!houses.contains(rotatedHouse))
							{
								houses.add(rotatedHouse);
							}
						}
					}
				}
				for (House temp : houses)
				{
					System.out.println(temp.getColor());
				}
				mapScrollPane.optimize(houses);
			}
			System.out.println();
		}

		public void buttonClick(ActionEvent e) {
			String bv = e.getActionCommand().toString();
			switch (bv) {
				case "New":
					NewMapDialog mapDialog = new NewMapDialog();
					int mapDialogResult = JOptionPane.showConfirmDialog(null, mapDialog, "Select", JOptionPane.OK_CANCEL_OPTION);
					if (mapDialogResult == JOptionPane.OK_OPTION) {
						mapScrollPane.createMap(mapDialog.getWSelectorValue(),mapDialog.getHSelectorValue());
					}
					break;
				case "Remove":
					mapScrollPane.removeMap();
					break;
				case "Show Grid":
					mapScrollPane.showGrid();
					break;
				case "Zoom In":
					mapScrollPane.zoomIn();
					break;
				case "Zoom Out":
					mapScrollPane.zoomOut();
					break;
				case "Optimise":
					actionOptimise();
					break;
				case "help":
					mapScrollPane.printGrid();
					break;


				default:
					break;
			}
		}
	}


}
