package org.zaproxy.addon.filetester.extensionapp;

import org.zaproxy.addon.filetester.utils.JSONLoader;
import org.zaproxy.addon.filetester.utils.ResourceLoader;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import java.awt.Component;

/**
 * This classes allows for configuration of the
 * AttackPrevention tools menu in the zap app
 * which allows for the user to enable the
 * password hygiene mechanism.
 *
 * @see JFrame
 */
@SuppressWarnings("serial")
public class FileTesterExtensionSettingsUI extends JFrame{

    private final JCheckBox checkboxEnableScanning;

    /**
     * Creates the settings panel.
     */
    public FileTesterExtensionSettingsUI() {

        setTitle("File tester extension settings");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        JPanel buttonPanel = new JPanel();

        checkboxEnableScanning =
                new JCheckBox("File Tester scanning tool");
        checkboxEnableScanning.setSelected(ResourceLoader.loadString(
                JSONLoader.getLabel("EnableFileTesterExtension"))
                        .equals("true"));
        checkboxEnableScanning.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(checkboxEnableScanning);
        JButton btnSaveChanges = new JButton("Save");
        btnSaveChanges.addActionListener(
                e -> {
                    boolean success = saveChanges();
                    if (success) {
                        JOptionPane.showMessageDialog(
                                FileTesterExtensionSettingsUI.this,
                                "Preference saved.",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                });
        buttonPanel.add(btnSaveChanges);

        JButton btnGenerateReport = new JButton("Generate report");

        btnGenerateReport.addActionListener(
                e -> {
                    FileTesterReportUI reportUI = new FileTesterReportUI();
                    reportUI.setVisible(true);
                });
        buttonPanel.add(btnGenerateReport);
        mainPanel.add(buttonPanel);
        add(mainPanel);
        pack();
    }

    /**
     * Saves to the database the user preference towards the password hygiene mechanism.
     *
     * @return true
     */
    private boolean saveChanges() {
        String fileName = JSONLoader.getLabel("EnableFileTesterExtension");

        ResourceLoader.saveString(fileName, checkboxEnableScanning.isSelected() ? "true" : "false");
        return true;
    }
}
