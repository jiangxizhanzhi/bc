package com.cyberpeace.cr.crbase.service.context.resource;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;

import lombok.Data;

/**
 * 资源导出中间模型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class ResourceDumpContext<T> implements Serializable {

	/**
	 * 任务
	 *
	 * @see List<MissionTemplate>
	 */
	private List<MissionTemplate> missions;

	/**
	 * 业务模板
	 *
	 * @see BusinessTemplate
	 */
	private BusinessTemplate businessTemplate;

	/**
	 * 业务
	 *
	 * @see T
	 */
	private T business;

	/**
	 * 文件
	 *
	 * @see Map<String, String>
	 */
	private Map<String, String> files;

	/**
	 * 场景数据
	 *
	 * @see JSONObject
	 */
	private JSONObject sceneData;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	private Integer type;
}
