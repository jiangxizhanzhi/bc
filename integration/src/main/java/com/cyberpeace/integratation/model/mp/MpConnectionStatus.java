package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

import lombok.Data;

/**
 * 中台连接状态
 *
 * @author SN
 * @date 2024/11/08
 */
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
