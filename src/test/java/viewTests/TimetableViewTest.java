package viewTests;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import model.course.Lesson;
import model.course.Lesson.Type;
import model.course.LessonGroup;
import model.course.WeekTime;
import view.TimetableVIew;

public class TimetableViewTest {
	private final JFrame frame = new JFrame("Testing");
	private final TimetableVIew ttpanel = new TimetableVIew();

	public TimetableViewTest() {
		frame.add(ttpanel);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

		final Lesson l1 = new Lesson(null, new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(8, 30)),
				new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 30)), "אולמן", Type.LECTURE, 11, "123", "מערכות הפעלה"),
				l2 = new Lesson(null, new WeekTime(DayOfWeek.TUESDAY, LocalTime.of(15, 30)),
						new WeekTime(DayOfWeek.TUESDAY, LocalTime.of(18, 00)), "אולמן", Type.LECTURE, 11, "123",
						"מערכות הפעלה");
		final LessonGroup lg1 = new LessonGroup(11);
		lg1.addLesson(l1);
		lg1.addLesson(l2);

		final Lesson l3 = new Lesson(null, new WeekTime(DayOfWeek.MONDAY, LocalTime.of(9, 30)),
				new WeekTime(DayOfWeek.MONDAY, LocalTime.of(10, 30)), "טאוב", Type.TUTORIAL, 15, "123",
				"מבוא לתכנות מערכות"),
				l4 = new Lesson(null, new WeekTime(DayOfWeek.TUESDAY, LocalTime.of(7, 30)),
						new WeekTime(DayOfWeek.TUESDAY, LocalTime.of(11, 30)), "טאוב", Type.LECTURE, 15, "123",
						"מבוא לתכנות מערכות");
		final LessonGroup lg2 = new LessonGroup(15);
		lg2.addLesson(l3);
		lg2.addLesson(l4);

		final Lesson l5 = new Lesson(null, new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(10, 30)),
				new WeekTime(DayOfWeek.SUNDAY, LocalTime.of(15, 30)), "אולמן", Type.LECTURE, 11, "123", "אלגו"),
				l6 = new Lesson(null, new WeekTime(DayOfWeek.WEDNESDAY, LocalTime.of(12, 30)),
						new WeekTime(DayOfWeek.WEDNESDAY, LocalTime.of(14, 00)), "אולמן", Type.LECTURE, 11, "123",
						"אלגו");
		final LessonGroup lg3 = new LessonGroup(11);
		lg3.addLesson(l5);
		lg3.addLesson(l6);

		final Lesson l7 = new Lesson(null, new WeekTime(DayOfWeek.THURSDAY, LocalTime.of(7, 30)),
				new WeekTime(DayOfWeek.THURSDAY, LocalTime.of(10, 00)), "טאוב", Type.LECTURE, 11, "123", "מבני נתונים"),
				l8 = new Lesson(null, new WeekTime(DayOfWeek.TUESDAY, LocalTime.of(11, 30)),
						new WeekTime(DayOfWeek.TUESDAY, LocalTime.of(14, 30)), "טאוב", Type.LECTURE, 11, "123",
						"מבני נתונים");
		final LessonGroup lg4 = new LessonGroup(11);
		lg3.addLesson(l7);
		lg3.addLesson(l8);

		final Lesson l9 = new Lesson(null, new WeekTime(DayOfWeek.THURSDAY, LocalTime.of(12, 00)),
				new WeekTime(DayOfWeek.THURSDAY, LocalTime.of(16, 30)), "פישבך", Type.LECTURE, 11, "123", "חישוביות");
		final LessonGroup lg5 = new LessonGroup(11);
		lg5.addLesson(l9);

		final Lesson l10 = new Lesson(null, new WeekTime(DayOfWeek.MONDAY, LocalTime.of(16, 00)),
				new WeekTime(DayOfWeek.MONDAY, LocalTime.of(17, 00)), "מאייר", Type.LECTURE, 11, "123", "אוטומטיים");
		final LessonGroup lg6 = new LessonGroup(11);
		lg6.addLesson(l10);

		final List<LessonGroup> ls = new ArrayList<>();
		ls.add(lg1);
		ls.add(lg2);
		ls.add(lg3);
		ls.add(lg4);
		ls.add(lg5);
		ls.add(lg6);

		ttpanel.displaySchedule(ls);
	}

	public static void main(final String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			@SuppressWarnings("unused")
			public void run() {
				new TimetableViewTest();

			}

		});
	}
}
