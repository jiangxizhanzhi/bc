package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.util.EnumUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
public class ElementContext implements Serializable {

	@ApiModelProperty("元素ID")
	private String elementId;

	@ApiModelProperty("元素类型")
	private String elementType;

	@ApiModelProperty("元素名称")
	private String name;

	@ApiModelProperty("元素角色")
	private String role;

	@ApiModelProperty("元素描述")
	private String desc;

	@ApiModelProperty("三方靶标链接")
	private String thirdUrl;

	@ApiModelProperty("虚实类型")
	private String vrType = ElementVrType.VIRTUAL.desc;

	@ApiModelProperty("设备类型")
	private String deviceType;

	@ApiModelProperty("设备模型")
	private String model;

	@ApiModelProperty("元素层级")
	private Float hierarchy;

	@ApiModelProperty("父节点ID")
	private String parent;

	@ApiModelProperty("平台ID")
	private String platformId;

	@ApiModelProperty("父节点ID列表")
	private List<String> parentIds;

	@ApiModelProperty("子节点ID列表")
	private List<String> child;

	@ApiModelProperty("节点坐标")
	private PositionContext position;

	public enum ElementRole implements GenericEnum {

		RED(1, "红方"), BLUE(2, "蓝方"), WHITE(3, "白方");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private ElementRole(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	public enum ElementVrType implements GenericEnum {

		VIRTUAL(1, "virtual"), ENTITY(2, "entity");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private ElementVrType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	public void setRole(Integer role) {
		this.role = EnumUtils.getEnumDescByCode(role, ElementRole.class);
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setVrType(Integer type) {
		this.vrType = EnumUtils.getEnumDescByCode(type, ElementVrType.class);
	}

	public void setVrType(String vrType) {
		this.vrType = vrType;
	};

	public void setParents(List<String> ids) {
		this.parentIds = ids;
	}

	public void setModelType(String modelType) {
		this.model = StringUtils.isEmpty(modelType) ? "其他" : modelType;
	}

}
