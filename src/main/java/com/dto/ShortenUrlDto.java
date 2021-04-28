package com.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Request object for POST method")
public class ShortenUrlDto {
    public ShortenUrlDto() {
    }

    @ApiModelProperty(required = true, notes = "Url to convert to short")
    private String longUrl;

    @ApiModelProperty(required = false, notes = "which client send this request")
    private String client;

    private String tinyUrl;
    private LocalDateTime createDate;
    private LocalDateTime expiresDate;

    @ApiModelProperty(required = false, notes = "message to client, maybe error message")
    private String message;

    public ShortenUrlDto(String message) {
        this.message = message;
    }
}
