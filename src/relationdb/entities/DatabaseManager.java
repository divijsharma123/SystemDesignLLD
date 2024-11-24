package relationdb.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {
    private static DatabaseManager instance;
    private final Map<String, Database> databases;

    private DatabaseManager() {
        this.databases = new HashMap<>();
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void createDatabase(String name) {
        if (databases.containsKey(name)) {
            throw new IllegalArgumentException("Database '" + name + "' already exists");
        }
        databases.put(name, new Database(name));
    }

    public void deleteDatabase(String name) {
        if (!databases.containsKey(name)) {
            throw new IllegalArgumentException("Database '" + name + "' not found");
        }
        databases.remove(name);
    }

    public Database getDatabase(String name) {
        Database database = databases.get(name);
        if (database == null) {
            throw new IllegalArgumentException("Database '" + name + "' not found");
        }
        return database;
    }

    public List<String> listDatabases() {
        return new ArrayList<>(databases.keySet());
    }
}