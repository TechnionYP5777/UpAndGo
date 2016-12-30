package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import model.CourseModel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class MainWinView extends JFrame {

	private static final String FINISH_MSG = "Your choise has been saved!\n   Up&Go will close now!";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CourseModel m;
	private JPanel contentPane;
	static JList<String> lstCourseList;
	static JTextArea txtCourseDescription;
	static JButton btnAddCourse;
	static JButton btnFinish;
	static JList<String> lstChosenCourses;
	static DefaultListModel<String> courseModel;
	static DefaultListModel<String> searchCourseModel;
	static DefaultListModel<String> ChosenCourseModel;
	private JLabel lblTitle;
	private JScrollPane scpChosenCourses;
	private JScrollPane scpCourseDescription;
	private JScrollPane scpCourseList;
	static JButton btnRemoveCourse;
	JTextField courseNum;

	/**
	 * Create the frame.
	 */
	public MainWinView(CourseModel m) {
		setSize(new Dimension(500, 300));

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Throwable ¢) {
			¢.printStackTrace();
		}
		this.m = m;
		initComponents();
		createEvents();

	}

	void close() {
		this.dispose();
	}

	///////////////////////////////////////////////////////////////////////////
	/// This method is creating and initializing components
	///////////////////////////////////////////////////////////////////////////
	private void initComponents() {
		setWindowPreferences();

		lblTitle = new JLabel("Choose Your Courses!!!");
		setTitleLbl(lblTitle);

		setCoursesListsModels();
		lstCourseList = new JList<>(courseModel);

		scpCourseList = new JScrollPane();
		scpCourseList.setViewportBorder(null);
		setCourseListArea(scpCourseList);

		scpCourseDescription = new JScrollPane();
		scpCourseDescription.setViewportBorder(null);
		scpCourseDescription.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		setCourseDescriptionArea(scpCourseDescription);
		scpChosenCourses = new JScrollPane();
		setChosenCoursesArea(scpChosenCourses);

		btnAddCourse = new JButton("Add Course");
		btnRemoveCourse = new JButton("Remove Course");
		btnRemoveCourse.setPreferredSize(new Dimension(76, 25));
		btnRemoveCourse.setMinimumSize(new Dimension(76, 25));
		btnFinish = new JButton("Finish");
		setButtonsPreferences();
		setSearchTextBox();
		setGroupLayout(lblTitle, scpCourseDescription, scpCourseList, scpChosenCourses);
	}

	private void setCoursesListsModels() {
		courseModel = new DefaultListModel<>();
		ChosenCourseModel = new DefaultListModel<>();

		for (String val : m.getCoursesNames())
			courseModel.addElement(val);
		for (String val : m.getChosenCourseNames()) {
			courseModel.removeElement(val);
			ChosenCourseModel.addElement(val);
		}
	}

	private static void setTitleLbl(JLabel lblTitle) {
		lblTitle.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(new Color(0, 102, 255));
		lblTitle.setFont(new Font("STIX", Font.BOLD, 16));
		lblTitle.setFocusable(false);
		lblTitle.setFocusTraversalKeysEnabled(false);
	}

	private static void setButtonsPreferences() {
		btnAddCourse.setMaximumSize(new Dimension(150, 25));
		btnAddCourse.setPreferredSize(new Dimension(130, 25));
		btnAddCourse.setMinimumSize(new Dimension(130, 25));
		btnAddCourse.setVisible(false);
		btnFinish.setHorizontalTextPosition(SwingConstants.CENTER);
		btnRemoveCourse.setHorizontalTextPosition(SwingConstants.CENTER);
		if (!ChosenCourseModel.isEmpty()) {
			btnRemoveCourse.setVisible(true);
			btnFinish.setVisible(true);
		} else {
			btnRemoveCourse.setVisible(false);
			btnFinish.setVisible(false);
		}

	}

	private static void setCourseListArea(JScrollPane scpCourseList) {
		lstCourseList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		scpCourseList.setPreferredSize(new Dimension(70, 22));
		scpCourseList.setMinimumSize(new Dimension(70, 22));
		scpCourseList.setBorder(
				new TitledBorder(null, "Course List", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLUE));
		lstCourseList.setBackground(UIManager.getColor("inactiveCaption"));
		scpCourseList.setViewportView(lstCourseList);
	}

	private static void setCourseDescriptionArea(JScrollPane scpCourseDescription) {
		txtCourseDescription = new JTextArea();
		txtCourseDescription.setDisabledTextColor(Color.GRAY);
		txtCourseDescription.setSelectedTextColor(Color.BLUE);
		txtCourseDescription.setSelectionColor(Color.LIGHT_GRAY);
		txtCourseDescription.setForeground(Color.BLACK);
		txtCourseDescription.setWrapStyleWord(true);
		txtCourseDescription.setLineWrap(true);
		scpCourseDescription.setForeground(Color.BLACK);
		scpCourseDescription.setMinimumSize(new Dimension(70, 22));
		scpCourseDescription.setPreferredSize(new Dimension(70, 22));
		scpCourseDescription.setBackground(UIManager.getColor("menu"));
		scpCourseDescription.setBorder(null);
		txtCourseDescription.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Description",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(128, 128, 128)));
		txtCourseDescription.setEnabled(false);
		txtCourseDescription.setEditable(false);
		txtCourseDescription.setCaretColor(Color.BLACK);
		txtCourseDescription.setBackground(UIManager.getColor("inactiveCaption"));
		scpCourseDescription.setViewportView(txtCourseDescription);
		txtCourseDescription.setVisible(false);
	}

	private static void setChosenCoursesArea(JScrollPane scpChosenCourses) {
		lstChosenCourses = new JList<>(ChosenCourseModel);
		scpChosenCourses.setPreferredSize(new Dimension(70, 22));
		scpChosenCourses.setMinimumSize(new Dimension(70, 22));
		scpChosenCourses.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Chosen Courses",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 204, 51)));
		scpChosenCourses.setViewportBorder(null);
		lstChosenCourses.setBorder(null);
		lstChosenCourses.setBackground(UIManager.getColor("inactiveCaption"));
		scpChosenCourses.setViewportView(lstChosenCourses);
		if (ChosenCourseModel.isEmpty())
			lstChosenCourses.setVisible(false);
	}

	private void setWindowPreferences() {
		setPreferredSize(new Dimension(500, 160));
		setMinimumSize(new Dimension(500, 300));
		setTitle("Schedual Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 468, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setSize(new Dimension(500, 300));
		contentPane.setPreferredSize(new Dimension(500, 160));
		contentPane.setMinimumSize(new Dimension(500, 300));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}

	private void setSearchTextBox() {
		courseNum = new JTextField();
		courseNum.setText("Enter course number");
		courseNum.setBackground(SystemColor.controlHighlight);
		courseNum.setColumns(10);
	}

	private void setGroupLayout(JLabel lblTitle, JScrollPane scpCourseDescription, JScrollPane scpCourseList,
			JScrollPane scpChosenCourses) {

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(158)
								.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE).addGap(106))
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(scpChosenCourses, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
										.addComponent(btnFinish, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
										.addComponent(btnRemoveCourse, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(scpCourseDescription, GroupLayout.DEFAULT_SIZE, 152,
												Short.MAX_VALUE)
										.addComponent(btnAddCourse, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(scpCourseList, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
										.addComponent(courseNum, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))))
				.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane
										.createSequentialGroup().addGroup(gl_contentPane
												.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(scpChosenCourses, GroupLayout.DEFAULT_SIZE, 159,
																Short.MAX_VALUE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(btnRemoveCourse, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addComponent(scpCourseDescription, GroupLayout.DEFAULT_SIZE, 190,
														Short.MAX_VALUE))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(btnAddCourse, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(btnFinish)))
								.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
										.addComponent(courseNum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(scpCourseList, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)))
						.addContainerGap()));
		contentPane.setLayout(gl_contentPane);
	}

	///////////////////////////////////////////////////////////////////////////
	/// This method is creating events
	///////////////////////////////////////////////////////////////////////////
	private void createEvents() {

		lstCourseList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(@SuppressWarnings("unused") ListSelectionEvent __) {
				txtCourseDescription.setDisabledTextColor(Color.BLUE);
				setListColor(lstCourseList, m);
				txtCourseDescription.setVisible(true);
				btnAddCourse.setVisible(true);
				btnAddCourse.setEnabled(true);

			}
		});

		lstChosenCourses.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(@SuppressWarnings("unused") ListSelectionEvent __) {
				txtCourseDescription.setDisabledTextColor(new Color(0, 204, 51));
				setListColor(lstChosenCourses, m);
				txtCourseDescription.setVisible(true);
				btnAddCourse.setVisible(true);
				btnAddCourse.setEnabled(true);

			}
		});

		btnAddCourse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
				if (lstCourseList.getSelectedValue() == null)
					return;
				ChosenCourseModel.addElement(lstCourseList.getSelectedValue());
				courseModel.remove(lstCourseList.getSelectedIndex());
				lstChosenCourses.setVisible(true);
				btnFinish.setVisible(true);
				btnRemoveCourse.setVisible(true);
				btnRemoveCourse.setEnabled(true);
				if (!courseModel.isEmpty())
					return;
				txtCourseDescription.setText(" No Courses Left ");
				btnAddCourse.setEnabled(false);
			}
		});
		btnRemoveCourse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
				if (lstChosenCourses.getSelectedValue() == null)
					return;
				if (courseModel.isEmpty()) {
					txtCourseDescription.setText(" Choose course to add ");
					btnAddCourse.setEnabled(false);
				}
				courseModel.addElement(lstChosenCourses.getSelectedValue());
				ChosenCourseModel.removeElement(lstChosenCourses.getSelectedValue());

				if (ChosenCourseModel.isEmpty())
					btnRemoveCourse.setEnabled(false);
			}
		});
		btnFinish.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
				List<String> names = new ArrayList<String>() {
					@SuppressWarnings("hiding")
					static final long serialVersionUID = 1L;
				};
				for (int ¢ = 0; ¢ < lstChosenCourses.getModel().getSize(); ++¢)
					names.add(lstChosenCourses.getModel().getElementAt(¢));
				m.choseCourses(names);
				Message.infoBox(FINISH_MSG, "Finish", new ImageIcon("resources/cat-laptop-icon.png"));
				close();

			}
		});
		courseNum.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(@SuppressWarnings("unused") MouseEvent __) {
				courseNum.setText("");
			}
		});
		courseNum.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
				lstCourseList.setSelectedValue(courseNum.getText(), true);
			}
		});
		courseNum.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(@SuppressWarnings("unused") FocusEvent __) {
				if ("".equals(courseNum.getText()))
					courseNum.setText("Enter course number");
			}
		});

	}

	static void setListColor(JList<String> lst, CourseModel m) {

		if (lst.getSelectedIndex() != -1)
			txtCourseDescription.setText(
					"Course " + lst.getSelectedValue() + "\n" + m.getCourseByName(lst.getSelectedValue()).getName());
		else {
			txtCourseDescription.setText(" Choose course to add ");
			txtCourseDescription.setDisabledTextColor(Color.GRAY);
		}

	}

	public static class Message {

		public static void infoBox(String infoMessage, String titleBar, ImageIcon i) {

			JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.PLAIN_MESSAGE, i);

		}
	}
}
