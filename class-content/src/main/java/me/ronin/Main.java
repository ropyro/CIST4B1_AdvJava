package me.ronin;

import me.ronin.midterm.parking.Car;
import me.ronin.midterm.parking.CompactCar;
import me.ronin.midterm.parking.FullSizeCar;
import me.ronin.midterm.parking.ParkingLot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public List<Object> findDuplicates(Object[] inputArray){
        Map<Object, Integer> dupes = new HashMap<>();
        List<Object> confirmedDupes = new ArrayList<>();
        for(Object potentialDupe : inputArray){
            int count = dupes.getOrDefault(potentialDupe, 0) + 1;
            dupes.put(potentialDupe, count);
            if(count == 2) {
                confirmedDupes.add(potentialDupe);
            }
        }
        return confirmedDupes;
    }
}