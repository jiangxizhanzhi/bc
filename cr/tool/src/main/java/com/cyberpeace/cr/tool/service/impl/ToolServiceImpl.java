package com.cyberpeace.cr.tool.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.cyberpeace.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.scene.DeviceInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.DateUtil;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.tool.controller.res.ToolRsp;
import com.cyberpeace.cr.tool.dao.context.ToolListQuery;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.dao.entity.ToolCategory;
import com.cyberpeace.cr.tool.dao.entity.ToolFile;
import com.cyberpeace.cr.tool.dao.mapper.ToolFileMapper;
import com.cyberpeace.cr.tool.dao.mapper.ToolMapper;
import com.cyberpeace.cr.tool.service.ToolCategoryService;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.integratation.service.RemoteTerminalService;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 工具Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see ServiceImpl
 * @see ToolService
 * @since 1.0.0
 */
@Slf4j
@Service
public class ToolServiceImpl extends ServiceImpl<ToolMapper, Tool> implements ToolService {

	/**
	 * 工具Mapper
	 *
	 * @see ToolMapper
	 */
	@Autowired
	private ToolMapper toolMapper;

	/**
	 * 工具类别Service
	 *
	 * @see ToolCategoryService
	 */
	@Autowired
	private ToolCategoryService toolCategoryService;

	/**
	 * 工具文件Mapper
	 *
	 * @see ToolFileMapper
	 */
	@Autowired
	private ToolFileMapper toolFileMapper;

	/**
	 * 远程终端Service
	 *
	 * @see RemoteTerminalService
	 */
	@Autowired
	private RemoteTerminalService remoteTerminalService;

	/**
	 * 保存批量
	 *
	 * @param entityList 实体列表
	 * @return boolean
	 * @throws
	 * @see Collection<Tool>
	 * @see boolean
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class }, transactionManager = "transactionManager")
	public boolean saveBatch(Collection<Tool> entityList) {
		return super.saveBatch(entityList);
	}

	/**
	 * 设置大约类别
	 *
	 * @param toolRsp 工具响应
	 * @param tool    工具
	 * @throws
	 * @see ToolRsp
	 * @see Tool
	 */
	private void setAboutCategory(ToolRsp toolRsp, Tool tool) {
		List<String> categoryList = LocalStringUtil.strToListStr(tool.getCategory());
		toolRsp.setCategory(categoryList);
		if (categoryList.isEmpty()) {
			toolRsp.setCategoryI18nName("");
			return;
		}

		List<ToolCategory> toolCategories = toolCategoryService.selectAll();
		Set<String> categoryIds = Sets.newHashSet();
		categoryIds.addAll(categoryList);

		List<String> i18nName = Lists.newArrayList();

		for (ToolCategory item : toolCategories) {
			if (categoryIds.contains(item.getResourceId())) {
				i18nName.add(item.selectI18nName());
			}
		}

		toolRsp.setCategoryI18nName(Joiner.on(",").join(i18nName));
	}

	/**
	 * 设置大约装置
	 *
	 * @param toolRsp 工具响应
	 * @param tool    工具
	 * @throws
	 * @see ToolRsp
	 * @see Tool
	 */
	private void setAboutDevice(ToolRsp toolRsp, Tool tool) {
		if (!StringUtils.isBlank(tool.getImageResourceId())) {
			DeviceInfo deviceInfo = remoteTerminalService.getById(tool.getImageResourceId());
			toolRsp.setDeviceName(deviceInfo.getName());
		}
	}

	/**
	 * 设置大约文件
	 *
	 * @param toolRsp 工具响应
	 * @param tool    工具
	 * @throws
	 * @see ToolRsp
	 * @see Tool
	 */
	private void setAboutFile(ToolRsp toolRsp, Tool tool) {
		List<Integer> fileList = LocalStringUtil.strToList(tool.getFiles());
		toolRsp.setFiles(tool.getFiles());

		List<ToolFile> toolFiles;
		if (null == fileList || fileList.isEmpty()) {
			toolFiles = Lists.newArrayList();
		} else {
			toolFiles = toolFileMapper.selectByIds(fileList);
		}
		toolRsp.setToolFiles(toolFiles);
	}

	/**
	 * 到 bo
	 *
	 * @param tool     工具
	 * @param isDetail 是详情
	 * @return {@code ToolRsp }
	 * @throws
	 * @see Tool
	 * @see boolean
	 * @see ToolRsp
	 */
	private ToolRsp toBo(Tool tool, boolean isDetail) {
		ToolRsp toolRsp = BeanUtils.copy(tool, ToolRsp.class);
		setAboutCategory(toolRsp, tool);
		setAboutDevice(toolRsp, tool);

		toolRsp.setLanguage(Arrays.asList(StringUtils.split(tool.getLanguage(), ",")));
		toolRsp.setPlatforms(Arrays.asList(StringUtils.split(tool.getPlatforms(), ",")));

		if (isDetail) {
			setAboutFile(toolRsp, tool);
		}

		toolRsp.setCreateTime(DateUtil.DateToString(tool.getCreateTime(), DateUtil.SHOT_PATTERN));
		toolRsp.setUpdateTime(DateUtil.DateToString(tool.getUpdateTime(), DateUtil.SHOT_PATTERN));

		return toolRsp;
	}

	/**
	 * 查询分页
	 *
	 * @param page          分页
	 * @param toolListQuery 工具列表查询
	 * @param isAdmin       是管理员
	 * @return {@code IPage<ToolRsp> }
	 * @throws
	 * @see Page
	 * @see ToolListQuery
	 * @see Boolean
	 * @see IPage<ToolRsp>
	 */
	@Override
	public IPage<ToolRsp> selectPage(Page page, ToolListQuery toolListQuery, Boolean isAdmin) {
		IPage<Tool> result = toolMapper.list(page, toolListQuery, isAdmin);
		return result.convert(tool -> toBo(tool, false));
	}

	/**
	 * 查询由id
	 *
	 * @param resourceId 资源id
	 * @return {@code ToolRsp }
	 * @throws
	 * @see String
	 * @see ToolRsp
	 */
	@Override
	public ToolRsp selectById(String resourceId) {
		Tool tool = getById(resourceId);
		return toBo(tool, true);
	}

	/**
	 * 获取由名称
	 *
	 * @param name 名称
	 * @return {@code Tool }
	 * @throws
	 * @see String
	 * @see Tool
	 */
	@Override
	public Tool getByName(String name) {
		return toolMapper.selectByName(name);
	}

	/**
	 * 验证工具名称存在
	 *
	 * @param tool 工具
	 * @throws
	 * @see Tool
	 */
	@Override
	public void validateToolNameExist(Tool tool) {
		// 名称重复性校验
		Tool toolObject = toolMapper.selectByName(tool.getName());
		if (toolObject != null) {
			log.error("toolObject is  null,tool =[{}]", JsonUtil.toJsonString(tool));
			throw new BusinessException(CrBusinessErrorCode.DUPLICATED_TOOL_NAME);
		}
	}

	/**
	 * 验证编辑工具名称存在
	 *
	 * @param tool 工具
	 * @throws
	 * @see Tool
	 */
	@Override
	public void validateEditToolNameExist(Tool tool){
		// 名称重复性校验
		Tool toolObject = toolMapper.selectOtherByName(tool.getName(),tool.getResourceId());
		if (toolObject != null) {
			log.error("toolObject is  null,tool =[{}]", JsonUtil.toJsonString(tool));
			throw new BusinessException(CrBusinessErrorCode.DUPLICATED_TOOL_NAME);
		}
	}
}
