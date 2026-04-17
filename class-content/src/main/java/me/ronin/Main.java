package me.ronin;

import me.ronin.midterm.parking.Car;
import me.ronin.midterm.parking.CompactCar;
import me.ronin.midterm.parking.FullSizeCar;
import me.ronin.midterm.parking.ParkingLot;

public class Main {
    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot(10, 5);

        //generate 20 random cars and make them attempt to park
        for(int i = 0; i < 20; i ++){
            Car newCar;
            if(Math.random()*1 > 0.5){
                newCar = new CompactCar();
            }else{
                newCar = new FullSizeCar();
            }
            newCar.park(parkingLot);
        }

        System.out.println("\nNormal Spaces: ");
        parkingLot.getOccupiedNormalSpaces().print();

        System.out.println("\nCompact Spaces: ");
        parkingLot.getOccupiedCompactSpaces().print();
    }
}