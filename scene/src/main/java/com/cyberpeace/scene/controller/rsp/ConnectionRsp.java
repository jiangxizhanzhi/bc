package com.cyberpeace.scene.controller.rsp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author :   SN
 * @date :   2024/1/3 14:31
 * @desc :
 **/
@Data
public class ConnectionRsp implements Serializable {

    private String nodeId;

    private String protocol;

    private String connectionUrl;
}
