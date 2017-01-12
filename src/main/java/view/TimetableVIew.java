package view;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import command.CourseCommand;
import command.TimeTableCommand;

import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.ComponentOrientation;

import javax.swing.UIManager;
import java.awt.Font;

@SuppressWarnings("serial")
public class TimetableVIew extends JPanel {
	private JTable table;
	private JButton nextBtn = new JButton("הבא>");
	private JButton prevBtn = new JButton("<הקודם");
	private JButton schedBtn = new JButton("בנה מערכת");
	
	static List<ActionListener> listeners;
	static boolean scheduleWasRequested = false;
	/**
	 * Create the panel.
	 */
	public TimetableVIew() {
		setLayout(new GridBagLayout());
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"\u05D7\u05DE\u05D9\u05E9\u05D9", "\u05E8\u05D1\u05D9\u05E2\u05D9", "\u05E9\u05DC\u05D9\u05E9\u05D9", "\u05E9\u05E0\u05D9 ", "\u05E8\u05D0\u05E9\u05D5\u05DF", null},
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
				"New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		table.getColumnModel().getColumn(5).setPreferredWidth(15);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridy=c.gridx = 0;
		c.gridheight=3;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(table,c);
		
		JPanel otherComponentsPanel=new JPanel();
		otherComponentsPanel.setLayout(new GridLayout(9,1,3,3));
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
		otherComponentsPanel.add(new JPanel());
		JTextField textField = new JTextField("בחר/י את ההעדפות לבניית המערכת:");
		textField.setFont(new Font("Dialog", Font.BOLD, 24));
		textField.setEditable(false);
		textField.setBackground(UIManager.getColor("Button.background"));
		textField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		otherComponentsPanel.add(textField);
		JRadioButton b1 = new JRadioButton("ימי חופש");
		b1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		otherComponentsPanel.add(b1);
		JRadioButton b2= new JRadioButton("מספר מינימלי של חלונות");
		b2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		otherComponentsPanel.add(b2);
		textbox.setLayout(new GridLayout(1,12));
		for(int ¢=0 ; ¢ < 10; ++¢)
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
		otherComponentsPanel.add(new JPanel());
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
		otherComponentsPanel.add(new JPanel());
		
		c.gridx=0;
		c.gridy=3;
		add(otherComponentsPanel,c);
		setEvents();

	}
	
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
}