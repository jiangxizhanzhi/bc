package com.cyberpeace.cs.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("oj_classroom_scores")
public class ClassroomScore extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String taskId;

	private String classroomId;

	private String lessonId;

	private String userId;

	private Integer count;

	private Integer score;

	public ClassroomScore() {}

	public ClassroomScore(String taskId, String classroomId, String lessonId, String userId) {
		this.taskId = taskId;
		this.classroomId = classroomId;
		this.lessonId = lessonId;
		this.userId = userId;
		this.count = 0;
		this.score = 0;

	}

}
