///
// Author : Lidia P.  Alex.V
///
package view;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import model.course.Course;
import model.course.Lesson;
import model.course.StuffMember;
import model.course.WeekTime;
import model.loader.XmlCourseLoader;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.ListSelectionModel;

public class AvailableCourses extends JPanel implements CourseListView {

	private static final long serialVersionUID = 1L;
	private JTextField searchField;
	private JScrollPane scrollPane;
	static JList<String> lstAvailableCourses;
	static JButton btnAddCourse;
	static DefaultListModel<String> courseModel;

	/**
	 * Create the panel.
	 */
	public AvailableCourses() {
		setMaximumSize(new Dimension(10000, 32767));
		setMinimumSize(new Dimension(210, 200));
		setPreferredSize(new Dimension(238, 397));
		setTextField();
		setAddButton();
		setListViewArea();
		setGroupLayout();
	}

	// **************** AUX methods for creating the view design ***********//

	//
	// Sets the scroll pane with the list of available courses
	//
	private void setListViewArea() {
		setScrollPane();
		setCoursesListsModels();
		setAvailableCoursesList();
		scrollPane.setViewportView(lstAvailableCourses);
	}

	//
	// Sets the list of available courses with the relevant model
	//
	private static void setAvailableCoursesList() {
		lstAvailableCourses = new JList<>(courseModel);
		lstAvailableCourses.setPreferredSize(new Dimension(180, 22));
		lstAvailableCourses.setMaximumSize(new Dimension(10000, 10000));
		lstAvailableCourses.setMinimumSize(new Dimension(180, 22));
		lstAvailableCourses.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		lstAvailableCourses.setBackground(UIManager.getColor("Button.background"));
	}

	//
	// Sets the overall group layout
	//
	private void setGroupLayout() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnAddCourse, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
						.addComponent(searchField, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
				.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(searchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnAddCourse).addGap(4)));

		setLayout(groupLayout);
	}

	//
	// Sets the button preferences
	//
	private static void setAddButton() {
		btnAddCourse = new JButton("Add Course");
		btnAddCourse.setMaximumSize(new Dimension(100000, 25));
		btnAddCourse.setMinimumSize(new Dimension(200, 25));
	}

	//
	// Sets the scroll pane for the list of courses preferences
	//
	private void setScrollPane() {
		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setPreferredSize(new Dimension(200, 22));
		scrollPane.setMinimumSize(new Dimension(200, 22));
		scrollPane.setBorder(
				new TitledBorder(null, "Course List", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLUE));
		return;
	}

	//
	// Sets the search field preferences
	//
	private void setTextField() {
		searchField = new JTextField();
		searchField.setPreferredSize(new Dimension(200, 20));
		searchField.setMaximumSize(new Dimension(100000, 2147483647));
		searchField.setMinimumSize(new Dimension(200, 20));
		searchField.setText("Enter course number");
		searchField.setBackground(SystemColor.controlHighlight);
	}

	//
	// Sets the List model of the courses available
	//
	private static void setCoursesListsModels() {
		XmlCourseLoader cr = new XmlCourseLoader("resources/testXML/viewTest.XML");
		List<Course> clist = new ArrayList<>(cr.loadAllCourses().values());
		courseModel = new DefaultListModel<>();
		for (Course val : clist)
			courseModel.addElement(val.getName() + " " + val.getId());

	}

	// *************************** actions and events **********************//
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
