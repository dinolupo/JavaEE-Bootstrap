package io.github.dinolupo.di.presentation;

import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dinolupo.github.io on 29/06/16.
 */
@Path("messages")
public class MessagesResource {

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("{id}")
    public Message message(@PathParam("id") long id) {
        return new Message("Message Id: " + id);
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Message> messages() {
        List<Message> retList = new ArrayList<>();
        retList.add(new Message("Star Wars"));
        retList.add(new Message("Star Trek"));
        return retList;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getFruitCart() {
        return Json.createObjectBuilder().add("bananas", 5).add("apples", 3).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void postMessages(@Valid Message message) {
        System.out.println("Message: " + message);
    }


}
