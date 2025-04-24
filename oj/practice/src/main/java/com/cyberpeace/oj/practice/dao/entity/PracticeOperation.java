package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 实践实操
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PracticeBase
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(callSuper = true)
@TableName("oj_practice_operation")
public class PracticeOperation extends PracticeBase {

	/**
	 * 附件
	 *
	 * @see String
	 */
	@ApiModelProperty("附件编号")
	private String attachmentId;

	/**
	 * 题解
	 *
	 * @see String
	 */
	@ApiModelProperty("题解")
	private String writeup;

	/**
	 * 是否公开题解
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否公开题解")
	private Boolean writeupPublic;

	/**
	 * 动态答题环境(使用场景)
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否为动态答题环境")
	private Boolean dynamicEnv;

	/**
	 * 关联场景管理ID
	 *
	 * @see String
	 */
	@ApiModelProperty("拓扑编号")
	private String topoId;

	/**
	 * 共享模式 (拥有场景时使用)
	 *
	 * @see Integer
	 */
	@ApiModelProperty("共享模式")
	private Integer sceneShareMode;

	/**
	 * 是否使用动态FLAG (拥有场景时使用)
	 *
	 * @see Integer
	 */
	@ApiModelProperty("是否使用动态FLAG 0=静态 1=动态 2=随机, 3=非flag")
	private Integer dynamicFlag;

	/**
	 * 场景存活时间 单位/h (拥有场景时使用)
	 *
	 * @see Integer
	 */
	@ApiModelProperty("场景存活时间")
	private Integer destroyDelay;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "类型，常量type=49，1：攻击，2：防守，3：其他")
	private Integer type;

	/**
	 * 需要外层
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否需要外部操作机，0=不需要，1=需要")
	private Boolean needOuter;

	/**
	 * 是否支持组网
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否支持组网，0=不支持，1=支持")
	private Boolean networking;

	@ApiModelProperty(value = "关联漏洞")
	private String vuln;

	public enum DynamicFlag implements GenericEnum {

		STATIC(0, "静态"), DYNAMIC(1, "动态"), RANDOM(2, "随机"), NOT(3, "非Flag");

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
