package io.github.dinolupo.di.presentation;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 * Created by dino on 19/06/16.
 */
@Presenter
public class Index {

    @Inject
    HelloWorldService helloWorldService;

    @Inject
    UserCounter userCounter;

    @Inject
    GlobalCounter globalCounter;

    @PostConstruct
    public void onInit() {
        System.out.println("Creating Index");
    }

    public String getMessage() {
        userCounter.increase();
        globalCounter.increase();
        return helloWorldService.serve();
    }

    public int getUserCounter() {
        return userCounter.getCounter();
    }

    public int getGlobalCounter() {
        return globalCounter.getCounter();
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("Destroying Index");
    }

}
