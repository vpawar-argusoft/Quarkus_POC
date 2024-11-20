package org.demo;

import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/greeting")
public class GreetingResource {

    private static List<Message> messages = new ArrayList<>();
    private static int idCounter = 1;

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @GET
    @Path("/goodbye")
    @Produces(MediaType.TEXT_PLAIN)
    public String goodbye() {
        return "Goodbye from Quarkus REST";
    }

    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Message json() {
        return new Message("Hello from Quarkus REST in JSON");
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Message createMessage(Message message) {
        message.setId(idCounter++);
        messages.add(message);
        return message;
    }

    @GET
    @Path("/read/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Message readMessage(@PathParam("id") int id) {
        return messages.stream()
                .filter(msg -> msg.getId() == id)
                .findFirst()
                .orElseThrow(() -> new WebApplicationException("Message not found", 404));
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Message updateMessage(@PathParam("id") int id, Message updatedMessage) {
        Message message = messages.stream()
                .filter(msg -> msg.getId() == id)
                .findFirst()
                .orElseThrow(() -> new WebApplicationException("Message not found", 404));
        message.setMessage(updatedMessage.getMessage());
        return message;
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteMessage(@PathParam("id") int id) {
        Message message = messages.stream()
                .filter(msg -> msg.getId() == id)
                .findFirst()
                .orElseThrow(() -> new WebApplicationException("Message not found", 404));
        messages.remove(message);
        return "Message deleted successfully";
    }

    public static class Message {
        private int id;
        private String message;

        public Message() {
        }

        public Message(String message) {
            this.message = message;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
