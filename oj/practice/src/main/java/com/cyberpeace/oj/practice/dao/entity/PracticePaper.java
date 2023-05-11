package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("oj_practice_paper")
public class PracticePaper extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	//题目状态
	private String practiceStatus;

	//出题数量
	private Integer count;

	//题目难度
	private String difficulty;

	//题目类型
	private String type;

	//方向选择
	private String direction;

	//是否完成
	private Boolean isFinish;

	//组卷人
	private String userId;

}
