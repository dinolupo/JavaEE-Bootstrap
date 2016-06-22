package io.github.dinolupo.di.presentation;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

/**
 * Created by dinolupo.github.io on 22/06/16.
 */

@Stateless
public class BigBrother {

    @Asynchronous
    public void gatherEverything(String message){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("EJB long operation --> Save it for later: %s\n", message);
    }

}
