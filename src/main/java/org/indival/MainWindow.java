/**
 * 
 */
package org.indival;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
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

import com.mxgraph.swing.mxGraphComponent;

public class MainWindow extends JFrame implements ActionListener {

    private static final long serialVersionUID = -8706457057984469938L;
    private ResourceBundle messages;
    private final JFileChooser fc = new JFileChooser();
    private Project project;
    private String projectFile;
    private JScrollPane graphPane;
    private JLabel startLabel;
    private MxGraphEdit mge;
    private DecisionOptionControl doc;
    private transient Logger log = LoggerFactory.getLogger(this.getClass().toString());

    public MainWindow() {
	super("INfluence DIagramme with VALues");

	log.info("Default character encoding: " + Charset.defaultCharset().name());

	Locale currentLocale = Locale.getDefault();
	messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);

	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
		| UnsupportedLookAndFeelException e) {
	    log.debug(e.getMessage());
	}

	setBackground(Color.WHITE);

	setJMenuBar(menuBar());
	showStartMessage();

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

    private void showStartMessage() {
	startLabel = new JLabel(messages.getString("startScreenMessage"));
	startLabel.setBackground(Color.DARK_GRAY);
	startLabel.setForeground(Color.white);
	startLabel.setOpaque(true);
	startLabel.setPreferredSize(new Dimension(400, 200));
	startLabel.setHorizontalAlignment(SwingConstants.CENTER);
	this.getContentPane().add(startLabel);
    }

    private void removeStartMessage() {
	this.getContentPane().remove(startLabel);
    }

    private void processFile(InputStream fis) {
	indigraphParser idg;
	try {
	    InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
	    idg = IdgParser.parse(isr);
	    indigraphParser.IndigraphContext idgctx = idg.indigraph();
	    IdgFileVisitor visitor = new IdgFileVisitor(project);
	    visitor.visit(idgctx);
	} catch (IOException e) {
	    log.debug(e.getMessage());
	}
    }

    private void applyLayout() {
	this.mge.layoutCompactTree();
	Container cp = getContentPane();
	cp.revalidate();
	cp.repaint();
	pack();
    }

    private void showIDEdit() {
	removeStartMessage();
	this.mge = new MxGraphEdit(this.project.getModel(), this.messages);
	mxGraphComponent comp = this.mge.getComponent();
	comp.getViewport().setOpaque(true);
	comp.getViewport().setBackground(Color.WHITE);
	Container cp = this.getContentPane();
	cp.add(this.mge.getComponent(), BorderLayout.CENTER);
	cp.revalidate();
	cp.repaint();
	pack();

	// Decision option control
	this.doc = new DecisionOptionControl(project.getSelectionNodeList(), this);
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
	Container cp = this.getContentPane();
	this.mge.getComponent().refresh();
	cp.revalidate();
	cp.repaint();
	this.mge.getComponent().setVisible(true);
	cp.revalidate();
	cp.repaint();
	pack();
    }

    private void initEmptyProject() {
	project = new Project();
    }

}
