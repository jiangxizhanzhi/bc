package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * CP 流
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class CpFlow implements Serializable {

	/**
	 * 元素
	 */
	private Map<String, ElementFlowList> elements;

}
