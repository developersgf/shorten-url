package com.service;

import com.dto.ShortenUrlDto;
import com.exception.BadRequestException;
import com.model.ShortenUrl;
import com.repository.ShortenUrlRepository;
import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ShortUrlService {

    private static final Logger log = LoggerFactory.getLogger(ShortUrlService.class);

    private final ShortenUrlRepository shortenUrlRepository;

    @Autowired
    public ShortUrlService(ShortenUrlRepository shortenUrlRepository) {
        this.shortenUrlRepository = shortenUrlRepository;
    }

    public ShortenUrlDto create(ShortenUrlDto dto) {

        if (!URLValidator.INSTANCE.validateURL(dto.getLongUrl())) {
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
        shortenUrl.setCreated(LocalDateTime.now());
        shortenUrl.setExpiry(LocalDateTime.now().plusDays(30));
        shortenUrl = shortenUrlRepository.save(shortenUrl);

        log.debug(MessageFormat.format("create tiny url: {0}", shortenUrl.toString()));

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
