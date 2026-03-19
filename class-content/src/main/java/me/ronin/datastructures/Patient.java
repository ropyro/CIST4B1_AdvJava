package me.ronin.datastructures;


public class Patient implements Comparable<Patient> {
    public String name;
    public int priority;

    public Patient(String name, int priority){
        this.name = name;
        this.priority = priority;
    }

    @Override
    public int compareTo(Patient o) {
        return o.priority - priority;
    }
}