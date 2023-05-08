package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.util.JsonUtil;

import lombok.Data;

/**
 * 拓扑配置请求
 *
 * @Author xuxz
 * @Date 27/2/2023 下午2:15
 */
@Data
public class SituationTopoReq implements Serializable {

	/**
	 * 资源id
	 */
	@NotBlank(groups = { Update.class })
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 配置
	 */
	private String config;

	/**
	 * 缩略图
	 */
	private String thumbnail;

	/**
	 * 设置配置
	 *
	 * @param config 配置
	 */
	public void setConfig(JSONObject config) {
		this.config = config == null ? null : JsonUtil.toJsonString(config);
	}
}
