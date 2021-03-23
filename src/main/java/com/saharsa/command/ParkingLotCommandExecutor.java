package com.saharsa.command;

import com.saharsa.ParkingLot;
import com.saharsa.ParkingSpot;
import com.saharsa.Vehicle;

import java.util.List;

public class ParkingLotCommandExecutor {

    public static ParkingLot parkingLot;

    static {
        parkingLot = new ParkingLot();
    }

    public static boolean executeCommand(String commandName, String ...arguments) {
        boolean exit = false;
        try {
            switch (commandName){
                case "exit":
                    exit = true;
                    break;
                case "create_parking_lot":
                    createParkingLot(arguments);
                    break;
                case "park":
                    park(arguments);
                    break;
                case "leave":
                    leave(arguments);
                    break;
                case "status":
                    status();
                    break;
                case "slot_number_for_registration_number":
                    getSpotByRegistrationNumber(arguments);
                    break;
                case "registration_numbers_for_cars_with_colour":
                    getRegistrationNumbersByColor(arguments);
                    break;
                case "slot_numbers_for_cars_with_colour":
                    getSpotNumbersByColor(arguments);
                    break;
                default:
                    throw new Exception("Command not recognized");
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return exit;
    }

    public static void createParkingLot(String ...arguments) throws Exception {
        if(arguments.length==0) {
            throw new Exception("Please provide capacity for parking lot");
        }
        int capacity = Integer.parseInt(arguments[0]);
        parkingLot.createParkingLot(capacity);
        System.out.println("Parking lot created");
    }

    public static void park(String ...arguments) throws Exception {
        if(arguments.length==0) {
            throw new Exception("Please provide vehicle registration number and color");
        }
        if(arguments.length==1) {
            throw new Exception("Either registration number or color is missing");
        }
        Vehicle vehicle = new Vehicle(arguments[0], arguments[1]);
        ParkingSpot parkingSpot = parkingLot.park(vehicle);
        System.out.println("Allocated slot number: "+parkingSpot.getSpotId());

    }

    public static void leave(String ...arguments) throws Exception {
        if(arguments.length==0) {
            throw new Exception("Please provide Spot ID");
        }
        int spotID;
        try {
            spotID = Integer.parseInt(arguments[0]);
        }catch (NumberFormatException e) {
            throw new Exception("Invalid Spot ID format");
        }
        ParkingSpot parkingSpot = parkingLot.leave(spotID);
        System.out.println("Slot number "+parkingSpot.getSpotId()+" is free");
    }

    public static void status() throws Exception {
        parkingLot.status();
    }

    public static void getSpotByRegistrationNumber(String ...arguments) throws Exception {
        if(arguments.length==0) {
            throw new Exception("Please provide registration number");
        }
        System.out.println(parkingLot.getSpotByRegistrationNumber(arguments[0]));
    }

    public static void getRegistrationNumbersByColor(String ...arguments) throws Exception {
        if(arguments.length==0) {
            throw new Exception("Please provide color");
        }
        List<String> list = parkingLot.getRegistrationNumbersByColor(arguments[0]);
        list.forEach((l) -> System.out.print(l+", "));
        System.out.println();
    }

    public static void getSpotNumbersByColor(String ...arguments) throws Exception {
        if(arguments.length==0) {
            throw new Exception("Please provide color");
        }
        List<Integer> list1 = parkingLot.getSpotNumbersByColor(arguments[0]);
        list1.forEach((l) -> System.out.print(l+", "));
        System.out.println();
    }
}
