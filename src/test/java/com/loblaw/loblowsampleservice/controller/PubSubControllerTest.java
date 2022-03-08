package com.loblaw.loblowsampleservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loblaw.loblowsampleservice.dto.UserInfo;
import com.loblaw.loblowsampleservice.service.PublisherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@AutoConfigureMockMvc
public class PubSubControllerTest {

    @Mock
    PublisherService publisherService;
    @InjectMocks
    PubSubController pubSubController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = standaloneSetup(this.pubSubController).build();
    }

    @Test
    public void publishMessage_with_requiredData_should_return_2xx() throws Exception {

        UserInfo userInfo1 = new UserInfo();
        userInfo1.setName("test");
        userInfo1.setEmail("test@xyz.com");
        userInfo1.setCustomerId(1);
        String request = new ObjectMapper().writeValueAsString(userInfo1);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/publishMessage")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(request))
                .andExpect(MockMvcResultMatchers.status().isOk());
//                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void publishMessage_with_invalidData_should_return_4xx() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/publishMessage")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//                .andDo(MockMvcResultHandlers.print());
    }
}
