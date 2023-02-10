package org.zaproxy.addon.profilingproxy.extensionapp.ui.improvements;

import org.zaproxy.addon.profilingproxy.extensionapp.ui.StatusPanelTableModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This class creates and updates the improvements table.
 *
 * @see StatusPanelTableModel
 */
public class ImprovementsPanelTableModel extends StatusPanelTableModel {

    static final long serialVersionUID = 1L;
    private static final String[] COLUMN_NAMES = {
            "Type of Improvement",
            "Size Reduction(Bytes)",
            "URL",
            "Response Content"
    };
    private final List<ImprovementsTableRecord> data = new ArrayList<>();

    public ImprovementsPanelTableModel() {
        super();
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    /**
     * This method extracts the value in the improvements table
     * at the given row and column.
     *
     * @param row - the row of the table
     * @param column - the column of the table
     * @return the value at the given row and column
     */
    @Override
    public Object getValueAt(int row, int column) {
        if (row > data.size()) {
            return null;
        }
        if (column == 0) {
            return data.get(row).getTypeOfImprovement();
        }
        else if (column == 1) {
            return data.get(row).getSizeReduction();
        }
        else if (column == 2) {
            return data.get(row).getUrl();
        }
        else if (column == 3) {
            return data.get(row).getResponseContent();
        }
        return null;
    }

    /**
     * This method adds a new record to the improvements table.
     *
     * @param rec - the record to be added.
     */
    public void addRecord(ImprovementsTableRecord rec) {
        data.add(rec);
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }
}
