package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 中台主机事件
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MpHostEvent implements Serializable {

	/**
	 * 事件名称
	 *
	 * @see String
	 */
	private String eventName;

	/**
	 * 触发器类型
	 *
	 * @see String
	 */
	private String triggerType;

	/**
	 * 操作类型
	 *
	 * @see String
	 */
	private String actionType;

	/**
	 * 目标节点id
	 *
	 * @see String
	 */
	private String targetNodeId;

	/**
	 * 目标节点名称
	 *
	 * @see String
	 */
	private String targetNodeName;

	/**
	 * 目标节点ip地址
	 *
	 * @see String
	 */
	private String targetNodeIp;

	/**
	 * 对抗cks
	 *
	 * @see List<String>
	 */
	private List<String> att_cks;

	/**
	 * 查看混沌骑士名字
	 *
	 * @see List<String>
	 */
	private List<String> att_ck_names;

	/**
	 * 主机事件类型
	 *
	 * @see String
	 */
	private String hostEventType;

	/**
	 * 主机事件描述
	 *
	 * @see String
	 */
	private String hostEventDesc;

	/**
	 * 时间
	 *
	 * @see Date
	 */
	private Date time;

}
