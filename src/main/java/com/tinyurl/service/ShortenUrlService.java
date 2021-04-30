package com.tinyurl.service;

import com.google.common.hash.Hashing;
import com.tinyurl.bean.URLValidator;
import com.tinyurl.configuration.ShortenUrlConfiguration;
import com.tinyurl.dto.ShortenUrlDto;
import com.tinyurl.exception.BadRequestException;
import com.tinyurl.model.ShortenUrl;
import com.tinyurl.repository.ShortenUrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ShortenUrlService {

    private static final Logger log = LoggerFactory.getLogger(ShortenUrlService.class);

    private final URLValidator urlValidator;
    private final ShortenUrlRepository shortenUrlRepository;
    private final ShortenUrlConfiguration shortenUrlConfiguration;

    @Autowired
    public ShortenUrlService(ShortenUrlRepository shortenUrlRepository,
                             ShortenUrlConfiguration shortenUrlConfiguration,
                             URLValidator urlValidator) {
        this.shortenUrlRepository = shortenUrlRepository;
        this.shortenUrlConfiguration = shortenUrlConfiguration;
        this.urlValidator = urlValidator;
    }

    public ShortenUrlDto create(ShortenUrlDto dto) {

        if (!urlValidator.validateURL(dto.getLongUrl())) {
            dto.setMessage("long url is not a URL");
            throw new BadRequestException("long url is not a URL");
        }

        String tinyUrl = Hashing.murmur3_32().hashString(dto.getLongUrl(), StandardCharsets.UTF_8).toString();
        Optional<ShortenUrl> optional = shortenUrlRepository.findById(tinyUrl);
        if (optional.isPresent()) {
            throw new BadRequestException(MessageFormat.format("long url: {0} already have tiny url: {1}", dto.getLongUrl(), tinyUrl));
        }

        ShortenUrl shortenUrl = new ShortenUrl();
        shortenUrl.setId(tinyUrl);
        shortenUrl.setLongUrl(dto.getLongUrl());
        shortenUrl.setClient(dto.getClient());

        LocalDateTime now = LocalDateTime.now();
        shortenUrl.setCreated(now);

        LocalDateTime expiry = now.plusDays(shortenUrlConfiguration.getDay());
        shortenUrl.setExpiry(expiry);

        log.debug("LocalDateTime.now: " + now);
        log.debug("shortenUrlConfiguration.day: " + shortenUrlConfiguration.getDay());
        log.debug("LocalDateTime.expiry: " + expiry);
        log.debug(MessageFormat.format("create tiny url: {0}", shortenUrl.toString()));
        shortenUrl = shortenUrlRepository.save(shortenUrl);

        dto.setTinyUrl(shortenUrl.getId());
        return dto;
    }

    public ShortenUrlDto redirect(String tinyUrl) {
        Optional<ShortenUrl> optional = shortenUrlRepository.findById(tinyUrl);
        if (!optional.isPresent()) {
            throw new BadRequestException(MessageFormat.format("tiny url: {0} not exist in system", tinyUrl));
        }

        ShortenUrl entity = optional.get();
        ShortenUrlDto dto = new ShortenUrlDto();
        dto.setTinyUrl(tinyUrl);
        dto.setLongUrl(entity.getLongUrl());
        dto.setCreateDate(entity.getCreated());
        dto.setExpiresDate(entity.getExpiry());
        return dto;
    }

    public ShortenUrlDto delete(String tinyUrl) {
        Optional<ShortenUrl> optional = shortenUrlRepository.findById(tinyUrl);
        if (!optional.isPresent()) {
            throw new BadRequestException(MessageFormat.format("tiny url: {0} not exist in system", tinyUrl));
        }

        ShortenUrl entity = optional.get();

        ShortenUrlDto dto = new ShortenUrlDto();
        dto.setTinyUrl(tinyUrl);
        dto.setLongUrl(entity.getLongUrl());
        dto.setMessage("deleted");
        shortenUrlRepository.delete(entity);
        log.debug(MessageFormat.format("delete tiny url: {0}", tinyUrl));

        return dto;
    }

}
