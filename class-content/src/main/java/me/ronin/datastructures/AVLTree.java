package me.ronin.datastructures;

public class AVLTree {

    public Node root;
    private int height;

    public AVLTree() {
        this.root = null;
        height = 0;
    }

    // ---- ADD ----
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


    // ---- SEARCH ----
    public boolean searchIterative(int value) {
        Node curr = root;
        while (curr != null) {
            if (curr.data == value) {
                return true;
            }
            if (value < curr.data) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return false;
    }

    public boolean searchRecursive(int value) {
        return searchRecursive(value, root);
    }

    private boolean searchRecursive(int value, Node current) {
        if (current == null) return false;
        if (current.data == value) return true;
        if (value < current.data)
            return searchRecursive(value, current.left);
        else
            return searchRecursive(value, current.right);
    }

    // ---- DELETE ----

    public void delete(int value) {
        root = delete(value, root);
    }

    public Node delete(int value, Node current) {
        if (current == null) return null;

        if (current.data == value) {
            //case 1: no children
            if (current.left == null && current.right == null)
                return null;

            //case 2: one child
            if (current.right == null)
                return current.left;

            if (current.left == null)
                return current.right;

            //case 3: two children
            //1: find smallest value in right subtree
            Node smallest = findSmallestNode(current.right);
            //2: replace current with smallest value
            current.data = smallest.data;
            //3: recursively delete the old smallest value
            current.right = delete(smallest.data, current.right);
        } else if (value < current.data) {
            current.left = delete(value, current.left);
        } else {
            current.right = delete(value, current.right);
        }
        return current;
    }


    // ---- HELPERS -----
    public Node findSmallestNode(Node current){
        if(current.left == null){
            return current;
        }
        return findSmallestNode(current.left);
    }

    private int findMinValue(Node subRoot){
        while(subRoot.left != null){
            subRoot = subRoot.left;
        }
        return subRoot.data;
    }

    // ---- traversals -----
    public void inorder(Node current){
        if(current != null){
            inorder(current.left);
            System.out.println(current.data);
            inorder(current.right);
        }
    }

    public void preorder(Node current){
        if(current != null){
            System.out.println(current.data);
            preorder(current.left);
            preorder(current.right);
        }
    }

    public void postorder(Node current){
        if(current != null){
            postorder(current.left);
            postorder(current.right);
            System.out.println(current.data);
        }
    }


    // ---- ROTATIONS ----
    private int height(Node current){
        if(current == null) return -1;
        if(current.left == null && current.right == null) return 0;
        return Math.max(height(current.left), height(current.right)) + 1;
    }

    private Node rightRotate(Node oldRoot){
        //rotate
        Node newRoot = oldRoot.left;
        oldRoot.left = newRoot.right;
        newRoot.right = oldRoot;

        //update heights
        oldRoot.height = height(oldRoot);
        newRoot.height = height(newRoot);

        //return new root
        return newRoot;
    }

    private Node leftRotate(Node oldRoot){
        //rotate
        Node newRoot = oldRoot.right;
        oldRoot.right = newRoot.left;
        newRoot.left = oldRoot;

        //update heights
        oldRoot.height = height(oldRoot);
        newRoot.height = height(newRoot);

        //return new root
        return newRoot;
    }

    private Node leftRightRotate(Node oldRoot){
        oldRoot.left = leftRotate(oldRoot.left);
        return rightRotate(oldRoot.right);
    }

    private Node rightLeftRotate(Node oldRoot){
        oldRoot.right = rightRotate(oldRoot.right);
        return leftRotate(oldRoot.left);
    }

    private static class Node {
        int data, height;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
            this.height = 0;
            this.left = null;
            this.right = null;
        }
    }
}
