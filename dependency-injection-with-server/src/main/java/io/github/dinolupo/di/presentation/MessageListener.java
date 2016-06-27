package io.github.dinolupo.di.presentation;

import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;

/**
 * Created by dinolupo.github.io on 27/06/16.
 */
public class MessageListener {
    
    public void onSuccess(@Observes(during = TransactionPhase.AFTER_SUCCESS) String message){
        System.out.println("+++++ Transaction commited");
    }

    public void onFailure(@Observes(during = TransactionPhase.AFTER_FAILURE) String message){
        System.out.println("----- Transaction Failure");
    }
    
}
