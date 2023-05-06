package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

/**
 * cp流
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class CpFlow implements Serializable {

	private Map<String, ElementFlowList> elements;

}
