package user;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import catalog.SoftwareEngineering;
import catalog.loader.SECatalogLoader;
import model.CourseModel;
import model.loader.XmlCourseLoader;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class UserGrades extends JFrame {

	private final JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				new UserGrades().setVisible(true);
			} catch (final Exception ¢) {
				¢.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UserGrades() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"הנדסת תוכנה", "תלת שנתי", "ארבע שנתי", "הנדסת מחשבים"}));
		final GroupLayout gl_contentPane = new GroupLayout(contentPane);
		final JButton btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			@Override
			@SuppressWarnings("null")
			public void actionPerformed(final ActionEvent arg0) {
				String result = "";
				try {
					final Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
					final boolean hasTransferableText = contents != null
							&& contents.isDataFlavorSupported(DataFlavor.stringFlavor);
					if (hasTransferableText)
						result = (String) contents.getTransferData(DataFlavor.stringFlavor);
					if("הנדסת תוכנה".equals(comboBox.getSelectedItem().toString())){
						final SECatalogLoader seCatalog = new SECatalogLoader("SoftwareEngineering.XML", new CourseModel(new XmlCourseLoader("REPFILE/REP.XML")));
						final SoftwareEngineering c = (SoftwareEngineering) seCatalog.getCatalog();
						final User user = new User(result, seCatalog);
					}
					JOptionPane.showMessageDialog(null,"הקטלוג נטען בהצלחה!");
					} catch (@SuppressWarnings("unused") UnsupportedFlavorException | IOException ¢) {
						JOptionPane.showMessageDialog(null,"העתק מתוך UG את גיליון הציונים שלך");
					}

			}
		});
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(156)
							.addComponent(btnDone))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(51)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(213, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(56)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(31)
					.addComponent(btnDone)
					.addContainerGap(154, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
