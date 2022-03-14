package com.loblaw.loblowsampleservice.dto;

import lombok.Data;
import org.json.JSONObject;

@Data
public class ResponseDTO {
    private String messageID;
    private JSONObject userInfo;
}
