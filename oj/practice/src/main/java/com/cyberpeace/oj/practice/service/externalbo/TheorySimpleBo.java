package com.cyberpeace.oj.practice.service.externalbo;

import lombok.Data;

import java.io.Serializable;

/**
 * 理论简单Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class TheorySimpleBo implements Serializable {

    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 理论类型
     */
    private Integer theoryType;

}
