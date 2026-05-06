package me.ronin.datastructures.graph;

import me.ronin.datastructures.Queue;

import java.util.*;

public class GraphAdjMatrix<T> {

    private List<List<Integer>> matrix;
    private HashMap<T, Integer> verticesIndices;

    public GraphAdjMatrix(){
        verticesIndices = new HashMap<>();
        matrix = new ArrayList<>();
    }

    public void addVertex(T vertex){
        verticesIndices.put(vertex, verticesIndices.size());
        for(List<Integer> row : matrix){
            row.add(0);
        }
        List<Integer> newRow = new ArrayList<>();
        for(int i = 0; i < verticesIndices.size(); i++){
            newRow.add(0);
        }
        matrix.add(newRow);
    }

    public void addEdge(T source, T destination){
        addEdge(source, destination, 1);
    }

    public void addEdge(T source, T destination, int weight){
        int indSrc = verticesIndices.get(source);
        int indDest = verticesIndices.get(destination);

        if(indSrc == -1 || indDest == -1){
            System.err.println("Error, source or destination does not exist");
            return;
        }

        matrix.get(indSrc).set(indDest, weight);

        //makes it undirected
        matrix.get(indDest).set(indSrc, weight);
    }



    public void dfs(T source){
        Stack<T> stack = new Stack<>();
        boolean[] arr = new boolean[verticesIndices.size()];

        stack.push(source);

        while(!stack.isEmpty()){
            T curr = stack.pop();
            int currIndex = verticesIndices.get(curr);
            if(!arr[currIndex]){
                arr[currIndex] = true;
                System.out.print(curr + " ");
            }
            List<Integer> neighborIndices = matrix.get(currIndex);
            for(T vertex : verticesIndices.keySet()){
                if(neighborIndices.contains(verticesIndices.get(vertex))){
                    stack.push(vertex);
                }
            }
        }
    }


    public boolean breadthFirstSearch(T source, T destination){
        Queue<T> nodesToVisit = new Queue<>(verticesIndices.size());
        Set<T> visited = new HashSet<>();

        nodesToVisit.enqueue(source);

        while(!nodesToVisit.isEmpty()){
            List<Integer> neighbors = matrix.get(verticesIndices.get(nodesToVisit.dequeue()));

            for(int neighborIndices : neighbors){
                //erm this hurting my brain
            }
        }
        return false;
    }
}
