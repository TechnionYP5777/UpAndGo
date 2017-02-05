package viewTests;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controller.CourseListController;
import model.CourseModel;
import model.loader.XmlCourseLoader;
import view.AvailableCourses;

public class viewTests {
	static CourseListController cntr; 

	public viewTests() {
		
		design();
	}

	public static void design() {
		final JFrame f = new JFrame();
		f.setMinimumSize(new Dimension(280, 500));
		f.setSize(280, 500);
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		f.setVisible(true);
		final AvailableCourses  avl= new AvailableCourses();
		cntr= new CourseListController(new CourseModel(new XmlCourseLoader("resources/testXML/REP.XML")), avl);
		f.getContentPane().add(avl);
	}

	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			@SuppressWarnings("unused")
			public void run() {
				try {
					new viewTests();
				} catch (final Exception ¢) {
					¢.printStackTrace();
				}

			}
		});
	}

}
