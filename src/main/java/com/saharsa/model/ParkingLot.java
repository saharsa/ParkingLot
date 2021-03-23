package com.saharsa.model;

import java.util.*;
import java.util.stream.Collectors;

public class ParkingLot {

    // Checks if ParkingLot instance has been specifically created by the user using create_parking_lot command
    private boolean created = false;
    // Map of occupied spots in the ParkingLot
    private Map<Integer, ParkingSpot> occupiedSpots;
    // A priority queue of empty spot IDs
    private Queue<Integer> emptySpots;
    // Max capacity of ParkingLot
    private int capacity;

    public Map<Integer, ParkingSpot> getOccupiedSpots() {
        return this.occupiedSpots;
    }

    public Queue<Integer> getEmptySpots() {
        return this.emptySpots;
    }

    /**
     * Creates a ParkingLot with the specified capacity.
     * Initializes the ParkingLot instance and sets created to true.
     * @param totalSpots Total capacity of parking lot
     * @throws Exception Throws exception if parking lot is already created
     */
    public void createParkingLot(int totalSpots) throws Exception {
        if(!created) {
            this.initializeParkingLot(totalSpots);
            return;
        }
        throw new Exception("Parking lot already created");

    }

    /**
     * Initializes the ParkingLot instance with the capacity and initializes the relevant maps and queues.
     * @param totalSpots Total capacity of parking lot
     */
    private void initializeParkingLot(int totalSpots) {
        this.occupiedSpots = new HashMap<>(totalSpots);
        this.emptySpots = new PriorityQueue<>(totalSpots);
        for(int i=0; i<totalSpots; i++) {
            this.emptySpots.add(i+1);
        }
        this.created = true;
        this.capacity = totalSpots;
    }

    /**
     * Before execution of any command, checks if ParkingLot is indeed created by the user or not.
     * @throws Exception Throws exception if ParkingLot not created by user yet.
     */
    private void checkParkingLot() throws Exception {
        if(!created) {
            throw new Exception("Parking lot not created yet");
        }
    }

    /**
     * Parks the vehicle at the closest available spot in the ParkingLot
     * @param vehicle Vehicle to be parked
     * @throws Exception Throws exception if parking lot not created or parking lot is full
     */
    public ParkingSpot park(Vehicle vehicle) throws Exception {

        checkParkingLot();

        Integer spotId = useNextAvailableSpot();
        ParkingSpot parkingSpot = new ParkingSpot(spotId);
        parkingSpot.assignVehicle(vehicle);
        occupiedSpots.put(spotId, parkingSpot);
        return parkingSpot;
    }

    /**
     * Removes a Vehicle from a ParkingSpot when it leaves the ParkingLot
     * @param spotId ID of the spot where the Vehicle is parked
     * @throws Exception Throws exception if parking lot not created, or parking spot unoccupied, or SpotID invalid
     */
    public ParkingSpot leave(int spotId) throws Exception {

        checkParkingLot();

        if(spotId > capacity) {
            throw new Exception("Such a parking spot does not exist");
        }

        ParkingSpot parkingSpot = occupiedSpots.remove(spotId);

        if(parkingSpot == null) {
            throw new Exception("Parking spot unoccupied");
        }

        parkingSpot.removeVehicle();
        freeSpot(parkingSpot);

        return parkingSpot;
    }

    /**
     * Gives a summary of the ParkingLot's occupied ParkingSpot's
     * @throws Exception Throws exception if ParkingLot not created
     */
    public Map<Integer, ParkingSpot> status() throws Exception {

        checkParkingLot();

        if(this.occupiedSpots.isEmpty()) {
            throw new Exception("Parking lot is empty");
        }

        return this.occupiedSpots;
    }

    /**
     * Returns SpotID by registration number of Vehicle
     * @param registrationNumber Registration number of Vehicle
     * @return SpotID
     * @throws Exception Throws exception if parking lot not created or vehicle not found
     */
    public Integer getSpotByRegistrationNumber(String registrationNumber) throws Exception {

        checkParkingLot();

        Optional<ParkingSpot> spot = this.occupiedSpots.values().stream().filter((e) -> e.getVehicle().getRegistrationNumber().equals(registrationNumber)).findFirst();
        if(spot.isPresent()) {
            return spot.get().getSpotId();
        }
        throw new Exception("No such vehicle found");
    }

    /**
     * Returns list of registration numbers for Vehicle's whose color matches the queried color
     * @param color Color of the Vehicle
     * @return List of registration numbers of Vehicles
     * @throws Exception Throws exception if parking lot not created or no matching vehicles found
     */
    public List<String> getRegistrationNumbersByColor(String color) throws Exception {

        checkParkingLot();

        List<String> registrationNumbers = this.occupiedSpots.values().stream().filter(parkingSpot -> parkingSpot.getVehicle().getColor().equals(color)).map(parkingSpot -> parkingSpot.getVehicle().getRegistrationNumber()).collect(Collectors.toList());
        if(registrationNumbers.isEmpty()){
            throw new Exception("No such vehicles found");
        }
        return registrationNumbers;
    }

    /**
     * Returns the SpotIDs where Vehicle of a specific color is parked.
     * @param color Color of the vehicle
     * @return List of SpotIDs
     * @throws Exception Throws exception if parking lot not created, or no such spots found
     */
    public List<Integer> getSpotNumbersByColor(String color) throws Exception {

        checkParkingLot();

        List<Integer> slotNumbers = this.occupiedSpots.entrySet().stream().filter( (entry) -> entry.getValue().getVehicle().getColor().equals(color)).map(Map.Entry::getKey).collect(Collectors.toList());
        if(slotNumbers.isEmpty()){
            throw new Exception("No such spots found");
        }
        return slotNumbers;
    }

    /**
     * Returns the next available SpotID from PriorityQueue.
     * @return Next available SpotID
     * @throws Exception Throws exception is parking lot not created, or parking lot full
     */
    public Integer getNextAvailableSpot() throws Exception {

        checkParkingLot();

        if(this.emptySpots.isEmpty()){
            throw new Exception("Sorry, parking lot is full");
        }
        return this.emptySpots.peek();
    }

    private Integer useNextAvailableSpot() throws Exception {

        checkParkingLot();

        if(this.emptySpots.isEmpty()){
            throw new Exception("Sorry, parking lot is full");
        }
        return this.emptySpots.poll();
    }

    /**
     * Adds a freed SpotID to the emptySpots PriorityQueue
     * @param parkingSpot ParkingSpot that is to be freed
     */
    private void freeSpot(ParkingSpot parkingSpot) {
        this.emptySpots.add(parkingSpot.getSpotId());
    }

}