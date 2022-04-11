package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.oj.ojbase.constant.SceneShare;

import lombok.Data;
import lombok.Getter;

@Data
@TableName("oj_practice_operation")
public class PracticeOperation extends PracticeBase {

	/** 附件*/
	private String attachmentId;

	/** 题解*/
	private String writeup;

	/** 是否公开题解*/
	private Boolean writeupPublic;

	/** 动态答题环境(使用场景)*/
	private Boolean dynamicEnv;

	/** 关联场景管理ID*/
	private String topoId;

	/** 共享模式 (拥有场景时使用)*/
	private Integer sceneShareMode;

	/** 是否使用动态FLAG (拥有场景时使用)*/
	private Integer dynamicFlag;

	/** 场景存活时间 单位/h (拥有场景时使用)*/
	private Integer destroyDelay;

	public enum DynamicFlag implements GenericEnum {

		STATIC(0, "静态"), DYNAMIC(1, "动态"), RANDOM(2, "随机");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		DynamicFlag(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
