package org.riffel.url.shortener;

import io.quarkus.redis.client.RedisClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.Arrays;
import java.util.Optional;

@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class UrlShortenerService {

    private static final char[] allowedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private static final int base = allowedCharacters.length;

    @Inject
    private ShortenedUrlRepository repository;

    @Inject
    private RedisClient redisClient;

    public String getShortenedUrl(final String url) {
        final Long id = repository.count() + 1L;
        final StringBuilder shortenedUrlBuilder = new StringBuilder();
        long aux = id;

        while (aux > 0) {
            shortenedUrlBuilder.append(allowedCharacters[(int) aux % base]);
            aux = aux / base;
        }

        final String shortenedUrlString = shortenedUrlBuilder.reverse().toString();
        final ShortenedUrl shortenedUrl = new ShortenedUrl();
        shortenedUrl.setOriginalUrl(url);
        shortenedUrl.setShortenedUrl(shortenedUrlString);

        repository.persist(shortenedUrl);

        return shortenedUrlString;
    }

    public String getOriginalUrl(final String shortenedUrl) {
        final String originalUrl;

        if (redisClient.exists(Arrays.asList(shortenedUrl)).toBoolean()) {
            originalUrl = redisClient.get(shortenedUrl).toString();
        } else {
            final Optional<ShortenedUrl> optionalUrl = Optional.ofNullable(repository.findByShortenedUrl(shortenedUrl));
            final ShortenedUrl url = optionalUrl.orElseThrow(WebApplicationException::new);

            originalUrl = url.getOriginalUrl();

            redisClient.set(Arrays.asList(url.getShortenedUrl(), url.getOriginalUrl()));
        }

        return originalUrl;
    }
}
