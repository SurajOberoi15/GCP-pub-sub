package com.loblaw.loblowsampleservice.controller;

import com.google.cloud.pubsub.v1.Publisher;
import com.loblaw.loblowsampleservice.dto.ResponseDTO;
import com.loblaw.loblowsampleservice.dto.UserInfo;
import com.loblaw.loblowsampleservice.service.PublisherService;
import com.loblaw.loblowsampleservice.service.SubscriberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class PubSubController {

    @Autowired
    PublisherService publisherService;

    @Autowired
    SubscriberService subscriberService;

    @GetMapping("/getMessage")
    public ResponseEntity<ResponseDTO> messageConsumer(){
        ResponseDTO responseDTO = subscriberService.messageSubscriber();
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }


    @PostMapping("/publishMessage")
    public ResponseEntity<String> publishMessage(@RequestBody UserInfo userInfo){
        log.info("User info entered:{}", userInfo);
        String response = null;
        try {
           response = publisherService.messagePublisher(userInfo);
        } catch (Exception e) {
            log.error("Exception Occurred" + e.getMessage());
            response = "Some Error Occurred";
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Message Generated: " + response, HttpStatus.OK);
    }

    @GetMapping(value="/user")
    public Principal user(Principal principal) {
        return principal;
    }

}
