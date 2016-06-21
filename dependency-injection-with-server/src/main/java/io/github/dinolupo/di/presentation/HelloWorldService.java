package io.github.dinolupo.di.presentation;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.interceptor.Interceptors;
import java.util.Date;

/**
 * Created by dino on 19/06/16.
 */
@Stateless
@Interceptors(MethodCallLogger.class)
public class HelloWorldService {

    @PostConstruct
    public void onInit() {
        System.out.println("Creating EJB HelloWorldService");
    }

    public String serve() {
        return "Hi there! today is " + new Date();
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("Destroying EJB HelloWorldService");
    }

}
