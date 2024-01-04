package com.cyberpeace.scene.service.bo;

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

    private Integer createConsumeSeconds;

    private Date createTime;

    private String description;

    private String displayContext;

    private String eid;

    private Integer event;

    private Integer eventStatus;

    private String file;

    private String name;

    private String process;

    private String processMessage;

    private Integer processStatus;

    private Integer processPercentage;

    private String stage;

    private List<WsSceneStage> stageList;

    private String stageName;

    private Integer stagePercentage;

    private Date updateTime;
}
