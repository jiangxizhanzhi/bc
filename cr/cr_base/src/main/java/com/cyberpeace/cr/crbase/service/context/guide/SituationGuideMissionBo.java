package com.cyberpeace.cr.crbase.service.context.guide;

import lombok.Data;

/**
 * 态势导调任务Bo
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class SituationGuideMissionBo {

    /**
     * 房间id
     */
    private String roomId;

    /**
     * 业务模板id
     */
    private String businessTemplateId;

    /**
     * 岗位配置
     */
    private String roleConfig;

    /**
     * 阶段
     */
    private String stage;

    /**
     * 标题
     */
    private String title;

    /**
     * 任务模板id
     */
    private String missionTemplateId;

}
