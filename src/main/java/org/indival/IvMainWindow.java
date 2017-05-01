/**
 * 
 */
package org.indival;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.indival.filereader.IvFileParser;
import org.indival.gui.IvDecisionAlternativesControl;
import org.indival.gui.MxGraphEdit;
import org.indival.model.IvProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mxgraph.swing.mxGraphComponent;

public class IvMainWindow extends JFrame {

    private static final long serialVersionUID = -8706457057984469938L;
    private ResourceBundle messages;
    private final JFileChooser fc = new JFileChooser();
    private String projectFile;
    private IvProject project = new IvProject();
    private JScrollPane graphPane;
    private JLabel startLabel;
    private MxGraphEdit mge;
    private transient Logger log = LoggerFactory.getLogger(this.getClass().toString());

    public IvMainWindow() {
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

	MainMenuActions mma = new MainMenuActions(this);
	setJMenuBar(new MainMenuBar(mma, messages));
	showStartMessage();

	// Display the window.
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	pack();
	setVisible(true);

    }

    protected void openProjectFile() {
	int returnOpen = fc.showOpenDialog(this.graphPane);
	if (returnOpen == JFileChooser.APPROVE_OPTION) {
	    File ofile = fc.getSelectedFile();
	    if (ofile.exists()) {
		try {
		    this.projectFile = ofile.getCanonicalPath();
		} catch (IOException e1) {
		    log.warn(e1.getMessage());
		}
		try {
		    this.project = new IvProject();
		    InputStream fis = new FileInputStream(this.projectFile);
		    IvFileParser.getInstance().processFile(fis, this.project.getStaticGraph());
		    showIDEdit();
		} catch (IOException e) {
		    log.debug(e.getMessage());
		}
	    }
	}
    }

    protected void reloadFromFile() {
	this.project = new IvProject();
	InputStream fis;
	try {
	    fis = new FileInputStream(this.projectFile);
	    IvFileParser.getInstance().processFile(fis, this.project.getStaticGraph());
	} catch (FileNotFoundException e) {
	    log.warn(e.getMessage());
	}
	showIDEdit();
    }

    protected void openExampleCar() {
	this.project = new IvProject();
	String filename = "example-car.txt";
	log.info("reading example file " + filename);
	IvFileParser.getInstance().parseFromResourceFile(filename, this.project.getStaticGraph());
	this.project.getStaticGraph().initDynamicNodes();
	showIDEdit();
    }

    protected void openExampleSoftware() {
	this.project = new IvProject();
	String filename = "example-software.txt";
	log.info("reading example file " + filename);
	IvFileParser.getInstance().parseFromResourceFile(filename, this.project.getStaticGraph());
	this.project.getStaticGraph().initDynamicNodes();
	showIDEdit();
    }

    protected void exit() {
	System.exit(0);
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

    protected void layoutCompactTree() {
	this.mge.layoutCompactTree();
	showIDEdit();
//	Container cp = getContentPane();
//	cp.revalidate();
//	cp.repaint();
//	pack();
    }
    
    protected void layoutOrganic(){
	this.mge.layouOrganic();
 	showIDEdit();
//	Container cp = getContentPane();
//	cp.revalidate();
//	cp.repaint();
//	pack();
    }
    
    protected void layoutFastOrganic(){
 	this.mge.layoutFastOrganic();
 	showIDEdit();
// 	Container cp = getContentPane();
// 	cp.revalidate();
// 	cp.repaint();
// 	pack();
     }

    protected void showIDEdit() {
	removeStartMessage();
	this.project.updateMxGraph();
	this.mge = new MxGraphEdit(this.project.getStaticMxgModel(), this.messages);
	mxGraphComponent comp = this.mge.getComponent();
	comp.getViewport().setOpaque(true);
	comp.getViewport().setBackground(Color.WHITE);
	Container cp = this.getContentPane();
	cp.removeAll();
	cp.add(this.mge.getComponent(), BorderLayout.CENTER);
	cp.revalidate();
	cp.repaint();
	pack();

	// Decision option control
	IvDecisionAlternativesControl doc = new IvDecisionAlternativesControl(this.project.getStaticGraph().getDynamicDecisionNodes(), this);
	cp.add(doc, BorderLayout.LINE_START);
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

}
