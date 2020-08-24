package org.riffel.url.shortener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ShortenedUrlRequest {

    @NotNull
    private String url;
}
