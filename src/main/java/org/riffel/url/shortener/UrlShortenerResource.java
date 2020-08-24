package org.riffel.url.shortener;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/")
public class UrlShortenerResource {

    @Inject
    private UrlShortenerService urlShortenerService;

    @POST
    @Path("/shorten")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String getShortenedUrl(@Valid final ShortenedUrlRequest request) {
        return urlShortenerService.getShortenedUrl(request.getUrl());
    }

    @GET
    @Path("/{shortenedUrl}")
    public Response redirectToOriginalUrl(@PathParam("shortenedUrl") final String shortenedUrl) {
        final String originalUrl = urlShortenerService.getOriginalUrl(shortenedUrl);

        return Response.status(Response.Status.FOUND).location(URI.create(originalUrl)).build();
    }
}
