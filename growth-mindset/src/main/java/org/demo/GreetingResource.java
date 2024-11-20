package org.demo;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/greeting")
public class GreetingResource {

    @Inject
    EntityManager em;

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

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Message createMessage(Message message) {
        em.persist(message);
        return message;
    }

    @GET
    @Path("/read")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> getAllMessages() {
        TypedQuery<Message> query = em.createQuery("SELECT m FROM Message m", Message.class);
        return query.getResultList();
    }
    

    @GET
    @Path("/read/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Message readMessage(@PathParam("id") int id) {
        Message message = em.find(Message.class, id);
        if (message == null) {
            throw new WebApplicationException("Message not found", 404);
        }
        return message;
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Message updateMessage(@PathParam("id") int id, Message updatedMessage) {
        Message message = em.find(Message.class, id);
        if (message == null) {
            throw new WebApplicationException("Message not found", 404);
        }
        message.setMessage(updatedMessage.getMessage());
        return message;
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public String deleteMessage(@PathParam("id") int id) {
        Message message = em.find(Message.class, id);
        if (message == null) {
            throw new WebApplicationException("Message not found", 404);
        }
        em.remove(message);
        return "Message deleted successfully";
    }

}
