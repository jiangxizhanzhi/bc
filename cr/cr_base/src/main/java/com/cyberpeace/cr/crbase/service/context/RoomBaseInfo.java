package com.cyberpeace.cr.crbase.service.context;

import lombok.Data;

import java.util.Date;

@Data
public class RoomBaseInfo {
    private String teamTemplateId;

    private String teamTemplateName;

    private String name;

    private String description;

    private Integer subType;

    private String preSkills;

    private String trainSkills;

    private Date startTime;

    private Date endTime;

    private Integer difficulty;

    private Integer trainingStatus;

    private String timeLineConfig;
}
