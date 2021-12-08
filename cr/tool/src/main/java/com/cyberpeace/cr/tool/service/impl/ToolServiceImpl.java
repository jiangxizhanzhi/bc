package com.cyberpeace.cr.tool.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.scene.DeviceInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.DateUtil;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.dao.entity.ToolCategory;
import com.cyberpeace.cr.tool.dao.entity.ToolFile;
import com.cyberpeace.cr.tool.dao.mapper.ToolCategoryMapper;
import com.cyberpeace.cr.tool.dao.mapper.ToolFileMapper;
import com.cyberpeace.cr.tool.dao.mapper.ToolMapper;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.cr.tool.service.bo.ToolBo;
import com.cyberpeace.integratation.service.RemoteTerminalService;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

@Service
public class ToolServiceImpl extends ServiceImpl<ToolMapper, Tool> implements ToolService {

	@Autowired
	private ToolMapper toolMapper;

	@Autowired
	private ToolCategoryMapper toolCategoryMapper;

	@Autowired
	private ToolFileMapper toolFileMapper;

	@Autowired
	private RemoteTerminalService remoteTerminalService;

	@Override
	@Transactional(
			rollbackFor = {Exception.class},
			transactionManager = "transactionManager"
	)
	public boolean saveBatch(Collection<Tool> entityList) {
		return super.saveBatch(entityList);
	}

	private void setAboutCategory(ToolBo toolBo, Tool tool) {
		List<String> categoryList = LocalStringUtil.strToListStr(tool.getCategory());
		toolBo.setCategory(categoryList);

		List<ToolCategory> toolCategories = toolCategoryMapper.selectByResourceIds(categoryList);
		List<String> i18nName = Lists.newArrayList();

		for (ToolCategory toolCategory : toolCategories) {
			if (CommonContext.getLanguage().equals(WebConstant.LANGUAGE_ZH)) {
				i18nName.add(toolCategory.getCnName());
			} else {
				i18nName.add(toolCategory.getEnName());
			}
		}
		toolBo.setCategoryI18nName(Joiner.on(",").join(i18nName));
	}

	private void setAboutDevice(ToolBo toolBo, Tool tool) {
		if (!StringUtils.isBlank(tool.getImageResourceId())) {
			DeviceInfo deviceInfo = remoteTerminalService.getById(tool.getImageResourceId());
			toolBo.setDeviceName(deviceInfo.getName());
		}
	}

	private void setAboutFile(ToolBo toolBo, Tool tool) {
		List<Integer> fileList = LocalStringUtil.strToList(tool.getFiles());
		toolBo.setFiles(fileList);

		List<ToolFile> toolFiles = toolFileMapper.selectByIds(fileList);
		toolBo.setToolFiles(toolFiles);
	}

	private ToolBo toBo(Tool tool, boolean isDetail) {
		ToolBo toolBo = BeanUtils.copy(tool, ToolBo.class);
		setAboutCategory(toolBo, tool);
		setAboutDevice(toolBo, tool);

		toolBo.setLanguage(Arrays.asList(StringUtils.split(tool.getLanguage(), ",")));
		toolBo.setPlatformsNames(tool.getPlatforms());

		if (isDetail) {
			setAboutFile(toolBo, tool);
		}

		toolBo.setPublishTime(DateUtil.DateToString(tool.getPublishTime(), DateUtil.SHOT_PATTERN));
		return toolBo;
	}

	@Override
	public ListRsp<ToolBo> selectPage(PageReqBase pageReqBase) {
		Page page = pageReqBase.toPageParam();
		IPage<Tool> result = toolMapper.list(page, pageReqBase.getPattern());
		result.convert(tool -> toBo(tool, false));
		return PageUtil.getListResult(result);
	}

	@Override
	public ToolBo selectById(String resourceId) {
		Tool tool = getById(resourceId);
		return toBo(tool, true);
	}

	@Override
	public Tool getByName(String name) {
		return toolMapper.selectByName(name);
	}
}
