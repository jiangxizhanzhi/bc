package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Getter;
import lombok.Setter;

/**
 * 练习错误
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("oj_practice_mistake")
public class PracticeMistake extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 实践id
	 *
	 * @see String
	 */
	private String practiceId;

	/**
	 * 执业类型
	 *
	 * @see Integer
	 */
	private Integer practiceType;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 正确
	 *
	 * @see Integer
	 */
	private Integer correct;

	/**
	 * 不對
	 *
	 * @see Integer
	 */
	private Integer incorrect;
}
