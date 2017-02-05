package user;

import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import catalog.loader.SECatalogLoader;
import model.CourseModel;
import model.loader.XmlCourseLoader;

public class UserTest extends JFrame {

	private final JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				final UserTest frame = new UserTest();
				frame.setVisible(true);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("unchecked")
	public UserTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		@SuppressWarnings("rawtypes")
		final
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(
				new DefaultComboBoxModel(new String[] { "הנדסת תוכנה", "ארבע שנתי", "תלת שנתי", "הנדסת מחשבים" }));

		final JTextPane textPane = new JTextPane();

		final JButton btnDone = new JButton("Done");
		btnDone.addActionListener(arg0 -> {
			final String catalogName = "" + comboBox.getSelectedItem();
			if ("הנדסת תוכנה".equals(catalogName)) {
				final SECatalogLoader catalogLoader = new SECatalogLoader("SoftwareEngineering.XML",
						new CourseModel(new XmlCourseLoader("REPFILE/REP.XML")));
				final User user = new User(textPane.getText(), catalogLoader);
				catalogLoader.markDoneCourses(user.courses);
				System.out.println("++++++++" + catalogLoader.getDoneCourse());
				JOptionPane.showMessageDialog(null, "שלום " + user.getName() + ", הנתונים נטענו בהצלחה!");
			}
		});
		final GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(69)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(52).addComponent(btnDone)))
						.addContainerGap(136, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(37)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnDone))
						.addGap(18).addComponent(textPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(156, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
	}
}
