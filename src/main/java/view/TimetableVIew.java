package view;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.ComponentOrientation;

import javax.swing.UIManager;
import java.awt.Font;

public class TimetableVIew extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public TimetableVIew() {
		setLayout(new GridLayout(2,1,3,3));
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
		
		add(table);
		JPanel panel1=new JPanel();
		panel1.setLayout(new GridLayout(10,1,3,3));
		JPanel panel2 =new JPanel();
		panel2.setLayout(new GridLayout(1,7,3,3));
		JPanel textbox=new JPanel();
		JButton next = new JButton("הבא>");
		JButton prev = new JButton("<הקודם");
		panel2.add(new JPanel());
		panel2.add(next);
		panel2.add(new JPanel());
		panel2.add(new JPanel());
		panel2.add(new JPanel());
		panel2.add(prev);
		panel2.add(new JPanel());
		panel1.add(panel2);
		panel1.add(new JPanel());
		JTextField textField = new JTextField("בחר/י את ההעדפות לבניית המערכת:");
		textField.setFont(new Font("Dialog", Font.BOLD, 24));
		textField.setEditable(false);
		textField.setBackground(UIManager.getColor("Button.background"));
		textField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		panel1.add(textField);
		JRadioButton b1 = new JRadioButton("ימי חופש");
		b1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		panel1.add(b1);
		JRadioButton b2= new JRadioButton("מספר מינימלי של חלונות");
		b2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		panel1.add(b2);
		textbox.setLayout(new GridLayout(1,12));
		for(int ¢=0 ; ¢ < 10; ++¢)
			textbox.add(new JPanel());
		JTextField tf = new JTextField();
		textbox.add(tf);
		JTextField tf1 = new JTextField("שעת התחלה:");
		tf1.setEditable(false);
		tf1.setFont(new Font("Dialog", Font.BOLD, 12));
		tf1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		textbox.add(tf1);
		panel1.add(textbox);
		panel1.add(new JPanel());
		panel1.add(new JPanel());
		panel1.add(new JPanel());
		panel1.add(new JPanel());
		add(panel1);

	}
}
