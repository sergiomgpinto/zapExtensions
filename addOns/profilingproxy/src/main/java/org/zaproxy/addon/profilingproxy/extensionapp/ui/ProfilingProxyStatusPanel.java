package org.zaproxy.addon.profilingproxy.extensionapp.ui;

import org.jdesktop.swingx.JXTable;
import org.parosproxy.paros.extension.AbstractPanel;
import org.zaproxy.addon.profilingproxy.extensionapp.ui.improvements.ImprovementsTableRecord;
import org.zaproxy.addon.profilingproxy.extensionapp.ui.improvements.ImprovementsPanelTableModel;
import org.zaproxy.addon.profilingproxy.extensionapp.ui.metrics.MetricsPanelTableModel;
import org.zaproxy.addon.profilingproxy.improvements.ImprovementDTO;
import org.zaproxy.addon.profilingproxy.metrics.MetricDTO;
import org.zaproxy.zap.view.ZapTable;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;

/**
 * This is the class that implements the status panel for the
 * profiling proxy extension.
 *
 * @see AbstractPanel
 */
public class ProfilingProxyStatusPanel extends AbstractPanel {

    private static final long serialVersionUID = 1L;
    private JPanel mainPanel;
    private JScrollPane metricsTableScrollPane;
    private JScrollPane improvementsTableScrollPane;
    private ZapTable metricsTable;
    private ZapTable improvementsTable;
    private static final MetricsPanelTableModel METRICS_TABLE_MODEL = new MetricsPanelTableModel();
    private static final ImprovementsPanelTableModel IMPROVEMENTS_TABLE_MODEL = new ImprovementsPanelTableModel();
    public static final String PANEL_NAME = "ProfilingProxyPanel";
    private static final String IMPROVEMENTS_CONTAINER_NAME = "ProfilingProxyImprovementsContainer";

    public ProfilingProxyStatusPanel() {
        super();
        initializePanel();
    }

    /**
     * Initializes the panel and its components.
     * Sets it visible to the zap app.
     */
    private void initializePanel() {
        setLayout(new BorderLayout());
        this.add(getWorkPanel());
        this.setName("Profiling Proxy");
        setVisible(true);
    }

    /**
     * This method creates the working main panel.
     *
     * @return javax.swing.JPanel
     */
    private JPanel getWorkPanel() {
        if (mainPanel == null) {
            mainPanel = new JPanel(new BorderLayout());

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addTab(
                    "Metrics", getMetricsTableScrollPane());
            tabbedPane.addTab(
                    "Improvements",
                    getImprovementsTableScrollPane());
            tabbedPane.setSelectedIndex(0);

            mainPanel.add(tabbedPane);
        }
        return mainPanel;
    }

    /**
     * This method creates the metrics table scroll pane.
     *
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getMetricsTableScrollPane() {
        if (metricsTableScrollPane == null) {
            metricsTableScrollPane = new JScrollPane();
            metricsTableScrollPane.setName("ProxyMetricsPane");
            metricsTableScrollPane.setViewportView(getMetricsTable());
        }
        return metricsTableScrollPane;
    }

    /**
     * This method creates the improvements table scroll pane.
     *
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getImprovementsTableScrollPane() {
        if (improvementsTableScrollPane == null) {
            improvementsTableScrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            improvementsTableScrollPane.setName("ProxyImprovementsPane");
            improvementsTableScrollPane.setViewportView(getImprovementsTable());
        }
        return improvementsTableScrollPane;
    }

    /**
     * This method creates the metrics table.
     *
     * @return org.jdesktop.swingx.JXTable
     */
    public JXTable getMetricsTable() {
        if (metricsTable == null) {
            // Create the table with a default, empty TableModel and the proper settings
            metricsTable = new ZapTable(METRICS_TABLE_MODEL);
            metricsTable.setColumnSelectionAllowed(false);
            metricsTable.setCellSelectionEnabled(false);
            metricsTable.setRowSelectionAllowed(false);
            metricsTable.setAutoCreateRowSorter(false);
            metricsTable.setAutoCreateColumnsFromModel(true);

            metricsTable.getColumnModel().getColumn(0).setMinWidth(80);
            metricsTable.getColumnModel().getColumn(0).setPreferredWidth(125); // Empty column

            metricsTable.getColumnModel().getColumn(1).setMinWidth(80);
            metricsTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Request size column

            metricsTable.getColumnModel().getColumn(2).setMinWidth(80);
            metricsTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Response size column

            metricsTable.getColumnModel().getColumn(3).setMinWidth(80);
            metricsTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Response time column

            metricsTable.setName(PANEL_NAME);
            metricsTable.setDoubleBuffered(true);
            metricsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        }
        return metricsTable;
    }

    /**
     * This method creates the improvements table.
     *
     * @return org.jdesktop.swingx.JXTable
     */
    public JXTable getImprovementsTable() {
        if (improvementsTable == null) {
            // Create the table with a default, empty TableModel and the proper settings
            improvementsTable = new ZapTable(IMPROVEMENTS_TABLE_MODEL);
            improvementsTable.setColumnSelectionAllowed(false);
            improvementsTable.setCellSelectionEnabled(false);
            improvementsTable.setRowSelectionAllowed(true);
            improvementsTable.setAutoCreateRowSorter(true);
            improvementsTable.setHorizontalScrollEnabled(true);
            improvementsTable.setAutoCreateColumnsFromModel(false);

            improvementsTable.getColumnModel().getColumn(0).setMinWidth(60);
            improvementsTable.getColumnModel().getColumn(0).setPreferredWidth(80); // Type of improvement.

            improvementsTable.getColumnModel().getColumn(1).setMinWidth(30);
            improvementsTable.getColumnModel().getColumn(1).setPreferredWidth(50); // Size reduction.

            improvementsTable.getColumnModel().getColumn(2).setMinWidth(100);
            improvementsTable.getColumnModel().getColumn(2).setPreferredWidth(120); // URL.

            improvementsTable.getColumnModel().getColumn(3).setMinWidth(100);
            improvementsTable.getColumnModel().getColumn(3).setPreferredWidth(120); // Response content.

            improvementsTable.setName(IMPROVEMENTS_CONTAINER_NAME);
            improvementsTable.setDoubleBuffered(true);
            improvementsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        }
        return improvementsTable;
    }

    /**
     * This method allows to update the metrics table.
     *
     * @param metric - Contains the metrics calculated
     * after a new message.
     */
    public void updateMetricsTable(MetricDTO metric) {
            METRICS_TABLE_MODEL.updateColumn(metric);
    }

    /**
     * This method allows to insert data into the improvements table.
     *
     * @param improvement - Contains the improvement to be inserted.
     */
    public void updateImprovementsTable(ImprovementDTO improvement) {

        String typeOfImprovement = improvement.getTypeOfImprovement();
        String sizeReduction = improvement.getSizeReductionToString();
        String url = improvement.getUrl();
        String responseContent = improvement.getResponseBody();

        ImprovementsTableRecord rec = new ImprovementsTableRecord(typeOfImprovement, sizeReduction, url, responseContent);

        if (improvementsTable != null) {
            IMPROVEMENTS_TABLE_MODEL.addRecord(rec);
        }
    }
}
