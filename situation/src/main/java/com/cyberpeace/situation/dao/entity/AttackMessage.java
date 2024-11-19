package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 攻击消息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("situation_attack_message")
public class AttackMessage extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(value = "resource_id")
	private String resourceId;

	/**
	 * 场景id
	 *
	 * @see String
	 */
	private String sceneId;

	/**
	 * 类型
	 *
	 * @see String
	 */
	private String type;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 源id
	 *
	 * @see String
	 */
	private String sourceId;

	/**
	 * 目标id
	 *
	 * @see String
	 */
	private String targetId;

	/**
	 * 间隔时间
	 *
	 * @see Integer
	 */
	private Integer intervalTime;

	/**
	 * 持续时间
	 *
	 * @see Integer
	 */
	private Integer durationTime;

	/**
	 * 查看目标
	 *
	 * @see Boolean
	 */
	private Boolean lookAtTarget;

	/**
	 * 线路类型
	 *
	 * @see String
	 */
	private String lineType;

	/**
	 * 线条颜色
	 *
	 * @see String
	 */
	private String lineColor;

	/**
	 * 线宽
	 *
	 * @see Integer
	 */
	private Integer lineWidth;

	/**
	 * 媒体动画
	 *
	 * @see String
	 */
	private String mediaAnimation;

}
