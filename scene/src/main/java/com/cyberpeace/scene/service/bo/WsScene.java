package com.cyberpeace.scene.service.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author :   SN
 * @date :   2024/1/4 9:47
 * @desc :
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WsScene implements Serializable {

    @JsonProperty("create_consume_seconds")
    private Integer createConsumeSeconds;

    @JsonProperty("create_time")
    private Date createTime;

    private String description;

    @JsonProperty("display_context")
    private String displayContext;

    private String eid;

    private Integer event;

    @JsonProperty("event_status")
    private Integer eventStatus;

    private String file;

    private String name;

    private String process;

    @JsonProperty("process_message")
    private String processMessage;

    @JsonProperty("progress_status")
    private Integer progressStatus;

    @JsonProperty("progress_percentage")
    private Integer progressPercentage;

    private String stage;

    @JsonProperty("stage_list")
    private List<WsSceneStage> stageList;

    @JsonProperty("stage_name")
    private String stageName;

    @JsonProperty("stage_percentage")
    private Integer stagePercentage;

    @JsonProperty("update_time")
    private Date updateTime;
}
