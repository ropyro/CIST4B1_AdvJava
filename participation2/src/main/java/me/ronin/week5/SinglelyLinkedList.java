package me.ronin.week5;

import java.util.Stack;

public class SinglelyLinkedList<T> {

    public Node<T> head;
    public int length;

    public SinglelyLinkedList() {
        this.head = null;
        this.length = 0;

    }

    // -------- INSERTS --------
    public void append(T data) {
        if (head == null) {
            this.head = new Node<>(data);
            this.length += 1;
        } else {
            Node<T> curr = this.head;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = new Node<>(data);
            this.length += 1;
        }
    }

    public void prepend(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = this.head;
        this.head = newNode;
        this.length += 1;
    }

    public void insertInMiddle(T data) {
        //Find middle and traverse to middle node
        int middle = length / 2;
        Node<T> curr = this.head;
        for (int i = 0; i < middle; i++) {
            curr = curr.next;
        }

        //Insert new node
        Node<T> newNode = new Node<>(data);
        newNode.next = curr.next;
        curr.next = newNode;

        //increment length
        this.length += 1;
    }

    public void insertAt(T data, int index) {
        //Option 1: if(index < 0 || index > length) return;
        //Option 2: python trick -1 index is the last element
        if (index < 0) index = this.length + index;
        if (index < 0 || index >= this.length) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            prepend(data);
            return;
        }

        Node<T> newNode = new Node<>(data);
        Node<T> curr = this.head;
        for (int i = 0; i < index - 1; i++) {
            curr = curr.next;
        }
        newNode.next = curr.next;
        curr.next = newNode;
    }

    // ---- SEARCH ----

    public boolean search(T target) {
        if (this.head == null) return false;
        var curr = this.head;
        while (curr != null) {
            if (curr.data == target) return true;
            curr = curr.next;
        }
        return false;
    }

    // ---- UTILS ----
    public boolean isEmpty(){
        return this.head == null;
    }

    public int getLength(){
        return this.length;
    }

    // ---- REMOVE ----
    public T removeValue(T data) {
        if (this.head == null) {
            return null;
        }
        //remove head
        if (this.head.data.equals(data)) {
            T returnData = this.head.data;
            this.head = this.head.next;
            this.length--;
            return returnData;
        }
        //remove anywhere else
        Node<T> curr = this.head;
        while (curr.next != null) {
            if (curr.next.data.equals(data)) {
                T returnData = curr.next.data;
                curr.next = curr.next.next;
                this.length -= 1;
                return returnData;
            }
            curr = curr.next;
        }
        return null;
    }

    public T removeIndex(int index) {
        if (this.head == null) {
            return null;
        }
        if (index < 0) index = this.length + index;
        if (index < 0 || index >= this.length) {
            throw new IndexOutOfBoundsException();
        }
        //remove head
        if (index == 0) {
            T returnData = this.head.data;
            this.head = this.head.next;
            this.length--;
            return returnData;
        }
        //remove anywhere else
        Node<T> curr = this.head;
        for (int i = 0; i < index - 1; i++) {
            curr = curr.next;
        }
        T returnData = curr.next.data;
        curr.next = curr.next.next;
        this.length -= 1;
        return returnData;
    }

    // ---- PRINT ----
    public void print() {
        var curr = this.head;
        while (curr.next != null) {
            System.out.print(curr.data + " -> ");
            curr = curr.next;
        }
        System.out.println(curr.data);
    }
}
