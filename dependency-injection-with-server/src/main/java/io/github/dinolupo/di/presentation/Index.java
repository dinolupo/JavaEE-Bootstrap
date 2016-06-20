package io.github.dinolupo.di.presentation;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

/**
 * Created by dino on 19/06/16.
 */
@Model
public class Index {

    @Inject
    HelloWorldService helloWorldService;

    public String getMessage() {
        return helloWorldService.serve();
    }

}
