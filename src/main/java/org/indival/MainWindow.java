/**
 * 
 */
package org.indival;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.indival.fileParser.indigraphParser;
import org.indival.filereader.IdgFileVisitor;
import org.indival.filereader.IdgParser;
import org.indival.gui.DecisionOptionControl;
import org.indival.gui.MxGraphEdit;
import org.indival.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = -8706457057984469938L;
	private ResourceBundle messages;
	private final JFileChooser fc = new JFileChooser();
	private Project project;
	private String projectFile;
	private JScrollPane graphPane;
	private MxGraphEdit mge;
	private DecisionOptionControl doc;
	private transient Logger log = LoggerFactory.getLogger(this.getClass().toString());

	public MainWindow() {
		super("INfluence DIagramme GRaphing VAlues");

		log.info("Default character encoding: " + Charset.defaultCharset().name());

		Locale currentLocale = Locale.getDefault();
		messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			log.debug(e.getMessage());
		}

		setJMenuBar(menuBar());

		// Display the window.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initEmptyProject();
		pack();
		setVisible(true);

	}

	private JMenuBar menuBar() {
		JMenuBar theMenuBar = new JMenuBar();
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
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu.addActionListener(this);

		theMenuBar.add(menu);

		menu = new JMenu(messages.getString("menu.view"));
		menu.setMnemonic(KeyEvent.VK_V);
		menu.getAccessibleContext().setAccessibleDescription(messages.getString("menu.view.desc"));

		menuItem = new JMenuItem(messages.getString("menu.view.id"));
		menuItem.setMnemonic(KeyEvent.VK_I);
		menuItem.setActionCommand("showIDEdit");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem(messages.getString("menu.view.dg"));
		menuItem.setMnemonic(KeyEvent.VK_D);
		menuItem.setActionCommand("showDGEdit");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem(messages.getString("menu.view.layout"));
		menuItem.setMnemonic(KeyEvent.VK_L);
		menuItem.setActionCommand("applyLayout");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu.addActionListener(this);

		theMenuBar.add(menu);

		menu = new JMenu(messages.getString("menu.example"));
		menu.setMnemonic(KeyEvent.VK_E);

		menuItem = new JMenuItem(messages.getString("menu.example.load"));
		menuItem.setMnemonic(KeyEvent.VK_L);
		menuItem.setActionCommand("projectExample");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		theMenuBar.add(menu);

		return theMenuBar;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JScrollPane cp = this.graphPane;
		switch (e.getActionCommand()) {
		case "showIDEdit":
			showIDEdit();
			break;
		case "applyLayout":
			applyLayout();
			break;
		case "projectOpen":
			openProjectFile(cp);
			break;
		case "projectExample":
			openExampleProjectFile();
			break;
		case "updateID":
			updateID();
			break;
		default:
			break;
		}
	}

	private void openProjectFile(Container cp) {
		int returnOpen = fc.showOpenDialog(cp);
		if (returnOpen == JFileChooser.APPROVE_OPTION) {
			File ofile = fc.getSelectedFile();
			if (ofile.exists()) {
				try {
					this.projectFile = ofile.getCanonicalPath();
					InputStream fis = new FileInputStream(this.projectFile);
					processFile(fis);
					showIDEdit();
				} catch (IOException e) {
					log.debug(e.getMessage());
				}
			}
		}
	}

	private void openExampleProjectFile() {
		String filename = "testfile.txt";
		log.info("reading example file " + filename);
		InputStream in = getClass().getResourceAsStream("/" + filename);
		processFile(in);
		showIDEdit();
	}

	private void processFile(InputStream fis) {
		indigraphParser idg;
		try {
			idg = IdgParser.parse(fis);
			indigraphParser.IndigraphContext idgctx = idg.indigraph();
			IdgFileVisitor visitor = new IdgFileVisitor(project);
			visitor.visit(idgctx);
		} catch (IOException e) {
			log.debug(e.getMessage());
		}
	}

	private void applyLayout() {
		this.mge.applyLayout();
		Container cp = getContentPane();
		cp.revalidate();
		cp.repaint();
		pack();
	}

	private void showIDEdit() {
		this.mge = new MxGraphEdit(this.project.getModel(), this.messages);
		Container cp = this.getContentPane();
		cp.add(this.mge.getComponent(), BorderLayout.CENTER);
		cp.revalidate();
		cp.repaint();
		pack();

		// Decision option control
		this.doc = new DecisionOptionControl(project.getDecisionNodeList(), this);
		cp.add(this.doc, BorderLayout.LINE_START);
		cp.revalidate();
		cp.repaint();
		pack();
	}

	/*
	 * Updates the influence diagramme. Should usually be triggered by events.
	 */
	public void updateID() {
		log.info("updating Influence diagramme");
		log.debug("Current object id: " + this.mge.toString());
		Container cp = this.getContentPane();
		// cp.remove(this.mge.getComponent());
		this.mge.getComponent().refresh();
		cp.revalidate();
		cp.repaint();
		log.debug("Currently set component number: " + cp.getComponentCount());
		// this.mge = new MxGraphEdit2(this.project.getModel(), this.messages);
		log.debug("New object id: " + this.mge.toString());
		this.mge.getComponent().setVisible(true);
		// cp.add(this.mge.getComponent(), BorderLayout.CENTER);
		log.debug("Currently set component number: " + cp.getComponentCount());
		cp.revalidate();
		cp.repaint();
		pack();
	}

	private void initEmptyProject() {
		project = new Project();
	}

}
