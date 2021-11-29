package com.cyberpeace.cr.singletrain.service.bo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EventDataBo implements Serializable {
    private String name;

    private String description;

    private Integer category;

    private String createUser;

    private List<String> preSkill;

    private List<String> trainSkill;

    private JSONObject sceneConfigData;

    private Integer difficult;

    private Integer durationTime;


}
