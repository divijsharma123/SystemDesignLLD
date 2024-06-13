package cache;

import java.util.*;

public class LfuCache<K, V> {

    private int capacity;
    private TreeMap<Integer, LinkedHashMap<K, V>> freqToLruMap;
    private HashMap<K, Integer> keyToFreqMap;

    public LfuCache(int capacity) {

        synchronized (this) {
            this.capacity = capacity;
            keyToFreqMap = new HashMap<>();
            freqToLruMap = new TreeMap<>();
        }
    }

    private synchronized LinkedHashMap<K, V> getNewLinkedHashMap() {
        return new LinkedHashMap<K, V>(capacity, 0.75f, true);
    }

    public synchronized V get(K key) {
        Integer freq = keyToFreqMap.get(key);
        if (Objects.isNull(freq)) {
            return null;
        }
        V value = freqToLruMap.get(freq).get(key);
        put(key, value);
        return value;
    }

    public synchronized void put(K key, V val) {
        Integer freq = keyToFreqMap.get(key);

        // entry for key is not found
        if (Objects.isNull(freq)) {

            // if freqToLruMap is empty (means it is the first key,value pair insert request)
            if (freqToLruMap.isEmpty()) {
                LinkedHashMap<K, V> lruMap = getNewLinkedHashMap();
                lruMap.put(key, val);
                freqToLruMap.put(1, lruMap);
                keyToFreqMap.put(key, 1);
                return;
            }

            /* cache is full need to evict
            least recent key,value pair having minimum  freq needs to be removed to make space for new entry insert request
            */
            if (keyToFreqMap.size() == capacity) {
                Map.Entry<Integer, LinkedHashMap<K, V>> minFreqEntry = freqToLruMap.firstEntry();
                Integer minFreq = minFreqEntry.getKey();
                LinkedHashMap<K, V> minFreqLruMap = minFreqEntry.getValue();
                K leastRecentKey = minFreqLruMap.entrySet().iterator().next().getKey();
                minFreqLruMap.remove(leastRecentKey);
                keyToFreqMap.remove(leastRecentKey);
                // clear the lru map at freq if it got empty after removal
                if (minFreqLruMap.isEmpty()) {
                    freqToLruMap.remove(minFreq);
                }
            }
            // insert new key with at freq = 1
            LinkedHashMap<K, V> freqOneMap = freqToLruMap.get(1);
            if (Objects.isNull(freqOneMap)) {
                freqOneMap = getNewLinkedHashMap();
            }
            freqOneMap.put(key, val);
            freqToLruMap.put(1, freqOneMap);
            keyToFreqMap.put(key, 1);
            /* inserted key value pair at freq = 1*/
        }

        // it is already present so just need to remove the key from 'freq' level and promote it to 'freq+1' with new value
        else {
            freqToLruMap.get(freq).remove(key);
            if (freqToLruMap.get(freq).isEmpty()) {
                freqToLruMap.remove(freq);
            }
            LinkedHashMap<K, V> freqPlusOneLruMap = freqToLruMap.get(freq + 1);
            if (Objects.isNull(freqPlusOneLruMap)) {
                freqPlusOneLruMap = getNewLinkedHashMap();
            }
            freqPlusOneLruMap.put(key, val);
            freqToLruMap.put(freq + 1, freqPlusOneLruMap);
            keyToFreqMap.put(key, freq + 1);
        }
    }

    public void showCache() {
        System.out.println("--------------CACHE PRINTED BELOW--------");
        for (Map.Entry<Integer, LinkedHashMap<K, V>> entry : freqToLruMap.entrySet()) {
            Integer freq = entry.getKey();
            LinkedHashMap<K, V> lruMap = entry.getValue();
            System.out.print("FREQ:" + freq + " -> ");
            System.out.println(lruMap);
        }
    }

    public static void main(String[] args) {
        LfuCache<Integer, String> lfu = new LfuCache<>(3);
        lfu.put(1, "1");
        lfu.put(2, "1");
        lfu.put(1, "3");
        lfu.put(3, "4");
        lfu.put(4, "5");
        System.out.println("lfu.get(3): " + lfu.get(3));
        lfu.showCache();

        lfu = new LfuCache<>(2);
        lfu.put(1, "1");
        lfu.put(2, "2");
        lfu.put(1, "1");
        lfu.put(2, "2");
        lfu.put(1, "1");
        lfu.put(2, "1000000");
        System.out.println("lfu.get(2): " + lfu.get(2));
        System.out.println("lfu.get(100): " + lfu.get(100));
        lfu.showCache();


    }
}
