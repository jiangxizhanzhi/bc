package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.service.context.scene.FlowElementFlow;
import lombok.Data;

/**
 * 单个流量
 */
@Data
public class Flow implements Serializable {

	private String nodeId;

	private String nodeName;

	private String id;

	private String name;

	private String desc;

	private Integer interactiveType;

	private Integer status;

	private String statusText;

	private Integer count;

}

