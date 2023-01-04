package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;
import java.util.Arrays;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class ConnectionContext implements Serializable {

	@ApiModelProperty("链路ID")
	private String connectionId;

	@ApiModelProperty("链路类型")
	private String connectionType;

	@ApiModelProperty("源节点ID")
	private String source;

	@ApiModelProperty("目标节点ID")
	private String target;

	@ApiModelProperty("元素层级")
	private Float hierarchy;

	public enum ConnectionType {

		DB("1", "短波"), CDB("2", "超短波"), WB("3", "微波"), WXTXLL("4", "卫星通信链路"), YDTX("5",
				"移动通信"), TZDL("8",
						"同轴电缆"), GX("9", "光纤"), SJX("10", "双绞线"), ZCB("11", "中长波"), QT("99", "其它");

		@Getter
		private String code;

		@Getter
		private String desc;

		private ConnectionType(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	public void setConnectionType(String type) {
		this.connectionType = Arrays.stream(ConnectionType.class.getEnumConstants())
				.filter(t -> t.getCode().equals(type)).findFirst().orElse(ConnectionType.QT)
				.getDesc();
	}

}
