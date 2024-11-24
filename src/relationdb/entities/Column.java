package relationdb.entities;

import relationdb.enums.ColumnType;

public class Column {
    private final String name;
    private final ColumnType type;
    private final boolean isRequired;
    private final Constraint constraint;

    public Column(String name, ColumnType type, boolean isRequired, Constraint constraint) {
        this.name = name;
        this.type = type;
        this.isRequired = isRequired;
        this.constraint = constraint;
    }

    public void validate(Object value) {
        if (value == null) {
            if (isRequired) {
                throw new IllegalArgumentException("Column '" + name + "' is required");
            }
            return;
        }

        switch (type) {
            case STRING:
                if (!(value instanceof String)) {
                    throw new IllegalArgumentException("Invalid type for column '" + name + "'. Expected String");
                }
                break;
            case INTEGER:
                if (!(value instanceof Integer)) {
                    throw new IllegalArgumentException("Invalid type for column '" + name + "'. Expected Integer");
                }
                break;
        }

        if (constraint != null) {
            constraint.validate(value);
        }
    }

    // Getters
    public String getName() { return name; }
    public ColumnType getType() { return type; }
    public boolean isRequired() { return isRequired; }
}
