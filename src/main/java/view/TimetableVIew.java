package view;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

import command.TimeTableCommand;
import model.course.Lesson;
import model.course.LessonGroup;
import property.TimeTableProperty;

@SuppressWarnings("serial")
public class TimetableVIew extends JPanel implements ITimeTableView {
	private MultiSpanCellTable table;
	private JButton nextBtn = new JButton("הבא>");
	private JButton prevBtn = new JButton("<הקודם");
	private JButton schedBtn = new JButton("בנה מערכת");
	final AttributiveCellTableModel defaultTableModel = new AttributiveCellTableModel(
			new Object[][] {
				
				{null, null, null, null, null, "7:30"},
				{null, null, null, null, null, "8:00"},
				{null, null, null, null, null, "8:30"},
				{null, null, null, null, null, "9:00"},
				{null, null, null, null, null, "9:30"},
				{null, null, null, null, null, "10:00"},
				{null, null, null, null, null, "10:30"},
				{null, null, null, null, null, "11:00"},
				{null, null, null, null, null, "11:30"},
				{null, null, null, null, null, "12:00"},
				{null, null, null, null, null, "12:30"},
				{null, null, null, null, null, "13:00"},
				{null, null, null, null, null, "13:30"},
				{null, null, null, null, null, "14:00"},
				{null, null, null, null, null, "14:30"},
				{null, null, null, null, null, "15:00"},
				{null, null, null, null, null, "15:30"},
				{null, null, null, null, null, "16:00"},
				{null, null, null, null, null, "16:30"},
				{null, null, null, null, null, "17:00"},
				{null, null, null, null, null, "17:30"},
				{null, null, null, null, null, "18:00"},
				{null, null, null, null, null, "18:30"},
				{null, null, null, null, null, "19:00"},
				{null, null, null, null, null, "19:30"},
			},
			new String[] {
					"\u05D7\u05DE\u05D9\u05E9\u05D9", "\u05E8\u05D1\u05D9\u05E2\u05D9", "\u05E9\u05DC\u05D9\u05E9\u05D9", "\u05E9\u05E0\u05D9 ", "\u05E8\u05D0\u05E9\u05D5\u05DF", ""
			}

		){
			//we want to cells to be non-editable
			    @Override
			    public boolean isCellEditable(@SuppressWarnings("unused") int row, @SuppressWarnings("unused") int column) {
			       return false;
			    }	
		};
	static List<ActionListener> listeners;
	static boolean scheduleWasRequested;
	/**
	 * Create the panel.
	 */
	public TimetableVIew() {
		
		scheduleWasRequested = false;
		setLayout(new GridBagLayout());
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	    table = new MultiSpanCellTable(defaultTableModel);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setMinimumSize(new Dimension(500, 350));
		//Aligning text in the table
		DefaultTableCellRenderer bottomRenderer = new DefaultTableCellRenderer();
		bottomRenderer.setVerticalAlignment(SwingConstants.BOTTOM);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		for(int ¢ = 0; ¢<table.getColumnCount()-1; ++¢)
			table.getColumnModel().getColumn(¢).setPreferredWidth(90);
		table.getColumnModel().getColumn(5).setPreferredWidth(10);
		for(int ¢ = 0; ¢<table.getColumnCount()-1; ++¢)
			table.getColumnModel().getColumn(¢).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(5).setCellRenderer(bottomRenderer);
		for(int ¢ = 0; ¢<table.getColumnCount(); ++¢)
			table.getColumnModel().getColumn(¢).setResizable(false);
		table.setRowHeight(30);
		//arrange all the components in the pannel using GridBagLayout
		GridBagConstraints c = new GridBagConstraints();
		c.gridy=c.gridx = 0;
		c.weightx = c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		
		add(scroll,c);
		
		JPanel otherComponentsPanel=new JPanel();
		otherComponentsPanel.setLayout(new GridLayout(6,1,3,3));
		JPanel nextprevPannel =new JPanel();
		nextprevPannel.setLayout(new GridLayout(1,7,3,3));
		JPanel textbox=new JPanel();

		nextprevPannel.add(new JPanel());
		nextprevPannel.add(nextBtn);
		nextprevPannel.add(new JPanel());
		nextprevPannel.add(new JPanel());
		nextprevPannel.add(new JPanel());
		nextprevPannel.add(prevBtn);
		nextprevPannel.add(new JPanel());
		otherComponentsPanel.add(nextprevPannel);
		JTextField textField = new JTextField("בחר/י את ההעדפות לבניית המערכת:");
		textField.setFont(new Font("Dialog", Font.BOLD, 24));
		textField.setEditable(false);
		textField.setBackground(UIManager.getColor("Button.background"));
		textField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		otherComponentsPanel.add(textField);
		JCheckBox b1 = new JCheckBox("ימי חופש");
		b1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		otherComponentsPanel.add(b1);
		JCheckBox b2= new JCheckBox("מספר מינימלי של חלונות");
		b2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		otherComponentsPanel.add(b2);
		textbox.setLayout(new GridLayout(1,6));
		for(int ¢=0 ; ¢ < 4; ++¢)
			textbox.add(new JPanel());
		JTextField tf = new JTextField();
		tf.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		textbox.add(tf);
		JTextField tf1 = new JTextField("שעת התחלה:");
		tf1.setEditable(false);
		tf1.setFont(new Font("Dialog", Font.BOLD, 12));
		tf1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		textbox.add(tf1);
		otherComponentsPanel.add(textbox);
		JPanel panelSchedButton =new JPanel();
		panelSchedButton.setLayout(new GridLayout(1,7,3,3));
		panelSchedButton.add(new JPanel());
		panelSchedButton.add(new JPanel());
		panelSchedButton.add(new JPanel());
		schedBtn.setBackground(UIManager.getColor("Button.darkShadow"));
		panelSchedButton.add(schedBtn);
		panelSchedButton.add(new JPanel());
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
		prevBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent __) {
				scheduleWasRequested = true;
				listeners.forEach(x -> x.actionPerformed(
					new ActionEvent(this, ActionEvent.ACTION_PERFORMED, TimeTableCommand.RECALC_SCHED)));
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
		table.setModel(defaultTableModel);
		final CellSpan cellAtt =(CellSpan)defaultTableModel.getCellAttribute();
		int[] columns = new int[1];
		int[] rows;
		for(LessonGroup lg : schedule)
			for (Lesson l : lg.getLessons()) {
				columns[0] = table.getColumnCount() - l.getStartTime().getDay().getValue() % 7 - 2;
				int startRow = 2 * (l.getStartTime().getTime().getHour() - 7)
						- (l.getStartTime().getTime().getMinute() > 0 ? 0 : 1);
				int endRow = 2 * (l.getEndTime().getTime().getHour() - 7)
						- (l.getEndTime().getTime().getMinute() > 0 ? 0 : 1);
				rows = new int[endRow - startRow + 1];
				for (int ¢ = 0; ¢ < rows.length; ++¢)
					rows[¢] = ¢ + startRow;
				cellAtt.combine(rows, columns);
			}

	}
}