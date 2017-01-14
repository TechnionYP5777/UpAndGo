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
		LessonGroup lg1 = new LessonGroup(11);
		lg1.addLesson(l1);
		lg1.addLesson(l2);
		
		
		Lesson l3 = new Lesson(null, new WeekTime(DayOfWeek.MONDAY, LocalTime.of(9, 30)), new WeekTime(DayOfWeek.MONDAY, LocalTime.of(10, 30)), "taub" , Type.TUTORIAL, 15, "");
		Lesson l4 = new Lesson(null, new WeekTime(DayOfWeek.TUESDAY, LocalTime.of(7, 30)), new WeekTime(DayOfWeek.TUESDAY, LocalTime.of(11, 30)), "taub" , Type.LECTURE, 15, "");
		LessonGroup lg2 = new LessonGroup(15);
		lg2.addLesson(l3);
		lg2.addLesson(l4);
		
		
		Lesson l5 = new Lesson(null, new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 30)), new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(15, 30)), "Ulman" , Type.LECTURE, 11, "");
		Lesson l6 = new Lesson(null, new WeekTime(DayOfWeek.WEDNESDAY, LocalTime.of(12, 30)), new WeekTime(DayOfWeek.WEDNESDAY, LocalTime.of(14, 00)), "Ulman" , Type.LECTURE, 11, "");
		LessonGroup lg3 = new LessonGroup(11);
		lg3.addLesson(l5);
		lg3.addLesson(l6);
		
		
		Lesson l7 = new Lesson(null, new WeekTime(DayOfWeek.THURSDAY, LocalTime.of(7, 30)), new WeekTime(DayOfWeek.THURSDAY, LocalTime.of(10, 00)), "taub" , Type.LECTURE, 11, "");
		Lesson l8 = new Lesson(null, new WeekTime(DayOfWeek.TUESDAY, LocalTime.of(11, 30)), new WeekTime(DayOfWeek.TUESDAY, LocalTime.of(14, 30)), "taub" , Type.LECTURE, 11, "");
		LessonGroup lg4 = new LessonGroup(11);
		lg3.addLesson(l7);
		lg3.addLesson(l8);
		
		Lesson l9 = new Lesson(null, new WeekTime(DayOfWeek.THURSDAY, LocalTime.of(12, 00)), new WeekTime(DayOfWeek.THURSDAY, LocalTime.of(16, 30)), "Ulman" , Type.LECTURE, 11, "");
		LessonGroup lg5 = new LessonGroup(11);
		lg5.addLesson(l9);
		
		Lesson l10 = new Lesson(null, new WeekTime(DayOfWeek.MONDAY, LocalTime.of(16, 00)), new WeekTime(DayOfWeek.MONDAY, LocalTime.of(17, 00)), "Ulman" , Type.LECTURE, 11, "");
		LessonGroup lg6 = new LessonGroup(11);
		lg6.addLesson(l10);


		List<LessonGroup> ls = new ArrayList<>();
		ls.add(lg1);
		ls.add(lg2);
		ls.add(lg3);
		ls.add(lg4);
		ls.add(lg5);
		ls.add(lg6);
		
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
