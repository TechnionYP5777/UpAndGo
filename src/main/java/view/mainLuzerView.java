package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import command.MenuCommand;

/**
 * 
 * @author Alex.V & Lidia.P
 * @since 12-01-2017
 * 
 * Class for displaying menu and upper frame of the application.
 * 
 */
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
	public mainLuzerView(final TimetableVIew tb, final AvailableCourses crs) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			initialize(tb, crs);
		} catch (final Throwable ¢) {
			¢.printStackTrace();
		}
	}

	@Override
	public void setVisible(final boolean ¢) {
		mainLuzer.setVisible(¢);
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize(final TimetableVIew tb, final AvailableCourses crs) {
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
			public void windowClosing(@SuppressWarnings("unused") final WindowEvent __) {
				final int userChoise = Message.yesNoCancleBox(FINISH_MSG, "CLOSE",
						new ImageIcon("resources/cat-laptop-icon.png"));
				if (userChoise == JOptionPane.CANCEL_OPTION)
					mainLuzer.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
				else {
					if (userChoise == JOptionPane.YES_OPTION)
						listeners.forEach(λ -> λ.actionPerformed(
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
		menu.setVisible(false); // TODO: set visible
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

	private void setAvailCoursesView(final TimetableVIew tb, final AvailableCourses crs) {
		final Container container = mainLuzer.getContentPane();
		container.setLayout(new BorderLayout());
		setMainPane(container, crs, tb);

	}

	private static void setMainPane(final Container c, final AvailableCourses avl, final TimetableVIew timeTbl) {
		final JPanel mainPane = new JPanel();
		mainPane.setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
				new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
		mainPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		mainPane.setLayout(new BorderLayout());
		c.add(mainPane);
		setSplitPane(mainPane, setCoursesPane(avl), setTablePane(timeTbl));
	}

	private static void setSplitPane(final JPanel mainPane, final JPanel panel1, final JPanel panel2) {
		final JSplitPane splitPaneV = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPaneV.setResizeWeight(0.5);
		splitPaneV.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		splitPaneV.setOneTouchExpandable(true);
		splitPaneV.setAlignmentY(Component.CENTER_ALIGNMENT);
		splitPaneV.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPane.add(splitPaneV, BorderLayout.CENTER);

		final JSplitPane splitPaneH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPaneH.setLeftComponent(panel2);
		splitPaneH.setResizeWeight(1);
		splitPaneH.setRightComponent(panel1);
		splitPaneV.setLeftComponent(splitPaneH);

	}

	private static JPanel setTablePane(final TimetableVIew timeTbl) {
		final JPanel $ = new JPanel();
		$.setLayout(new BorderLayout());
		$.add(timeTbl, BorderLayout.CENTER);
		return $;
	}

	private static JPanel setCoursesPane(final AvailableCourses avl) {
		final JPanel $ = new JPanel();
		$.setPreferredSize(new Dimension(280, 500));
		$.setMinimumSize(new Dimension(280, 500));
		$.setLayout(new BorderLayout());
		$.add(avl, BorderLayout.CENTER);
		return $;
	}

	private void createEvents() {
		mnItCatalog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@SuppressWarnings("unused") final ActionEvent __) {
				listeners.forEach(λ -> λ.actionPerformed(
						new ActionEvent(this, ActionEvent.ACTION_PERFORMED, MenuCommand.LOAD_CATALOG)));
			}
		});
		mnItGilayon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@SuppressWarnings("unused") final ActionEvent __) {
				listeners.forEach(λ -> λ.actionPerformed(
						new ActionEvent(this, ActionEvent.ACTION_PERFORMED, MenuCommand.LOAD_GILAYON)));
			}
		});
	}

	@Override
	public void addActionListener(final ActionListener ¢) {
		listeners.add(¢);
	}

	@Override
	public void removeActionListener(final ActionListener ¢) {
		listeners.remove(¢);
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		evt.getPropertyName();
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
