package me.ronin.midterm.parking;

public class FullSizeCar extends Car {
    @Override
    public void park(ParkingLot parkingLot) {
        if (parkingLot.getOccupiedNormalSpaces().length < parkingLot.getNormalSpaces()){
            parkingLot.getOccupiedNormalSpaces().append(this);
            System.out.println("Normal size car parked in a normal size spot.");
        } else {
            System.out.println("Normal car could not find a parking spot.");
        }
    }
}
