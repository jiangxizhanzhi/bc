package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * 练习试卷
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@TableName("oj_practice_paper")
public class PracticePaper extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 练习状态
	 *///题目状态
	private String practiceStatus;

	/**
	 * 计数
	 *///出题数量
	private Integer count;

	/**
	 * 难度
	 *///题目难度
	private String difficulty;

	/**
	 * 类型
	 *///题目类型
	private String type;

	/**
	 * 方向
	 *///方向选择
	private String direction;

	/**
	 * 是否完成
	 *///是否完成
	private Boolean isFinish;

	/**
	 * 用户id
	 *///组卷人
	private String userId;

	private Boolean aiCreateFlag = false;

}
