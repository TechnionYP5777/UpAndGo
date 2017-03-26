package view;

import java.awt.Desktop;
import java.awt.Font;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;

/**
 * 
 * @author Alex V.
 * @since 13-01-2017
 * 
 * Class represents a massage box.
 * 
 */
public class Message {
	// info message with ok button
	public static void infoBox(final String infoMessage, final String titleBar, final ImageIcon i) {
		JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.PLAIN_MESSAGE, i);
	}

	// option message yes/ no
	public static int yesNoBox(final String infoMessage, final String titleBar, final ImageIcon i) {
		return JOptionPane.showConfirmDialog(null, infoMessage, titleBar, JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, i);
	}

	// option message yes/ no / cancel
	public static int yesNoCancleBox(final String infoMessage, final String titleBar, final ImageIcon i) {
		return JOptionPane.showConfirmDialog(null, infoMessage, titleBar, JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, i);
	}

	// info message with hyper link to the used icon (cat icon)
	public static void hyperCatBox(final String infoMessage, final String title, final ImageIcon i) {
		final JLabel label = new JLabel();
		final Font font = label.getFont();

		// create some css from the label's font
		final StringBuffer style = new StringBuffer("font-family:" + font.getFamily() + ";");
		style.append("font-weight:" + (font.isBold() ? "bold" : "normal") + ";");
		style.append("font-size:" + font.getSize() + "pt;");

		// html content
		final JEditorPane ep = new JEditorPane("text/html",
				"<html><body style=\"" + style + "\">" //
						+ "<br>" + infoMessage + "<br>" //
						+ "<br>more cats here:<br> <a href=\"http://google.com/\">http://www.iconka.com</a>" //
						+ "</body></html>");
		// handle link events
		ep.addHyperlinkListener(e -> {
			if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED) && Desktop.isDesktopSupported())
				try {
					Desktop.getDesktop().browse(new URI("http://www.iconka.com"));
				} catch (@SuppressWarnings("unused") final Exception ex) {
					Message.infoBox("couldn't open\nhttp://www.iconika.com", "Something went wrong", null);
				}
		});
		ep.setEditable(false);
		ep.setBackground(label.getBackground());

		// show
		JOptionPane.showMessageDialog(null, ep, title, JOptionPane.PLAIN_MESSAGE, i);
	}
}