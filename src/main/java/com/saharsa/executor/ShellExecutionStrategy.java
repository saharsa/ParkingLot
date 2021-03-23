package com.saharsa.executor;

import com.saharsa.command.ParkingLotCommandExecutor;

import java.util.Arrays;
import java.util.Scanner;

public class ShellExecutionStrategy implements ExecutionStrategy{

    @Override
    public void execute(String... args) {

        Scanner scanner = new Scanner(System.in);
        while(true){
            String command = scanner.nextLine();
            String[] arguments = command.split(" ");
            boolean exit = ParkingLotCommandExecutor.executeCommand(arguments[0], Arrays.copyOfRange(arguments, 1, arguments.length));
            if(exit) {
                break;
            }
        }
    }
}
