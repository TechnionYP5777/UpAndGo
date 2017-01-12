package user;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import catalog.SoftwareEngineering;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

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

		JButton btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			@Override
			@SuppressWarnings("null")
			public void actionPerformed(ActionEvent arg0) {
				String result = "";
				Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
				boolean hasTransferableText = (contents != null)
						&& contents.isDataFlavorSupported(DataFlavor.stringFlavor);
				if (hasTransferableText)
					try {
						result = (String) contents.getTransferData(DataFlavor.stringFlavor);
						User user = new User(result, new SoftwareEngineering(new ArrayList<>(), new ArrayList<>(),
								new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
					} catch (UnsupportedFlavorException | IOException ¢) {
						System.out.println(¢);
						¢.printStackTrace();
					}

			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(156).addComponent(btnDone).addContainerGap(179, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(111).addComponent(btnDone).addContainerGap(117, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
	}
}
