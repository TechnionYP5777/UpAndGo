/**
 * 
 */
package launcher;

import java.awt.EventQueue;

import model.ConcreteModel;
import model.loader.XmlCourseLoader;
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
					new MainWinView(new ConcreteModel(new XmlCourseLoader("REPFILE/REP.XML"))).setVisible(true);
				} catch (Exception ¢) {
					¢.printStackTrace();
				}
			}
		});

	}

}
