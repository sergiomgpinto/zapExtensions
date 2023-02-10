package org.zaproxy.addon.filetester.extensionapp;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;

/**
 * This class implements the pop-up window
 * for the file tester feature when an
 * unsafe file download is detected.
 *
 * @see JFrame
 */
@SuppressWarnings("serial")
public class FileTesterPopupMenuUI extends JFrame {
    /**
     * create the pop up window
     * @param filename - the name of the unsafe file to be displayed
     */
        public FileTesterPopupMenuUI(String filename) {
            setTitle("File tester extension pop up");
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            setSize(400, 500);
            JLabel l = new JLabel("The " + filename + " file is not safe. It was moved" +
                    " to the unsafe directory.");
            setLocationRelativeTo(null);
            JPanel contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
            contentPane.setLayout(new BorderLayout(0, 10));
            contentPane.add(l);
            setContentPane(contentPane);
            JButton btnOk = new JButton("Ok");
            btnOk.addActionListener(e -> this.dispose());

            contentPane.add(btnOk, BorderLayout.SOUTH);
            pack();
            setVisible(true);
        }
}
