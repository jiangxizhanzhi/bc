package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;


@Data
@TableName("oj_classroom_group_info")
public class ClassroomGroupInfo extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String classroomId;

	private Integer groupNo;

	private String groupUserIds;

	private Integer destroyDelay;

	private Integer sceneShareMode;

	private String lessonId;

	private String topoId;

}
