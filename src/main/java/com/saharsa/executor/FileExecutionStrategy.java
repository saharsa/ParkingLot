package com.saharsa.executor;

import com.saharsa.command.ParkingLotCommandExecutor;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileExecutionStrategy implements ExecutionStrategy{

    @Override
    public void execute(String ...args) throws Exception {
        if(args.length==0){
            throw new Exception("No file path provided");
        }
        Path path = Paths.get(args[0]);
        BufferedReader reader = Files.newBufferedReader(path);
        String line;
        while((line = reader.readLine())!= null){
            String[] arguments = line.split(" ");
            boolean exit = ParkingLotCommandExecutor.executeCommand(arguments[0], Arrays.copyOfRange(arguments, 1, arguments.length));
            if(exit) {
                break;
            }
        }
    }
}
