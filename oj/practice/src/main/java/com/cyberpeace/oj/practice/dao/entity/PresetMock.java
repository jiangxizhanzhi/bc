package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 预置模拟考试类型
 * @author SN
 * @date 2024/01/04
 */
@Data
@TableName("oj_preset_mock")
public class PresetMock extends BaseTable {

	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 计数
	 */
	private Integer count;

	/**
	 * theory描述
	 */
	private String theoryDesc;

	/**
	 * 实操描述
	 */
	private String operationDesc;

	/**
	 * 理论length, min
	 */
	private Integer theoryLength;

	/**
	 * 实操length, min
	 */
	private Integer operationLength;

	/**
	 * 理论json
	 */
	private String theory;

	/**
	 * 实操json
	 */
	private String operation;

	/**
	 * 是否真题
	 */
	private boolean actual;

	private String logo;

}
