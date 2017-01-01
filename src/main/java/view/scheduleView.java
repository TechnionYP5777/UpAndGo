package view;

import java.awt.EventQueue;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Component;

import model.course.Lesson;
import model.course.StuffMember;
import model.course.WeekTime;

public class scheduleView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					scheduleView frame = new scheduleView();
					frame.getContentPane().add(new JScrollPane(table));
					frame.setSize(500, 300);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	static JTable table;
	static String[] days = { "Shishi", "Hamishi", "Revii", "Shlishi", "Sheni", "Rishon", "" };

	static int rowNum = 13;
	static int colNum = 7;

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("serial")
	public scheduleView() {

		ArrayList<Lesson> list = new ArrayList<>();
		// list.add(new Lesson(new StuffMember("A", "a"),new
		// WeekTime(DayOfWeek.MONDAY, LocalTime.of(8, 30)), new WeekTime(
		// DayOfWeek.MONDAY ,LocalTime.of(10, 30)),"Taub 3",
		// Lesson.Type.LECTURE,12,"MATAM"));
		// list.add(new Lesson(new StuffMember("B", "b"),new
		// WeekTime(DayOfWeek.WEDNESDAY, LocalTime.of(8, 30)), new WeekTime(
		// DayOfWeek.WEDNESDAY ,LocalTime.of(10, 30)),"Taub 3",
		// Lesson.Type.LECTURE,12,"MATAM"));
		// list.add(new Lesson(new StuffMember("C", "c"),new
		// WeekTime(DayOfWeek.MONDAY, LocalTime.of(12, 30)), new WeekTime(
		// DayOfWeek.MONDAY ,LocalTime.of(14, 30)),"Taub 3",
		// Lesson.Type.TUTORIAL,12,"MATAM"));
		list.add(new Lesson(new StuffMember("D", "d"), new WeekTime(DayOfWeek.TUESDAY, LocalTime.of(10, 30)),
				new WeekTime(DayOfWeek.MONDAY, LocalTime.of(11, 30)), "Taub 3", Lesson.Type.LECTURE, 12, "MATAM"));

		getContentPane().setMinimumSize(new Dimension(500, 300));
		getContentPane().setLayout(new BorderLayout(0, 0));
		setMinimumSize(new Dimension(500, 300));

		table = new JTable(rowNum, colNum) {
			@Override
			public Component prepareRenderer(TableCellRenderer r, int row, int col) {
				Component $ = super.prepareRenderer(r, row, col);
				int rendererWidth = $.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(col);
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return $;
			}
		};
		table.setRowHeight(100);
		for (int i = 0; i < days.length; ++i) {
			TableColumn tc = table.getColumnModel().getColumn(i);
			tc.setHeaderValue(days[i]);

			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			tc.setCellRenderer(dtcr);
		}
		for (int hour = 8, ¢ = 0; ¢ < rowNum; ++¢, ++hour)
			table.getModel().setValueAt(getHour(hour), ¢, 6);

		insertCourses(list);
		table.setCellSelectionEnabled(true);
		getContentPane().add(table, BorderLayout.CENTER);

	}

	private static void insertCourses(ArrayList<Lesson> ls) {
		if (ls != null)
			for (Lesson l : ls) {
				int col = 6 - l.getDay();
				WeekTime s = l.getStartTime();
				WeekTime e = l.getEndTime();
				int row = s.getTime().getHour() - 8;
				int hours = e.getTime().getHour() - s.getTime().getHour();
				table.getModel().setValueAt(getDescriptionString(l), row, col);
			}

	}

	private static String getDescriptionString(Lesson ¢) {
		return ¢.getCourse() + " " + ¢.getType() + System.lineSeparator() + " " + ¢.getRepresenter().getTitle() + " "
				+ ¢.getRepresenter().getFirstName() + " " + ¢.getRepresenter().getLastName() + System.lineSeparator()
				+ " " + ¢.getPlace();
	}

	private static Object getHour(int hour) {

		return hour + ":30";
	}

}
