package view;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

import command.TimeTableCommand;
import model.course.Lesson;
import model.course.LessonGroup;
import property.TimeTableProperty;


@SuppressWarnings("serial")
public class TimetableVIew extends JPanel implements ITimeTableView {
	MultiSpanCellTable table;
	private JButton cleanTble = new JButton("נקה מערכת");
	private JButton nextBtn = new JButton("הבא>");
	private JButton prevBtn = new JButton("<הקודם");
	private JButton schedBtn = new JButton("בנה מערכת");
	private JButton saveBtn = new JButton("שמור מערכת");
	private JCheckBox isDaysOff;
	private JCheckBox isMinWindows;
	private DefaultTableCellRenderer bottomCenterRenderer = new DefaultTableCellRenderer();
	private DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
	
	JComboBox<String> startTimeComBoxHours;
	JComboBox<String> startTimeComBoxMins;
	JComboBox<String> endTimeComBoxHours;
	JComboBox<String> endTimeComBoxMins;
	
	JCheckBox startTimeChkBox;
	JCheckBox endTimeChkBox;
	
	JPanel startTime;
	JPanel endTime;
	final String[] hours = {"07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22"};
	final String[] minutes = {"00", "30"};
	static List<ActionListener> listeners = new ArrayList<>();
	static boolean scheduleWasRequested;
	/**
	 * Create the panel.
	 */
	public TimetableVIew() {
		
		bottomCenterRenderer.setVerticalAlignment(SwingConstants.BOTTOM);
		bottomCenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		
		
		scheduleWasRequested = false;
		setLayout(new GridBagLayout());
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
	    table = new MultiSpanCellTable(null);
	    resetTable();
		
		JScrollPane scroll = new JScrollPane(table);
		scroll.setMinimumSize(new Dimension(500, 350));
		

		//arrange all the components in the pannel using GridBagLayout
		GridBagConstraints c = new GridBagConstraints();
		c.gridy=c.gridx = 0;
		c.weightx = c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		
		add(scroll,c);
		
		JPanel otherComponentsPanel=new JPanel();
		otherComponentsPanel.setLayout(new GridLayout(7,1,3,3));
		JPanel nextprevPannel =new JPanel();
		nextprevPannel.setLayout(new GridLayout(1,7,3,3));

		nextprevPannel.add(new JPanel());
		nextprevPannel.add(nextBtn);
		nextprevPannel.add(new JPanel());
		nextprevPannel.add(cleanTble);
		nextprevPannel.add(new JPanel());
		nextprevPannel.add(prevBtn);
		nextprevPannel.add(new JPanel());
		
		otherComponentsPanel.add(nextprevPannel);
		
		JLabel textField = new JLabel("בחר/י את ההעדפות לבניית המערכת:");
		textField.setFont(new Font("Dialog", Font.BOLD, 24));
		textField.setBackground(UIManager.getColor("Button.background"));
		textField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		otherComponentsPanel.add(textField);
		
		isDaysOff = new JCheckBox("ימי חופש");
		isDaysOff.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		otherComponentsPanel.add(isDaysOff);
		
		isMinWindows= new JCheckBox("מספר מינימלי של חלונות");
		isMinWindows.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		otherComponentsPanel.add(isMinWindows);
		
		JPanel startTimePanel=new JPanel();
		startTimePanel.setLayout(new GridLayout(1,8));
		for(int ¢=0 ; ¢ < 6; ++¢)
			startTimePanel.add(new JPanel());
		
		startTime = new JPanel(new GridLayout(1,2));
		startTimeComBoxHours = new JComboBox<>(hours);
		startTimeComBoxMins = new JComboBox<>(minutes);
		startTimeComBoxHours.setEnabled(false);
		startTimeComBoxMins.setEnabled(false);
		startTime.add(startTimeComBoxHours);
		startTime.add(startTimeComBoxMins);
		startTimePanel.add(startTime);
		
		startTimeChkBox = new JCheckBox("שעת התחלה:");
		startTimeChkBox.setFont(new Font("Dialog", Font.BOLD, 12));
		startTimeChkBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		startTimePanel.add(startTimeChkBox);
		
		otherComponentsPanel.add(startTimePanel);
		
		JPanel endTimePannel=new JPanel();
		endTimePannel.setLayout(new GridLayout(1,8));
		for(int ¢=0 ; ¢ < 6; ++¢)
			endTimePannel.add(new JPanel());
		
		endTime = new JPanel(new GridLayout(1,2));
		endTimeComBoxHours = new JComboBox<>(hours);
		endTimeComBoxMins = new JComboBox<>(minutes);
		endTimeComBoxHours.setEnabled(false);
		endTimeComBoxMins.setEnabled(false);
		endTime.add(endTimeComBoxHours);
		endTime.add(endTimeComBoxMins);
		endTimePannel.add(endTime);
		
		endTimeChkBox = new JCheckBox("שעת סיום:");
		endTimeChkBox.setFont(new Font("Dialog", Font.BOLD, 12));
		endTimeChkBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		endTimePannel.add(endTimeChkBox);
		
		otherComponentsPanel.add(endTimePannel);
		
		JPanel panelSchedButton =new JPanel();
		panelSchedButton.setLayout(new GridLayout(1,6,3,3));
		panelSchedButton.add(new JPanel());
		panelSchedButton.add(new JPanel());	
		panelSchedButton.add(saveBtn);
		panelSchedButton.add(schedBtn);
		panelSchedButton.add(new JPanel());
		panelSchedButton.add(new JPanel());
		
		otherComponentsPanel.add(panelSchedButton);
		
		c.gridx=0;
		c.gridy=3;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(otherComponentsPanel,c);
		
		setEvents();

	}
	//setting events and actions
	public void setEvents(){
		
		startTimeChkBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
				startTimeComBoxHours.setEnabled(startTimeChkBox.isSelected());
				startTimeComBoxMins.setEnabled(startTimeChkBox.isSelected());
				
			}
		});
		
		endTimeChkBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
				endTimeComBoxHours.setEnabled(endTimeChkBox.isSelected());
				endTimeComBoxMins.setEnabled(endTimeChkBox.isSelected());
				
			}
		});
		
		
		
		//next button has been pressed -> ask for the next schedule
		nextBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
				if (scheduleWasRequested)
					listeners.forEach(x -> x.actionPerformed(
						new ActionEvent(this, ActionEvent.ACTION_PERFORMED, TimeTableCommand.GET_NEXT_GENERATED_SCHED)));
			}
		});
		
		//prev button has been pressed -> ask for the previous schedule
		prevBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
				if (scheduleWasRequested)
					listeners.forEach(x -> x.actionPerformed(
						new ActionEvent(this, ActionEvent.ACTION_PERFORMED, TimeTableCommand.GET_PREV_GENERATED_SCHED)));
			}
		});
		
		//schedBtn button has been pressed -> ask for a schedule (list of lessonGroup)
		schedBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
				scheduleWasRequested = true;
				listeners.forEach(x -> x.actionPerformed(
					new ActionEvent(this, ActionEvent.ACTION_PERFORMED, TimeTableCommand.RECALC_SCHED)));
			}
		});
		
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
				if (scheduleWasRequested)
					listeners.forEach(x -> x.actionPerformed(
						new ActionEvent(this, ActionEvent.ACTION_PERFORMED, TimeTableCommand.SAVE_SCHED)));
			}
		});
		
		cleanTble.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
				scheduleWasRequested = false;
				resetTable();
				
			}
		});
	}
	//-------------------------------------------------------------------------------------------------

	//connection with model and controller
	
	@Override
	public void addActionListener(ActionListener ¢) {
		listeners.add(¢);
	}

	@Override
	public void removeActionListener(ActionListener ¢) {
		listeners.remove(¢);
	}

	@Override
	public boolean isDaysoffCount() {
		return isDaysOff.isSelected();
	}
	
	@Override
	public boolean isBlankSpaceCount() {
		return isMinWindows.isSelected();
	}
	
	@Override
	public LocalTime getMinStartTime() {
		return !startTimeChkBox.isSelected() ? null
				: LocalTime.of(Integer.parseInt(hours[startTimeComBoxHours.getSelectedIndex()]),
						Integer.parseInt(minutes[startTimeComBoxMins.getSelectedIndex()]));
	}
	
	@Override
	public LocalTime getMaxEndTime() {
		return !endTimeChkBox.isSelected() ? null
				: LocalTime.of(Integer.parseInt(hours[endTimeComBoxHours.getSelectedIndex()]),
						Integer.parseInt(minutes[endTimeComBoxMins.getSelectedIndex()]));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {

		case TimeTableProperty.SCHEDULE:
			displaySchedule((List<LessonGroup>) evt.getNewValue());
			break;
		default:
			break;
		
		}
	}
	//-----------------------------------------------------------------------------
	
	
	//this function receives a list of LessonGroup(which is a schedule) and displays the schedule in the GUI 
	public void displaySchedule(List<LessonGroup> schedule) {
		resetTable();
		final ColoredCell cellColor =(ColoredCell)((AttributiveCellTableModel)table.getModel()).getCellAttribute();
		final CellSpan cellAtt =(CellSpan)((AttributiveCellTableModel)table.getModel()).getCellAttribute();
		
		int[] columns = new int[1];
		int[] rows;
		for(LessonGroup lg : schedule)
			for (Lesson l : lg.getLessons()) {
				columns[0] = table.getColumnCount() - l.getStartTime().getDay().getValue() % 7 - 2;
				int startRow = 2 * (l.getStartTime().getTime().getHour() - 7)
						- (l.getStartTime().getTime().getMinute() > 0 ? -1 : 0 );
				int endRow = 2 * (l.getEndTime().getTime().getHour() - 7)
						- (l.getEndTime().getTime().getMinute() > 0 ? 0 : 1);
				rows = new int[endRow - startRow + 1];
				for (int ¢ = 0; ¢ < rows.length; ++¢)
					rows[¢] = ¢ + startRow;
				cellAtt.combine(rows, columns);
				cellColor.setBackground(Color.lightGray, rows[0],columns[0]);
				
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				table.getColumnModel().getColumn(columns[0]).setCellRenderer(centerRenderer);
				table.setValueAt(l.getCourse() + " ב" +  l.getPlace() + " " + l.getRoomNumber(),rows[0],columns[0]);
				
			}

	}
	
	void resetTable(){
		table.setModel(new AttributiveCellTableModel(
				new Object[][] {
					
					{null, null, null, null, null, null, "7:30"},
					{null, null, null, null, null, null, "8:00"},
					{null, null, null, null, null, null, "8:30"},
					{null, null, null, null, null, null, "9:00"},
					{null, null, null, null, null, null, "9:30"},
					{null, null, null, null, null, null, "10:00"},
					{null, null, null, null, null, null, "10:30"},
					{null, null, null, null, null, null, "11:00"},
					{null, null, null, null, null, null, "11:30"},
					{null, null, null, null, null, null, "12:00"},
					{null, null, null, null, null, null, "12:30"},
					{null, null, null, null, null, null, "13:00"},
					{null, null, null, null, null, null, "13:30"},
					{null, null, null, null, null, null, "14:00"},
					{null, null, null, null, null, null, "14:30"},
					{null, null, null, null, null, null, "15:00"},
					{null, null, null, null, null, null, "15:30"},
					{null, null, null, null, null, null, "16:00"},
					{null, null, null, null, null, null, "16:30"},
					{null, null, null, null, null, null, "17:00"},
					{null, null, null, null, null, null, "17:30"},
					{null, null, null, null, null, null, "18:00"},
					{null, null, null, null, null, null, "18:30"},
					{null, null, null, null, null, null, "19:00"},
					{null, null, null, null, null, null, "19:30"},
				},
				new String[] {
						"\u05e9\u05d9\u05e9\u05d9", "\u05D7\u05DE\u05D9\u05E9\u05D9", "\u05E8\u05D1\u05D9\u05E2\u05D9", "\u05E9\u05DC\u05D9\u05E9\u05D9", "\u05E9\u05E0\u05D9 ", "\u05E8\u05D0\u05E9\u05D5\u05DF", ""
				}

			){
				//we want to cells to be non-editable
				    @Override
				    public boolean isCellEditable(@SuppressWarnings("unused") int row, @SuppressWarnings("unused") int column) {
				       return false;
				    }	
		});
		//Aligning text in the table
		for(int ¢ = 0; ¢<table.getColumnCount()-1; ++¢)
			table.getColumnModel().getColumn(¢).setPreferredWidth(90);
		table.getColumnModel().getColumn(6).setPreferredWidth(10);
		
		for(int ¢ = 0; ¢<table.getColumnCount()-1; ++¢)
			table.getColumnModel().getColumn(¢).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(6).setCellRenderer(bottomCenterRenderer);
		
		for(int ¢ = 0; ¢<table.getColumnCount(); ++¢)
			table.getColumnModel().getColumn(¢).setResizable(false);
		
		table.setRowHeight(40);
	}

}

