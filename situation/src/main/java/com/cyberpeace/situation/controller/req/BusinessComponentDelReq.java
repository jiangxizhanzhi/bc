package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 业务组件删除请求
 *
 * @author dell
 * @date 2023/06/20
 */
@Data
public class BusinessComponentDelReq implements Serializable {

	/**
	 * ID列表
	 */
	private List<String> idList;
}