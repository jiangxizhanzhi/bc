package com.cyberpeace.integratation.service.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @BelongsPackage: com.cyberpeace.integratation.service.bo
 * @Author: hhuang
 * @CreateTime: 2025/05/06  17:53
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeTopoConfigBo {

    /**
     * 场景拓扑Id
     */
    private String topoId;

    /**
     * 变更的区域位置
     */
    private List<AreaChangeData> areaDataList;

    /**
     * 变更的节点位置
     */
    private List<ElementChangeData> elementDataList;
}
