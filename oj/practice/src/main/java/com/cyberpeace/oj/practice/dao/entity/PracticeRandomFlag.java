package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("oj_practice_random_flag")
public class PracticeRandomFlag extends BaseTable {

	private String userId;

	private String attachmentId;

	private String practiceId;

	private String flag;

}
