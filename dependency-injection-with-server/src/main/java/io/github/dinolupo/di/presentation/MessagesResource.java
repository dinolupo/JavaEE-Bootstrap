package io.github.dinolupo.di.presentation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by dinolupo.github.io on 29/06/16.
 */
@Path("messages")
public class MessagesResource {

    @GET
    public Message message() {
        return new Message("hello from a Message object");
    }

}
