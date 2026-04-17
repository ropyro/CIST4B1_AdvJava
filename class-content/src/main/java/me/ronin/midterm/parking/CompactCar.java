package me.ronin.midterm.parking;

public class CompactCar  extends Car{
    @Override
    public void park(ParkingLot parkingLot) {
        if(parkingLot.getOccupiedCompactSpaces().length < parkingLot.getCompactSpaces()){
            parkingLot.getOccupiedCompactSpaces().append(this);
            System.out.println("Compact car parked in a compact spot.");
        }else if (parkingLot.getOccupiedNormalSpaces().length < parkingLot.getNormalSpaces()){
            parkingLot.getOccupiedNormalSpaces().append(this);
            System.out.println("Compact car had to park in a normal size spot.");
        } else {
            System.out.println("Compact car could not find a parking spot.");
        }
    }
}
