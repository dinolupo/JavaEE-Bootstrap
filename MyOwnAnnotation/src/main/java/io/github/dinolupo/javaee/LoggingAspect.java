package io.github.dinolupo.javaee;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by dino on 17/06/16.
 */
public class LoggingAspect implements InvocationHandler {

    private Object target;

    public LoggingAspect(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.nanoTime();
        try {
            System.out.printf("Before method %s\n", method);
            return method.invoke(this.target, args);
        } finally {
            System.out.printf("Method invoked in %d ns\n", System.nanoTime() - start);
        }
    }
}
