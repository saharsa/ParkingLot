package com.saharsa;

import com.saharsa.executor.ExecutionStrategy;
import com.saharsa.executor.FileExecutionStrategy;
import com.saharsa.executor.ShellExecutionStrategy;

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
