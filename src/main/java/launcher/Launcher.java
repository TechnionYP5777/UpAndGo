/**
 * 
 */
package launcher;

import java.awt.EventQueue;

import model.CourseModel;
import model.loader.XmlCourseLoader;
import view.MainWinView;

/**
 * @author nikitadizhur
 *
 * The main class that bootstraps the application (launch it)
 */
public class Launcher {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new MainWinView(new CourseModel(new XmlCourseLoader("REPFILE/REP.XML"))).setVisible(true);
				} catch (Exception ¢) {
					¢.printStackTrace();
				}
			}
		});

	}

}
