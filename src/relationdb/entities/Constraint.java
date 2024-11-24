package relationdb.entities;

public class Constraint {
    private final int minValue;
    private final int maxValue;
    private final int maxLength;

    public Constraint(int minValue, int maxValue, int maxLength) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.maxLength = maxLength;
    }

    public void validate(Object value) {
        if (value instanceof String) {
            String strValue = (String) value;
            if (strValue.length() > maxLength) {
                throw new IllegalArgumentException(
                        "String length exceeds maximum allowed length of " + maxLength
                );
            }
        } else if (value instanceof Integer) {
            int intValue = (Integer) value;
            if (intValue < minValue || intValue > maxValue) {
                throw new IllegalArgumentException(
                        "Integer value must be between " + minValue + " and " + maxValue
                );
            }
        }
    }
}

