package com.cyberpeace.integratation.model.mp;

import lombok.Data;

import java.io.Serializable;

@Data
public class MpFlowAttackCount implements Serializable {


    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 次数
     */
    private Integer times;


}
