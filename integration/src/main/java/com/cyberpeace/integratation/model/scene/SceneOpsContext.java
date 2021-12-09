package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * 操作场景实例的上下文。
 * 部分场景相关接口需要传入该数据
 */
@Data
public class SceneOpsContext implements Serializable {

	private SceneMpContext context = new SceneMpContext();

	private String id;

}
