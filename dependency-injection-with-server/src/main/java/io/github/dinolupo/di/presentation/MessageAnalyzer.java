package io.github.dinolupo.di.presentation;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by dinolupo.github.io on 23/06/16.
 */
@Stateless
public class MessageAnalyzer {

    @Asynchronous
    public Future<Boolean> analyze(String message) {
        Boolean retValue = message.hashCode() % 2 == 0;
        // with sleep() here we are going to simulate a long process required to analyze the message
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new AsyncResult<>(retValue);
    }

    @Asynchronous
    public void showResults(List<Future<Boolean>> results){
        for (Future<Boolean> result: results) {
            try {
                System.out.printf("### Result is %s\n", result.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
    
}
