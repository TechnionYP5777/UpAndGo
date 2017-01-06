package viewTests;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import view.AvailableCourses;

public class viewTests {
	public viewTests() {
		design();
	}// end Test()

	public static void design() {
		JFrame f = new JFrame();
		f.setMinimumSize(new Dimension(220, 300));
		f.setSize(300, 400);
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		f.setVisible(true);
		f.getContentPane().add((new AvailableCourses()));
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					viewTests viewTests = new viewTests();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

}
