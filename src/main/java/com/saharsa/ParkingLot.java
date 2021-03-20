package com.saharsa;

import java.util.*;
import java.util.stream.Collectors;

public class ParkingLot {

    private static ParkingLot parkingLot;

    private Map<Integer, ParkingSpot> occupiedSpots;
    private Queue<ParkingSpot> emptySpots;

    public static ParkingLot getParkingLot() {
        return parkingLot;
    }

    public static void createParkingLot(int totalSpots) throws Exception {
        if(parkingLot == null) {
            parkingLot = new ParkingLot(totalSpots);
            return;
        }
        throw new Exception("Parking lot already created");

    }

    private ParkingLot(int totalSpots) {
        this.occupiedSpots = new HashMap<>(totalSpots);
        this.emptySpots = new PriorityQueue<>(totalSpots);
        for(int i=0; i<totalSpots; i++) {
            this.emptySpots.add(new ParkingSpot(i+1));
        }
        System.out.println("Parking lot created");
    }

    public void park(Vehicle vehicle) throws Exception {
        ParkingSpot parkingSpot = getNextAvailableSpot();
        parkingSpot.assignVehicle(vehicle);
        occupiedSpots.put(parkingSpot.getSpotId(), parkingSpot);
        System.out.println("Allocated slot number: "+parkingSpot.getSpotId());
    }

    public void leave(int spotId){
        ParkingSpot parkingSpot = occupiedSpots.remove(spotId);
        parkingSpot.removeVehicle();
        freeSpot(parkingSpot);
        System.out.println("Slot number "+parkingSpot.getSpotId()+" is free");
    }

    public void status(){
        System.out.println("Slot No.\tRegistration No.\t\tColor");
        this.occupiedSpots.forEach((spotId, spot) -> {
            System.out.println(spotId+"\t\t\t"+spot.getVehicle().getRegistrationNumber()+"\t\t\t"+spot.getVehicle().getColor());
        });
    }

    public Integer getSlotByRegistrationNumber(String registrationNumber) throws Exception {
        Optional<ParkingSpot> spot = this.occupiedSpots.values().stream().filter((e) -> e.getVehicle().getRegistrationNumber().equals(registrationNumber)).findFirst();
        if(spot.isPresent()) {
            return spot.get().getSpotId();
        }
        throw new Exception("No such vehicle found");
    }

    public List<String> getRegistrationNumbersByColor(String color) throws Exception {
        List<String> registrationNumbers = this.occupiedSpots.values().stream().filter(parkingSpot -> parkingSpot.getVehicle().getColor().equals(color)).map(parkingSpot -> parkingSpot.getVehicle().getRegistrationNumber()).collect(Collectors.toList());
        if(registrationNumbers.isEmpty()){
            throw new Exception("No such vehicles found");
        }
        return registrationNumbers;
    }

    public List<Integer> getSlotNumbersByColor(String color) throws Exception {
        List<Integer> slotNumbers = this.occupiedSpots.entrySet().stream().filter( (entry) -> entry.getValue().getVehicle().getColor().equals(color)).map( entry -> entry.getKey()).collect(Collectors.toList());
        if(slotNumbers.isEmpty()){
            throw new Exception("No such slots found");
        }
        return slotNumbers;
    }

    private ParkingSpot getNextAvailableSpot() throws Exception {
        if(this.emptySpots.isEmpty()){
            throw new Exception("Sorry, parking lot is full");
        }
        return this.emptySpots.poll();
    }

    private void freeSpot(ParkingSpot parkingSpot) {
        this.emptySpots.add(parkingSpot);
    }

}

