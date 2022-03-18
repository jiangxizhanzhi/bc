package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("situation_config")
public class SituationConfig {

	private Integer id;

	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	private String scope;

	private String nodeId;

	private String nodeName;

	private Integer nodeType;

	private Integer attribute;

	private String attributeValue;

	public SituationConfig() {

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

		HEIGHT(1, "高度");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private Attribute(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}
