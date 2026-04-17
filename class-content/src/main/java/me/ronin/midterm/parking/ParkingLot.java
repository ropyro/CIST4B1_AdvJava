package me.ronin.midterm.parking;

import me.ronin.datastructures.SinglelyLinkedList;

public class ParkingLot {

    private SinglelyLinkedList<Car> occupiedNormalSpaces;
    private SinglelyLinkedList<CompactCar> occupiedCompactSpaces;

    private final int normalSpaces;
    private final int compactSpaces;

    public ParkingLot(int normalSpaces, int compactSpaces) {
        this.normalSpaces = normalSpaces;
        this.compactSpaces = compactSpaces;

        occupiedNormalSpaces = new SinglelyLinkedList<>();
        occupiedCompactSpaces = new SinglelyLinkedList<>();
    }

    public SinglelyLinkedList<Car> getOccupiedNormalSpaces() {
        return occupiedNormalSpaces;
    }

    public SinglelyLinkedList<CompactCar> getOccupiedCompactSpaces() {
        return occupiedCompactSpaces;
    }

    public int getNormalSpaces() {
        return normalSpaces;
    }

    public int getCompactSpaces() {
        return compactSpaces;
    }
}
