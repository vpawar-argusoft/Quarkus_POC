package org.demo;


import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/shows")
@RegisterRestClient(baseUri = "https://api.tvmaze.com")
public interface TVSeriesIdProxy {
    TvSeries getTvSeriesById(int id);
}
