package me.ronin.datastructures.graph;

import me.ronin.datastructures.hashtable.HashTable;

public class MyGraphAdjList {

    //ds to represent the adjacency list
    private HashTable<Integer, HashTable<Integer, Integer>> lists;

    //constructor with whatever -.-

    public MyGraphAdjList(){
        lists = new HashTable<>(10);
    }

    //add edge(int source, int destination)

    public void edge(int source, int destination){
        HashTable<Integer, Integer> adjList = lists.get(source);
        if(adjList != null){
            adjList.put(destination, destination);
        }else{
            HashTable<Integer, Integer> adjencents = new HashTable<>(10);
            adjencents.put(destination, 1);
            lists.put(source, adjencents);
        }
    }
}
