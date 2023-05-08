package com.cyberpeace.bs.service.bo.lc;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 远程变量响应
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class LcVariableRsp implements Serializable {
    /**
     * 流程图id
     */
    private String id;

    /**
     * 变量
     */
    private List<LcVariableRspBo> variables;
}
