package com.cyberpeace.cr.crbase.service.context;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.CommonContext;
import com.google.common.collect.Lists;

import lombok.Data;
import lombok.Getter;

@Data
public class TrainingReport {

	private String teacherName = "";

	private Date sceneStartTime;

	private Integer trainingTime = 0;

	private List<UserReport> users = Lists.newArrayList();

	public enum TIME implements GenericEnum {

		DAY(1, "天", "day"), HOUR(2, "小时", "hour"), MINUTE(3, "分钟", "minute"), SECOND(4, "秒",
				"second");

		@Getter
		private Integer code;

		private String desc;

		private String enDesc;

		@Override
		public String getDesc() {
			String aaa="222";

			if (StringUtils.equals(aaa, "222")) {
				return enDesc;
			} else {
				return desc;
			}
		}

		TIME(Integer code, String desc, String enDesc) {
			this.code = code;
			this.desc = desc;
			this.enDesc = enDesc;
		}
	}
}
