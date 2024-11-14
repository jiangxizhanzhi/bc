package com.cyberpeace.oj.course.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.*;

/**
 * 教室标志
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("oj_classroom_sign")
public class ClassroomSign extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 课堂id
	 */
	private String classroomId;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 登录
	 */
	private Boolean signIn;

	/**
	 * 需要帮助
	 */
	private Integer needHelp;

	/**
	 * 签到时间
	 */
	private Date signInTime;

	/**
	 * 节点id
	 */
	private String nodeId;

	public enum NEED_HELP implements GenericEnum {

		/** 协助状态*/
		NO_HELP(0, "未请求协助"),
		NEED_HELP(1, "请求协助"),
		IN_HELP(2, "协助中"),
		END_HELP(3, "结束协助");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		NEED_HELP(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

}
