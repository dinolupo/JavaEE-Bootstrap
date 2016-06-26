package io.github.dinolupo.di.presentation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by dinolupo.github.io on 25/06/16.
 */
public class MessageArchive {

    @PersistenceContext(unitName = "production")
    EntityManager entityManager;

    public void saveMessage(String message){
        entityManager.merge(new Message(message));
    }

}
