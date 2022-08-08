package com.cyberpeace.gateway.controller.req;

import java.io.Serializable;

import lombok.Data;

@Data
public class HldTokenReq implements Serializable {

    private String sessionId;

    private String userId;
}
