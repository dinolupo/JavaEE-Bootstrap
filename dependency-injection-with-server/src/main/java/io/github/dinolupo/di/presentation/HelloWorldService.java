package io.github.dinolupo.di.presentation;

import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.interceptor.Interceptors;
import java.util.Date;

/**
 * Created by dino on 19/06/16.
 */
@Stateless
//@Interceptors(MethodCallLogger.class)
public class HelloWorldService {

    public String serve() {
        return "Hi there! today is " + new Date();
    }

}
