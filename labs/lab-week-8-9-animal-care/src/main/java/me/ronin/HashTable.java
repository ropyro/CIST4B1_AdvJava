package me.ronin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Simple linear probe (open addressing) hashtable written in class with resizing
 * Small addition for this project was the key list. Thought about using a Set<> like hashmap,
 * but I have not worked with one so, I just used a List<>
 */
public class HashTable {

    private HTEntry[] table;
    private int size;
    private double loadFactor; // result of: size / table.length (not integer division)

    private List<String> keys;

    public HashTable(int capacity) {
        table = new HTEntry[capacity];
        size = 0;
        loadFactor = 0;
        keys = new ArrayList<>(capacity);
    }

    private int hash(String key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    //resize when load factor is >0.7
    //create new array
    //rehash every item from our old array to our new one
    private void resize(int newCapacity){
        if(loadFactor < 0.7) return;

        HTEntry[] oldTable = Arrays.copyOf(this.table, this.table.length);
        table = new HTEntry[newCapacity];

        //reset these values so that put() updates them properly
        this.size = 0;
        this.loadFactor = 0;
        this.keys.clear();

        for(HTEntry entry : oldTable){
            if(entry != null)
                put(entry.key, entry.value);
        }
    }

    //Put (add)
    public void put(String key, Object value) {
        put(key, value, false);
    }

    private void put(String key, Object value, boolean checkLoadFactor){
        //check for room to add
        if (checkLoadFactor && loadFactor > 0.7)
            resize(size*2);

        //make sure value is valid
        if (value.equals("DELETED")) throw new Error("DONT ENTER A DELETED VALUE");

        //get the hashed index based on key
        int index1 = hash(key);
        int startIndex = index1;

        //check the hashed index: if not occupied insert
        //if occupied, probe to next valid location and repeat
        //also stop if we return to the start index
        while (table[index1] != null && !table[index1].value.equals("DELETED")) {
            //if we find key again, overwrite value at that position
            if (table[index1].key.equals(key)) {
                table[index1].value = value;
                return;
            }

            //linear probe
            index1 = ++index1 % table.length;

            if (index1 == startIndex) {
                throw new Error("No empty slot");
            }
        }

        //insert into table, my new item;
        table[index1] = new HTEntry(key, value);
        //save key for key set function
        keys.add(key);
        size++;
        loadFactor = (double)size/table.length;
    }

    //Get (lookup)
    public Object get(String key) {
        //get the hashed index based on key
        int index = hash(key);
        int startIndex = index;

        //loop: while key not found and not null -> probe to next location
        while (table[index] != null) {
            //handle key being found
            if (!table[index].value.equals("DELETED") && table[index].key.equals(key)) {
                return table[index].value;
            }

            //linear probe
            index = ++index % table.length;

            if (index == startIndex) {
                break;
            }
        }
        //if no key found, return failed
        return null;
    }

    //Remove (delete)
    public void remove(String key) {
        //get the hashed index based on key
        int index = hash(key);
        int startIndex = index;

        //loop: while value of current index is not null -> probe to next location
        while (table[index] != null) {
            //handle key being found
            if (!table[index].value.equals("DELETED") && table[index].key.equals(key)) {
                table[index].value = "DELETED";
                keys.remove(key);
                size--;
                loadFactor = (double)size/table.length;
                return;
            }

            //linear probe
            index = ++index % table.length;

            if (index == startIndex) {
                break;
            }
        }
        //if no key found, fail
        throw new Error("No value found");
    }

    public List<String> getKeys(){
        return keys;
    }

    private static class HTEntry {

        public String key;
        public Object value;

        public HTEntry(String key, Object value){
            this.key = key;
            this.value = value;
        }
    }
}
