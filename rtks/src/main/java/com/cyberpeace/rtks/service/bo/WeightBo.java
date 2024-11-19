package com.cyberpeace.rtks.service.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 权重Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class WeightBo implements Serializable {

    /**
     * id
     *
     * @see String
     */
    private String id;

    /**
     * 权重
     *
     * @see Integer
     */
    private Integer weight;

}
