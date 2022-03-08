package com.loblaw.loblowsampleservice.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserInfo {

    private String name;
    private String email;
    private int customerId;
}
