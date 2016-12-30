package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.Dimension;

import java.awt.BorderLayout;

public class scheduleView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					scheduleView frame = new scheduleView();
					frame.getContentPane().add(new JScrollPane(table));
					frame.setSize(500, 300);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	static JTable table;
	static String[] days = { "Shishi", "Hamishi", "Revii", "Shlishi", "Sheni", "Rishon", "" };

	static int rowNum = 13;
	static int colNum = 7;

	/**
	 * Create the frame.
	 */
	public scheduleView() {
		getContentPane().setMinimumSize(new Dimension(500, 300));
		getContentPane().setLayout(new BorderLayout(0, 0));
		setMinimumSize(new Dimension(500, 300));

		table = new JTable(rowNum, colNum);
		for (int i = 0; i < days.length; ++i) {
			TableColumn tc = table.getColumnModel().getColumn(i);
			tc.setHeaderValue(days[i]);

			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			tc.setCellRenderer(dtcr);
		}
		for (int hour = 8, ¢ = 0; ¢ < rowNum; ++¢, ++hour)
			table.getModel().setValueAt(getHour(hour), ¢, 6);

		table.setCellSelectionEnabled(true);
		getContentPane().add(table, BorderLayout.CENTER);

	}

	private static Object getHour(int hour) {
		
		return hour+ ":30";
	}

}
