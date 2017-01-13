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
import javax.swing.DefaultComboBoxModel;
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
import model.course.CourseId;
import property.CourseProperty;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.ListSelectionModel;
import javax.swing.JCheckBox;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.ComponentOrientation;

public class AvailableCourses extends JPanel implements CourseListView {

	private static final long serialVersionUID = 1L;
	static ArrayList<ImageIcon> catList;
	static int catIconNum;

	static JTextField searchField;

	static JScrollPane scrollPane;
	static DefaultListModel<String> courseModel;
	static JList<String> lstAvailableCourses;
	static JButton btnAddCourse;

	static JScrollPane scpChoseCourses;
	static DefaultListModel<String> ChosenCourseModel;
	static JList<String> lstChosenCourses;
	static JButton btnRemoveCourse;

	static DefaultComboBoxModel<String> facultiesModel;
	static JComboBox<String> cmbFaculties;

	private static final String DEFAULT_COURSE_NUM_TEXT = "הקש מספר קורס לחיפוש";
	static JCheckBox chckbxKdamim;
	static JCheckBox chckbxTaken;
	static JCheckBox chckbxCats;
	static List<ActionListener> listeners;

	/**
	 * Create the panel.
	 */
	public AvailableCourses() {
		catList = new ArrayList<>();
		setCatList();
		setPreferredSize(new Dimension(280, 500));
		setMinimumSize(new Dimension(250, 500));
		listeners = new ArrayList<>();
		setTextField();
		setAddButton();
		setRemoveButton();
		setListViewArea();
		setCheckBoxes();
		setComboBox();
		setGroupLayout();
		createEvents();
		btnAddCourse.setEnabled(!courseModel.isEmpty());
		btnRemoveCourse.setEnabled(!ChosenCourseModel.isEmpty());

	}

	// **************** AUX methods for creating the view design ***********//
	private static void setCatList() {
		catList.add(new ImageIcon("resources/cat-fish-icon.png"));
		catList.add(new ImageIcon("resources/cat-purr-icon.png"));
		catList.add(new ImageIcon("resources/cat-drunk-icon.png"));
		catIconNum = 0;
	}

	//
	// Sets the search field preferences
	//
	private static void setTextField() {
		searchField = new JTextField();
		searchField.setHorizontalAlignment(SwingConstants.CENTER);
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
		btnAddCourse = new JButton("הוסף");
		btnAddCourse.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		btnAddCourse.setPreferredSize(new Dimension(150, 25));
		btnAddCourse.setMaximumSize(new Dimension(100000, 25));
		btnAddCourse.setMinimumSize(new Dimension(150, 25));
	}

