package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

/**
 * 节点动态更新
 *
 * @author SN
 */
@Data
public class NodeUpdateContext extends SceneOpsContext implements Serializable {

	private JSONObject update;
}
