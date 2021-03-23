package com.saharsa;

import com.saharsa.executor.ExecutionStrategy;
import com.saharsa.executor.FileExecutionStrategy;
import com.saharsa.executor.ShellExecutionStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception {

        ExecutionStrategy executionStrategy;

        if(args!=null && args.length>0){
            executionStrategy = new FileExecutionStrategy();
        }else{
            executionStrategy = new ShellExecutionStrategy();
        }
        executionStrategy.execute(args);

    }

}
