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
import view.MainWinView;
import view.TimetableVIew;
import view.mainLuzerView;

/**
 * @author nikitadizhur
 *
 * The main class that bootstraps the application (launch it)
 */
public class Launcher {
	static protected CourseModel cModel;
	static protected TimeTableModel ttModel;
	static protected MenuModel mModel;
	
	static protected AvailableCourses acView;
	static protected TimetableVIew ttView;
	static protected mainLuzerView mlView;
	
	static protected CourseListController clCtrl;
	static protected TimeTableController ttCtrl;
	static protected MenuController mCtrl;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					cModel = new CourseModel(new XmlCourseLoader("REPFILE/REP.XML"));
					ttModel = new TimeTableModel();
					mModel = new MenuModel();
					
					acView = new AvailableCourses();
					ttView = new TimetableVIew();
					mlView = new mainLuzerView();
					
					clCtrl = new CourseListController(cModel, acView);
					ttCtrl = new TimeTableController(ttModel, ttView, clCtrl);
					mCtrl = new MenuController(mModel, mlView, clCtrl, ttCtrl);
					
//					CourseModel cModel = new CourseModel(new XmlCourseLoader("REPFILE/REP.XML"));
//					CourseListController clCtrl = new CourseListController(cModel, new AvailableCourses());
//					TimeTableController ttCtrl = new TimeTableController(new TimeTableModel(), new TimetableVIew(), clCtrl);
//					MenuController mCtrl = new MenuController(new MenuModel(), new mainLuzerView(), clCtrl, ttCtrl);
//					new MainWinView(new CourseModel(new XmlCourseLoader("REPFILE/REP.XML"))).setVisible(true);
				} catch (Exception ¢) {
					¢.printStackTrace();
				}
			}
		});

	}

}
