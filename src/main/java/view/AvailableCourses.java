///
// Author : Lidia P.  Alex.V
///
package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import command.CourseCommand;
import model.course.Course;
import model.course.CourseId;
import model.loader.XmlCourseLoader;
import property.CourseProperty;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.ListSelectionModel;
import javax.swing.JCheckBox;
import javax.swing.border.LineBorder;

public class AvailableCourses extends JPanel implements CourseListView {

	private static final long serialVersionUID = 1L;
	static JTextField searchField;

	static JScrollPane scrollPane;
	static DefaultListModel<String> courseModel;
	static JList<String> lstAvailableCourses;
	static JButton btnAddCourse;

	static JScrollPane scpChoseCourses;
	static DefaultListModel<String> ChosenCourseModel;
	static JList<String> lstChosenCourses;
	static JButton btnRemoveCourse;

	static List<Course> clist;

	private static final String DEFAULT_COURSE_NUM_TEXT = "Enter course number or name";
	private JCheckBox chckbxKdamim;
	private JCheckBox chckbxFaculty;
	private JCheckBox chckbxTaken;
	private JCheckBox chckbxCats;
	static List<ActionListener> listeners;

	/**
	 * Create the panel.
	 */
	public AvailableCourses() {
		setPreferredSize(new Dimension(300, 700));
		setSize(new Dimension(300, 700));
		setMinimumSize(new Dimension(300, 700));
		listeners = new ArrayList<>();
		setTextField();
		setAddButton();
		setRemoveButton();
		setListViewArea();
		setCheckBoxes();
		setGroupLayout();
		createEvents();
	}

	// **************** AUX methods for creating the view design ***********//
	//
	// Sets the search field preferences
	//
	private static void setTextField() {
		searchField = new JTextField();
		searchField.setPreferredSize(new Dimension(200, 20));
		searchField.setMaximumSize(new Dimension(100000, 2147483647));
		searchField.setMinimumSize(new Dimension(200, 20));
		searchField.setText(DEFAULT_COURSE_NUM_TEXT);
		searchField.setBackground(SystemColor.controlHighlight);
	}
	//
	// Sets the button preferences
	//
	private static void setAddButton() {
		btnAddCourse = new JButton("Add Course");
		btnAddCourse.setMaximumSize(new Dimension(100000, 25));
		btnAddCourse.setMinimumSize(new Dimension(200, 25));
	}
	private static void setRemoveButton() {
		btnRemoveCourse = new JButton("Remove Course");
		btnAddCourse.setMaximumSize(new Dimension(100000, 25));
		btnAddCourse.setMinimumSize(new Dimension(200, 25));
	}
	//
	// Sets the scroll pane with the list of available courses
	//
	private void setListViewArea() {
		setScrollPane();
		setCoursesListsModels();
		setAvailableCoursesList();
		setChosenCoursesList();
		scrollPane.setViewportView(lstAvailableCourses);
		scpChoseCourses.setViewportView(lstChosenCourses);

	}
	// @@@@@@@@@@@@@@@@@@@ setting list view areas @@@@@@@@@@@@@@@@@@@@//

