package com.cyberpeace.scene.controller.rsp;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.scene.service.bo.Topology;
import lombok.Data;

import java.io.Serializable;

/**
 * @author :   辛发圣
 * @date :   2024/1/4 20:27
 * @desc :
 **/
@Data
public class SceneConfigRsp implements Serializable {

    private Topology topology;

    private JSONObject plugins;
}
