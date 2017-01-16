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

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new UserGrades().setVisible(true);
				} catch (Exception ¢) {
					¢.printStackTrace();
				}
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
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"הנדסת תוכנה", "תלת שנתי", "ארבע שנתי", "הנדסת מחשבים"}));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		JButton btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			@Override
			@SuppressWarnings("null")
			public void actionPerformed(ActionEvent arg0) {
				String result = "";
				try {
					Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
					boolean hasTransferableText = (contents != null)
							&& contents.isDataFlavorSupported(DataFlavor.stringFlavor);
					if (hasTransferableText)
						result = (String) contents.getTransferData(DataFlavor.stringFlavor);
					if("הנדסת תוכנה".equals(comboBox.getSelectedItem().toString())){
						SECatalogLoader seCatalog = new SECatalogLoader("SoftwareEngineering.XML", new CourseModel(new XmlCourseLoader("REPFILE/REP.XML")));
						SoftwareEngineering c = (SoftwareEngineering) seCatalog.getCatalog();
						User user = new User(result, seCatalog);
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
