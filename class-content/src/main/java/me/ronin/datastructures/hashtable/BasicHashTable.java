package me.ronin.datastructures.hashtable;

public class BasicHashTable {

    private HTEntry[] table;
    private int size;

    public BasicHashTable(int capacity) {
        table = new HTEntry[capacity];
        this.size = 0;
    }

    //Adds value of each character to make the hash of the key
    private int hash(String key) {
        int hashValue = 0;
        for (int i = 0; i < key.length(); i++) {
            hashValue += key.charAt(i);
        }
        return Math.abs(hashValue % table.length);
        //return key.hashCode() % table.length;
    }

    //Put (add)
    public void put(String key, Object value){
        //check for room to add
        if(size == table.length) throw new Error("Hash table full!");

        //make sure value is valid
        if(value.equals("DELETED")) throw new Error("DONT ENTER A DELETED VALUE");

        //get the hashed index based on key
        int index = hash(key);
        int startIndex = index;

        //check the hashed index: if not occupied insert
        //if occupied, probe to next valid location and repeat
        //also stop if we return to the start index
        while(table[index] != null && !table[index].value.equals("DELETED")){
            //if we find key again, overwrite value at that position
            if(table[index].key.equals(key)){
                table[index].value = value;
                return;
            }

            //linear probe
            index = ++index % table.length;

            if(index == startIndex) {
                throw new Error("No empty slot");
            }
        }

        //insert into table, my new item;
        table[index] = new HTEntry(key, value);
        size++;
    }

    //Get (lookup)
    public Object get(String key){
        //get hash index based key
        int index = hash(key);
        int startIndex = index;
        //loop: while key not found and not null -> probe to next location
        while(table[index] != null){
            //handle key being found
            if(!table[index].value.equals("DELETED") && table[index].key.equals(key)){
                return table[index].value;
            }
            //linear probe
            index = ++index % table.length;

            if(index == startIndex) {
                break;
            }
        }
        //if no key found, return failed
        return null;
    }

    //Remove (delete)
    public void remove(String key){
        //get hash index based key
        int index = hash(key);
        int startIndex = index;
        //loop: while value of current index is not null -> probe to next location
        while(table[index] != null){
            //handle key being found
            if(!table[index].value.equals("DELETED") && table[index].key.equals(key)) {
                table[index].value = "DELETED";
                size--;
                return;
            }
            //linear probe
            index = ++index % table.length;

            if(index == startIndex) {
                 break;
            }
        }
        //if no key found, fail
        throw new Error("No value found");
    }
}
