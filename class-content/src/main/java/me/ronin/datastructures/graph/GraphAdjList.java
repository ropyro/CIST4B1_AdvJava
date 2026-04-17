package me.ronin.datastructures.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphAdjList<T> {

    private int size;
    private Map<T, List<T>> adjLists;

    public GraphAdjList() {
        this.size = 0;
        adjLists = new HashMap<>();
    }


    //unweighted
    //cyclic
    //complex
    //
    public void addEdge(T source, T destination){
        var currEdges = adjLists.get(source);
        if(currEdges == null){
            List<T> adjList = new ArrayList<>();
            adjList.add(destination);
            adjLists.put(source, adjList);
        }else{
            adjLists.get(source).add(destination);
        }
        size++;
    }
}
