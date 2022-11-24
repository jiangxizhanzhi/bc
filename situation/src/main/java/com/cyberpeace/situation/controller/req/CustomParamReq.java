package com.cyberpeace.situation.controller.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 24/11/2022 上午11:56
 */
@Data
public class CustomParamReq implements Serializable {

    private String name;

    private String value;

}
