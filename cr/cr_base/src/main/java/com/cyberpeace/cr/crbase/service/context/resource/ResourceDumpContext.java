package com.cyberpeace.cr.crbase.service.context.resource;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.google.common.collect.Lists;

import lombok.Data;

/**
 * 数据导入导出模型
 */
@Data
public class ResourceDumpContext implements Serializable {

	private List<BusinessDumpContext> items = Lists.newArrayList();

	private List<Mission> template_questions = Lists.newArrayList();

	private Map<String, String> file;
}
