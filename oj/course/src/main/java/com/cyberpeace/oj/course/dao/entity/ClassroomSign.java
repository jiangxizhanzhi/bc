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
 * 5.6 update: 5.6自动签到取消，而该表实际上也和协助、监控功能相关，导致未签到情况下无法请求协助；所以将原先的自动签到功能移动到访问课堂接口中
 * 见CourseLessonService.getLessonDetail
 * 手动签到功能signIn=true，自动签到signIn=false
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("oj_classroom_sign")
public class ClassroomSign extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 课堂id
	 *
	 * @see String
	 */
	private String classroomId;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 登录
	 *
	 * @see Boolean
	 */
	private Boolean signIn;

	/**
	 * 需要帮助
	 *
	 * @see Integer
	 */
	private Integer needHelp;

	/**
	 * 签到时间
	 *
	 * @see Date
	 */
	private Date signInTime;

	/**
	 * 节点id
	 *
	 * @see String
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
