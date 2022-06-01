package com.cyberpeace.oj.course.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("oj_classroom")
public class Classroom extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private Date startTime;

	private Date endTime;

	private String courseId;

	private String lessonId;

	private String organizationId;

	private String organizationIds;

	private String createUserId;

	private String userIds;

}
