package me.ronin.datastructures;

public class Stack<T> {

    public int maxSize;
    public int currSize;
    public Object[] data;

    public Stack(int maxSize) {
        this.maxSize = maxSize;
        data = new Object[maxSize];
        this.currSize = 0;
    }

    public void push(T item) {
        if (!this.isFull()) {
            data[this.currSize] = item;
            this.currSize++;
        } else {
            Object[] newData = new Object[this.maxSize * 2];
            for (int i = 0; i < this.maxSize; i++) {
                newData[i] = this.data[i];
            }
            newData[this.currSize] = item;
            this.data = newData;
            this.maxSize *= 2;
            this.currSize++;
        }
    }

    public T pop() {
        if (!this.isEmpty()) {
            currSize--;
            T retValue = (T) data[currSize];
            data[currSize] = null;
            return retValue;
        }
        System.err.println("Error: Attempted to pop from empty stack.");
        return null;
    }

    public T peek() {
        if (!this.isEmpty()) {
            return (T) data[currSize - 1];
        } else {
            return null;
        }
    }

    public boolean isFull() {
        return currSize == this.maxSize;
    }

    public boolean isEmpty() {
        return currSize == 0;
    }
}
