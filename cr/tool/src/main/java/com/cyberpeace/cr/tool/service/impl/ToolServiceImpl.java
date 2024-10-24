package com.cyberpeace.cr.tool.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.scene.DeviceInfo;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.tool.controller.res.ToolRsp;
import com.cyberpeace.cr.tool.controller.res.ToolTypeCount;
import com.cyberpeace.cr.tool.dao.context.ToolListQuery;
import com.cyberpeace.cr.tool.dao.context.ToolStatistics;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ToolServiceImpl extends ServiceImpl<ToolMapper, Tool> implements ToolService {

	@Autowired
	private ToolMapper toolMapper;

	@Autowired
	private ToolCategoryService toolCategoryService;

	@Autowired
	private ToolFileMapper toolFileMapper;

	@Autowired
	private RemoteTerminalService remoteTerminalService;

	@Override
	@Transactional(rollbackFor = { Exception.class }, transactionManager = "transactionManager")
	public boolean saveBatch(Collection<Tool> entityList) {
		return super.saveBatch(entityList);
	}

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

	private void setAboutDevice(ToolRsp toolRsp, Tool tool) {
		if (!StringUtils.isBlank(tool.getImageResourceId())) {
			DeviceInfo deviceInfo = remoteTerminalService.getById(tool.getImageResourceId());
			toolRsp.setDeviceName(deviceInfo.getName());
		}
	}

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

	private ToolRsp toBo(Tool tool, boolean isDetail) {
		ToolRsp toolRsp = BeanUtils.copy(tool, ToolRsp.class);
		setAboutCategory(toolRsp, tool);
		setAboutDevice(toolRsp, tool);

		toolRsp.setLanguage(Arrays.asList(StringUtils.split(tool.getLanguage(), ",")));
		toolRsp.setPlatforms(Arrays.asList(StringUtils.split(tool.getPlatforms(), ",")));

		toolRsp.setTag(JsonUtil.toJsonObjectArray(tool.getTag(), String.class));

		if (isDetail) {
			setAboutFile(toolRsp, tool);
		}

		toolRsp.setCreateTime(DateUtil.DateToString(tool.getCreateTime(), DateUtil.SHOT_PATTERN));
		toolRsp.setUpdateTime(DateUtil.DateToString(tool.getUpdateTime(), DateUtil.SHOT_PATTERN));

		return toolRsp;
	}

	@Override
	public IPage<ToolRsp> selectPage(Page page, ToolListQuery toolListQuery, Boolean isAdmin) {
		IPage<Tool> result = toolMapper.list(page, toolListQuery, isAdmin);
		return result.convert(tool -> toBo(tool, false));
	}

	@Override
	public ToolRsp selectById(String resourceId) {
		Tool tool = getById(resourceId);
		return toBo(tool, true);
	}

	@Override
	public Tool getByName(String name) {
		return toolMapper.selectByName(name);
	}

	@Override
	public void validateToolNameExist(Tool tool) {
		// 名称重复性校验
		Tool toolObject = toolMapper.selectByName(tool.getName());
		if (toolObject != null) {
			log.error("toolObject is  null,tool =[{}]", JsonUtil.toJsonString(tool));
			throw new BusinessException(CrBusinessErrorCode.DUPLICATED_TOOL_NAME);
		}
	}

	@Override
	public void validateEditToolNameExist(Tool tool){
		// 名称重复性校验
		Tool toolObject = toolMapper.selectOtherByName(tool.getName(),tool.getResourceId());
		if (toolObject != null) {
			log.error("toolObject is  null,tool =[{}]", JsonUtil.toJsonString(tool));
			throw new BusinessException(CrBusinessErrorCode.DUPLICATED_TOOL_NAME);
		}
	}

	@Override
	public List<ToolTypeCount> statistics() {
		List<ToolTypeCount> typeCounts = new ArrayList<>();
		List<ToolStatistics> tools = toolMapper.statistics();
		String thisMonth = TimeUtil.format11(new Date());
		String lastMonth = TimeUtil.format11(TimeUtil.addMonths(new Date(), -1));
		List<ToolStatistics> typeTools;
		for (Tool.Type type : Tool.Type.values()) {
			ToolTypeCount toolTypeCount = new ToolTypeCount();
			toolTypeCount.setName(type.getDesc());
			typeTools = tools.stream().filter(tool -> type.getCode().equals(tool.getType())).collect(Collectors.toList());
			toolTypeCount.setCount(typeTools.size());
			toolTypeCount.setThisAdd((int) typeTools.stream().filter(tool -> thisMonth.equals(tool.getDate())).count());
			toolTypeCount.setLastAdd((int) typeTools.stream().filter(tool -> lastMonth.equals(tool.getDate())).count());
			typeCounts.add(toolTypeCount);
		}
		ToolTypeCount toolTypeCount = new ToolTypeCount();
		toolTypeCount.setName("总计");
		toolTypeCount.setCount(tools.size());
		toolTypeCount.setThisAdd((int) tools.stream().filter(tool -> thisMonth.equals(tool.getDate())).count());
		toolTypeCount.setLastAdd((int) tools.stream().filter(tool -> lastMonth.equals(tool.getDate())).count());
		typeCounts.add(toolTypeCount);
		return typeCounts;
	}

    @Override
    public List<Tool> listByIdsInOrder(List<String> ids) {
		 List<Tool> res =  Lists.newArrayList();
		if(ids.isEmpty()){
			return res;
		}
		List<Tool> toolList = this.listByIds(ids);
		Map<String, Tool> toolMap = StreamApiUtils.toMap(toolList, Tool::getResourceId, t->t);
		for (String id : ids) {
			if (toolMap.containsKey(id)){
				res.add(toolMap.get(id));
			}
		}
		return res;
    }
}
