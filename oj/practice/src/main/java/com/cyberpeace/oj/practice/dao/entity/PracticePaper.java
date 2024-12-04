package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * 练习论文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("oj_practice_paper")
public class PracticePaper extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 实践状态
	 *
	 * @see String
	 */ //题目状态
	private String practiceStatus;

	/**
	 * 计数
	 *
	 * @see Integer
	 */ //出题数量
	private Integer count;

	/**
	 * 难度
	 *
	 * @see String
	 */ //题目难度
	private String difficulty;

	/**
	 * 类型
	 *
	 * @see String
	 */ //题目类型
	private String type;

	/**
	 * 方向
	 *
	 * @see String
	 */ //方向选择
	private String direction;

	/**
	 * 结束了
	 *
	 * @see Boolean
	 */ //是否完成
	private Boolean isFinish;

	/**
	 * 用户id
	 *
	 * @see String
	 */ //组卷人
	private String userId;

}
