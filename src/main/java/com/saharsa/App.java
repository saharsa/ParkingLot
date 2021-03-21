package com.saharsa;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {

    public static ParkingLot parkingLot = null;

    public static void main(String[] args) throws IOException {


        if(args!=null && args.length>0 && args[0]!=null){
            Path path = Paths.get(args[0]);
            readFromFile(path);
        }else{
            runInteractiveShell();
        }

        /*try {
            ParkingLot parkingLot = new ParkingLot(6);

            Vehicle car = new Vehicle("KA-01-HH-1234", "White");
            parkingLot.park(car);

            Vehicle car1 = new Vehicle("KA-01-HH-9999", "White");
            parkingLot.park(car1);

            Vehicle car2 = new Vehicle("KA-01-BB-0001", "Black");
            parkingLot.park(car2);

            Vehicle car3 = new Vehicle("KA-01-HH-7777", "Red");
            parkingLot.park(car3);

            Vehicle car4 = new Vehicle("KA-01-HH-2701", "Blue");
            parkingLot.park(car4);

            Vehicle car5 = new Vehicle("KA-01-HH-3141", "Black");
            parkingLot.park(car5);

            Vehicle car6 = new Vehicle("KA-01-P-333", "White");
            parkingLot.park(car6);

            parkingLot.leave(4);
            parkingLot.leave(1);

            parkingLot.status();

            Vehicle car7 = new Vehicle("DL-12-AA-9999", "White");
            parkingLot.park(car7);

            System.out.println(parkingLot.getSlotByRegistrationNumber("KA-01-HH-7777"));


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

         */

    }

    public static void readFromFile(Path path) throws IOException {
        BufferedReader reader = Files.newBufferedReader(path);
        String line;
        while((line = reader.readLine())!= null){
            String[] arguments = line.split(" ");
            boolean exit = executeCommand(arguments[0], Arrays.copyOfRange(arguments, 1, arguments.length));
            if(exit) {
                break;
            }
        }
    }

    public static void runInteractiveShell() {

        Scanner scanner = new Scanner(System.in);
        while(true){
            String command = scanner.nextLine();
            String[] arguments = command.split(" ");
            boolean exit = executeCommand(arguments[0], Arrays.copyOfRange(arguments, 1, arguments.length));
            if(exit) {
                break;
            }
        }
    }

    public static boolean executeCommand(String commandName, String ...arguments) {
        boolean exit = false;
        try {
            switch (commandName){
                case "exit":
                    exit = true;
                    break;
                case "create_parking_lot":
                    ParkingLot.createParkingLot(Integer.parseInt(arguments[0]));
                    parkingLot = ParkingLot.getParkingLot();
                    break;
                case "park":
                    Vehicle vehicle = new Vehicle(arguments[0], arguments[1]);
                    assert parkingLot != null;
                    parkingLot.park(vehicle);
                    break;
                case "leave":
                    assert parkingLot != null;
                    parkingLot.leave(Integer.parseInt(arguments[0]));
                    break;
                case "status":
                    assert parkingLot != null;
                    parkingLot.status();
                    break;
                case "slot_number_for_registration_number":
                    assert parkingLot != null;
                    System.out.println(parkingLot.getSlotByRegistrationNumber(arguments[0]));
                    break;
                case "registration_numbers_for_cars_with_colour":
                    assert parkingLot != null;
                    List<String> list = parkingLot.getRegistrationNumbersByColor(arguments[0]);
                    list.forEach((l) -> System.out.print(l+", "));
                    System.out.println();
                    break;
                case "slot_numbers_for_cars_with_colour":
                    assert parkingLot != null;
                    List<Integer> list1 = parkingLot.getSlotNumbersByColor(arguments[0]);
                    list1.forEach((l) -> System.out.print(l+", "));
                    System.out.println();
                    break;
                default:
                    throw new Exception("Command not recognized");
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return exit;
    }

}
