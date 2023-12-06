package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
@TableName(value = "cr_eval_mission_scheme_template")
public class EvalMissionSchemeTemplate extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	@ApiModelProperty("技战法名称")
	private String title;

	@ApiModelProperty("技战法描述")
	private String description;

	@ApiModelProperty("技战法类型")
	private Integer type;

	@ApiModelProperty("技战法内容")
	private String templateData;

	@ApiModelProperty("模板类型")
	private Integer templateCategory;

	@ApiModelProperty("创建者")
	private String createUserId;

	public enum Type implements GenericEnum {

		TYPICAL(1, "典型攻击技战法"), CUSTOM(2, "自定义技战法");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		Type(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
