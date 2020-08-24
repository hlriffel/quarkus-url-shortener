package org.riffel.url.shortener;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class ShortenedUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "shortened_url", length = 8, unique = true)
    private String shortenedUrl;

    @NotNull
    @Column(name = "original_url")
    private String originalUrl;
}
