package relationdb.entities;

import relationdb.enums.FilterOperator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Table {
    private final String name;
    private final List<Column> columns;
    private final List<Row> rows;
    private final Map<String, Index> indexes;
    private int nextRowId;

    public Table(String name, List<Column> columns) {
        this.name = name;
        this.columns = new ArrayList<>(columns);
        this.rows = new ArrayList<>();
        this.indexes = new HashMap<>();
        this.nextRowId = 0;
    }

    public void createIndex(String columnName) {
        if (indexes.containsKey(columnName)) {
            throw new IllegalArgumentException("Index already exists on column '" + columnName + "'");
        }

        // Verify column exists
        getColumn(columnName);

        Index index = new Index(columnName);
        // Add existing rows to index
        for (Row row : rows) {
            index.add(row.getValue(columnName), row);
        }
        indexes.put(columnName, index);
    }

    public void dropIndex(String columnName) {
        if (!indexes.containsKey(columnName)) {
            throw new IllegalArgumentException("Index not found on column '" + columnName + "'");
        }
        indexes.remove(columnName);
    }

    public void addRow(List<Object> values) {
        if (values.size() != columns.size()) {
            throw new IllegalArgumentException("Value count doesn't match column count");
        }

        // Validate values
        for (int i = 0; i < values.size(); i++) {
            columns.get(i).validate(values.get(i));
        }

        // Create row
        Row row = new Row(nextRowId++);
        for (int i = 0; i < values.size(); i++) {
            row.setValue(columns.get(i).getName(), values.get(i));
        }

        // Update indexes
        for (Index index : indexes.values()) {
            index.add(row.getValue(index.getColumnName()), row);
        }

        rows.add(row);
    }

    public void updateRow(int rowId, Map<String, Object> values) {
        Row row = getRow(rowId);

        // Validate and prepare new values
        Map<String, Object> oldValues = new HashMap<>(row.getAllValues());

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            Column column = getColumn(entry.getKey());
            column.validate(entry.getValue());
        }

        // Update indexes
        for (Index index : indexes.values()) {
            String columnName = index.getColumnName();
            if (values.containsKey(columnName)) {
                index.remove(oldValues.get(columnName), row);
                index.add(values.get(columnName), row);
            }
        }

        // Update row values
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            row.setValue(entry.getKey(), entry.getValue());
        }
    }

    public void deleteRow(int rowId) {
        Row row = getRow(rowId);

        // Remove from indexes
        for (Index index : indexes.values()) {
            index.remove(row.getValue(index.getColumnName()), row);
        }

        rows.remove(row);
    }

    public void truncate() {
        rows.clear();
        for (Index index : indexes.values()) {
            index.clear();
        }
        nextRowId = 0;
    }

    public List<Row> select(Filter filter) {
        if (filter == null) {
            return new ArrayList<>(rows);
        }

        // Use index if available and operator is EQUALS
        if (filter.getOperator() == FilterOperator.EQUALS) {
            Index index = indexes.get(filter.getColumnName());
            if (index != null) {
                return index.find(filter.getValue());
            }
        }

        // Fallback to full table scan
        return rows.stream()
                .filter(row -> filter.matches(row))
                .collect(Collectors.toList());
    }

    private Column getColumn(String columnName) {
        return columns.stream()
                .filter(col -> col.getName().equals(columnName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Column '" + columnName + "' not found"));
    }

    private Row getRow(int rowId) {
        return rows.stream()
                .filter(row -> row.getRowId() == rowId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Row with id " + rowId + " not found"));
    }

    public void printAllRecords() {
        // Print header
        columns.forEach(col -> System.out.print(col.getName() + "\t"));
        System.out.println();

        // Print rows
        for (Row row : rows) {
            columns.forEach(col -> System.out.print(row.getValue(col.getName()) + "\t"));
            System.out.println();
        }
    }
}
