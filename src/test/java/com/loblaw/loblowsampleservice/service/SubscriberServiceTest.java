package com.loblaw.loblowsampleservice.service;

import com.google.api.core.ApiService;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.support.PublisherFactory;
import com.google.cloud.spring.pubsub.support.SubscriberFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;


@SpringBootTest
class SubscriberServiceTest {

    @Mock
    private SubscriberFactory mockSubscriberFactory;

    @Mock
    private PublisherFactory mockPublisherFactory;

    @Mock
    private Subscriber mockSubscriber;

    private PubSubTemplate pubSubTemplate;

    private PubSubTemplate createTemplate() {
        PubSubTemplate pubSubTemplate = new PubSubTemplate(this.mockPublisherFactory, this.mockSubscriberFactory);

        return pubSubTemplate;
    }


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        this.pubSubTemplate = createTemplate();
        Mockito.when(this.mockSubscriberFactory.createSubscriber(
                        eq("testSubscription"), isA(MessageReceiver.class)))
                .thenReturn(this.mockSubscriber);
        Mockito.when(this.mockSubscriber.startAsync()).thenReturn(Mockito.mock(ApiService.class));

    }

    @Test
    void testSubscribe() {
        Subscriber subscriber = this.pubSubTemplate.subscribe("testSubscription",
                (message) -> {
                });
        Assertions.assertThat(subscriber).isEqualTo(this.mockSubscriber);
        Mockito.verify(this.mockSubscriber, Mockito.times(1)).startAsync();
    }

}
