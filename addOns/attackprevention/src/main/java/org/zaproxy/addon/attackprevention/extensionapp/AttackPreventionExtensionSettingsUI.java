package org.zaproxy.addon.attackprevention.extensionapp;

import org.zaproxy.addon.attackprevention.utils.JSONLoader;
import org.zaproxy.addon.attackprevention.utils.DBLoader;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.WindowConstants;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

/**
 * This classes allows for configuration of the
 * AttackPrevention tools menu in the zap app
 * which allows for the user to enable the
 * password hygiene mechanism.
 *
 * @see JFrame
 * @see JCheckBox
 * @see JPanel
 */
public class AttackPreventionExtensionSettingsUI extends JFrame{

    private static final long serialVersionUID = 1L;
    private final JCheckBox checkboxEnablePasswordHygiene;

    /**
     * Creates the settings panel.
     */
    public AttackPreventionExtensionSettingsUI() {

        setTitle("Vulnerability Prevention settings");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(275, 100);
        setLocationRelativeTo(null);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        checkboxEnablePasswordHygiene =
                new JCheckBox("Password Hygiene Mechanism");
        checkboxEnablePasswordHygiene.setSelected(DBLoader.loadDB(
                JSONLoader.getLabel("PasswordHygieneMechanismFile"))
                .equals("true"));
        contentPane.add(checkboxEnablePasswordHygiene, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel();
        contentPane.add(footerPanel, BorderLayout.SOUTH);
        footerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JButton btnSaveChanges = new JButton("Save");
        btnSaveChanges.addActionListener(
                e -> {
                    boolean success = saveChanges();
                    if (success) {
                        JOptionPane.showMessageDialog(
                                AttackPreventionExtensionSettingsUI.this,
                                "Preference saved.",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                });
        contentPane.add(btnSaveChanges, BorderLayout.SOUTH);
    }

    /**
     * Saves to the database the user preference towards the password hygiene mechanism.
     *
     * @return true
     */
    private boolean saveChanges() {
        String fileName = JSONLoader.getLabel("PasswordHygieneMechanismFile");

        DBLoader.saveToDB(fileName, checkboxEnablePasswordHygiene.isSelected() ? "true" : "false");
        return true;
    }
}
