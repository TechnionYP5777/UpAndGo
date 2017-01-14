//
//	Author Alex V.
//

package view;

import java.awt.Desktop;
import java.awt.Font;
import java.net.URI;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

///
// This class is a massage box class
///
public class Message {
	// info message with ok button 
	public static void infoBox(String infoMessage, String titleBar, ImageIcon i) {
		JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.PLAIN_MESSAGE, i);
	}
	// option message yes/ no
	public static int yesNoBox(String infoMessage, String titleBar, ImageIcon i) {
		return JOptionPane.showConfirmDialog(null, infoMessage, titleBar, JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, i);
	}
	// option message yes/ no / cancel
	public static int yesNoCancleBox(String infoMessage, String titleBar, ImageIcon i) {
		return JOptionPane.showConfirmDialog(null, infoMessage, titleBar, JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, i);
	}
	// info message with hyper link to the used icon (cat icon)
	public static void hyperCatBox(String infoMessage , String title ,ImageIcon i ) {
		JLabel label = new JLabel();
		Font font = label.getFont();

		// create some css from the label's font
		StringBuffer style = new StringBuffer("font-family:" + font.getFamily() + ";");
		style.append("font-weight:" + (font.isBold() ? "bold" : "normal") + ";");
		style.append("font-size:" + font.getSize() + "pt;");

		// html content
		JEditorPane ep = new JEditorPane("text/html",
				"<html><body style=\"" + style + "\">" //
					+ "<br>"+ infoMessage+ "<br>" //
						+ "<br>more cats here:<br> <a href=\"http://google.com/\">http://www.iconka.com</a>" //
						+ "</body></html>");

		// handle link events
		ep.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED))
					if (Desktop.isDesktopSupported()) {
						try {
							Desktop.getDesktop().browse(new URI("http://www.iconka.com"));
						} catch (@SuppressWarnings("unused") Exception ex)
						{
							Message.infoBox("couldn't open\nhttp://www.iconika.com", "Something went wrong", null);
						}
					}
			}

		});ep.setEditable(false);ep.setBackground(label.getBackground());

	// show
	JOptionPane.showMessageDialog(null,ep,title,JOptionPane.PLAIN_MESSAGE,i);
}}