package io.github.dinolupo.di.presentation;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;


/**
 * Created by dinolupo.github.io on 22/06/16.
 */
public class BigBrotherNotEJB {

    @Resource
    ManagedExecutorService managedExecutorService;

    public void gatherEverything(String message){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("Not EJB long operation --> Save it for later: %s\n", message);
            }
        };

        managedExecutorService.execute(runnable);

    }
}
