package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.*;

import java.util.Date;

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

	private Date signInTime;

	private String nodeId;

	public enum SessionType {

		ALL("all", "监控路径和与远程协助路径"), ASSISTANCE("assistance", "仅获取远程协助路径");

		@Getter
		private String type;

		@Getter
		private String desc;

		SessionType(String type, String desc) {
			this.type = type;
			this.desc = desc;
		}

	}

}
