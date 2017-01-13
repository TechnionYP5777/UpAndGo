//
//	Author Alex V.
//

package view;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

///
// This class is a massage box class
///
public class Message {

	public static void infoBox(String infoMessage, String titleBar, ImageIcon i) {
		JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.PLAIN_MESSAGE, i);
	}
	public static int yesNoBox(String infoMessage, String titleBar, ImageIcon i) {
		return JOptionPane.showConfirmDialog(null, infoMessage, titleBar,JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,i );
	}
}