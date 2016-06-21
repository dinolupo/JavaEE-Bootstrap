package io.github.dinolupo.di.presentation;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;

/**
 * Created by dino on 21/06/16.
 */
public class MethodCallLogger {

    @AroundInvoke
    public Object log(InvocationContext invocationContext) throws Exception {
        long start = System.nanoTime();
        Method method = invocationContext.getMethod();
        try {
            return invocationContext.proceed();
        } finally {
            System.out.printf("Method %s invoked in %d ns\n", method, System.nanoTime() - start);
        }
    }
}
