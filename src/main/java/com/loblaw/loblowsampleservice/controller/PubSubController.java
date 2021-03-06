package com.loblaw.loblowsampleservice.controller;

import com.loblaw.loblowsampleservice.dto.ResponseDTO;
import com.loblaw.loblowsampleservice.service.PublisherService;
import com.loblaw.loblowsampleservice.service.SubscriberService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class PubSubController {

    @Autowired
    PublisherService publisherService;

    @Autowired
    SubscriberService subscriberService;

    @GetMapping("/getMessage")
    public ResponseEntity<String> messageConsumer() {
        ResponseDTO responseDTO = subscriberService.messageSubscriber();
        return new ResponseEntity<>(new JSONObject(responseDTO.getUserInfo()).toString(), HttpStatus.OK);
    }


    @PostMapping("/publishMessage")
    public ResponseEntity<String> publishMessage(@RequestBody String userInfo) {
        log.info("User info entered:{}", userInfo);
        String response;
        try {
            response = publisherService.messagePublisher(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            response = "Some Error Occurred";
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Message Generated: " + response, HttpStatus.OK);
    }

//    @GetMapping(value="/user")
//    public Principal user(Principal principal) {
//        return principal;
//    }

}
