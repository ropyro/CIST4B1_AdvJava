package me.ronin.week5;

public class Stack<T> {

    public int maxSize;
    public int currSize;

    public Stack(){

    }

    public void push(T item){

    }

    public T pop(){
        if(!this.isEmpty()){
            currSize--;
            T retValue = (T) data[currSize];
        }
    }

    public boolean isEmpty() {
        return currSize == 0;
    }

    public boolean isFull(){
        return currSize == maxSize;
    }
}
