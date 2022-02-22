package com.cyberpeace.cr.hld.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("cr_hld_room")
public class HldRoom extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String templateId;

	private String taskId;

	private String localSceneId;

	private String copyedTopoId;

	private String roleConfig;

	private Integer status;

	private String createUserId;

	private String modifyUserId;

	public boolean isUnopened() {
		return status.equals(StatusCode.UNOPENED.getCode());
	}

	public boolean isPaused() {
		return status.equals(StatusCode.PAUSE.getCode());
	}

	public boolean isOpening() {
		return status.equals(StatusCode.RUNNING.getCode());
	}

	public boolean isEnded() {
		return status.equals(StatusCode.ENDED.getCode());
	}

	public enum StatusCode {

		UNOPENED(4, "未开始"), RUNNING(2, "已开始"), ENDED(0, "已结束"), PAUSE(1, "已暂停"), RECOVERY(3, "恢复");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private StatusCode(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}