	private static void setRemoveButton() {
		btnRemoveCourse = new JButton("הסר");
		btnRemoveCourse.setPreferredSize(new Dimension(200, 25));
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
		scrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		scrollPane.setAutoscrolls(true);
		scrollPane.setMinimumSize(new Dimension(200, 80));
		scrollPane.setBorder(
				new TitledBorder(null, "רשימת קורסים", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLUE));
		scpChoseCourses = new JScrollPane();
		scpChoseCourses.setMinimumSize(new Dimension(200, 100));
		scpChoseCourses.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "הקורסים שנבחרו",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 204, 51)));
		return;
	}

	//
	// Sets the List model of the courses available
	//
	private void setCoursesListsModels() {
		listeners.forEach(
				x -> x.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, CourseCommand.GET_QUERY)));
		courseModel = new DefaultListModel<>();
		ChosenCourseModel = new DefaultListModel<>();
	}

	//
	// Sets the list of available courses with the relevant model
	//
	private static void setAvailableCoursesList() {
		lstAvailableCourses = new JList<>(courseModel);
		lstAvailableCourses.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		lstAvailableCourses.setToolTipText("");
		lstAvailableCourses.setMinimumSize(new Dimension(190, 50));
		lstAvailableCourses.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		lstAvailableCourses.setBackground(UIManager.getColor("Button.background"));
	}

	//
	// Sets the list of chosen courses with the relevant model
	//
	private static void setChosenCoursesList() {
		lstChosenCourses = new JList<>(ChosenCourseModel);
		lstChosenCourses.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		lstChosenCourses.setToolTipText("");
		lstChosenCourses.setMinimumSize(new Dimension(190, 50));
		lstChosenCourses.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		lstChosenCourses.setBackground(UIManager.getColor("Button.background"));
	}

	// @@@@@@@@@@@@@@@@@@@ end of setting list view areas @@@@@@@@@@@@@@@@@@@@//
	//
	// Sets the check boxes
	//
	private static void setCheckBoxes() {
		chckbxKdamim = new JCheckBox("יש קדמים");
		chckbxKdamim.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		chckbxTaken = new JCheckBox("קורסים שלקחתי");
		chckbxTaken.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		chckbxCats = new JCheckBox("חתולים");
		chckbxCats.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	}

	//
	// Sets the combo box list
	//
	private static void setComboBox() {
		facultiesModel = new DefaultComboBoxModel<>();
		facultiesModel.addElement("all faculties");
		facultiesModel.addElement("Physics");
		facultiesModel.addElement("Computers");
		facultiesModel.addElement("Sports");
		facultiesModel.addElement("EE");
		cmbFaculties = new JComboBox<>(facultiesModel);
		cmbFaculties.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		((JLabel) cmbFaculties.getRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);
	}

	//
	// Sets the overall group layout
	//
	private void setGroupLayout() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout
						.createParallelGroup(Alignment.TRAILING)
						.addComponent(chckbxCats, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.LEADING,
								groupLayout.createSequentialGroup().addGap(36).addComponent(chckbxKdamim).addGap(18)
										.addComponent(chckbxTaken, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
						.addComponent(searchField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
						.addComponent(btnRemoveCourse, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 256,
								Short.MAX_VALUE)
						.addComponent(scpChoseCourses, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
						.addComponent(cmbFaculties, 0, 256, Short.MAX_VALUE)
						.addComponent(btnAddCourse, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)).addGap(12)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(scpChoseCourses, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnRemoveCourse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(searchField, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(cmbFaculties, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(chckbxTaken)
								.addComponent(chckbxKdamim))
						.addGap(3).addComponent(chckbxCats).addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btnAddCourse, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGap(10)));
		setLayout(groupLayout);
	}

	// *************************** helper private methods
	// **********************//

	static String getIdFromDescription(String val) {
		return val.split(" ")[0]; // 0= name , 1 = id
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
		cmbFaculties.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
				chosenFaculty = cmbFaculties.getSelectedItem() + "";
				listeners.forEach(x -> x
						.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, CourseCommand.GET_QUERY)));
			}
		});
		btnAddCourse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
				if (lstAvailableCourses.getSelectedValue() != null)
					if (courseModel.isEmpty())
						btnAddCourse.setEnabled(false);
					else {
						picked = getIdFromDescription(lstAvailableCourses.getSelectedValue());
						listeners.forEach(x -> x.actionPerformed(
								new ActionEvent(this, ActionEvent.ACTION_PERFORMED, CourseCommand.PICK)));
					}
			}
		});
		btnRemoveCourse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
				if (lstChosenCourses.getSelectedValue() != null)
					if (ChosenCourseModel.isEmpty())
						btnRemoveCourse.setEnabled(false);
					else {
						droped = getIdFromDescription(lstChosenCourses.getSelectedValue());
						listeners.forEach(x -> x.actionPerformed(
								new ActionEvent(this, ActionEvent.ACTION_PERFORMED, CourseCommand.DROP)));
					}
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
				query = searchField.getText();
				listeners.forEach(x -> x
						.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, CourseCommand.GET_QUERY)));
			}
		});
		searchField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(@SuppressWarnings("unused") FocusEvent __) {
				if (!courseModel.isEmpty() && !"".equals(searchField.getText()))
					return;
				searchField.setText(DEFAULT_COURSE_NUM_TEXT);
				query = "";
				listeners.forEach(x -> x
						.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, CourseCommand.GET_QUERY)));
			}
		});
		chckbxCats.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
				Message.infoBox("", "", catList.get(catIconNum));
				chckbxCats.setSelected(false);
				catIconNum = (catIconNum + 1) % 3;
			}
		});
		chckbxKdamim.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
				String txt = searchField.getText();
				query = (txt.equals(DEFAULT_COURSE_NUM_TEXT)) ? "" : txt;
				listeners.forEach(x -> x
						.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, CourseCommand.GET_QUERY)));
			}
		});
		chckbxTaken.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
				String txt = searchField.getText();
				query = (txt.equals(DEFAULT_COURSE_NUM_TEXT)) ? "" : txt;
				listeners.forEach(x -> x
						.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, CourseCommand.GET_QUERY)));
			}
		});
	}

	// ******************* communication with controller ******************//

	static String highlighted = "";
	static String picked = "";
	static String droped = "";
	static String query = "";
	static String chosenFaculty = ALL_FACULTIES_STRING;

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
			btnAddCourse.setEnabled(!courseModel.isEmpty());
			break;
		case CourseProperty.CHOSEN_LIST:
			ChosenCourseModel = new DefaultListModel<>();
			for (CourseId val : (List<CourseId>) evt.getNewValue())
				ChosenCourseModel.addElement(val.number + " " + val.name);
			lstChosenCourses.setModel(ChosenCourseModel);
			btnRemoveCourse.setEnabled(!ChosenCourseModel.isEmpty());
			break;
		case CourseProperty.FACULTY_LIST:
			facultiesModel = new DefaultComboBoxModel<>();
			facultiesModel.addElement(ALL_FACULTIES_STRING);
			for (String val : (List<String>) evt.getNewValue())
				facultiesModel.addElement(val);
			cmbFaculties.setModel(facultiesModel);
			break;
		default:
			break;
		}
	}

	@Override
	public String getQuery() {
		return query;
	}

	@Override
	public String getHighlightedCourse() {
		return highlighted;
	}

	@Override
	public String getLastPickedCourse() {
		return picked;
	}

	@Override
	public String getLastDropedCourse() {
		return droped;
	}

	@Override
	public boolean getIsKdamimChecked() {
		return chckbxKdamim.isSelected();
	}

	@Override
	public String getFaculty() {
		return chosenFaculty;
	}

	@Override
	public boolean getIsTakenChecked() {
		return chckbxTaken.isSelected();
	}

}
