package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.JsonUtil;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@TableName("cr_eval_cloud_template")
public class EvalCloudTemplate extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 试验名称
	 */
	private String name;

	/**
	 * 试验目标
	 */
	private String target;

	/**
	 * 封面url
	 */
	private String logo;

	/**
	 * 试验类型
	 */
	private Integer type;

	/**
	 * 计划时长
	 */
	private Integer time;

	/**
	 * 训练难度
	 */
	private Integer difficulty;

	/**
	 * 云类型
	 */
	private Integer cloudType;

	/**
	 * 关联topo uuid
	 */
	private String topoId;

	/**
	 * 步骤id列表
	 */
	private String stepIds;

	/**
	 * 创建者id
	 */
	private String createUserId;

	/**
	 * 拓扑LOGO
	 */
	private String topo;


	public void setStepIdList(List<String> stepIds) {
		this.stepIds = JsonUtil.toJsonString(stepIds);
	}

	public List<String> getStepIdList() {
		if (StringUtils.isBlank(this.stepIds)) {
			return new ArrayList<>();
		}else {
			return JsonUtil.toJsonObjectArray(this.stepIds, String.class);
		}
	}


//	试验类型
	public enum TestType implements GenericEnum {

		EQUIPMENT_EVALUATION(0, "装备测评"),
		PENETRATION_TEST(1, "渗透测试"),
		SECURITY_VERIFICATION(2, "安全验证"),
		SYSTEM_TESTING(3, "系统测试");

		@Getter
		private final Integer code;

		@Getter
		private final String desc;

		TestType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
		public static String getDesc(Integer code) {
			for (TestType item : TestType.values()) {
				if (item.getCode().equals(code)) {
					return item.getDesc();
				}
			}
			return null;
		}
	}

//	云类型
	public enum CloudType implements GenericEnum {

		MIX(0, "混合云"),
		PRIVATE(1, "私有云"),
		PUBLIC(2, "公有云");

		@Getter
		private final Integer code;

		@Getter
		private final String desc;

		CloudType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}