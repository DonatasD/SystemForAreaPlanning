package ncl.areaplanning.footer;

import javax.swing.JLabel;

import ncl.areaplanning.SystemForAreaPlanning;
import ncl.areaplanning.container.Container;
import ncl.areaplanning.handlers.MouseMotionHandler;

public class FooterContainer extends Container {
	
	public JLabel lblCurXPos;
	public JLabel lblCurYPos;
	private SystemForAreaPlanning sfap;
	

	public FooterContainer( SystemForAreaPlanning sfap, int x, int y) {
		super();
		this.sfap = sfap;
		this.lblCurXPos = new JLabel();
		this.lblCurXPos.setText(sfap.handXPos + "");
		this.lblCurYPos = new JLabel(sfap.handYPos + "");
		this.add(lblCurXPos);
		this.add(lblCurYPos);
		this.addMouseMotionListener(new MouseMotionHandler(sfap));
	}


	public void updateHand(int handXPos, int handYPos) {
		this.lblCurXPos.setText(handXPos + "");
		this.lblCurYPos.setText(handYPos + "");
	}
}
