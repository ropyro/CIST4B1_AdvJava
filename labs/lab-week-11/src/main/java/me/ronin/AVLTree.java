package me.ronin;

public class AVLTree<T extends Comparable<T>> {

    private Node<T> root;

    public AVLTree() {
        this.root = null;
    }

    // ---- ADD ----
    public void add(T value) {
        root = add(value, root);
    }

    private Node<T> add(T value, Node<T> current) {
        if (current == null) {
            return new Node<>(value);
        }
        if (value.compareTo(current.data) <= 0) {
            current.left = add(value, current.left);
        } else {
            current.right = add(value, current.right);
        }

        current.height = height(current);
        int balance = getBalance(current);

        //left heavy
        if(balance > 1 && value.compareTo(current.left.data) < 0){
            return rightRotate(current);
        }
        //right heavy
        if(balance < -1 && value.compareTo(current.right.data) > 0){
            return leftRotate(current);
        }
        //left right
        if(balance > 1 && value.compareTo(current.left.data) > 0){
            return leftRightRotate(current);
        }
        //right left
        if(balance < -1 && value.compareTo(current.right.data) < 0){
            return rightLeftRotate(current);
        }

        return current;
    }


    // ---- SEARCH ----

    public boolean searchRecursive(T value) {
        return searchRecursive(value, root);
    }

    private boolean searchRecursive(T value, Node<T> current) {
        if (current == null) return false;
        if (current.data.equals(value)) return true;
        if (value.compareTo(current.data) < 0)
            return searchRecursive(value, current.left);
        else
            return searchRecursive(value, current.right);
    }

    // ---- DELETE ----

    public void delete(T value) {
        root = delete(value, root);
    }

    public Node<T> delete(T value, Node<T> current) {
        if (current == null) return null;

        if (current.data.equals(value)) {
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
            Node<T> smallest = findSmallestNode(current.right);
            //2: replace current with smallest value
            current.data = smallest.data;
            //3: recursively delete the old smallest value
            current.right = delete(smallest.data, current.right);
        } else if (value.compareTo(current.data) < 0) {
            current.left = delete(value, current.left);
        } else {
            current.right = delete(value, current.right);
        }

        current.height = height(current);
        int balance = getBalance(current);

        //left heavy
        if(balance > 1 && getBalance(current.left) >= 0){
            return rightRotate(current);
        }
        //right heavy
        if(balance < -1 && getBalance(current.right) <= 0){
            return leftRotate(current);
        }
        //left right
        if(balance > 1 && getBalance(current.left) < 0){
            return leftRightRotate(current);
        }
        //right left
        if(balance < -1 && getBalance(current.right) > 0){
            return rightLeftRotate(current);
        }

        return current;
    }


    // ---- HELPERS -----
    public Node<T> findSmallestNode(Node<T> current){
        if(current.left == null){
            return current;
        }
        return findSmallestNode(current.left);
    }

    private T findMinValue(Node<T> subRoot){
        while(subRoot.left != null){
            subRoot = subRoot.left;
        }
        return subRoot.data;
    }

    // ---- traversals -----
    public void inorder(){
        inorder(root);
    }
    public void inorder(Node<T> current){
        if(current != null){
            inorder(current.left);
            System.out.println(current.data + " " + getBalance(current));
            inorder(current.right);
        }
    }

    public void preorder(Node<T> current){
        if(current != null){
            System.out.println(current.data);
            preorder(current.left);
            preorder(current.right);
        }
    }

    public void postorder(Node<T> current){
        if(current != null){
            postorder(current.left);
            postorder(current.right);
            System.out.println(current.data);
        }
    }


    // ---- ROTATIONS ----
    private int height(Node<T> current){
        if(current == null) return -1;
        if(current.left == null && current.right == null) return 0;
        return Math.max(height(current.left), height(current.right)) + 1;
    }

    public int getBalance(Node<T> node){
        if(node == null) return 0;
        return height(node.left) - height(node.right);
    }

    private Node<T> rightRotate(Node<T> oldRoot){
        //rotate
        Node<T> newRoot = oldRoot.left;
        oldRoot.left = newRoot.right;
        newRoot.right = oldRoot;

        //update heights
        oldRoot.height = height(oldRoot);
        newRoot.height = height(newRoot);

        //return new root
        return newRoot;
    }

    private Node<T> leftRotate(Node<T> oldRoot){
        //rotate
        Node<T> newRoot = oldRoot.right;
        oldRoot.right = newRoot.left;
        newRoot.left = oldRoot;

        //update heights
        oldRoot.height = height(oldRoot);
        newRoot.height = height(newRoot);

        //return new root
        return newRoot;
    }

    private Node<T> leftRightRotate(Node<T> oldRoot){
        oldRoot.left = leftRotate(oldRoot.left);
        return rightRotate(oldRoot);
    }

    private Node<T> rightLeftRotate(Node<T> oldRoot){
        oldRoot.right = rightRotate(oldRoot.right);
        return leftRotate(oldRoot);
    }

    private static class Node<T extends Comparable<T>> {
        T data;
        int height;
        Node<T> left;
        Node<T> right;

        Node(T data) {
            this.data = data;
            this.height = 0;
            this.left = null;
            this.right = null;
        }
    }
}
