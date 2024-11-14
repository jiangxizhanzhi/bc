package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 中台主机事件
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class MpHostEvent implements Serializable {

	/**
	 * 事件名称
	 */
	private String eventName;

	/**
	 * 触发器类型
	 */
	private String triggerType;

	/**
	 * 操作类型
	 */
	private String actionType;

	/**
	 * 目标节点id
	 */
	private String targetNodeId;

	/**
	 * 目标节点名称
	 */
	private String targetNodeName;

	/**
	 * 目标节点ip地址
	 */
	private String targetNodeIp;

	/**
	 * 对抗cks
	 */
	private List<String> att_cks;

	/**
	 * 查看混沌骑士名字
	 */
	private List<String> att_ck_names;

	/**
	 * 主机事件类型
	 */
	private String hostEventType;

	/**
	 * 主机事件描述
	 */
	private String hostEventDesc;

	/**
	 * 时间
	 */
	private Date time;

}
