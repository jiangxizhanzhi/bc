package com.cyberpeace.cr.tool.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.scene.DeviceInfo;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.tool.controller.res.ToolRsp;
import com.cyberpeace.cr.tool.controller.res.ToolTypeCount;
import com.cyberpeace.cr.tool.dao.context.MonthCount;
import com.cyberpeace.cr.tool.dao.context.ToolListQuery;
import com.cyberpeace.cr.tool.dao.context.ToolStatistics;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.dao.entity.ToolCategory;
import com.cyberpeace.cr.tool.dao.entity.ToolFile;
import com.cyberpeace.cr.tool.dao.mapper.ToolFileMapper;
import com.cyberpeace.cr.tool.dao.mapper.ToolMapper;
import com.cyberpeace.cr.tool.service.ToolCategoryService;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.cr.tool.service.context.TacticTool;
import com.cyberpeace.integratation.service.RemoteTerminalService;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 工具Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码是一个名为 `ToolServiceImpl` 的服务实现类，用于处理与工具（`Tool`）相关的业务逻辑。以下是该类的主要功能和关键点的简要分析： 
 * ### 类概述 
 * - **包名**：`com.cyberpeace.cr.tool.service.impl` 
 * - **继承关系**：该类继承自 `ServiceImpl<ToolMapper, Tool>`，这是 MyBatis-Plus 提供的一个基类，用于简化数据访问层的操作。 
 * ### 主要属性 
 * - **toolMapper**：用于执行与 `Tool` 相关的数据库操作。 
 * - **toolCategoryService**：用于处理工具类别相关的业务逻辑。 
 * - **toolFileMapper**：用于执行与 `ToolFile` 相关的数据库操作。 
 * - **remoteTerminalService**：用于远程终端相关的业务逻辑。 
 * ### 方法分析 
 * 1. **saveBatch** 
 * - 保存一批工具对象。 
 * - 使用了事务管理，确保操作的原子性。 
 * 2. **setAboutCategory** 
 * - 设置工具的类别信息。 
 * - 根据工具的类别ID查询类别名称，并设置到响应对象中。 
 * 3. **setAboutDevice** 
 * - 设置工具关联的设备信息。 
 * - 通过设备ID查询设备信息并设置到响应对象中。 
 * 4. **setAboutFile** 
 * - 设置工具关联的文件信息。 
 * - 根据文件ID查询文件信息并设置到响应对象中。 
 * 5. **toBo** 
 * - 将工具对象转换为响应对象（`ToolRsp`）。 
 * - 包含类别、设备、文件等信息的设置。 
 * 6. **selectPage** 
 * - 分页查询工具列表。 
 * - 返回分页结果，并将工具对象转换为响应对象。 
 * 7. **selectById** 
 * - 根据ID查询工具详细信息。 
 * - 返回详细的工具响应对象。 
 * 8. **getByName** 
 * - 根据名称查询工具。 
 * - 返回匹配的工具对象。 
 * 9. **validateToolNameExist** 
 * - 验证工具名称是否存在。 
 * - 如果名称已存在，则抛出异常。 
 * 10. **validateEditToolNameExist** 
 * - 验证编辑工具名称是否存在（排除自身）。 
 * - 如果名称已存在，则抛出异常。 
 * 11. **statistics** 
 * - 统计工具类型和数量。 
 * - 返回按类型统计的结果，包括过去六个月的数据。 
 * 12. **listByIdsInOrder** 
 * - 根据ID列表查询工具并保持顺序。 
 * - 返回按输入顺序排列的工具列表。 
 * 13. **listTacticTool** 
 * - 列出策略工具。 
 * - 返回包含策略工具信息的列表。 
 * 14. **getIdNameMap** 
 * - 获取工具ID与名称的映射。 
 * - 返回工具ID到名称的映射关系。 
 * 15. **checkBuiltin** 
 * - 检查工具是否为内置工具。 
 * - 如果是内置工具，则抛出异常。 
 * ### 总结 
 * `ToolServiceImpl` 类主要负责处理工具的增删改查以及相关业务逻辑，如验证工具名称、统计工具信息等。它利用了 MyBatis-Plus 和 Spring 的事务管理来保证数据的一致性和完整性。 
**/
@Slf4j
@Service
public class ToolServiceImpl extends ServiceImpl<ToolMapper, Tool> implements ToolService {

	/**
	 * 工具Mapper
	 */
	@Autowired
	private ToolMapper toolMapper;

	/**
	 * 工具类别Service
	 */
	@Autowired
	private ToolCategoryService toolCategoryService;

	/**
	 * 工具文件Mapper
	 */
	@Autowired
	private ToolFileMapper toolFileMapper;

	/**
	 * 远程终端Service
	 */
	@Autowired
	private RemoteTerminalService remoteTerminalService;

	/**
	 * 保存批量
	 *
	 * @param entityList 实体列表
	 * @return boolean
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
	 */
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

