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


    //TODO: finish implementing
    public T pop() {
        if (!this.isEmpty()) {
            currSize--;
            T retValue = (T) data[currSize];
        }
        return null;
    }

    public T peek(){
        return null;
    }

    public boolean isEmpty() {
        return currSize == 0;
    }

    public boolean isFull() {
        return currSize == maxSize;
    }
}
