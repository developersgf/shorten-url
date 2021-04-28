package com.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Table(name = "shorten_url")
@Entity(name = "ShortenUrl")
public class ShortenUrl {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "long_url", nullable = false)
    private String longUrl;

    @Column(name = "client", nullable = false)
    private String client;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "expiry", nullable = false)
    private LocalDateTime expiry;

}
