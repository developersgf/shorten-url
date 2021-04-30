package com.tinyurl.controller;

import com.tinyurl.dto.ShortenUrlDto;
import com.tinyurl.exception.BadRequestException;
import com.tinyurl.service.ShortenUrlService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ShortenUrlController {

    private static final Logger log = LoggerFactory.getLogger(ShortenUrlController.class);

    private final ShortenUrlService shortenUrlService;

    @Autowired
    public ShortenUrlController(ShortenUrlService shortenUrlService) {
        this.shortenUrlService = shortenUrlService;
    }

    @RequestMapping(value = "/shortenurl",
            method = POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create Short Url",
            response = ShortenUrlDto.class,
            notes = "This method creates a Short Url")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "shortenUrlDto",
                    value = "ShortenUrlDto Body",
                    required = true,
                    dataType = "ShortenUrlDto",
                    paramType = "body"
            )
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "Create a New Short Url"),
            @ApiResponse(code = 400, message = "request body is a bad request")
    })
    @ResponseBody
    public ResponseEntity<ShortenUrlDto> createShortenUrl(@RequestBody ShortenUrlDto shortenUrlDto) {
        log.debug(MessageFormat.format("Get shortUrlDto in controller: {0}", shortenUrlDto.toString()));
        try {
            shortenUrlService.create(shortenUrlDto);
            return new ResponseEntity<>(shortenUrlDto, HttpStatus.CREATED);
        } catch (BadRequestException e) {
            shortenUrlDto.setMessage(e.getMessage());
            return new ResponseEntity<>(shortenUrlDto, e.getStatus());
        }
    }

    @RequestMapping(value = "/shortenurl/{tiny}",
            method = GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get URL according to Short Url",
            response = ShortenUrlDto.class,
            notes = "get URL according to Short Url")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "tiny",
                    value = "Short URL",
                    required = true,
                    dataType = "String",
                    paramType = "path"
            )
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "get the long url by short one"),
            @ApiResponse(code = 404, message = "can not found the long url by short one")
    })
    @ResponseBody
    public ResponseEntity<ShortenUrlDto> getLongUrl(@PathVariable("tiny") String tiny) {
        log.debug(MessageFormat.format("Get tiny url in controller: {0}", tiny));
        try {
            return new ResponseEntity<>(shortenUrlService.redirect(tiny), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(new ShortenUrlDto(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/shortenurl/{tiny}",
            method = DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get URL according to Short Url",
            response = ShortenUrlDto.class,
            notes = "get URL according to Short Url")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "tiny",
                    value = "Short URL",
                    required = true,
                    dataType = "String",
                    paramType = "path"
            )
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "delete the long url by short one"),
            @ApiResponse(code = 404, message = "can not found the long url by short one")
    })
    @ResponseBody
    public ResponseEntity<ShortenUrlDto> deleteLongUrl(@PathVariable("tiny") String tiny) {
        log.debug(MessageFormat.format("Get tiny url in controller: {0}", tiny));
        try {
            return new ResponseEntity<>(shortenUrlService.delete(tiny), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(new ShortenUrlDto(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

}
