package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;


@Data
@TableName("oj_train_plan")
public class TrainPlan extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String name;

	private Integer type;

	private String createUserId;

	private String organizationId;

	private String organizationIds;

	public static String TASK_SET = "Task Set";

	public static String TASK_SET_ID = "task_set_id";

	public static class PlanType {

		public static final Integer NORMAL = 1;

		public static final Integer CONTINUOUS = 2;

	}

	public static class PlanStatus {

		public static final Integer NOT_STARTED = 1;

		public static final Integer ONGOING = 2;

		public static final Integer ENDED = 3;

	}

}
