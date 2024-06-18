package cache;

import java.util.*;

public class LfuCacheUsingPriorityQueue<K, V> {
    public static Integer ARRIVAL_TIME = 1;

    private class Node {
        private K key;
        private V value;
        private Integer frequency;
        private Integer arrivalTime;

        Node(K key, V value, Integer frequency, Integer arrivalTime) {
            this.key = key;
            this.value = value;
            this.frequency = frequency;
            this.arrivalTime = arrivalTime;
        }

        public Integer getFrequency() {
            return frequency;
        }

        public V getValue() {
            return value;
        }

        public Integer getArrivalTime() {
            return arrivalTime;
        }


        public K getKey() {
            return key;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    ", frequency=" + frequency +
                    ", arrivalTime=" + arrivalTime +
                    '}';
        }
    }

    private class SortOrder implements Comparator<Node> {
        @Override
        public int compare(Node n1, Node n2) {
            if (n1.getFrequency().equals(n2.getFrequency())) {
                return n1.getArrivalTime().compareTo(n2.getArrivalTime());
            }
            return n1.getFrequency().compareTo(n2.getFrequency());
        }
    }

    private int capacity;
    private HashMap<K, Node> keyToNodeMap;
    private PriorityQueue<Node> pqFreqOrdered;

    public LfuCacheUsingPriorityQueue(int capacity) {
        this.capacity = capacity;
        keyToNodeMap = new HashMap<>();
        pqFreqOrdered = new PriorityQueue<>(new SortOrder());
    }


    public V get(K key) {
        Node node = keyToNodeMap.get(key);
        if (Objects.isNull(node)) {
            return null;
        } else {
            V value = node.getValue();
            put(key, value);
            return value;
        }
    }

    public void put(K key, V value) {
        Node node = keyToNodeMap.get(key);
        Node newNode;
        if (Objects.isNull(node)) {
            newNode = new Node(key, value, 1, ARRIVAL_TIME++);
            if (capacity == keyToNodeMap.size()) {
                Node removedNode = pqFreqOrdered.poll();
                keyToNodeMap.remove(removedNode.getKey());
            }
        } else {
            newNode = new Node(key, value, node.getFrequency() + 1, ARRIVAL_TIME++);
            keyToNodeMap.remove(key);
            pqFreqOrdered.remove(node);
        }
        keyToNodeMap.put(key, newNode);
        pqFreqOrdered.add(newNode);
    }

    public void showPq(){
        PriorityQueue<Node> pqCopy = new PriorityQueue<>(new SortOrder());
        Iterator<Node> it = pqFreqOrdered.iterator();
        while(it.hasNext()){
            pqCopy.add(it.next());
        }
        System.out.println("****");
        while(!pqCopy.isEmpty()){
            System.out.println(pqCopy.poll());
        }
    }

    public static void main(String[] args){
        LfuCacheUsingPriorityQueue<Integer,String> lfu = new LfuCacheUsingPriorityQueue(3);
        lfu.put(1,"NAME");
        lfu.put(1,"NAME1");
        lfu.put(2,"NAME2");
        lfu.put(3,"NAME3");
        lfu.put(4,"NAME4");
        lfu.showPq();

    }

}







