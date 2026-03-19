package me.ronin.datastructures;

public class Tree {

    public Node root;
    public int length;

    public Tree(){
        this.root = null;
        this.length = 0;
    }


    class Node {
        int data;
        Node[] children;

        public Node(int data, int numChildren){
            this.data = data;
            children = new Node[numChildren];
        }
    }
}
