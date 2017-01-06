package view;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import model.course.Lesson;
import model.course.StuffMember;
import model.course.WeekTime;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.ListSelectionModel;

public class AvailableCourses extends JPanel implements CourseListView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	static JList<String> lstCourseList;
	static JButton btnAddCourse;
	static DefaultListModel<String> courseModel;
	/**
	 * Create the panel.
	 */
	public AvailableCourses() {
		setMaximumSize(new Dimension(10000, 32767));
		setMinimumSize(new Dimension(210, 300));
		setPreferredSize(new Dimension(222, 300));
		
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(200, 20));
		textField.setMaximumSize(new Dimension(100000, 2147483647));
		textField.setMinimumSize(new Dimension(200, 20));
		textField.setText("Enter course number");
		textField.setBackground(SystemColor.controlHighlight);
		

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setPreferredSize(new Dimension(200, 22));
		scrollPane.setMinimumSize(new Dimension(200, 22));
		scrollPane.setBorder(new TitledBorder(null, "Course List", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLUE));
		
		JButton btnAddCourse = new JButton("Add Course");
		btnAddCourse.setMaximumSize(new Dimension(100000, 25));
		btnAddCourse.setMinimumSize(new Dimension(200, 25));
		
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnAddCourse, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
						.addComponent(textField, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAddCourse)
					.addGap(4))
		);

		setLayout(groupLayout);
		setCoursesListsModels();
		JList<String> lstAvailableCourses = new JList<String>(courseModel);
		
		lstAvailableCourses.setPreferredSize(new Dimension(180, 22));
		lstAvailableCourses.setMaximumSize(new Dimension(10000, 10000));
		lstAvailableCourses.setMinimumSize(new Dimension(180, 22));
		lstAvailableCourses.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		lstAvailableCourses.setBackground(UIManager.getColor("Button.background"));
		scrollPane.setViewportView(lstAvailableCourses);

		
	}
	private static void setCoursesListsModels() {
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

		courseModel = new DefaultListModel<>();

		for (Lesson val : list)
			courseModel.addElement(val.getCourse());

	}
	@Override
	public void addActionListener(ActionListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeActionListener(String property, ActionListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHighlightedCourse() {
		// TODO Auto-generated method stub
		return null;
	}
}
