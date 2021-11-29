package com.cyberpeace.cr.singletrain.service.bo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class SingleTemplateBo implements Serializable {

    private EventDataBo eventData;

    private JSONObject missionScheme;
}
