package com.loblaw.loblowsampleservice.service;

import com.google.api.core.SettableApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.PublisherFactory;
import com.google.cloud.spring.pubsub.support.SubscriberFactory;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.isA;

@Slf4j
@SpringBootTest
class PublisherServiceTest {

    @Mock
    private PublisherFactory mockPublisherFactory;

    @Mock
    private SubscriberFactory mockSubscriberFactory;

    private PubSubTemplate pubSubTemplate;
    @Mock
    private Publisher publisher;

    private SettableApiFuture<String> settableApiFuture;

    private PubsubMessage pubsubMessage;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private PubSubTemplate createTemplate() {
        PubSubTemplate pubSubTemplate = new PubSubTemplate(this.mockPublisherFactory, this.mockSubscriberFactory);

        return pubSubTemplate;
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        this.pubSubTemplate = createTemplate();
        Mockito.when(this.mockPublisherFactory.createPublisher("testTopic"))
                .thenReturn(this.publisher);
        this.settableApiFuture = SettableApiFuture.create();
        Mockito.when(this.publisher.publish(isA(PubsubMessage.class)))
                .thenReturn(this.settableApiFuture);
        this.pubsubMessage = PubsubMessage.newBuilder().setData(
                ByteString.copyFrom("testing publisher".getBytes())).build();
    }


    @Test
    void test() {

        this.pubSubTemplate.publish("testTopic", "testPayload");
        Mockito.verify(this.publisher, Mockito.times(1))
                .publish(isA(PubsubMessage.class));

    }

    @Test
    void testPublish() throws ExecutionException, InterruptedException {
        this.settableApiFuture.set("result");
        ListenableFuture<String> future = this.pubSubTemplate.publish("testTopic",
                this.pubsubMessage);

        assertThat(future.get()).isEqualTo("result");
    }

    @Test
    void testPublish_String() {
        this.pubSubTemplate.publish("testTopic", "testPayload");

        Mockito.verify(this.publisher, Mockito.times(1))
                .publish(isA(PubsubMessage.class));
    }

    @Test
    void testPublish_Bytes() {
        this.pubSubTemplate.publish("testTopic", "testPayload".getBytes());

        Mockito.verify(this.publisher, Mockito.times(1))
                .publish(isA(PubsubMessage.class));
    }

    @Test
    void testPublish_withHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Debugged code", "Error code");
        headers.put("Hotfix", "bug resolved");

        this.pubSubTemplate.publish("testTopic", "jaguar god", headers);

        Mockito.verify(this.publisher).publish(argThat((message) ->
                message.getAttributesMap().get("Debugged code").equals("Error code") &&
                        message.getAttributesMap().get("Hotfix").equals("bug resolved")));
    }


}
