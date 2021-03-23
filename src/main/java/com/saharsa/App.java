package com.saharsa;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {

    public static ParkingLot parkingLot;

    static {
        parkingLot = new ParkingLot();
    }

    public static void main(String[] args) throws IOException {

        if(args!=null && args.length>0 && args[0]!=null){
            Path path = Paths.get(args[0]);
            readFromFile(path);
        }else{
            runInteractiveShell();
        }

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
                    parkingLot.createParkingLot(Integer.parseInt(arguments[0]));
                    break;
                case "park":
                    Vehicle vehicle = new Vehicle(arguments[0], arguments[1]);
                    parkingLot.park(vehicle);
                    break;
                case "leave":
                    parkingLot.leave(Integer.parseInt(arguments[0]));
                    break;
                case "status":
                    parkingLot.status();
                    break;
                case "slot_number_for_registration_number":
                    System.out.println(parkingLot.getSpotByRegistrationNumber(arguments[0]));
                    break;
                case "registration_numbers_for_cars_with_colour":
                    List<String> list = parkingLot.getRegistrationNumbersByColor(arguments[0]);
                    list.forEach((l) -> System.out.print(l+", "));
                    System.out.println();
                    break;
                case "slot_numbers_for_cars_with_colour":
                    List<Integer> list1 = parkingLot.getSpotNumbersByColor(arguments[0]);
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
