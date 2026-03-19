package me.ronin.datastructures;

public class NodePQ<T> {
    public T data;
    public NodePQ<T> left;
    public NodePQ<T> right;

    public NodePQ(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}
