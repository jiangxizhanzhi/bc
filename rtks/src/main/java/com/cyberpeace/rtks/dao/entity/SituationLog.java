package com.cyberpeace.rtks.dao.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.constant.BaseI18n;

import lombok.Data;
import lombok.Getter;

/**
 * 态势日志
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
@TableName("rtks_situation_log")
public class SituationLog implements Serializable {

	/**
	 * id
	 *
	 * @see Integer
	 */
	private Integer id;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 冗余，记录用户名，暂时不用
	 *
	 * @see String
	 */
	private String userName;

	/**
	 * 岗位id
	 *
	 * @see String
	 */
	private String roleId;

	/**
	 * 岗位名称
	 *
	 * @see String
	 */
	private String roleName;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	private Integer type;

	/**
	 * 创建时间
	 *
	 * @see Date
	 */
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
