package com.cyberpeace.cr.evaluation.service.bo;

import lombok.Data;

/**
 * 排名信息
 */
@Data
public class EvalRankTaskBo {

    private String submitId;

    private String taskId;

    private Integer score;

    private String missionId;

    private String userId;

    private String roleId;

    private String roomId;

    private String evalTemplateId;

}
