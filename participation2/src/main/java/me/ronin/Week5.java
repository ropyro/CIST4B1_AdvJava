package me.ronin;

public class Week5 {

    class Node<T> {
        //data
        T data;

        //link
        Node next;

        Node(T data){
            this.data = data;
            this.next = null;
        }
    }

    class SingleLinkedList<T> {
        Node<T> head;
        int length;

        SingleLinkedList(){
            this.head = null;
            this.length = 0;
        }

        void append(T data){
            Node<T> curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = new Node<T>(data);
        }
    }
}