package relationdb.entities;

import relationdb.enums.FilterOperator;

public class Filter {
    private final String columnName;
    private final Object value;
    private final FilterOperator operator;

    public Filter(String columnName, Object value, FilterOperator operator) {
        this.columnName = columnName;
        this.value = value;
        this.operator = operator;
    }

    public boolean matches(Row row) {
        Object rowValue = row.getValue(columnName);
        if (rowValue == null || value == null) {
            return false;
        }

        if (rowValue instanceof String && value instanceof String) {
            String strRowValue = (String) rowValue;
            String strValue = (String) value;
            return operator == FilterOperator.EQUALS && strRowValue.equals(strValue);
        }

        if (rowValue instanceof Integer && value instanceof Integer) {
            Integer intRowValue = (Integer) rowValue;
            Integer intValue = (Integer) value;
            switch (operator) {
                case EQUALS:
                    return intRowValue.equals(intValue);
                case GREATER_THAN:
                    return intRowValue > intValue;
                case LESS_THAN:
                    return intRowValue < intValue;
                default:
                    return false;
            }
        }

        return false;
    }

    // Getters
    public String getColumnName() { return columnName; }
    public Object getValue() { return value; }
    public FilterOperator getOperator() { return operator; }
}

