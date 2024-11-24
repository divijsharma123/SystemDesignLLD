package relationdb.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Index {
    private final String columnName;
    private final TreeMap<Object, List<Row>> indexMap;

    public Index(String columnName) {
        this.columnName = columnName;
        this.indexMap = new TreeMap<>();
    }

    public void add(Object value, Row row) {
        indexMap.computeIfAbsent(value, k -> new ArrayList<>()).add(row);
    }

    public void remove(Object value, Row row) {
        List<Row> rows = indexMap.get(value);
        if (rows != null) {
            rows.remove(row);
            if (rows.isEmpty()) {
                indexMap.remove(value);
            }
        }
    }

    public List<Row> find(Object value) {
        return indexMap.getOrDefault(value, new ArrayList<>());
    }

    public String getColumnName() {
        return columnName;
    }

    public void clear() {
        indexMap.clear();
    }
}

