package com.cyberpeace.cr.crbase.service.context.resource;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;

import lombok.Data;

/**
 *
 * 资源导出中间模型
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class ResourceDumpContext<T> implements Serializable {

	private List<MissionTemplate> missions;

	private BusinessTemplate businessTemplate;

	private T business;

	private Map<String, String> files;

	private JSONObject sceneData;

	private Integer type;
}
