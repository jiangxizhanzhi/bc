package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("situation_config")
public class SituationConfig {

	@ApiModelProperty("自增主键")
	private Integer id;

	@ApiModelProperty("资源id")
	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	@ApiModelProperty("作用域")
	private String scope;

	@ApiModelProperty("节点id")
	private String nodeId;

	@ApiModelProperty("节点名称")
	private String nodeName;

	@ApiModelProperty("节点类型")
	private Integer nodeType;

	@ApiModelProperty("参数类型")
	private Integer attribute;

	@ApiModelProperty("参数值")
	private String attributeValue;

	public SituationConfig() {
	// SituationConfig
	}

	public enum NodeType {

		AREA(1, "区域"), ELEMENT(2, "节点");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private NodeType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

	public enum Attribute {

		HEIGHT(1, "高度", "hierarchy"), NAME(2, "名称", "name"), COLOR(3, "颜色", "color");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		@Getter
		private String field;

		private Attribute(Integer code, String desc, String field) {
			this.code = code;
			this.desc = desc;
			this.field = field;
		}

	}
}
