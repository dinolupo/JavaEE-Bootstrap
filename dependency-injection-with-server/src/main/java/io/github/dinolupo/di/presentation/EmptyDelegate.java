package io.github.dinolupo.di.presentation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Created by dinolupo.github.io on 21/06/16.
 */
@ApplicationScoped
public class EmptyDelegate {

    @Inject
    GlobalCounter globalCounter;

    @Inject
    UserCounter userCounter;

    public int getUserCounter() {
        return userCounter.getCounter();
    }

    public int getGlobalCounter() {
        return globalCounter.getCounter();
    }

}
