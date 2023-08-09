package com.cyberpeace.cr.tool.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.scene.DeviceInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.DateUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.service.context.ShareContext;
import com.cyberpeace.cr.tool.controller.req.ToolAddReq;
import com.cyberpeace.cr.tool.controller.req.ToolUpdateReq;
import com.cyberpeace.cr.tool.controller.res.ToolRsp;
import com.cyberpeace.cr.tool.dao.context.ToolListQuery;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.dao.entity.ToolCategory;
import com.cyberpeace.cr.tool.dao.entity.ToolFile;
import com.cyberpeace.cr.tool.dao.mapper.ToolFileMapper;
import com.cyberpeace.cr.tool.dao.mapper.ToolMapper;
import com.cyberpeace.cr.tool.service.ToolCategoryService;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.integratation.service.RemoteTerminalService;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private UtilService utilService;

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

		if (isDetail) {
			setAboutFile(toolRsp, tool);
		}
		toolRsp.setShare(tool.getOperationAuthType());
		toolRsp.setShareTeachers(JsonUtil.toJsonObjectArray(tool.getOperationAuthIds(), String.class));
		toolRsp.setCreateTime(DateUtil.DateToString(tool.getCreateTime(), DateUtil.SHOT_PATTERN));
		toolRsp.setUpdateTime(DateUtil.DateToString(tool.getUpdateTime(), DateUtil.SHOT_PATTERN));

		return toolRsp;
	}

	@Override
	public IPage<ToolRsp> selectPage(Page page, ToolListQuery toolListQuery, Boolean isAdmin, AuthContext authContext) {
		List<String> ids = toolMapper.selectByAuth(authContext);
		IPage<Tool> result = toolMapper.list(page, toolListQuery, isAdmin,	ids);
		return result.convert(tool -> toBo(tool, false));
	}

	@Override
	public IPage<ToolRsp> selectNameSortPage(Page page, ToolListQuery toolListQuery, Boolean isAdmin) {
		IPage<Tool> result = toolMapper.selectNameSortList(page, toolListQuery, isAdmin);
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
			throw new BusinessException(CrBusinessErrorCode.DUPLICATED_TOOL_NAME);
		}
	}

	@Override
	public void validateEditToolNameExist(Tool tool){
		// 名称重复性校验
		Tool toolObject = toolMapper.selectOtherByName(tool.getName(),tool.getResourceId());
		if (toolObject != null) {
			throw new BusinessException(CrBusinessErrorCode.DUPLICATED_TOOL_NAME);
		}
	}

	@Override
	public void insert(ToolAddReq req) {
		Tool tool = BeanUtils.copy(req, Tool.class);
		tool.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		tool.setReadAuthType(AuthTable.AuthType.ALL_SHARE_MODE.getCode());
		tool.setReadAuthIds(JsonUtil.toJsonString(Lists.newArrayList()));
		tool.setOperationAuthType(AuthTable.AuthType.ALL_SHARE_MODE.getCode());
		tool.setOperationAuthIds(JsonUtil.toJsonString(Lists.newArrayList()));
		tool.setCover(utilService.replaceFileUrl(tool.getCover()));
		validateToolNameExist(tool);
		validateToolAttachment(req.getToolFiles());
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
										@Override
										protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
											save(tool);
											req.getToolFiles().forEach(item-> toolFileMapper.updateById(item));
										}
									});
	}

	@Override
	public void update(ToolUpdateReq req) {
		Tool tool = BeanUtils.copy(req, Tool.class);
		validateEditToolNameExist(tool);
		validateToolAttachment(req.getToolFiles());
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				updateById(tool);
				req.getToolFiles().forEach(item-> toolFileMapper.updateById(item));
			}
		});
	}

	@Override
	public void batchSetShares(ShareContext shareContext) {
		String shareTeachers = JsonUtil.toJsonString(shareContext.getShareTeachers());
		List<Tool> businessTemplateList = listByIds(shareContext.getIds());
		businessTemplateList.forEach(businessTemplate -> {
			businessTemplate.setOperationAuthType(shareContext.getShareMode());
			businessTemplate.setOperationAuthIds(shareTeachers);
		});
		updateBatchById(businessTemplateList);
	}

	public void validateToolAttachment(List<ToolFile> toolFileList) {
		// todo 系统配置
		int MAX_TOOL_FILE = 5;
		// version重复性校验
		if (CollectionUtils.isEmpty(toolFileList)) {
			throw new BusinessException(CrBusinessErrorCode.EMPTY_TOOL_VERSION);
		}
		Set<String> versions = toolFileList.stream().map(ToolFile::getVersion).collect(Collectors.toSet());
		if (toolFileList.size() != versions.size()) {
			throw new BusinessException(CrBusinessErrorCode.DUPLICATED_TOOL_VERSION);
		}
		if (toolFileList.size()>MAX_TOOL_FILE) {
			throw new BusinessException(CrBusinessErrorCode.TOOL_OUT_OF_RANGE);
		}
	}

}
