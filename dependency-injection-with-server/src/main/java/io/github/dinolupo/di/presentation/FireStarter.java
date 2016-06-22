package io.github.dinolupo.di.presentation;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Created by dinolupo.github.io on 22/06/16.
 */
@Startup
@Singleton
public class FireStarter {

    @PostConstruct
    public void onStart() {
        System.out.println("FireStarter is starting");
    }

}
