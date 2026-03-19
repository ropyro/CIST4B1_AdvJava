package me.ronin.datastructures.hashtable;

import java.util.Arrays;

public class DoubleHashTable {

    private HTEntry[] table;
    private int size;
    private double loadFactor; // result of: size / table.length (not integer division)

    public DoubleHashTable(int capacity) {
        table = new HTEntry[capacity];
        size = 0;
        loadFactor = 0;
    }

    //Adds value of each character to make the hash of the key
    private int hash(String key) {
        int hashValue = 0;
        for (int i = 0; i < key.length(); i++) {
            hashValue += key.charAt(i);
        }
        return Math.abs(hashValue % table.length);
    }

    private int hash2(String key) {
        return Math.abs(key.hashCode());
    }

    //resize when load factor is >0.7
    //create new array
    //rehash every item from our old array to our new one
    private void resize(int newCapacity){
        if(loadFactor < 0.7) return;

        HTEntry[] oldTable = Arrays.copyOf(this.table, this.table.length);
        table = new HTEntry[newCapacity];

        for(HTEntry entry : oldTable){
            if(entry != null)
                put(entry.key, entry.value);
        }

        loadFactor = size / newCapacity;
    }

    //Put (add)
    public void put(String key, Object value) {
        //check for room to add
        if (loadFactor > 0.7)
            resize(size*2);

        //make sure value is valid
        if (value.equals("DELETED")) throw new Error("DONT ENTER A DELETED VALUE");

        //get the hashed index based on key
        int index1 = hash(key);
        int index2 = hash2(key);
        int startIndex = index1;
        //probe count
        int i = 0;

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
            //index1 = ++inde1x % table.length;

            //double hash probe
            index1 = (startIndex + i*index2) % table.length;
            i++;

            if (index1 == startIndex) {
                throw new Error("No empty slot");
            }
        }

        //insert into table, my new item;
        table[index1] = new HTEntry(key, value);
        size++;
        loadFactor = size/table.length;
    }

    //Get (lookup)
    public Object get(String key) {
        //get hash index based key
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
        //get hash index based key
        int index = hash(key);
        int startIndex = index;
        //loop: while value of current index is not null -> probe to next location
        while (table[index] != null) {
            //handle key being found
            if (!table[index].value.equals("DELETED") && table[index].key.equals(key)) {
                table[index].value = "DELETED";
                size--;
                loadFactor = size/table.length;
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
}
