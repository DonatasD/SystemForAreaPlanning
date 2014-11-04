package ncl.areaplanning.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;

import ncl.areaplanning.SystemForAreaPlanning;
import ncl.areaplanning.handlers.ActionHandler;


public class MyTableContainer extends JPanel
{
	public final MyTable table = new MyTable();
	
	public MyTableContainer(SystemForAreaPlanning sfap)
	{
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createMatteBorder(4, 2, 0, 2, getBackground())));
		add(table.getTableHeader());
		//add(table, BorderLayout.CENTER);
		
		JButton btn_optimise = new JButton("Optimise");
		JPanel btn_Panel = new JPanel();
		
		btn_optimise.setBackground(UIManager.getColor("Button.background"));
		btn_optimise.setForeground(Color.BLACK);
		btn_optimise.addActionListener(new ActionHandler(sfap));
		btn_Panel.add(btn_optimise);
		add(table);
		add(btn_Panel);
	}
	public MyTable getTable()
	{
		return this.table;
	}
}
