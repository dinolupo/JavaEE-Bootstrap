package io.github.dinolupo.di.presentation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by dinolupo.github.io on 25/06/16.
 */
@Entity
public class Message {

    // id
    @Id
    @GeneratedValue
    long id;

    // payload
    String message;

    // needed for our purpose
    public Message(String message) {
        this.message = message;
    }

    // needed by JPA
    public Message() {
    }


}
