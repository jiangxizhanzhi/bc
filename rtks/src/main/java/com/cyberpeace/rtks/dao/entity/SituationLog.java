package com.cyberpeace.rtks.dao.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.constant.BaseI18n;

import lombok.Data;
import lombok.Getter;

@Data
@TableName("rtks_situation_log")
public class SituationLog implements Serializable {

	private Integer id;

	private String userId;

	/**
	 * 冗余，记录用户名，暂时不用
	 */
	private String userName;

	private String roleId;

	private String roleName;

	private Integer type;

	private Date createTime;

	public enum MatchType implements GenericEnum, BaseI18n {

		QUALIFIED(1, "合格", "Qualified"), EXCELLENT(2, "优秀", "Excellent");

		@Getter
		private final Integer code;

		@Getter
		private final String chinese;

		@Getter
		private final String english;

		MatchType(Integer code, String chinese, String english) {
			this.code = code;
			this.chinese = chinese;
			this.english = english;
		}

		@Override
		public String getDesc() {
			return this.getMsg();
		}
	}
}
