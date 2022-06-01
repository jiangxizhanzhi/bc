package com.cyberpeace.oj.course.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("oj_classroom_sign")
public class ClassroomSign extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String classroomId;

	private String userId;

	private Boolean signIn;

	private Integer needHelp;

	private Date signInTime;

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
