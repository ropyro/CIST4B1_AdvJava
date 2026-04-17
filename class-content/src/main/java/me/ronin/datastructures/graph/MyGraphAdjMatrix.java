package me.ronin.datastructures.graph;

import java.util.HashMap;
import java.util.Map;

public class MyGraphAdjMatrix<T> {
    private Map<T, Integer> vertextIndices;
    private int[][] adjMatrix;
    private int size;

    public MyGraphAdjMatrix(int maxSize) {
        this.size = 0;
        adjMatrix = new int[maxSize][maxSize];
        vertextIndices = new HashMap<>();
    }

    public void addEdge(T source, T destination){
        if(!vertextIndices.containsKey(source)){
            vertextIndices.put(source, size++);
        }
        if(!vertextIndices.containsKey(destination)){
            vertextIndices.put(destination, size++);
        }
        adjMatrix[vertextIndices.get(source)][vertextIndices.get(destination)] = 1;
    }
}
