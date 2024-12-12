package com.cyberpeace.oj.practice.service.externalbo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 理论简单博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class TheorySimpleBo implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    private String resourceId;

    /**
     * 理论类型
     *
     * @see Integer
     */
    private Integer theoryType;

}
