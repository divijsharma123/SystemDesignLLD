package cache;

import java.security.PublicKey;
import java.util.LinkedHashMap;
import java.util.Objects;

public class LruCacheV2<K,V> {

    LinkedHashMap<K,V> mp;
    int capacity;
    LruCacheV2(int capacity){
      this.capacity = capacity;
      mp = new LinkedHashMap<>(capacity,0.75f,true);
    }

    public V get(K key){
        return (V)mp.get(key);
    }
    public void put(K key,V value){
       if(mp.size()==capacity){
           if(!mp.containsKey(key)){
               K keyRemoved = mp.entrySet().iterator().next().getKey();
               mp.remove(keyRemoved);
           }
       }
       mp.put(key,value);
    }
    public void showCache(){
        mp.forEach((key,value)->{System.out.println("(key:" + key + " ->value:" + value);});
        System.out.println("*******************");
    }
    public static void main(String[] args){
        LruCacheV2<Integer,String> lru = new LruCacheV2<>(4);
        lru.put(1,"1");
        lru.put(3,"3");
        lru.put(2,"2");
        lru.put(4,"4");
        lru.showCache();
        lru.put(5,"5");
        lru.showCache();
        lru.put(4,"6");
        lru.showCache();

    }
}
