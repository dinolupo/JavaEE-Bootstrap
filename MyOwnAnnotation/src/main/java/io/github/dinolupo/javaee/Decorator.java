package io.github.dinolupo.javaee;

import java.lang.reflect.Proxy;

/**
 * Created by dino on 17/06/16.
 */
public class Decorator {
    public static Object decorate(Object toDecorate) {
        Class<?> clazz = toDecorate.getClass();
        return  Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(), new LoggingAspect(toDecorate));

    }
}
