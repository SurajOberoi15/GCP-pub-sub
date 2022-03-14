//package com.loblaw.loblowsampleservice.dto;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//public class UserInfoTest {
//
//    @Test
//    public void equalAndHashCode_with_equal_object_should_match(){
//        UserInfo userInfo1 = new UserInfo();
//        userInfo1.setName("test");
//        userInfo1.setEmail("test@xyz.com");
//        userInfo1.setCustomerId(1);
//
//        UserInfo userInfo2 = new UserInfo();
//        userInfo2.setName("test");
//        userInfo2.setEmail("test@xyz.com");
//        userInfo2.setCustomerId(1);
//
//        Assertions.assertEquals(userInfo1,userInfo2);
//        Assertions.assertEquals(userInfo1.toString(),userInfo2.toString());
//        Assertions.assertEquals(userInfo1.hashCode(),userInfo2.hashCode());
//
//
//    }
//
//    @Test
//    public void equalAndHashCode_with_unequal_object_should_not_match(){
//        UserInfo userInfo1 = new UserInfo();
//        userInfo1.setName("test");
//        userInfo1.setEmail("test@xyz.com");
//        userInfo1.setCustomerId(1);
//
//        UserInfo userInfo2 = new UserInfo();
//        userInfo2.setName("test1");
//        userInfo2.setEmail("test1@xyz.com");
//        userInfo2.setCustomerId(2);
//
//        Assertions.assertNotEquals(userInfo1,userInfo2);
//        Assertions.assertNotEquals(userInfo1.toString(),userInfo2.toString());
//        Assertions.assertNotEquals(userInfo1.hashCode(),userInfo2.hashCode());
//
//
//    }
//}
