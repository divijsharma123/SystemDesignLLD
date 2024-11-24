package relationdb.entities;

import java.util.HashMap;
import java.util.Map;

public class Row {
    private final int rowId;
    private final Map<String, Object> values;

    public Row(int rowId) {
        this.rowId = rowId;
        this.values = new HashMap<>();
    }

    public Object getValue(String columnName) {
        return values.get(columnName);
    }

    public void setValue(String columnName, Object value) {
        values.put(columnName, value);
    }

    public int getRowId() {
        return rowId;
    }

    public Map<String, Object> getAllValues() {
        return new HashMap<>(values);
    }
}

