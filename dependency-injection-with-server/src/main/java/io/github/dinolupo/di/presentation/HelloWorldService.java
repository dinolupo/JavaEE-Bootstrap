package io.github.dinolupo.di.presentation;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by dinolupo.github.io on 19/06/16.
 */
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@DependsOn("FireStarter")
@Startup
@Singleton
@Interceptors(MethodCallLogger.class)
public class HelloWorldService {

    @Resource
    SessionContext sessionContext;

    @PostConstruct
    public void onInit() {
        System.out.println("Starting... EJB HelloWorldService");
    }

    public String serve() {
        return "Hi there! today is " + new Date() + " Caller Principal is " + sessionContext.getCallerPrincipal();
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("Destroying EJB HelloWorldService");
    }

}
