package com.cyberpeace.rtks.service.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 重量 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class WeightBo implements Serializable {

    /**
     * id
     */
    private String id;

    /**
     * 重量
     */
    private Integer weight;

}
