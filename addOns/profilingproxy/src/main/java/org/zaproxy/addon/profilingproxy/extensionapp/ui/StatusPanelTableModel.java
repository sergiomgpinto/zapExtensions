package org.zaproxy.addon.profilingproxy.extensionapp.ui;

import org.zaproxy.addon.profilingproxy.extensionapp.ui.improvements.ImprovementsPanelTableModel;
import org.zaproxy.addon.profilingproxy.extensionapp.ui.metrics.MetricsPanelTableModel;

import javax.swing.table.AbstractTableModel;

/**
 * This class is the profiling proxy abstraction for an
 * abstract model used to represent data to be displayed
 * in the status panel.
 *
 * @see AbstractTableModel
 * @see MetricsPanelTableModel
 * @see ImprovementsPanelTableModel
 */
public abstract class StatusPanelTableModel extends AbstractTableModel {

    static final long serialVersionUID = 1L;

    protected StatusPanelTableModel() {
        super();
    }

    /**
     * This method retrieves the column name.
     * @param column - The index of the column.
     * @return the column name.
     */
    @Override
    public abstract String getColumnName(int column);

    /**
     * This method returns the number of rows in the table.
     *
     * @return the number of rows in the table.
     */
    @Override
    public abstract int getRowCount();

    /**
     * This method returns the number of columns in the table.
     *
     * @return the number of columns in the table.
     */
    @Override
    public abstract int getColumnCount();

    /**
     * This method returns the value in the table
     * at the given indexes row and column.
     *
     * @return the value in the table.
     */
    @Override
    public abstract Object getValueAt(int row, int column);
}
