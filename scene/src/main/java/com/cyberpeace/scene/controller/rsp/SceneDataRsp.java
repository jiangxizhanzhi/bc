package com.cyberpeace.scene.controller.rsp;

import com.cyberpeace.scene.dao.entity.SceneEntity;
import com.cyberpeace.scene.service.bo.WsSceneStage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author :   SN
 * @date :   2024/1/5 13:46
 * @desc :
 **/
@Data
public class SceneDataRsp extends SceneEntity {

    private Integer event;

    private Integer eventStatus;

    @JsonProperty("stage_list")
    private List<WsSceneStage> stageList;

    @JsonProperty("stage_percentage")
    private Integer stagePercentage;

    @JsonProperty("progress_message")
    private String progressMessage;

    @JsonProperty("progress_status")
    private Integer progressStatus;

    @JsonProperty("progress_percentage")
    private Integer progressPercentage;

    private String stage;

    @JsonProperty("stage_name")
    private String stageName;
}
