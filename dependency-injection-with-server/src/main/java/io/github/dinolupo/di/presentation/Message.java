package io.github.dinolupo.di.presentation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by dinolupo.github.io on 25/06/16.
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Message {

    // id
    @Id
    @GeneratedValue
    @XmlTransient
    long id;

    @Size(min = 3, max = 10)
    String message;

    // needed for our purpose
    public Message(String message) {
        this.message = message;
    }

    // needed by JPA
    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
