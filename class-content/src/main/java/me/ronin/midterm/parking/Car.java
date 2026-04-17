package me.ronin.midterm.parking;


//Abstract keyword stops Car class from being instantiated directly, not sure if it is different in python
//I chose to also use the abstract key word for the park method, forcing child classes to implement it
//this should prevent the need to throw any exceptions like NotImplemntedError
public abstract class Car {
    public abstract void park(ParkingLot parkingLot);
}