	//
	// Sets the scroll pane for the list of courses preferences
	//
	private static void setScrollPane() {
		scrollPane = new JScrollPane();
		scrollPane.setSize(new Dimension(200, 50));
		scrollPane.setViewportBorder(null);
		scrollPane.setPreferredSize(new Dimension(200, 80));
		scrollPane.setMinimumSize(new Dimension(200, 80));
		scrollPane.setBorder(
				new TitledBorder(null, "Course List", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLUE));
		scpChoseCourses = new JScrollPane();
		scpChoseCourses.setViewportBorder(null);
		scpChoseCourses.setPreferredSize(new Dimension(200, 100));
		scpChoseCourses.setMinimumSize(new Dimension(200, 100));
		scpChoseCourses.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Chosen Courses",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 204, 51)));
		return;
	}
	//
	// Sets the List model of the courses available
	//
	private void setCoursesListsModels() {
		XmlCourseLoader cr = new XmlCourseLoader("resources/testXML/viewTest.XML");
		clist = new ArrayList<>(cr.loadAllCourses().values());
		listeners.forEach(
				x -> x.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, CourseCommand.GET_QUERY)));
		courseModel = new DefaultListModel<>();
		ChosenCourseModel =  new DefaultListModel<>();
		 for (Course val : clist)
			 ChosenCourseModel.addElement(getNameToDisplay(val));

	}
	//
	// Sets the list of available courses with the relevant model
	//
	private static void setAvailableCoursesList() {
		lstAvailableCourses = new JList<>(courseModel);
		lstAvailableCourses.setSize(new Dimension(200, 50));
		lstAvailableCourses.setToolTipText("");
		lstAvailableCourses.setPreferredSize(new Dimension(190, 50));
		lstAvailableCourses.setMaximumSize(new Dimension(10000, 10000));
		lstAvailableCourses.setMinimumSize(new Dimension(190, 50));
		lstAvailableCourses.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		lstAvailableCourses.setBackground(UIManager.getColor("Button.background"));
	}

	//
	// Sets the list of chosen courses with the relevant model
	//
	private static void setChosenCoursesList() {
		lstChosenCourses = new JList<>(ChosenCourseModel);
		lstChosenCourses.setToolTipText("");
		lstChosenCourses.setPreferredSize(new Dimension(190, 50));
		lstChosenCourses.setMaximumSize(new Dimension(10000, 10000));
		lstChosenCourses.setMinimumSize(new Dimension(190, 50));
		lstChosenCourses.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		lstChosenCourses.setBackground(UIManager.getColor("Button.background"));
	}

	// @@@@@@@@@@@@@@@@@@@ end of setting list view areas @@@@@@@@@@@@@@@@@@@@//
	//
	// Sets the check boxes 
	//
	private void setCheckBoxes() {
		chckbxKdamim = new JCheckBox("יש קדמים");
		chckbxFaculty = new JCheckBox("לפי פקולטה");
		chckbxTaken = new JCheckBox("קורסים שלקחתי");
		chckbxCats = new JCheckBox("חתולים");
	}
	//
	// Sets the overall group layout
	//
	private void setGroupLayout() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
						.addComponent(btnAddCourse, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
						.addGroup(
								Alignment.LEADING, groupLayout
										.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(chckbxTaken).addComponent(chckbxFaculty))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(chckbxKdamim).addComponent(chckbxCats)))
						.addComponent(scpChoseCourses, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
						.addComponent(btnRemoveCourse, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 300,
								Short.MAX_VALUE)
						.addComponent(searchField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
				.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(scpChoseCourses, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE).addGap(3)
						.addComponent(btnRemoveCourse).addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(searchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(chckbxFaculty)
								.addComponent(chckbxKdamim))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(chckbxTaken)
								.addComponent(chckbxCats))
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnAddCourse,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(13)));

		setLayout(groupLayout);
	}


	// *************************** helper private methods **********************//

	//
	// Create the string that will be displayed in the list view
	//
	private static String getNameToDisplay(Course val) {
		return val.getName() + " " + val.getId();
	}

	//
	// Create the string that will be displayed by the tool tip
	//
	static String getDescription(Course val) {
		return val.getName() + " " + val.getId() + "\n" + val.getFaculty() + " " + val.getPoints();
	}

	static String getIdFromDescription(String val) {
		return val.split(" ")[0]; // 0= name , 1 = id
	}

	//
	// Searches the courses list for the name or the id of the course
	// typed in the search field.
	// if found returns the string that will be displayed in the list
	// else returns "".
	//
	static String findCourse() {
		String searched = searchField.getText();
		for (Course ¢ : clist)
			if (¢.getId().equals(searched) || ¢.getName().equals(searched))
				return getNameToDisplay(¢);
		return "";
	}

	//
	// Searches the courses list for the name and id of the course
	// as returned by getNameToDisplay().
	// if found returns the course
	// else returns null.
	//
	static Course getCoursebyString(String txt) {
		for (Course $ : clist)
			if (($ + "").equals(txt))
				return $;
		return null;
	}

	// *************************** actions and events **********************//
	
	private static void createEvents() {
		lstAvailableCourses.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {

				JList<?> theList = (JList<?>) e.getSource();

				ListModel<?> model = theList.getModel();
				int index = theList.locationToIndex(e.getPoint());
				if (index <= -1)
					return;
				highlighted = getIdFromDescription((String) model.getElementAt(index));
				listeners.forEach(x -> x
						.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, CourseCommand.DETAILS)));

			}
		});
		lstChosenCourses.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {

				JList<?> theList = (JList<?>) e.getSource();

				ListModel<?> model = theList.getModel();
				int index = theList.locationToIndex(e.getPoint());
				if (index <= -1)
					return;
				highlighted = getIdFromDescription((String) model.getElementAt(index));
				listeners.forEach(x -> x
						.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, CourseCommand.DETAILS)));

			}
		});
		btnAddCourse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
				if (lstAvailableCourses.getSelectedValue() != null)
					if (courseModel.isEmpty())
						btnAddCourse.setEnabled(false);
					else
						Message.infoBox("add pressed", "ADD", null);
			}
		});
		searchField.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(@SuppressWarnings("unused") MouseEvent __) {
				searchField.setText("");
			}
		});
		searchField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {

				String searched = findCourse();
				if (!"".equals(searched))
					lstAvailableCourses.setSelectedValue(searched, true);
				else {
					searchField.setText("");
					lstAvailableCourses.clearSelection();
				}
			}

		});
		searchField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(@SuppressWarnings("unused") FocusEvent __) {
				searchField.setText(DEFAULT_COURSE_NUM_TEXT);
			}
		});
	}

	// ******************* communication with controller ******************//

	static String highlighted = "";
	private JScrollPane ScpChosenCourse;
	// private JList<String> lstChosenCourses;
	
	@Override
	public void addActionListener(ActionListener ¢) {
		listeners.add(¢);
	}

	@Override
	public void removeActionListener(ActionListener ¢) {
		listeners.remove(¢);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		// i need to check what the hell is changed and act according
		switch (evt.getPropertyName()) {
		case CourseProperty.DETAILS:
			lstAvailableCourses.setToolTipText((evt.getNewValue() + ""));
			lstChosenCourses.setToolTipText((evt.getNewValue() + ""));
			break;
		case CourseProperty.COURSE_LIST:
			courseModel = new DefaultListModel<>();
			for (CourseId val : (List<CourseId>) evt.getNewValue())
				courseModel.addElement(val.number + " " + val.name);
			lstAvailableCourses.setModel(courseModel);
			break;
		case CourseProperty.CHOSEN_LIST:
			ChosenCourseModel = new DefaultListModel<>();
			for (CourseId val : (List<CourseId>) evt.getNewValue())
				ChosenCourseModel.addElement(val.number + " " + val.name);
			lstChosenCourses.setModel(ChosenCourseModel);
			break;
		default:
			break;
		}
	}

	@Override
	public String getQuery() {
		// TODO Auto-generated method stub
		return "";
	}


	@Override
	public String getHighlightedCourse() {
		return highlighted;
	}



	@Override
	public String getLastPickedCourse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLastDropedCourse() {
		// TODO Auto-generated method stub
		return null;
	}
	
	// *************************** message box class  **********************//

	///
	// This class is a massage box class
	///
	public static class Message {

		public static void infoBox(String infoMessage, String titleBar, ImageIcon i) {

			JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.PLAIN_MESSAGE, i);

		}
	}
}
