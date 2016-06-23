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

    @Inject
    EmptyDelegate emptyDelegate;

    //@Inject
    //BigBrother bigBrother;

    //@Inject
    //BigBrotherNotEJB bigBrother;

    //@Inject
    //BigBrotherWithQueue bigBrother;

    @Inject
    BigBrotherWithQueueUsingTimer bigBrother;

    @PostConstruct
    public void onInit() {
        System.out.println("Creating Index");
    }

    public String getMessage() {
        userCounter.increase();
        globalCounter.increase();
        String message = helloWorldService.serve();
        bigBrother.gatherEverything(message);
        return message;
    }

    public int getUserCounter() {
//        return userCounter.getCounter();
        return emptyDelegate.getUserCounter();
    }

    public int getGlobalCounter() {
//        return globalCounter.getCounter();
        return emptyDelegate.getGlobalCounter();
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("Destroying Index");
    }

}
