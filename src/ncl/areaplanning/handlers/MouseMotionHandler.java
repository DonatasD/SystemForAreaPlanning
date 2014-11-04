package ncl.areaplanning.handlers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import ncl.areaplanning.SystemForAreaPlanning;
import ncl.areaplanning.SystemForAreaPlanning.MouseMoved;

public class MouseMotionHandler implements MouseMotionListener {

	private SystemForAreaPlanning sfap;
	private SystemForAreaPlanning.MouseMoved mouseMoved;

	public MouseMotionHandler(SystemForAreaPlanning sfap) {
		this.sfap = sfap;
		this.mouseMoved = this.sfap.new MouseMoved();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		this.mouseMoved.mouseMoved(e);
	}

}
