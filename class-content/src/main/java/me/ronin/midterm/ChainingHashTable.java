package me.ronin.midterm;

import java.util.Arrays;

public class ChainingHashTable {
    private HTChainEntry[] table;
    private int size;

    public ChainingHashTable(int capacity) {
        table = new HTChainEntry[capacity];
        size = 0;
    }

    private int hash(String key) {
        return Math.abs(key.hashCode() % table.length);
    }

    private void resize(int newCapacity){
        HTChainEntry[] oldTable = Arrays.copyOf(this.table, this.table.length);
        table = new HTChainEntry[newCapacity];
        size = 0;

        for(HTChainEntry entry : oldTable){
            if(entry != null){
                HTChainEntry curr = entry;
                while(curr != null){
                    insert(curr.key, curr.value);
                    curr = curr.next;
                }
            }
        }
    }

    public void insert(String key, Object value) {
        //check load factor and resize if greater than 0.7
        if((double)size/table.length > 0.7) resize(table.length*2);

        //get the hashed index based on key
        int index = hash(key);

        if(table[index] == null){
            table[index] = new HTChainEntry(key, value);
            size++;
            return;
        }

        HTChainEntry curr = table[index];
        while(curr != null){
            if(curr.key.equals(key)){
                curr.value = value;
                return;
            }
            if(curr.next == null){
                curr.next = new HTChainEntry(key, value);
                size++;
                return;
            }
            curr = curr.next;
        }
    }

    public Object search(String key) {
        //get hash index based key
        int index = hash(key);

        if(table[index] != null){
            HTChainEntry curr = table[index];
            while(curr != null){
                if(curr.key.equals(key)){
                    return curr.value;
                }
                curr = curr.next;
            }
        }

        //if no key found, return failed
        return null;
    }

    //Remove (delete)
    public void remove(String key) {
        //get hash index based key
        int index = hash(key);

        if(table[index] != null){
            HTChainEntry prev = null;
            HTChainEntry curr = table[index];
            while(curr != null){
                if(curr.key.equals(key)){
                    if(prev != null){
                        prev.next = curr.next;
                    }else{
                        table[index] = curr.next;
                    }
                    size--;
                    return;
                }
                prev = curr;
                curr = curr.next;
            }
        }

        //if no key found, fail
        System.out.println("No value found for key: " + key);
    }

     private class HTChainEntry {
        public String key;
        public Object value;
        public HTChainEntry next;

        public HTChainEntry(String key, Object value){
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }
}
