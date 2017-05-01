package org.indival;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainMenuBar extends JMenuBar {
    
    /**
     * 
     */
    private static final long serialVersionUID = -2604060721601492783L;

    public MainMenuBar(ActionListener parent, ResourceBundle messages){
	JMenuBar theMenuBar = this;
	JMenu menu;
	JMenuItem menuItem;

	// Layout of the menu bar
	theMenuBar.setOpaque(true);
	theMenuBar.setPreferredSize(new Dimension(600, 20));

	// Build the first level of the menu.
	menu = new JMenu(messages.getString("menu.project"));
	menu.setMnemonic(KeyEvent.VK_P);
	menu.getAccessibleContext().setAccessibleDescription(messages.getString("menu.project.desc"));

	menuItem = new JMenuItem(messages.getString("menu.project.open"));
	menuItem.setMnemonic(KeyEvent.VK_O);
	menuItem.setActionCommand("projectOpen");
	menuItem.addActionListener(parent);
	menu.add(menuItem);
	
	menuItem = new JMenuItem(messages.getString("menu.project.reload"));
	menuItem.setMnemonic(KeyEvent.VK_R);
	menuItem.setActionCommand("projectReload");
	menuItem.addActionListener(parent);
	menu.add(menuItem);

	menuItem = new JMenuItem(messages.getString("menu.project.example-car"));
	menuItem.setMnemonic(KeyEvent.VK_L);
	menuItem.setActionCommand("projectExampleCar");
	menuItem.addActionListener(parent);
	menu.add(menuItem);

	menuItem = new JMenuItem(messages.getString("menu.project.example-software"));
	menuItem.setMnemonic(KeyEvent.VK_L);
	menuItem.setActionCommand("projectExampleSoftware");
	menuItem.addActionListener(parent);
	menu.add(menuItem);

	menuItem = new JMenuItem(messages.getString("menu.project.exit"));
	menuItem.setMnemonic(KeyEvent.VK_E);
	menuItem.setActionCommand("exit");
	menuItem.addActionListener(parent);
	menu.add(menuItem);

	menu.addActionListener(parent);

	theMenuBar.add(menu);

	menu = new JMenu(messages.getString("menu.layout"));
	menu.setMnemonic(KeyEvent.VK_V);
	menu.getAccessibleContext().setAccessibleDescription(messages.getString("menu.layout.desc"));

	menuItem = new JMenuItem(messages.getString("menu.layout.compacttree"));
	menuItem.setMnemonic(KeyEvent.VK_C);
	menuItem.setActionCommand("layoutCompactTree");
	menuItem.addActionListener(parent);
	menu.add(menuItem);
	
	menuItem = new JMenuItem(messages.getString("menu.layout.organic"));
	menuItem.setMnemonic(KeyEvent.VK_O);
	menuItem.setActionCommand("layoutOrganic");
	menuItem.addActionListener(parent);
	menu.add(menuItem);
	
	menuItem = new JMenuItem(messages.getString("menu.layout.fastorganic"));
	menuItem.setMnemonic(KeyEvent.VK_F);
	menuItem.setActionCommand("layoutFastOrganic");
	menuItem.addActionListener(parent);
	menu.add(menuItem);

	menu.addActionListener(parent);

	theMenuBar.add(menu);

	menu = new JMenu(messages.getString("menu.about"));
	menu.setMnemonic(KeyEvent.VK_A);

	menuItem = new JMenuItem(messages.getString("menu.about.info"));
	menuItem.setMnemonic(KeyEvent.VK_I);
	menuItem.setActionCommand("infoBox");
	menuItem.addActionListener(parent);
	menu.add(menuItem);

	theMenuBar.add(menu);
    }

}
