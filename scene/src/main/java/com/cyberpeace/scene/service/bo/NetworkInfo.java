package com.cyberpeace.scene.service.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author :   SN
 * @date :   2023/12/21 19:11
 * @desc :
 **/
@Data
public class NetworkInfo implements Serializable {

    private String name;

    private String cidr;

    private String gateway;
}
