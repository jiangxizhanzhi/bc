package com.cyberpeace.cr.tool.service.impl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.dao.entity.ToolCategory;
import com.cyberpeace.cr.tool.dao.mapper.ToolCategoryMapper;
import com.cyberpeace.cr.tool.dao.mapper.ToolFileMapper;
import com.cyberpeace.cr.tool.dao.mapper.ToolMapper;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.cr.tool.service.bo.ToolBo;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@Service
public class ToolServiceImpl extends ServiceImpl<ToolMapper, Tool> implements ToolService {

	@Autowired
	private ToolMapper toolMapper;

	@Value("${cr.uploaded_file_prefix}")
	private String urlPrefix;

	@Autowired
	private ToolCategoryMapper toolCategoryMapper;

	private void setAboutCategory(ToolBo toolBo, Tool tool) {
		List<Integer> categoryList = LocalStringUtil.strToList(tool.getCategory());

		List<ToolCategory> toolCategories = toolCategoryMapper.selectByIds(categoryList);
		List<String> i18nName = Lists.newArrayList();
		for (ToolCategory toolCategory : toolCategories) {
			// todo i18n
			i18nName.add(toolCategory.getCnName());
		}
		toolBo.setCategoryI18nName(Joiner.on(",").join(i18nName));
	}

	private void setAboutDevice(ToolBo toolBo, Tool tool) {
		toolBo.setDeviceName("");
	}


	private ToolBo toBo(Tool tool) {
		ToolBo toolBo = BeanUtils.copy(tool, ToolBo.class);
		setAboutCategory(toolBo, tool);
		setAboutDevice(toolBo, tool);

		toolBo.setLanguage(Arrays.asList(StringUtils.split(tool.getLanguage(), ",")));
		toolBo.setPlatformsNames(tool.getPlatforms());

		toolBo.setPublishTime(new SimpleDateFormat("yyyy-MM-dd").format(tool.getPublishTime()));
		return toolBo;
	}

	@Override
	public ListRsp<ToolBo> selectPage(PageReqBase pageReqBase) {
		Page page = pageReqBase.toPageParam();
		IPage<Tool> result = toolMapper.list(page, pageReqBase.getPattern());
		result.convert(this::toBo);
		return PageUtil.getListResult(result);
	}
}
