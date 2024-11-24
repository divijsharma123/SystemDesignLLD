package relationdb.application;

import relationdb.entities.Column;
import relationdb.entities.Constraint;
import relationdb.entities.Database;
import relationdb.entities.DatabaseManager;
import relationdb.entities.Filter;
import relationdb.entities.Row;
import relationdb.entities.Table;
import relationdb.enums.ColumnType;
import relationdb.enums.FilterOperator;

import java.util.Arrays;
import java.util.List;


public class Main {

    public static void main(String[] args){
        // Create a database
        DatabaseManager dbManager = DatabaseManager.getInstance();
        dbManager.createDatabase("mydb");
        Database db = dbManager.getDatabase("mydb");

// Create columns with constraints
        List<Column> columns = Arrays.asList(
                new Column("id", ColumnType.INTEGER, true,
                        new Constraint(-1024, 1024, 0)),
                new Column("name", ColumnType.STRING, true,
                        new Constraint(0, 0, 20))
        );

// Create table
        db.createTable("users", columns);
        Table usersTable = db.getTable("users");

// Create index
        usersTable.createIndex("id");

// Insert data
        usersTable.addRow(Arrays.asList(1, "John"));
        usersTable.addRow(Arrays.asList(2, "Jane"));

// Query with filter
        Filter filter = new Filter("id", 1, FilterOperator.EQUALS);
        List<Row> results = usersTable.select(filter);

// Print results
        usersTable.printAllRecords();
    }
}
