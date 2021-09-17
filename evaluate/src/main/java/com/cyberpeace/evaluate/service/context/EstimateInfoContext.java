package com.cyberpeace.evaluate.service.context;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.EstimateVariableLinks;

import lombok.Data;

@Data
public class EstimateInfoContext {

    /**评估名称 */
    private String name;

    /**评估描述 */
    private String desc;

    /** */
    private String logo;

    /**拓扑id */
    private String topoId;

    /**评估方案id */
    private String estimateSchemeId;

    /**用户id */
    private String userId;

    List<EstimateVariableLinks> estimateVariableLinks;
}
