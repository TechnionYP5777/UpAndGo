//
// Authors Alex.V & Lidia.P
//

package view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JMenu;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import command.MenuCommand;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class mainLuzerView extends JFrame implements MenuView {

	private static final long serialVersionUID = 1L;
	private static final String FINISH_MSG = "Luzer will close now!\nWould you like to save your progress?";
	private static final String BYE_MSG = "Luzer says BYE";
	JFrame mainLuzer;
	private JMenuItem mnItCatalog;
	private JMenuItem mnItGilayon;
	static List<ActionListener> listeners;
	JPanel tblView;
	JPanel courseView;

	/**
	 * Create the application.
	 */
	public mainLuzerView(TimetableVIew tb, AvailableCourses crs) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			initialize(tb, crs);
		} catch (Throwable ¢) {
			¢.printStackTrace();
		}
	}

	@Override
	public void setVisible(boolean ¢) {
		this.mainLuzer.setVisible(¢);
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize(TimetableVIew tb, AvailableCourses crs) {
		listeners = new ArrayList<>();
		setMainPageProperties();
		setMenu();
		setAvailCoursesView(tb, crs);
		createEvents();
	}

	private void setMainPageProperties() {
		mainLuzer = new JFrame("Welcome To The LuzeR");
		mainLuzer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainLuzer.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(@SuppressWarnings("unused") WindowEvent __) {
				int userChoise = Message.yesNoCancleBox(FINISH_MSG, "CLOSE",
						new ImageIcon("resources/cat-laptop-icon.png"));
				if (userChoise == JOptionPane.CANCEL_OPTION)
					mainLuzer.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
				else {
					if (userChoise == JOptionPane.YES_OPTION)
						listeners.forEach(x -> x.actionPerformed(
								new ActionEvent(this, ActionEvent.ACTION_PERFORMED, MenuCommand.SAVE)));
					Message.hyperCatBox(BYE_MSG, "Bye Bye", new ImageIcon("resources/cat-6-icon.png"));
					System.exit(0);
				}
			}
		});
		mainLuzer.getContentPane().setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		mainLuzer.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		mainLuzer.setMinimumSize(new Dimension(1100, 600));
		mainLuzer.setBounds(100, 100, 450, 300);
	}

	void setMenu() {
		JMenuBar menuBar;
		JMenu menu;

		// Create the menu bar.
		menuBar = new JMenuBar();
		menuBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		// Build the menu.
		menu = new JMenu("טעינה");
		menu.setPreferredSize(new Dimension(100, 20));
		menu.setHorizontalAlignment(SwingConstants.RIGHT);
		menu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		// add menu items
		setCatalogItem();
		setGilayonItem();
		menu.add(mnItCatalog);
		menu.add(mnItGilayon);
		menuBar.add(menu);
		mainLuzer.setJMenuBar(menuBar);
	}

	private void setGilayonItem() {
		mnItGilayon = new JMenuItem("טען גיליון");
		mnItGilayon.setPreferredSize(new Dimension(100, 20));
		mnItGilayon.setHorizontalTextPosition(SwingConstants.RIGHT);
		mnItGilayon.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		mnItGilayon.setHorizontalAlignment(SwingConstants.RIGHT);
	}

	private void setCatalogItem() {
		mnItCatalog = new JMenuItem("טען קטלוג");
		mnItCatalog.setPreferredSize(new Dimension(100, 20));
		mnItCatalog.setHorizontalTextPosition(SwingConstants.RIGHT);
		mnItCatalog.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		mnItCatalog.setHorizontalAlignment(SwingConstants.RIGHT);
	}

	private void setAvailCoursesView(TimetableVIew tb, AvailableCourses crs) {
		Container container = mainLuzer.getContentPane();
		container.setLayout(new BorderLayout());
		setMainPane(container, crs, tb);

	}

	private static void setMainPane(Container c, AvailableCourses avl, TimetableVIew timeTbl) {
		JPanel mainPane = new JPanel();
		mainPane.setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
				new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
		mainPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		mainPane.setLayout(new BorderLayout());
		c.add(mainPane);
		setSplitPane(mainPane, setCoursesPane(avl), setTablePane(timeTbl));
	}

	private static void setSplitPane(JPanel mainPane, JPanel panel1, JPanel panel2) {
		JSplitPane splitPaneV = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPaneV.setResizeWeight(0.5);
		splitPaneV.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		splitPaneV.setOneTouchExpandable(true);
		splitPaneV.setAlignmentY(Component.CENTER_ALIGNMENT);
		splitPaneV.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPane.add(splitPaneV, BorderLayout.CENTER);

		JSplitPane splitPaneH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPaneH.setLeftComponent(panel2);
		splitPaneH.setResizeWeight(1);
		splitPaneH.setRightComponent(panel1);
		splitPaneV.setLeftComponent(splitPaneH);

	}

	private static JPanel setTablePane(TimetableVIew timeTbl) {
		JPanel $ = new JPanel();
		$.setLayout(new BorderLayout());
		$.add(timeTbl, BorderLayout.CENTER);
		return $;
	}

	private static JPanel setCoursesPane(AvailableCourses avl) {
		JPanel $ = new JPanel();
		$.setPreferredSize(new Dimension(280, 500));
		$.setMinimumSize(new Dimension(280, 500));
		$.setLayout(new BorderLayout());
		$.add(avl, BorderLayout.CENTER);
		return $;
	}

	private void createEvents() {
		mnItCatalog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
				listeners.forEach(x -> x.actionPerformed(
						new ActionEvent(this, ActionEvent.ACTION_PERFORMED, MenuCommand.LOAD_CATALOG)));
			}
		});
		mnItGilayon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
				listeners.forEach(x -> x.actionPerformed(
						new ActionEvent(this, ActionEvent.ACTION_PERFORMED, MenuCommand.LOAD_GILAYON)));
			}
		});
	}

	@Override
	public void addActionListener(ActionListener ¢) {
		listeners.add(¢);
	}

	@Override
	public void removeActionListener(ActionListener ¢) {
		listeners.remove(¢);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
		default:
			break;
		}
	}

	@Override
	public String getCatalogPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getGilayonPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
