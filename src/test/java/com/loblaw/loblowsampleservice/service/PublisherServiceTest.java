package com.loblaw.loblowsampleservice.service;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.Topic;
import com.google.pubsub.v1.TopicName;
import com.loblaw.loblowsampleservice.dto.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Slf4j
public class PublisherServiceTest {

    @Mock
    PublisherService publisherService;

    @Mock
    private ProjectName mockProjects;
    @Mock
    private TopicName mockTopics;

    private final String topicIdTest = "test";
    private final String projectIdTest = "project-test";

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test() throws IOException, ExecutionException, InterruptedException {
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setName("test");
        userInfo1.setEmail("test@xyz.com");
        userInfo1.setCustomerId(1);
        TopicName name = TopicName.of(projectIdTest,topicIdTest);
        Mockito.when(TopicName.of(projectIdTest,topicIdTest)).thenReturn(name);
        String response = publisherService.messagePublisher(userInfo1);
        log.info(response);

    }

}
