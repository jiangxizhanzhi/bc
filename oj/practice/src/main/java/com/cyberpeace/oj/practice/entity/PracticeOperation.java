package com.cyberpeace.oj.practice.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;

import com.cyberpeace.oj.practice.entity.PracticeBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@TableName("oj_practice_operation")
public class PracticeOperation extends PracticeBase {

	/** 附件*/
	@ApiModelProperty("附件编号")
	private String attachmentId;

	/** 题解*/
	@ApiModelProperty("题解")
	private String writeup;

	/** 是否公开题解*/
	@ApiModelProperty("是否公开题解")
	private Boolean writeupPublic;

	/** 动态答题环境(使用场景)*/
	@ApiModelProperty("是否为动态答题环境")
	private Boolean dynamicEnv;

	/** 关联场景管理ID*/
	@ApiModelProperty("拓扑编号")
	private String topoId;

	/** 共享模式 (拥有场景时使用)*/
	@ApiModelProperty("共享模式")
	private Integer sceneShareMode;

	/** 是否使用动态FLAG (拥有场景时使用)*/
	@ApiModelProperty("是否使用动态FLAG")
	private Integer dynamicFlag;

	/** 场景存活时间 单位/h (拥有场景时使用)*/
	@ApiModelProperty("场景存活时间")
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
