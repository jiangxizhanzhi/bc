package com.cyberpeace.situation.controller.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @BelongsPackage: com.cyberpeace.situation.controller.req
 * @Author: hhuang
 * @CreateTime: 2025/05/06  17:36
 * @Description: 变更拓扑请求
 * @Version: 1.0
 */
@Data
public class ChangeTopoReq implements Serializable {

    /**
     * 场景实例id
     */
    private String sceneId;

    /**
     * 拓扑id
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
