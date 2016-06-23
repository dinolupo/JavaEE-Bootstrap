package io.github.dinolupo.di.presentation;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by dinolupo.github.io on 23/06/16.
 */
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Singleton
public class BigBrotherWithQueue {

    CopyOnWriteArrayList<String> messageQueue;

    @PostConstruct
    public void initialize(){
        this.messageQueue = new CopyOnWriteArrayList<>();
    }
    
    public void gatherEverything(String message){
        messageQueue.add(message);
    }

    @Schedule(second = "*/5", minute = "*", hour = "*")
    public void batchAnalyze(){
        System.out.printf("**************** Analyzing at %s ***************\n", new Date());
        for (String message: messageQueue) {
            System.out.printf("---- Working on message: %s\n", message);
            messageQueue.remove(message);

        }
    }
    
}
