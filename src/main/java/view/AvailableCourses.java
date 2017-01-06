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
import model.course.Course;
import model.loader.XmlCourseLoader;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.ListSelectionModel;
import javax.swing.JCheckBox;

public class AvailableCourses extends JPanel implements CourseListView {

	private static final long serialVersionUID = 1L;
	static JTextField searchField;
	private JScrollPane scrollPane;
	static JList<String> lstAvailableCourses;
	static JButton btnAddCourse;
	static DefaultListModel<String> courseModel;
	static List<Course> clist;

	private static final String DEFAULT_COURSE_NUM_TEXT = "Enter course number or name";
	private JCheckBox chckbxKdamim;
	private JCheckBox chckbxFaculty;
	private JCheckBox chckbxTaken;
	private JCheckBox chckbxCats;

	/**
	 * Create the panel.
	 */
	public AvailableCourses() {
		setMaximumSize(new Dimension(10000, 32767));
		setMinimumSize(new Dimension(210, 200));
		setPreferredSize(new Dimension(324, 467));
		setTextField();
		setAddButton();
		setListViewArea();
		setGroupLayout();
		createEvents();
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
		lstAvailableCourses.setToolTipText("\"\"");
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
		chckbxKdamim = new JCheckBox("יש קדמים");
		chckbxFaculty = new JCheckBox("לפי פקולטה");
		chckbxTaken = new JCheckBox("קורסים שלקחתי");
		chckbxCats = new JCheckBox("חתולים");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
						.addComponent(searchField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
						.addComponent(btnAddCourse, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(chckbxTaken)
								.addComponent(chckbxFaculty))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(chckbxKdamim)
								.addComponent(chckbxCats))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(searchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(chckbxFaculty)
						.addComponent(chckbxKdamim))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(chckbxTaken)
						.addComponent(chckbxCats))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAddCourse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(13))
		);

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
	private static void setTextField() {
		searchField = new JTextField();
		searchField.setPreferredSize(new Dimension(200, 20));
		searchField.setMaximumSize(new Dimension(100000, 2147483647));
		searchField.setMinimumSize(new Dimension(200, 20));
		searchField.setText(DEFAULT_COURSE_NUM_TEXT);
		searchField.setBackground(SystemColor.controlHighlight);
	}

	//
	// Sets the List model of the courses available
	//
	private static void setCoursesListsModels() {
		XmlCourseLoader cr = new XmlCourseLoader("resources/testXML/viewTest.XML");
		clist = new ArrayList<>(cr.loadAllCourses().values());
		courseModel = new DefaultListModel<>();
		for (Course val : clist)
			courseModel.addElement(getNameToDisplay(val));

	}

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
		return val.getName() + " " + val.getId()+ "\n" + val.getFaculty()+" " +val.getPoints();
	}
	//
	// Searches the courses list for the name or the id of the course
	// typed in the search field.
	// if found returns the string that will be displayed in the list
	// else returns "".
	//
	static String findCourse(  ) {
		String searched =  searchField.getText();
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
	static Course getCoursebyString(String txt  ) {
		for (Course $ : clist)
			if (($ + "").equals(txt) )
				return $;
		return null;
	}
	// *************************** actions and events **********************//
	@Override
	public void addActionListener(@SuppressWarnings("unused") ActionListener __) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeActionListener(@SuppressWarnings("unused") String property,
			@SuppressWarnings("unused") ActionListener __) {
		// TODO Auto-generated method stub

	}

	@Override
	public void propertyChange(@SuppressWarnings("unused") PropertyChangeEvent evt) {
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

	private static void createEvents() {
		lstAvailableCourses.addMouseMotionListener( new MouseMotionAdapter() {
			
			@Override
			public void mouseMoved( MouseEvent e) {
				JList<?> theList = (JList<?>) e.getSource();
				ListModel<?> model = theList.getModel();
				int index = theList.locationToIndex(e.getPoint());
				if (index <= -1)
					return;
				theList.setToolTipText("");
				String text = (String) model.getElementAt(index);
				Course c = getCoursebyString(text);
				theList.setToolTipText(getDescription(c));
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

	///
	// This class is a massage box class
	///
	public static class Message {

		public static void infoBox(String infoMessage, String titleBar, ImageIcon i) {

			JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.PLAIN_MESSAGE, i);

		}
	}
}
