package me.ronin.datastructures.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GraphAdjMatrix<T> {

    private List<List<Integer>> matrix;
    private HashMap<T, Integer> vertices;

    public GraphAdjMatrix(){
        vertices = new HashMap<>();
        matrix = new ArrayList<>();
    }

    public void addVertex(T vertex){
        vertices.put(vertex, vertices.size());
        for(List<Integer> row : matrix){
            row.add(0);
        }
        List<Integer> newRow = new ArrayList<>();
        for(int i = 0; i < vertices.size(); i++){
            newRow.add(0);
        }
        matrix.add(newRow);
    }

    public void addEdge(T source, T destination){
        addEdge(source, destination, 1);
    }

    public void addEdge(T source, T destination, int weight){
        int indSrc = vertices.get(source);
        int indDest = vertices.get(destination);

        if(indSrc == -1 || indDest == -1){
            System.err.println("Error, source or destination does not exist");
            return;
        }

        matrix.get(indSrc).set(indDest, weight);

        //makes it undirected
        matrix.get(indDest).set(indSrc, weight);
    }
}
