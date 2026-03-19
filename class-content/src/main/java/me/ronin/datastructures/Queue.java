package me.ronin.datastructures;

public class Queue<T> {
    private Object[] data;
    private int size;
    private int head, tail;

    public Queue(int size) {
        this.size = size;
        this.data = new Object[size];
        size = 0;
        head = 0;
        tail = 0;
    }

    public void enqueue(T item) {
        if (this.size == this.data.length) {
            Object[] newData = new Object[this.size * 2];
            for (int i = 0; i < this.size; i++) {
                newData[i] = this.data[i];
            }
            newData[size] = item;
        } else {
            this.data[this.tail] = item;
            this.tail = (this.tail + 1) % data.length;
        }
    }

    public T dequeue() {
        if (size == 0) {
            System.out.println("Error");
            return null;
        } else {
            T retValue = (T) this.data[this.head];
            this.data[this.head] = null;
            this.head = (this.head + 1) % data.length;
            this.size--;
            return retValue;
        }
    }
}
