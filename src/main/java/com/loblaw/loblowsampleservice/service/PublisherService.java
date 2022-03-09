package com.loblaw.loblowsampleservice.service;


import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import com.loblaw.loblowsampleservice.dto.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PublisherService {


    private final String projectID;

    private final String topicID;

    private Publisher publisher;

    PublisherService(@Value("${pubsub.projectId}") String projectID,
                     @Value("${pubsub.topicId}") String topicID) {
        this.projectID = projectID;
        this.topicID = topicID;

    }

    public String messagePublisher(UserInfo userInfo) throws IOException, ExecutionException, InterruptedException {

        log.info("TESTING");
        TopicName topicName = TopicName.of(projectID,topicID);


        log.info("Message Info :{}",userInfo);

        try {
            publisher = Publisher.newBuilder(topicName).build();
            ByteString messageData = ByteString.copyFromUtf8(userInfo.toString());
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(messageData).build();
            ApiFuture<String> publishedMessage = publisher.publish(pubsubMessage);
            log.info("Message id generated:{}", publishedMessage.get());
            return "Message Generated:" + publishedMessage.get();
        } finally {
            if(publisher != null){
                publisher.shutdown();
                publisher.awaitTermination(30, TimeUnit.SECONDS);
            }
        }
    }
}
