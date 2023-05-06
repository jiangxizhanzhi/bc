package com.cyberpeace.bs.service.bo.lc;

import lombok.Data;

import java.io.Serializable;

/**
 * 远程变量基础信息
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class LcVariableBase implements Serializable {

    /**
     * 变量id
     */
    private String dataId;

    /**
     * 变量名称
     */
    private String name;

    /**
     * 变量值
     */
    private String varValue;

    /**
     * 变量备注
     */
    private String note;
}
