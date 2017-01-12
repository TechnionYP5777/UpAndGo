//
// Authors Alex.V & Lidia.P
//

package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.ComponentOrientation;
import javax.swing.SwingConstants;

public class mainLuzerView {

	JFrame mainLuzer;
	private JMenuItem mnItCatalog;
	private JMenuItem mnItGilayon;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					mainLuzerView window = new mainLuzerView();
					window.mainLuzer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainLuzerView() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			initialize();

		} catch (Throwable ¢) {
			¢.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		mainLuzer = new JFrame();
		mainLuzer.setMinimumSize(new Dimension(600, 500));
		mainLuzer.setBounds(100, 100, 450, 300);
		mainLuzer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMenu();
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
		mnItCatalog = new JMenuItem("טען קטלוג");
		mnItCatalog.setPreferredSize(new Dimension(100, 20));
		mnItCatalog.setHorizontalTextPosition(SwingConstants.RIGHT);
		mnItCatalog.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		mnItCatalog.setHorizontalAlignment(SwingConstants.RIGHT);

		mnItGilayon = new JMenuItem("טען גיליון");
		mnItGilayon.setPreferredSize(new Dimension(100, 20));
		mnItGilayon.setHorizontalTextPosition(SwingConstants.RIGHT);
		mnItGilayon.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		mnItGilayon.setHorizontalAlignment(SwingConstants.RIGHT);
	
		menu.add(mnItCatalog);
		menu.add(mnItGilayon);
		menuBar.add(menu);
	
		mainLuzer.setJMenuBar(menuBar);
	}

}
