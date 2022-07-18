package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

import lombok.Data;

@Data
public class MpConnectionStatus implements Serializable {

    /**
     * 连线Id
     */
    private String connectionId;

    /**
     * 连线状态
     */
    private Integer status;

}
