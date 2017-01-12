package viewTests;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import view.TimetableVIew;

public class TimetableViewTest {
	private JFrame frame = new JFrame("Testing");
	private JPanel ttpanel = new TimetableVIew();
	
	@SuppressWarnings("static-access")
	public TimetableViewTest(){
		frame.add(ttpanel);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			@SuppressWarnings("unused")
			@Override
			public void run() {
				new TimetableViewTest();		
			}
			
		});
	}
}
