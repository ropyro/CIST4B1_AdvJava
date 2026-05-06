package me.ronin.datastructures.graph;

import me.ronin.datastructures.Queue;

import java.util.*;

public class GraphAdjList<T extends Comparable> {

    private int size;
    private Map<T, List<Edge<T>>> adjLists;

    public GraphAdjList() {
        this.size = 0;
        adjLists = new HashMap<>();
    }


    //unweighted
    //cyclic
    //complex
    //
    public void addEdge(T source, T destination, int weight){
        var currEdges = adjLists.get(source);
        if(currEdges == null){
            List<Edge<T>> adjList = new ArrayList<>();
            adjList.add(new Edge<>(weight, source, destination));
            adjLists.put(source, adjList);
        }else{
            adjLists.get(source).add(new Edge<>(weight, source, destination));
        }
        size++;
    }


    public HashMap<Edge, Integer> djikstra(Edge startEdge){
        HashMap<Edge, Integer> distances = new HashMap<>();
        HashSet<Edge> visited = new HashSet<>();

        distances.put(startEdge, 0);

        PriorityQueue<Edge> pq = new PriorityQueue<>(adjLists.size(), Comparator.comparingInt(e -> e.weight));
        pq.add(startEdge);

        while(!pq.isEmpty()){
            Edge curr = pq.poll();

            if(visited.contains(curr)){
                continue;
            }

            visited.add(curr);

            List<Edge<T>> neighbors = adjLists.get(curr);
            for(Edge n : neighbors){
                T dest = (T) n.destination;
                int weight = n.weight;
                int tentDist = distances.get(curr) + weight;

                if(tentDist < distances.get(dest)){
                    distances.put(n, tentDist);
                    pq.add(n);
                }
            }
        }
        return distances;
    }

//    public PriorityQueue<Edge> djikstra(T source){
//        PriorityQueue<Edge> distances = new PriorityQueue<>();
//        Edge shortest = null;
//        for(Edge neighbor : adjLists.get(source)){
//            if(shortest != null && neighbor.getWeight() < shortest.getWeight()){
//                shortest = neighbor;
//            }
//        }
//        for(Edge neighbor : adjLists.get(source)){
//            neighbor.setWeight(Integer.MAX_VALUE);
//            distances.offer(neighbor);
//        }
//        distances.offer(shortest);
//    }

    public List<Edge<T>> kruskal(){
        List<Edge<T>> allEdges = new ArrayList<>();
        for(List<Edge<T>> edgeList : adjLists.values()){
            allEdges.addAll(edgeList);
        }
        //sort the edge lists
        Collections.sort(allEdges);

        //create our set of vertexes
        Map<T, T> parent = new HashMap<>();
        for (T vertex : adjLists.keySet()) {
            parent.put(vertex, vertex);
        }

        List<Edge<T>> mst = new ArrayList<>();

        //iterate through the sorted edges
        for(Edge<T> edge : allEdges){
            T sourceRoot = find(parent, edge.getSource());
            T destinationRoot = find(parent, edge.getDestination());

            //see if the edge would cause a cycle
            if(sourceRoot.equals(destinationRoot)){

            }else{
                //if not union the two sets
                mst.add(edge);
                parent.put(sourceRoot, destinationRoot);
            }
        }
        return mst;
    }

    private T find(Map<T, T> parent, T vertex){
        //can our sets be joined together
        if(parent.get(vertex).equals(vertex)) return vertex;
        return find(parent, parent.get(vertex));
    }


    public Map<T, Integer> dijkstra(T source) {
        Map<T, Integer> distances = new HashMap<>();
        adjLists.keySet().forEach(node -> distances.put(node, Integer.MAX_VALUE));
        distances.put(source, 0);

        PriorityQueue<T> pq = new PriorityQueue<>();
        distances.keySet().forEach(k -> pq.offer(k));
        pq.add(source);

        while (!pq.isEmpty()) {
            T currSource = pq.poll();

            for (Edge<T> edge : adjLists.get(currSource)) {
                T currDest = edge.getDestination();
                int newDist = distances.get(currSource) + edge.getWeight();

                if (newDist <= distances.get(currDest)) {
                    pq.remove(currDest);
                    distances.put(currDest, newDist);
                    pq.add(currDest);
                }
            }
        }
        return distances;
    }

    //my imp
    public boolean breadthFirstSearch(T source, T destination){
        if(source.equals(destination)) return true;

        Queue<T> nodesToVisit = new Queue<>(adjLists.size());
        Set<T> visited = new HashSet<>();

        nodesToVisit.enqueue(source);
        visited.add(source);

        while(!nodesToVisit.isEmpty()){
            T curr = nodesToVisit.dequeue();
            System.out.println(curr);
            List<T> neighbors = adjLists.get(curr).stream().map(edge -> edge.getDestination()).toList();

            if(neighbors == null) continue;

            for(T neighbor : neighbors){
                if(neighbor.equals(destination)){
                    return true;
                }
                if(!visited.contains(neighbor)){
                    visited.add(neighbor);
                    nodesToVisit.enqueue(neighbor);
                }
            }
        }
        return false;
    }

    //prof imp
    public void bfs(T startValue){
        Set<T> visited = new HashSet<>();
        List<T> queue = new ArrayList<>();

        visited.add(startValue);
        queue.add(startValue);

        while(!queue.isEmpty()){
            T curr = queue.remove(0);
            System.out.print(curr + " ");

            for(T neighbor : adjLists.get(curr).stream().map(edge -> edge.getDestination()).toList()){
                if(!visited.contains(neighbor)){
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
    }


    public void dfs(T source){
        Stack<T> stack = new Stack<>();
        Set<T> visited = new HashSet<>();

        stack.push(source);

        while(!stack.isEmpty()){
            T curr = stack.pop();
            if(!visited.contains(curr)){
                visited.add(curr);
                System.out.print(curr + " ");
            }
            for(T neighbor : adjLists.get(curr).stream().map(edge -> edge.getDestination()).toList()){
                stack.push(neighbor);
            }
        }
    }

    private static class Edge<T> implements Comparable<Edge<T>>{
        private T source;
        private T destination;
        private int weight;
        public Edge(int weight, T destination){
           this(weight, null, destination);
        }

        public Edge(int weight, T source, T destination){
            this.weight = weight;
            this.source = source;
            this.destination = destination;
        }

        public T getSource(){
            return source;
        }

        public T getDestination(){
            return destination;
        }

        public int getWeight(){
            return weight;
        }

        public void setWeight(int weight){
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge<T> o) {
            return getWeight() - o.getWeight();
        }
    }

}
