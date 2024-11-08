package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * 练习错误
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@TableName("oj_practice_mistake")
public class PracticeMistake extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 实践id
	 */
	private String practiceId;

	/**
	 * 执业类型
	 */
	private Integer practiceType;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 正确
	 */
	private Integer correct;

	/**
	 * 不對
	 */
	private Integer incorrect;
}
