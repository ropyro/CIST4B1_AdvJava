package me.ronin.datastructures;

public class BinarySearchTree {

    public Node root;

    public BinarySearchTree() {
        this.root = null;
    }

    /**
     * Recursive method
     * @param value
     */
    public void add(int value) {
        root = add(value, root);
    }

    private Node add(int value, Node current) {
        if (current == null) {
            return new Node(value);
        }
        if (value <= current.data) {
            current.left = add(value, current.left);
        } else {
            current.right = add(value, current.right);
        }
        return current;
    }

    public void addIterative(int value) {
        //case 1: root is empty
        if (root == null) {
            root = new Node(value);
            return;
        }

        //case 2: root is not empty
        Node current = root;

        //navigate to the correct leaf
        while (current.left != null || current.right != null) {
            if (value <= current.data) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        //choose which child to add to the leaf
        if (value <= current.data) {
            current.left = new Node(value);
        } else {
            current.right = new Node(value);
        }
    }

    class Node {
        int data;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }
}
