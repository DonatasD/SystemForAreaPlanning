package ncl.areaplanning.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ncl.areaplanning.SystemForAreaPlanning;
import ncl.areaplanning.SystemForAreaPlanning.ActionPerformed;

public class ActionHandler implements ActionListener {

	private SystemForAreaPlanning sfap;
	private SystemForAreaPlanning.ActionPerformed actionPerformed;

	public ActionHandler(SystemForAreaPlanning sfap) {
		this.sfap = sfap;
		this.actionPerformed = this.sfap.new ActionPerformed();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		actionPerformed.buttonClick(e);

	}

}
