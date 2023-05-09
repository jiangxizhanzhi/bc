package com.cyberpeace.bs.service.bo.simulator;

import lombok.Data;

import java.io.Serializable;

/**
 * 行为配置
 * 变量json未解析
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class BehaviorConfigRaw implements Serializable {

    /**
     * 行为id
     */
    private String behaviorId;

    /**
     * 变量
     */
    private String variables;
}
