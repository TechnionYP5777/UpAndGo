package viewTests;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.course.Lesson;
import model.course.Lesson.Type;
import model.course.LessonGroup;
import model.course.WeekTime;
import view.TimetableVIew;

public class TimetableViewTest {
	private JFrame frame = new JFrame("Testing");
	private TimetableVIew ttpanel = new TimetableVIew();
	
	@SuppressWarnings("static-access")
	public TimetableViewTest(){
		frame.add(ttpanel);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		Lesson l1 = new Lesson(null, new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(8, 30)), new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 30)), "Ulman" , Type.LECTURE, 11, "");
		Lesson l2 = new Lesson(null, new WeekTime(DayOfWeek.TUESDAY, LocalTime.of(15, 30)), new WeekTime(DayOfWeek.TUESDAY, LocalTime.of(18, 00)), "Ulman" , Type.LECTURE, 11, "");
		LessonGroup lg = new LessonGroup(11);
		lg.addLesson(l1);
		lg.addLesson(l2);
		List<LessonGroup> ls = new ArrayList<>();
		ls.add(lg);
		ttpanel.displaySchedule(ls);
	}
	
	public static void main(String[] args){

		
		SwingUtilities.invokeLater(new Runnable(){
			@SuppressWarnings("unused")
			@Override
			public void run() {
				new TimetableViewTest();		
			}
			
		});
	}
}
