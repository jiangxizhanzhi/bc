package com.cyberpeace.situation.constant.topo;

import lombok.Getter;

/**
 * 拓扑Demo
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum DemoTopo {

	/**
	 * 默认
	 */
	DEFAULT("demo", "默认"),

	/**
	 * 创建中
	 */
	TEST("test", "测试5000节点");

	/**
	 * 代码
	 */
	@Getter
	private String code;

	/**
	 * 描述
	 */
	@Getter
	private String desc;

	DemoTopo(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
