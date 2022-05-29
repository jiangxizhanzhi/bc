package com.cyberpeace.cs.service.context;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.util.JsonUtil;
import com.google.common.collect.Lists;

import lombok.Data;
import lombok.Getter;

@Data
public class GxTaskListContext extends GxTaskContext implements Serializable {

	private Integer traineesCount;

	private Integer validityPeriod;

	private Integer status;

	private String eventId;

	private Integer eventType;

	private String result;

	private List<String> userIds;

	public enum StatusEnum implements GenericEnum {

		UNSTARTED(1, "未开始"), RUNNING(2, "运行中"), FINISHED(3, "已上报");

		@Getter
		private final Integer code;

		@Getter
		private final String desc;

		StatusEnum(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

	public void setTaskStatus() {
		if (this.result != null) {
			this.status = StatusEnum.FINISHED.getCode();
		} else if (this.eventId != null) {
			this.status = StatusEnum.RUNNING.getCode();
		} else {
			this.status = StatusEnum.UNSTARTED.getCode();
		}
	}

	public void setUserIds(String trainees) {

		List<String> userIds = Lists.newArrayList();

		List<JSONObject> traineeList = JsonUtil.toJsonObjectArray(trainees, JSONObject.class);

		for (JSONObject user : traineeList) {
			userIds.add(user.getString("userId"));
		}

		this.userIds = userIds;

	}

}