package cache;
import java.util.LinkedHashMap;
import java.util.Map;

public class LruCacheThreadSafe<K, V> {

    private final LinkedHashMap<K, V> cache;
    private final int capacity;

    public LruCacheThreadSafe(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > LruCacheThreadSafe.this.capacity;
            }
        };
    }

    public synchronized V get(K key) {
        return cache.get(key);
    }

    public synchronized void put(K key, V value) {
        cache.put(key, value);
    }

    public synchronized void showCache() {
        cache.forEach((key, value) -> System.out.println("(key: " + key + " -> value: " + value + ")"));
        System.out.println("*******************");
    }

    public static void main(String[] args) {
        LruCacheV2<Integer, String> lru = new LruCacheV2<>(4);
        lru.put(1, "1");
        lru.put(3, "3");
        lru.put(2, "2");
        lru.put(4, "4");
        lru.showCache();
        lru.put(5, "5");
        lru.showCache();
        lru.put(4, "6");
        lru.showCache();
    }
}
