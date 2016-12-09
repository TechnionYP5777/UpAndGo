package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;

import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.JList;
import java.awt.Dimension;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWinView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static JList<String> lstCourseList;
	static JTextArea txtCourseDescription;
	static JButton btnAddCourse;
	static JButton btnSaveChoice;
	static JList<String> lstChosenCourses;
	static DefaultListModel<String> courseModel;
	static DefaultListModel<String> ChosenCourseModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainWinView frame = new MainWinView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWinView() {

		initComponents();
		createEvents();

	}

	///////////////////////////////////////////////////////////////////////////
	/// This method is creating and initializing components
	///////////////////////////////////////////////////////////////////////////
	private void initComponents() {
		setWindowPreferences();

		JLabel lblTitle = new JLabel("Choose Your Courses!!!");
		setTitleLbl(lblTitle);

		String[] data = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j" , "k" ,"l" ,"m" ,"n" ,"o" , "p" };

		courseModel = new DefaultListModel<>();
		for (String val : data)
			courseModel.addElement(val);
		lstCourseList = new JList<>(courseModel);

		JScrollPane scpCourseList = new JScrollPane();
		setCourseListArea(scpCourseList);

		JScrollPane scpCourseDescription = new JScrollPane();
		scpCourseDescription.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		setCourseDescriptionArea(scpCourseDescription);
		ChosenCourseModel = new DefaultListModel<>();
		JScrollPane scpChosenCourses = new JScrollPane();
		setChosenCoursesArea(scpChosenCourses);

		btnAddCourse = new JButton("Add Course");
		btnSaveChoice = new JButton("Save");
		setButtonsPreferences(btnAddCourse, btnSaveChoice);

		setGroupLayout(lblTitle, scpCourseDescription, scpCourseList, btnAddCourse, scpChosenCourses, btnSaveChoice);
	}

	private static void setTitleLbl(JLabel lblTitle) {
		lblTitle.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setForeground(new Color(0, 102, 255));
		lblTitle.setFont(new Font("STIX", Font.BOLD, 16));
		lblTitle.setFocusable(false);
		lblTitle.setFocusTraversalKeysEnabled(false);
	}

	private static void setButtonsPreferences(JButton btnAddCourse, JButton btnSaveChoice) {
		btnAddCourse.setMaximumSize(new Dimension(150, 25));
		btnAddCourse.setPreferredSize(new Dimension(130, 25));
		btnAddCourse.setMinimumSize(new Dimension(130, 25));
		btnAddCourse.setVisible(false);
		btnSaveChoice.setHorizontalTextPosition(SwingConstants.CENTER);
		btnSaveChoice.setVisible(false);
	}

	private static void setCourseListArea(JScrollPane scpCourseList) {

		lstCourseList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		scpCourseList.setPreferredSize(new Dimension(70, 22));
		scpCourseList.setMinimumSize(new Dimension(70, 22));
		scpCourseList.setBorder(
				new TitledBorder(null, "Course List", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLUE));
		lstCourseList.setBackground(UIManager.getColor("inactiveCaption"));
		scpCourseList.setViewportView(lstCourseList);
		return;
	}

	private static void setCourseDescriptionArea(JScrollPane scpCourseDescription) {
		txtCourseDescription = new JTextArea();
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
		return;
	}

	private static void setChosenCoursesArea(JScrollPane scpChosenCourses) {
		lstChosenCourses = new JList<>(ChosenCourseModel);
		scpChosenCourses.setPreferredSize(new Dimension(70, 22));
		scpChosenCourses.setMinimumSize(new Dimension(70, 22));
		scpChosenCourses.setBorder(null);
		scpChosenCourses.setViewportBorder(null);
		lstChosenCourses.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Chosen Courses",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(51, 153, 51)));
		lstChosenCourses.setBackground(UIManager.getColor("inactiveCaption"));
		scpChosenCourses.setViewportView(lstChosenCourses);
		lstChosenCourses.setVisible(false);
		return;
	}

	private void setWindowPreferences() {
		setPreferredSize(new Dimension(450, 160));
		setMinimumSize(new Dimension(450, 160));
		setTitle("Schedual Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}

	private void setGroupLayout(JLabel lblTitle, JScrollPane scpCourseDescription, JScrollPane scpCourseList,
			JButton btnAddCourse, JScrollPane scpChosenCourses, JButton btnSaveChoice) {
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING,
								gl_contentPane.createSequentialGroup().addGap(158)
										.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGap(106))
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnSaveChoice, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
										.addComponent(scpChosenCourses, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(scpCourseDescription, GroupLayout.DEFAULT_SIZE, 164,
												Short.MAX_VALUE)
										.addComponent(btnAddCourse, GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(scpCourseList, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)))
						.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scpCourseList, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(scpChosenCourses, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												190, Short.MAX_VALUE)
										.addComponent(scpCourseDescription, GroupLayout.DEFAULT_SIZE, 190,
												Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnAddCourse, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnSaveChoice))))
				.addContainerGap()));
		contentPane.setLayout(gl_contentPane);
		return;
	}

	///////////////////////////////////////////////////////////////////////////
	/// This method is creating events
	///////////////////////////////////////////////////////////////////////////
	private static void createEvents() {

		lstCourseList.addListSelectionListener(new ListSelectionListener() {
			@SuppressWarnings("unused")
			@Override
			public void valueChanged(ListSelectionEvent __) {
				txtCourseDescription.setText(lstCourseList.getSelectedIndex() == -1 ? " Choose course to add "
						: "Course " + lstCourseList.getSelectedValue() + " Description Here ...");
				txtCourseDescription.setVisible(true);
				btnAddCourse.setVisible(true);
			}
		});
		btnAddCourse.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent __) {
				if (lstCourseList.getSelectedValue() == null)
					return;
				ChosenCourseModel.addElement(lstCourseList.getSelectedValue());
				courseModel.remove(lstCourseList.getSelectedIndex());
				lstChosenCourses.setVisible(true);
				btnSaveChoice.setVisible(true);
				if (!courseModel.isEmpty())
					return;
				txtCourseDescription.setText(" No Courses Left ");
				btnAddCourse.setVisible(false);
			}
		});
	}
}
