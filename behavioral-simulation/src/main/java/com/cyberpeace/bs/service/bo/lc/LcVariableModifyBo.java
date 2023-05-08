package com.cyberpeace.bs.service.bo.lc;

import lombok.Data;

import java.io.Serializable;

/**
 * 远程变量修改请求体
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class LcVariableModifyBo implements Serializable {

    /**
     * 变量id
     */
    private Long dataId;

    /**
     * 名称
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
