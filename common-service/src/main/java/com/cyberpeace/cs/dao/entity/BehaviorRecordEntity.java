package com.cyberpeace.cs.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 行为记录实体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@TableName("behavior_record")
public class BehaviorRecordEntity {

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 模块
	 */
	private Integer module;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 行动
	 */
	private Integer action;

	/**
	 * 目标
	 */
	private String target;

	/**
	 * 结果
	 */
	private Integer result;

	/**
	 * 链接
	 */
	private String link;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 时间
	 */
	private Date time;

}
