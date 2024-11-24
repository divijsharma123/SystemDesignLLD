package relationdb.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Database {
    private final String name;
    private final Map<String, Table> tables;

    public Database(String name) {
        this.name = name;
        this.tables = new HashMap<>();
    }

    public void createTable(String name, List<Column> columns) {
        if (tables.containsKey(name)) {
            throw new IllegalArgumentException("Table '" + name + "' already exists");
        }
        validateColumns(columns);
        tables.put(name, new Table(name, columns));
    }

    public void deleteTable(String name) {
        if (!tables.containsKey(name)) {
            throw new IllegalArgumentException("Table '" + name + "' not found");
        }
        tables.remove(name);
    }

    public Table getTable(String name) {
        Table table = tables.get(name);
        if (table == null) {
            throw new IllegalArgumentException("Table '" + name + "' not found");
        }
        return table;
    }

    private void validateColumns(List<Column> columns) {
        if (columns == null || columns.isEmpty()) {
            throw new IllegalArgumentException("Table must have at least one column");
        }
        Set<String> columnNames = new HashSet<>();
        for (Column column : columns) {
            if (columnNames.contains(column.getName())) {
                throw new IllegalArgumentException("Duplicate column name: " + column.getName());
            }
            columnNames.add(column.getName());
        }
    }

    public List<String> listTables() {
        return new ArrayList<>(tables.keySet());
    }
}

