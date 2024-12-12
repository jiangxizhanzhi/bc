package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import lombok.Getter;
import lombok.Setter;

/**
 * 节点动态更新
 *
 * @author SN
 * @date 2023/05/11
 */
@Getter
@Setter
public class NodeUpdateContext extends SceneOpsContext implements Serializable {

	/**
	 * 更新
	 */
	private JSONObject update;
}
