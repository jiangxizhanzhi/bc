package com.cyberpeace.situation.controller.res;

import java.util.List;

import com.cyberpeace.integratation.model.situation.ThirdDictContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 事件类型返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class EventTypeRes extends ThirdDictContext {

	/**
	 * 子类型
	 */
	@ApiModelProperty("编码")
	private List<ThirdDictContext> subTypes;

	public EventTypeRes() {}

	/**
	 * 构造函数
	 *
	 * @param code 代码
	 * @param name 名称
	 */
	public EventTypeRes(String code, String name) {
		this.setName(name);
		this.setCode(code);
	}

}
