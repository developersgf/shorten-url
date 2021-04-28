package com.tinyurl.controller;

import com.tinyurl.dto.ShortenUrlDto;
import com.tinyurl.model.ShortenUrl;
import com.tinyurl.repository.ShortenUrlRepository;
import com.tinyurl.service.ShortUrlService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.hamcrest.core.IsEqual;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ShortenUrlControllerTest {

    ObjectMapper om = new ObjectMapper();

    @Autowired
    ShortUrlService shortUrlService;

    @Autowired
    ShortenUrlRepository shortenUrlRepository;

    @Autowired
    MockMvc mockMvc;

    Map<String, ShortenUrlDto> testData;

    @Before
    public void setup() {
        shortenUrlRepository.deleteAll();
        testData = getTestData();
    }

    @Test
    public void testCreate_Success() throws Exception {
        //test new creation
        ShortenUrlDto expectedRecord = testData.get("create-01");
        mockMvc.perform(post("/shortenurl")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecord)))
                .andDo(print())
                .andExpect(status().isCreated());

        List<ShortenUrl> list = shortenUrlRepository.findAll();
        Assert.assertNotNull(list);
        Assert.assertEquals(list.get(0).getLongUrl(), expectedRecord.getLongUrl());
    }

    @Test
    public void testGet_Success() throws Exception {
        //test new creation
        ShortenUrlDto toCreate = testData.get("create-01");
        mockMvc.perform(post("/shortenurl")
                .contentType("application/json")
                .content(om.writeValueAsString(toCreate)))
                .andDo(print())
                .andExpect(status().isCreated());

        List<ShortenUrl> list = shortenUrlRepository.findAll();
        String tiny = list.get(0).getId();
        mockMvc.perform(get("/shortenurl/"+tiny)
                .contentType("application/json")
                .content(om.writeValueAsString(toCreate)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.longUrl", IsEqual.equalTo(toCreate.getLongUrl())));
    }

    @Test
    public void testDelete_Success() throws Exception {
        //test new creation
        ShortenUrlDto toCreate = testData.get("create-01");
        mockMvc.perform(post("/shortenurl")
                .contentType("application/json")
                .content(om.writeValueAsString(toCreate)))
                .andDo(print())
                .andExpect(status().isCreated());

        List<ShortenUrl> list = shortenUrlRepository.findAll();
        String tiny = list.get(0).getId();
        mockMvc.perform(delete("/shortenurl/"+tiny)
                .contentType("application/json")
                .content(om.writeValueAsString(toCreate)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", IsEqual.equalTo("deleted")));
    }

    private Map<String, ShortenUrlDto> getTestData() {
        Map<String, ShortenUrlDto> data = new HashMap<>();
        ShortenUrlDto create01 = new ShortenUrlDto();
        create01.setLongUrl("https://docs.spring.io/spring-boot/docs/2.4.0/actuator-api/htmlsingle/#overview");
        create01.setClient("public");
        data.put("create-01", create01);
        return data;
    }
}
