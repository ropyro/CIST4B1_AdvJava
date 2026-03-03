package me.ronin.week5;

public class LinkedListStack<T> {

    public Node<T> head;
    public int length;

    public LinkedListStack(){
        this.head = null;
        this.length = 0;

    }

    public void insert(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = this.head;
        this.head = newNode;
        this.length += 1;
    }

    public T pop(){
        if(this.isEmpty()){
            return null;
        }
        T data = this.head.data;
        this.head = this.head.next;
        this.length -= 1;
        return data;
    }

    public T peek(){
        if(this.head == null){
            return null;
        }
        return this.head.data;
    }

    public boolean isEmpty() {
        return length == 0;
    }
}

