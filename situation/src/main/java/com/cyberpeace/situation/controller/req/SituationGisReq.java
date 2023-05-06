package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.util.JsonUtil;

import lombok.Data;

/**
 * GIS配置请求
 *
 * @Author xuxz
 * @Date 27/2/2023 下午2:15
 */
@Data
public class SituationGisReq implements Serializable {

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
		// 如果config为null，config字段也设置为null
		this.config = config == null ? null : JsonUtil.toJsonString(config);
	}
}
