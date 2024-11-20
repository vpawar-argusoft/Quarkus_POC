package org.demo;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/tvseries")
public class TvSeriesResource {


//    @RestClient
//    @Inject
    TVSeriesIdProxy proxy;

    @GET
    @Path("/{id}")
    public TvSeries getTvSeriesById(@PathParam("id") int id){
        return proxy.getTvSeriesById(id);
    }
    
}
