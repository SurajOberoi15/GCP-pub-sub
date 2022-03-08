package com.loblaw.loblowsampleservice.service;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;
import com.loblaw.loblowsampleservice.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class SubscriberService {

    @Value("${pubsub.projectId}")
    private String projectID;
    @Value("${pubsub.subscriberId}")
    private String subscriberID;

    public ResponseDTO messageSubscriber() {
        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectID,subscriberID);

        ResponseDTO responseDTO = new ResponseDTO();

        MessageReceiver messageReceiver = (PubsubMessage pubsubMessage, AckReplyConsumer ackReplyConsumer) -> {
            log.info("Message Details...");
            log.info("ID :" + pubsubMessage.getMessageId());
            log.info("DATA: " + pubsubMessage.getData().toStringUtf8());
            responseDTO.setMessageID(pubsubMessage.getMessageId());
            responseDTO.setUserInfo(pubsubMessage.getData().toStringUtf8());
            ackReplyConsumer.ack();
        };

        Subscriber subscriber = null;

        try{
            subscriber = Subscriber.newBuilder(subscriptionName, messageReceiver).build();
            subscriber.startAsync().awaitRunning();
            subscriber.awaitTerminated(2, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            subscriber.stopAsync();
        }
        return responseDTO;
    }

}
