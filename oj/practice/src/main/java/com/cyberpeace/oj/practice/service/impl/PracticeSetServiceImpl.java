package com.cyberpeace.oj.practice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.cyberpeace.oj.practice.dao.context.SetListQuery;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.PracticeSetService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.bo.AdminPracticeSetBo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PracticeSetServiceImpl extends ServiceImpl<PracticeSetMapper, PracticeSet> implements PracticeSetService {

	@Autowired
	private PracticeSetMapper practiceSetMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	@Autowired
	private UtilService utilService;

	@Autowired
	private PracticeTheoryService practiceTheoryService;

	@Autowired
	private PracticeOperationService practiceOperationService;

	@Autowired
	private PracticeValidateHandler practiceValidateHandler;

	@Override
	public AdminPracticeSetBo selectById(String resourceId, AuthContext authContext) {

		// 无权限则无数据返回
		List<String> authIds = practiceSetMapper.selectByAuthContext(authContext);
		if (!authIds.contains(resourceId)) {
			return null;
		}

		PracticeSet practiceSet = practiceSetMapper.selectById(resourceId);
		if (null == practiceSet) {
			log.error("Practice set not found! practice_set_id=[{}]", resourceId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return entityToSetBo(practiceSet, authContext);
	}

	@Override
	public void enableAccess(String resourceId, AuthContext authContext) {

		// 数据权限校验
		List<String> accessSetIds = practiceSetMapper.selectByAuthContext(authContext);

		if (CollectionUtils.isEmpty(accessSetIds) || !accessSetIds.contains(resourceId)) {
			log.error("This set not permission! practice_set_id=[{}]", resourceId);
			throw new InvalidParamException("非法操作！");
		}
	}

	/**
	 * 实体类数据转换
	 *
	 * @param practiceSet
	 * @return
	 */
	private AdminPracticeSetBo entityToSetBo(PracticeSet practiceSet, AuthContext authContext) {

		AdminPracticeSetBo setBo = BeanUtils.copy(practiceSet, AdminPracticeSetBo.class);
		setBo.setLogo(utilService.handlerFileUrl(practiceSet.getLogo()));
		UserInfo userInfo = remoteUserService.getById(practiceSet.getCreateUserId());
		setBo.setCreateUserName(null != userInfo ? userInfo.getUserName() : "");

		if (practiceSet.getPracticeType().intValue() == PracticeSet.PracticeType.OPERATION.getCode()
				.intValue()) {
			List<String> practiceOperations = practiceOperationMapper.selectBySetIds(
					Arrays.asList(practiceSet.getResourceId()), authContext.getIsWeb());
			setBo.setPracticeCount(practiceOperations.size());
		} else {
			List<String> theoryIds = practiceTheoryMapper.selectBySetIds(
					Arrays.asList(practiceSet.getResourceId()), authContext.getIsWeb());
			setBo.setPracticeCount(theoryIds.size());
		}

		List<String> operationIds = JsonUtil.toJsonObjectArray(practiceSet.getOperationAuthIds(),
				String.class);
		setBo.setOperationCount(operationIds.size());

		List<String> readIds = JsonUtil.toJsonObjectArray(practiceSet.getReadAuthIds(),
				String.class);
		setBo.setReadCount(readIds.size());

		return setBo;
	}

	@Override
	public IPage<AdminPracticeSetBo> selectByTypeAndName(Page page, Integer practiceType,
			String pattern, AuthContext authContext) {

		List<String> authIds = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(authIds)) {
			return null;
		}

		SetListQuery listQuery = SetListQuery.builder().practiceType(practiceType).setIds(authIds)
				.pattern(pattern).build();
		IPage<PracticeSet> iPage = practiceSetMapper.pageByTypeAndName(page, listQuery,
				authContext.getIsWeb());

		IPage<AdminPracticeSetBo> boIPage = iPage
				.convert(practiceSet -> entityToSetBo(practiceSet, authContext));
		return boIPage;
	}

	@Override
	public Boolean updateByAuth(PracticeSet practiceSet, AuthContext authContext) {

		List<String> permissionIds = practiceSetMapper.selectByAuthContext(authContext);
		// 权限校验
		if (CollectionUtils.isEmpty(permissionIds)
				|| !permissionIds.contains(practiceSet.getResourceId())) {
			log.error("This practice not permission! practice_set_id=[{}]",
					practiceSet.getResourceId());
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}

		PracticeSet existSet = practiceSetMapper.selectById(practiceSet.getResourceId());
		String categoryId = practiceSetMapper.selectByName(practiceSet.getName());
		// 名称校验
		if (StringUtils.isNotBlank(categoryId) && !categoryId.equals(existSet.getResourceId())) {
			throw new InvalidParamException("习题集名称已存在！");
		}

		// 类型校验 防止习题集类型改变
		if (null != practiceSet.getPracticeType()) {
			if (existSet.getPracticeType().intValue() != practiceSet.getPracticeType().intValue()) {
				throw new InvalidParamException("非法操作,习题集下存在不兼容类型习题！");
			}
		}

		return practiceSetMapper.updateById(practiceSet) > CommonConstant.ZERO;
	}

	@Override
	public void enableDelete(List<String> resourceIds, AuthContext authContext) {

		for (String setId : resourceIds) {
			// 权限校验
			enableAccess(setId, authContext);

			// 数据校验
			PracticeSet practiceSet = practiceSetMapper.selectById(setId);
			List<String> practiceIds = hasPractice(practiceSet, authContext);
			if (CollectionUtils.isNotEmpty(practiceIds)) {
				throw new InvalidParamException("习题集[" + practiceSet.getName() + "]下存在习题！");
			}
		}
	}

	@Override
	public boolean importRmTheorySet(String path) {

		// 习题集数据插入处理
		PracticeSet practiceSet = handlerPracticeSet(path, true);

		// 习题数据导入
		List<PracticeTheory> theoryList = practiceTheoryService.parseRmTheory(path, practiceSet);
		return practiceTheoryService.saveBatch(theoryList);
	}

	@Override
	public boolean importRmCtfSet(String path, Map<String, String> envReqs) {
		// 习题集数据处理
		PracticeSet practiceSet = handlerPracticeSet(path, false);

		// 遍历习题数据处理
		List<String> ctfPracticeDirs = FileUtil.listDirs(path, false);

		List<String> failOperations = Lists.newArrayList();
		List<PracticeOperation> operationList = Lists.newArrayList();

		for (String ctfPracticeDir : ctfPracticeDirs) {
			try {
				PracticeOperation operation = practiceOperationService.parseRmCtf(ctfPracticeDir,
						envReqs, practiceSet);
				operationList.add(operation);
			} catch (Exception e) {
				failOperations.add(ctfPracticeDir);
				log.error("Parse ctf practice fail! ctf_practice_path=[{}], catch error:",
						ctfPracticeDir, e);
				continue;
			}
		}

		if (CollectionUtils.isNotEmpty(failOperations)) {
			String failOperation = String.join(",", failOperations);
			log.error("Import fail ctf practices is [{}]", failOperation);
			return false;
		}

		return practiceOperationService.saveBatch(operationList);
	}

	/**
	 * 习题集数据读取
	 *
	 * @param path 习题集路径
	 * @param throwException 习题集已存在 覆盖或者抛出异常
	 * @return
	 */
	private PracticeSet handlerPracticeSet(String path, boolean throwException) {

		// 读取习题集数据信息
		File setMarkdownFile = new File(path, ResourceImportConstant.README_NAME);
		JSONObject setData = MdUtil.parseJsonMarkdown(setMarkdownFile);
		JSONObject setJson = JsonUtil.filedConvert(setData,
				ResourceImportConstant.PRACTICE_SET_DATA_MAPPING);

		// 格式化习题习题集数据 并存储数据库
		PracticeSet practiceSet = jsonParsePracticeSet(setJson);

		// logo文件地址转换
		File file = new File(path, ResourceImportConstant.PNG_LOGO);
		File destFile = new File(AttachmentPath.LOGO.getDesc(), ResourceImportConstant.PNG_LOGO);
		if (!file.exists()) {
			file = new File(path, ResourceImportConstant.JPG_LOGO);
			destFile = new File(AttachmentPath.LOGO.getDesc(), ResourceImportConstant.JPG_LOGO);
		}
		String destLogoPath = FileUtil.copyFile(file.getAbsolutePath(), destFile.getAbsolutePath(),
				true);
		practiceSet.setLogo(utilService.replaceFileUrl(destLogoPath));

		// 名称数据校验
		String existSetId = practiceSetMapper.selectByName(practiceSet.getName());

		// 不存在则创建 存在则直接返回
		if (StringUtils.isBlank(existSetId)) {
			practiceSet.setBuiltin(CommonConstant.TRUE);
			practiceSet.setCreateUserId(ResourceImportConstant.SUPER_ADMIN_HASH);
			practiceSet.setIsPublic(CommonConstant.FALSE);
			practiceSet.setOperationAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
			practiceSet.setOperationAuthIds(JsonUtil.arrayToString(Lists.newArrayList()));
			practiceSet.setReadAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
			practiceSet.setReadAuthIds(JsonUtil.arrayToString(Lists.newArrayList()));

			practiceSetMapper.insert(practiceSet);
			return practiceSet;
		}

		if (throwException) {
			log.error("Practice set exist! practice_set_id=[{}], practice_set_name=[{}]",
					existSetId, practiceSet.getName());
			throw new BusinessException(ErrorCode.DUPLICATE_KEY);
		}

		return practiceSetMapper.selectById(existSetId);
	}

	/**
	 * 格式化习题集数据
	 *
	 * @param setJson
	 * @return
	 */
	private PracticeSet jsonParsePracticeSet(JSONObject setJson) {
		// 习题集类型处理
		String typeName = (String) setJson.remove("practice_type");
		Integer practiceType = EnumUtils.getEnumCodeByDesc(typeName,
				PracticeSet.PracticeType.class);

		PracticeSet practiceSet = setJson.toJavaObject(PracticeSet.class);
		practiceSet.setPracticeType(practiceType);

		return practiceSet;

	}

	/**
	 * 查询习题集下是否存在数据
	 *
	 * @param practiceSet
	 * @param authContext
	 * @return
	 */
	private List<String> hasPractice(PracticeSet practiceSet, AuthContext authContext) {

		if (practiceSet.getPracticeType().intValue() == PracticeSet.PracticeType.THEORY.getCode()
				.intValue()) {
			return practiceTheoryMapper.selectBySetIds(Arrays.asList(practiceSet.getResourceId()),
					authContext.getIsWeb());
		} else {
			return practiceOperationMapper.selectBySetIds(
					Arrays.asList(practiceSet.getResourceId()), authContext.getIsWeb());
		}

	}
}
