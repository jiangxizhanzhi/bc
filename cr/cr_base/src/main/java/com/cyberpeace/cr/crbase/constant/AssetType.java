package com.cyberpeace.cr.crbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum AssetType implements GenericEnum {
	OPERATING_SYSTEM(1,"操作系统"),
	BUSINESS_NODES(2, "业务节点"),
	NETWORK_NODE(3,"网络节点"),
	SECURITY_NODE(4, "安全节点");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	AssetType(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
