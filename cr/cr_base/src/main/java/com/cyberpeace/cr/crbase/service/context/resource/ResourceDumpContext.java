package com.cyberpeace.cr.crbase.service.context.resource;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;

import lombok.Getter;
import lombok.Setter;

/**
 * 资源导出中间模型
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class ResourceDumpContext<T> implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 任务
	 */
	private List<MissionTemplate> missions;

	/**
	 * 业务模板
	 */
	private BusinessTemplate businessTemplate;

	/**
	 * 业务
	 */
	private T business;

	/**
	 * 文件
	 */
	private Map<String, String> files;

	/**
	 * 场景数据
	 */
	private JSONObject sceneData;

	/**
	 * 类型
	 */
	private Integer type;
}
