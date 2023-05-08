package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 元素流列表
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class ElementFlowList implements Serializable {

	private List<ElementFlow> flows;
}
