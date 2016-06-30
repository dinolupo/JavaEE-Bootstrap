package io.github.dinolupo.di.presentation;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by dinolupo.github.io on 29/06/16.
 */
@Path("messages")
public class MessagesResource {

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Message message() {
        return new Message("hello from a Message object");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getFruitCart() {
        return Json.createObjectBuilder().add("bananas", 5).add("apples", 3).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void postMessages(Message message) {
        System.out.println("Message: " + message);
    }


}
