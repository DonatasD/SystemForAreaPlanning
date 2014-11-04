package ncl.areaplanning.handlers;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import ncl.areaplanning.SystemForAreaPlanning;
import ncl.areaplanning.SystemForAreaPlanning.MouseHeld;

public class MouseHandler implements MouseListener {

	private SystemForAreaPlanning sfap;
	private SystemForAreaPlanning.MouseHeld mouseHeld;

	public MouseHandler(SystemForAreaPlanning sfap) {
		this.sfap = sfap;
		this.mouseHeld = this.sfap.new MouseHeld();
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		this.mouseHeld.mouseDown(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
