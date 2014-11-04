package ncl.areaplanning.handlers;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import ncl.areaplanning.SystemForAreaPlanning;

public class WheelHandler implements MouseWheelListener {

	private SystemForAreaPlanning sfap;
	
	public WheelHandler(SystemForAreaPlanning sfap)
	{
		this.sfap = sfap;
	}
	public void mouseWheelMoved(MouseWheelEvent e) 
	{
		
	}

}