	/**
	 * 查询分页
	 *
	 * @param page          分页
	 * @param toolListQuery 工具列表查询
	 * @param isAdmin       是管理员
	 * @return {@code IPage<ToolRsp> }
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
	 */
	@Override
	public Tool getByName(String name) {
		return toolMapper.selectByName(name);
	}

	/**
	 * 验证工具名称存在
	 *
	 * @param tool 工具
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

	/**
	 * 统计学
	 *
	 * @return {@code List<ToolTypeCount> }
	 */
	@Override
	public List<ToolTypeCount> statistics() {
		List<ToolTypeCount> typeCounts = new ArrayList<>();
		List<ToolStatistics> tools = toolMapper.statistics();
		List<ToolStatistics> typeTools;
		for (Tool.Type type : Tool.Type.values()) {
			ToolTypeCount toolTypeCount = new ToolTypeCount();
			toolTypeCount.setName(type.getDesc());
			typeTools = tools.stream().filter(tool -> type.getCode().equals(tool.getType())).collect(Collectors.toList());
			toolTypeCount.setCount(typeTools.size());
			List<MonthCount> monthCounts = new ArrayList<>();
			for (int i = 0 ; i < 6 ; i++) {
				String month = TimeUtil.format11(TimeUtil.addMonths(new Date(), -i));
				monthCounts.add(new MonthCount(month, (int) typeTools.stream().filter(tool -> month.equals(tool.getDate())).count()));
			}
			toolTypeCount.setCountList(monthCounts);
			typeCounts.add(toolTypeCount);
		}
		ToolTypeCount toolTypeCount = new ToolTypeCount();
		toolTypeCount.setName("总计");
		toolTypeCount.setCount(tools.size());
		List<MonthCount> monthCounts = new ArrayList<>();
		for (int i = 0 ; i < 6 ; i++) {
			String month = TimeUtil.format11(TimeUtil.addMonths(new Date(), -i));
			monthCounts.add(new MonthCount(month, (int) tools.stream().filter(tool -> month.equals(tool.getDate())).count()));
		}
		toolTypeCount.setCountList(monthCounts);
		typeCounts.add(toolTypeCount);
		return typeCounts;
	}

	/**
	 * 列表由id列表在次序
	 *
	 * @param ids id列表
	 * @return {@code List<Tool> }
	 */
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

	/**
	 * 列出策略工具
	 *
	 * @param toolIds 工具id列表
	 * @return {@code List<TacticTool> }
	 */
	@Override
	public List<TacticTool> listTacticTool(List<String> toolIds) {
		List<TacticTool> res = Lists.newArrayList();
		List<Tool> toolList = this.listByIdsInOrder(toolIds);
		if(toolList.isEmpty()){
			return res;
		}
		List<ToolCategory> toolCategories = toolCategoryService.selectAll();
		Map<String, String> categoryMap = StreamApiUtils.toMap(toolCategories, ToolCategory::getResourceId, ToolCategory::getCnName);
		List<Integer> toolFileIds = Lists.newArrayList();
		for (Tool tool : toolList) {
			List<Integer> fileList = LocalStringUtil.strToList(tool.getFiles());
			if (CollectionUtils.isNotEmpty(fileList)) {
				toolFileIds.addAll(fileList);
			}
		}
		List<ToolFile> toolFiles = toolFileMapper.selectByIds(toolFileIds);
		Map<Integer, ToolFile> toolFileMap = StreamApiUtils.toMap(toolFiles, ToolFile::getId,
				toolFile -> toolFile);
		for (Tool tool : toolList) {
			TacticTool tacticTool = BeanUtils.copy(tool, TacticTool.class);
			List<String> categoryIds = LocalStringUtil.strToListStr(tool.getCategory());
			List<String> categoryNames = StreamApiUtils.map(categoryIds, categoryMap::get);
			categoryNames = StreamApiUtils.filter(categoryNames, Objects::nonNull);
			tacticTool.setCategoryName(StringUtils.join(categoryNames, ","));
			tacticTool.setCategoryI18nName(tacticTool.getCategoryName());
			tacticTool.setPlatforms(Arrays.asList(StringUtils.split(tool.getPlatforms(), ",")));
			List<Integer> fileList = LocalStringUtil.strToList(tool.getFiles());
			List<ToolFile> toolFileList = StreamApiUtils.map(fileList, toolFileMap::get);
			tacticTool.setToolFiles(StreamApiUtils.filter(toolFileList, Objects::nonNull));
			res.add(tacticTool);
		}
		return res;
	}

	/**
	 * 获取id名称映射
	 *
	 * @param toolIds 工具id列表
	 * @return {@code Map<String, String> }
	 */
	@Override
	public Map<String, String> getIdNameMap(List<String> toolIds) {
		List<Tool> toolList = this.listByIdsInOrder(toolIds);
		return StreamApiUtils.toMap(toolList, Tool::getResourceId, Tool::getName);
	}

	@Override
	public void checkBuiltin(String resourceId) {
		Tool tool = this.getById(resourceId);
		if (tool.getBuiltin()) {
			throw new BusinessException(ErrorCode.BUILT_IN);
		}
	}
}

