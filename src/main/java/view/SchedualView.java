/*
 * Authors: Alex v. and Lidia P.
 * 
 * This class will be the view of the application
 * the user interacts with the application using this view 
 * 
 * 
 * */

package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Point;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SchedualView {

	JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					SchedualView window = new SchedualView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SchedualView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		String[] data = {"a","b","c","d","e","f","g","h","i"};

		DefaultListModel<String> model = new DefaultListModel<>();
				 for(String val : data)
				         model.addElement(val);
		frame = new JFrame();
		setFrameOptions();
		
		JScrollPane listScroller = new JScrollPane();
		setListScroller(listScroller);
		frame.getContentPane().add(listScroller, BorderLayout.EAST);
		
		JLabel lblCoursedetails = new JLabel("courseDetails");
		setLableCourseDetails(lblCoursedetails);
		frame.getContentPane().add(lblCoursedetails, BorderLayout.CENTER);
		
		JButton btnAddCourse = new JButton("Add Course");
		setBtnAddCourse(btnAddCourse);
		frame.getContentPane().add(btnAddCourse, BorderLayout.WEST);
	
		JLabel lblCourseAdded = new JLabel("Course added");
		setLableCourseAdded(lblCourseAdded);
		frame.getContentPane().add(lblCourseAdded, BorderLayout.SOUTH);
		
		JLabel listHeader = new JLabel("Choose a course");
		setListHeader(listScroller, listHeader);
		
		@SuppressWarnings("unused")
		JList<String> list = new JList<String>(model);
		
		listScroller.setViewportView(list);
		setJlist(list);
		
	
		
		btnAddCourse.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent ¢) {
			lblCourseAdded.setText(list.getSelectedValue()+" "+"chosen");	
			lblCourseAdded.setVisible(true);
			btnAddCourse.setVisible(false);
			¢.getSource().toString();
			}
		});
		
		list.addListSelectionListener(new ListSelectionListener() {
			@SuppressWarnings("unused")
			@Override
			public void valueChanged(ListSelectionEvent __) {
				lblCoursedetails.setVisible(true);
				lblCoursedetails.setText((list.getSelectedValue() + " Course details here"));
				btnAddCourse.setVisible(true);
				lblCourseAdded.setVisible(false);
			}
		});
	}

	private static void setListHeader(JScrollPane listScroller, JLabel listHeader) {
		listHeader.setForeground(Color.BLUE);
		listHeader.setFocusable(false);
		listHeader.setBackground(Color.BLUE);
		listScroller.setColumnHeaderView(listHeader);
	}

	private void setFrameOptions() {
		frame.getContentPane().setSize(new Dimension(250, 250));
		frame.getContentPane().setPreferredSize(new Dimension(200, 200));
		frame.getContentPane().setMaximumSize(new Dimension(400, 400));
		frame.getContentPane().setMinimumSize(new Dimension(100, 100));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
	}

	private static void setLableCourseAdded(JLabel lblCourseAdded) {
		lblCourseAdded.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourseAdded.setHorizontalTextPosition(SwingConstants.CENTER);
		lblCourseAdded.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblCourseAdded.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblCourseAdded.setSize(new Dimension(100, 30));
		lblCourseAdded.setPreferredSize(new Dimension(100, 30));
		lblCourseAdded.setMaximumSize(new Dimension(400, 50));
		lblCourseAdded.setMinimumSize(new Dimension(80, 30));
		lblCourseAdded.setVisible(false);
	}

	private static void setJlist(JList<String> ¢) {
		¢.setBounds(new Rectangle(0, 0, 0, 50));
		¢.setSize(new Dimension(120, 50));
		¢.setPreferredSize(new Dimension(120, 50));
		¢.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		¢.setAlignmentX(Component.LEFT_ALIGNMENT);
		¢.setBorder(UIManager.getBorder("List.focusCellHighlightBorder"));
		¢.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		¢.setLayoutOrientation(JList.VERTICAL);
		¢.setFixedCellWidth(150);
		¢.setVisibleRowCount(8);
		¢.setAutoscrolls(true);
		¢.setForeground(Color.DARK_GRAY);
	}

	private static void setListScroller(JScrollPane listScroller) {
		listScroller.setSize(new Dimension(150, 150));
		listScroller.setPreferredSize(new Dimension(150, 150));
		listScroller.setMinimumSize(new Dimension(100, 100));
		listScroller.setMaximumSize(new Dimension(400, 400));
		listScroller.setBackground(Color.LIGHT_GRAY);
		listScroller.setAlignmentX(Component.LEFT_ALIGNMENT);
		listScroller.setAlignmentY(Component.BOTTOM_ALIGNMENT);
	}

	private static void setBtnAddCourse(JButton btnAddCourse) {
		btnAddCourse.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAddCourse.setPreferredSize(new Dimension(150, 100));
		btnAddCourse.setMinimumSize(new Dimension(25, 25));
		btnAddCourse.setMaximumSize(new Dimension(150, 150));
		btnAddCourse.setSize(new Dimension(150, 150));
		btnAddCourse.setVisible(false);
	}

	private static void setLableCourseDetails(JLabel lblCoursedetails) {
		lblCoursedetails.setSize(new Dimension(50, 50));
		lblCoursedetails.setLocation(new Point(50, 50));
		lblCoursedetails.setHorizontalTextPosition(SwingConstants.CENTER);
		lblCoursedetails.setHorizontalAlignment(SwingConstants.CENTER);
		lblCoursedetails.setForeground(Color.RED);
		lblCoursedetails.setFont(new Font("David CLM", Font.BOLD, 14));
		lblCoursedetails.setFocusable(false);
		lblCoursedetails.setBackground(Color.ORANGE);
		lblCoursedetails.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblCoursedetails.setPreferredSize(new Dimension(50, 50));
		lblCoursedetails.setMinimumSize(new Dimension(50, 50));
		lblCoursedetails.setVisible(false);
	}

}
