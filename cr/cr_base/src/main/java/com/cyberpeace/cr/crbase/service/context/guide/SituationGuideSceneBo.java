package com.cyberpeace.cr.crbase.service.context.guide;

import lombok.Data;

/**
 * 态势导调场景Bo
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class SituationGuideSceneBo {

    /**
     * 房间id
     */
    private String roomId;
    /**
     * 拓扑id
     */
    private String topoId;
    /**
     * 场景id
     */
    private String sceneId;

}
