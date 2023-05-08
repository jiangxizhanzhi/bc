package com.cyberpeace.bs.service.bo.lc;

import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 远程变量修改请求体
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class LcVariableModifyReq implements Serializable {

    /**
     * 流程图id
     */
    private Long flowChartId;

    /**
     * 变量
     */
    private List<LcVariableModifyBo> variables = Lists.newArrayList();
}
