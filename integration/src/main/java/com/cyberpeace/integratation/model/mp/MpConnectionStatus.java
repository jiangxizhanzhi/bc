package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

import lombok.Data;

/**
 * 中台连接状态
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MpConnectionStatus implements Serializable {

    /**
     * 连线Id
     *
     * @see String
     */
    private String connectionId;

    /**
     * 连线状态
     *
     * @see Integer
     */
    private Integer status;

}
