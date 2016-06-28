package io.github.dinolupo.di.presentation;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.validation.constraints.Size;

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

    // JSF text field, with validation
    @Size(min = 3, max = 8)
    private String textField;

    //@Inject
    //BigBrother bigBrother;

    //@Inject
    //BigBrotherNotEJB bigBrother;

    //@Inject
    //BigBrotherWithQueue bigBrother;

    //@Inject
    //BigBrotherWithQueueUsingTimerAndMessageAnalyzer bigBrother;

    @Inject
    BigBrotherJPA bigBrother;

    @PostConstruct
    public void onInit() {
        System.out.println("Creating Index");
    }

    public String getMessage() {
        userCounter.increase();
        globalCounter.increase();
        String message = helloWorldService.serve();
        //bigBrother.gatherEverything(message);
        return message;
    }

    public int getUserCounter() {
        return emptyDelegate.getUserCounter();
    }

    public int getGlobalCounter() {
        return emptyDelegate.getGlobalCounter();
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("Destroying Index");
    }

    public String getTextField() {
        return textField;
    }

    public void setTextField(String textField) {
        this.textField = textField;
    }

    public Object save() {
        this.bigBrother.gatherEverything(textField);
        return null;
    }

}
