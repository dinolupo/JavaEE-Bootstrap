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

    @PostConstruct
    public void onInit() {
        System.out.println("Creating Index");
    }

    public String getMessage() {
//        return helloWorldService.getClass().getName();
        return helloWorldService.serve();
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("Destroying Index");
    }

}
