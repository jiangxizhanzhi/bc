package com.cyberpeace.scene.service.bo;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.scene.dao.entity.SceneEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author :   SN
 * @date :   2024/1/3 10:41
 * @desc :
 **/
@Data
public class SceneData implements Serializable {

    private Topology topology;

    private SceneEntity scene;

    private JSONObject plugins;
}
