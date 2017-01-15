/**
 * 
 */
package launcher;

import java.awt.EventQueue;

import controller.CourseListController;
import controller.MenuController;
import controller.TimeTableController;
import model.CourseModel;
import model.MenuModel;
import model.TimeTableModel;
import model.loader.XmlCourseLoader;
import view.AvailableCourses;
import view.TimetableVIew;
import view.mainLuzerView;

/**
 * @author nikitadizhur
 *
 *         The main class that bootstraps the application (launch it)
 */
public class Launcher {
	protected static CourseModel cModel;
	protected static TimeTableModel ttModel;
	protected static MenuModel mModel;

	protected static AvailableCourses acView;
	protected static TimetableVIew ttView;
	protected static mainLuzerView mlView;

	protected static CourseListController clCtrl;
	protected static TimeTableController ttCtrl;
	protected static MenuController mCtrl;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					cModel = new CourseModel(new XmlCourseLoader("REPFILE/test.XML"));
					ttModel = new TimeTableModel();
					mModel = new MenuModel();

					acView = new AvailableCourses();
					ttView = new TimetableVIew();

					clCtrl = new CourseListController(cModel, acView);
//					ttCtrl = new TimeTableController(ttModel, ttView, clCtrl);
					mlView = new mainLuzerView(ttView, acView);
//					mCtrl = new MenuController(mModel, mlView, clCtrl, ttCtrl);
					mlView.setVisible(true);
				} catch (Exception ¢) {
					¢.printStackTrace();
				}
			}
		});

	}

}
