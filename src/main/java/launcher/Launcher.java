/**
 * 
 */
package launcher;

import java.awt.EventQueue;

import view.MainWinView;

/**
 * @author magellan
 *
 * The main class that bootstraps the application (launch it)
 */
public class Launcher {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainWinView frame = new MainWinView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
