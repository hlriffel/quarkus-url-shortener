package org.riffel.url.shortener;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ShortenedUrlRepository implements PanacheRepository<ShortenedUrl> {

    public ShortenedUrl findByShortenedUrl(final String shortenedUrl) {
        return find("shortened_url", shortenedUrl).firstResult();
    }
}
