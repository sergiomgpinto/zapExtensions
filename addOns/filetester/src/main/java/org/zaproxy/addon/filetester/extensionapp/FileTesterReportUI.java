package org.zaproxy.addon.filetester.extensionapp;

import org.zaproxy.addon.filetester.utils.ResourceLoader;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.JLabel;
import java.awt.Component;
import java.util.Scanner;

/**
 * This class implements the pop-up window
 * with the report of all the files downloaded
 * with their specific tests.
 *
 * @see JFrame
 **/
@SuppressWarnings("serial")
public class FileTesterReportUI extends JFrame {

    public FileTesterReportUI() {

        setTitle("File tester extension report");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
        String report = ResourceLoader.loadString("reports/report.log");
        JPanel textPane = new JPanel();
        textPane.setLayout(new BoxLayout(textPane, BoxLayout.PAGE_AXIS));
        JPanel buttonPane = new JPanel();

         try (Scanner scanner = new Scanner(report)) {
             while (scanner.hasNextLine()) {
                 String line = scanner.nextLine();
                 String cleanedLine = line.replace(";", " ");

                 JLabel lineOfText = new JLabel(cleanedLine);
                 lineOfText.setAlignmentX(Component.LEFT_ALIGNMENT);
                 textPane.add(lineOfText);
             }
         }

        JScrollPane scrollBar = new JScrollPane(textPane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
                ,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JScrollPane scrollPane = new JScrollPane(textPane,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setRowHeaderView(scrollBar);

        JButton btnSaveChanges = new JButton("Close report");
        btnSaveChanges.addActionListener(
                e -> this.dispose());
        btnSaveChanges.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPane.add(btnSaveChanges);
        mainPane.add(scrollPane);
        mainPane.add(buttonPane);
        add(mainPane);
        pack();
    }
}
