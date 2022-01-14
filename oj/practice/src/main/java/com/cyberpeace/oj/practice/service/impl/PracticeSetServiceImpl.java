package com.cyberpeace.oj.practice.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.AttachmentMapper;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.constant.JsonKeyConstant;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.cyberpeace.oj.practice.dao.context.SetListQuery;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.PracticeSetService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.bo.*;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.PatternMatchUtils;

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
	private TransactionTemplate transactionTemplate;

	@Autowired
	private PracticeValidateHandler practiceValidateHandler;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private PracticeCategoryService practiceCategoryService;

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
		practiceSet.setPracticeCount(theoryList.size());

		return transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				practiceSetMapper.insert(practiceSet);
				return practiceTheoryService.saveBatch(theoryList);
			}
		});
	}

	@Override
	public List<String> importRmCtfSet(String path, Map<String, String> envReqs) {
		// 习题集数据处理
		PracticeSet practiceSet = handlerPracticeSet(path, false);

		// 遍历习题数据处理
		List<String> ctfPracticeDirs = FileUtil.listDirs(path, false);

		// 构建数据存储
		List<String> failOperations = Lists.newArrayList();
		List<PracticeOperation> operationList = Lists.newArrayList();

		// 执行数据导入
		for (String ctfPracticeDir : ctfPracticeDirs) {
			try {
				PracticeOperation operation = practiceOperationService.parseRmCtf(ctfPracticeDir,
						envReqs, practiceSet);
				operationList.add(operation);
			} catch (BusinessException e) {
				log.error("Parse ctf practice fail! ctf_practice_path=[{}], catch error:",
						ctfPracticeDir, e);

				if (e.getMessage().equals(OjBusinessErrorCode.NAME_IS_EXIST)) {
					failOperations.add(ctfPracticeDir);
					continue;
				}

				throw new BusinessException(e.getMessage());

			}
		}

		// 数据去重
		List<PracticeOperation> operations = StreamApiUtils.distinctByColumn(operationList,
				PracticeOperation::getTitle);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				practiceSet.setPracticeCount(practiceSet.getPracticeCount() + operations.size());
				saveOrUpdate(practiceSet);
				if (CollectionUtils.isEmpty(operations)) {
					practiceOperationService.saveBatch(operationList);
				}
			}
		});

		return failOperations;
	}

	@Override
	public File dumpPracticeSet(List<String> practiceSetIds, AuthContext authContext) {

		// 权限校验
		practiceSetIds.forEach(practiceSetId -> practiceValidateHandler
				.validatePracticeSetPermission(practiceSetId, authContext));

		// 设置数据存储
		String destDir = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID();
		String destFilePath = destDir + File.separatorChar
				+ ResourceImportConstant.DUMP_LOAD_FILE_STORE_PATH;

		List<PracticeSet> practiceSets = practiceSetMapper.selectBatchIds(practiceSetIds);
		List<PracticeSetDumpLoadBo> setDumpLoadBos = StreamApiUtils.map(practiceSets,
				practiceSet -> {
					Map<String, String> pathMap = Maps.newHashMap();
					// 习题集数据
					PracticeSetDumpLoadBo setDumpLoadBo = dumpSet(practiceSet, destFilePath,
							pathMap);

					// 习题数据
					List<? extends PracticeDumpLoadBo> dumpPractice = dumpPractice(practiceSet,
							destFilePath, pathMap);
					setDumpLoadBo.setPractice(dumpPractice);

					// 文件映射路径设置
					setDumpLoadBo.setFilePathMap(pathMap);
					return setDumpLoadBo;
				});

		String dataPath = destDir + File.separatorChar + ResourceImportConstant.DATA_JSON;
		FileUtil.writeToDestFile(JsonUtil.arrayToString(setDumpLoadBos), dataPath);

		// 压缩当前文件夹
		String zipDest = ZipUtil.toZipDest(destDir, AttachmentPath.OTHER.getDesc());
		FileUtil.deleteDir(destDir, true);
		return new File(zipDest);
	}

	@Override
	public Boolean loadPracticeSet(String realPath, String userId) {
		String destPath = realPath.substring(0, realPath.lastIndexOf(CommonConstant.PERIOD));
		ZipUtil.unzipToDest(realPath, destPath);

		// 解析数据
		String filePath = destPath + File.separatorChar
				+ ResourceImportConstant.DUMP_LOAD_FILE_STORE_PATH;
		String dataPath = destPath + File.separatorChar + ResourceImportConstant.DATA_JSON;
		String data = FileUtil.getContent(new File(dataPath));
		List<JSONObject> dumpLoadBos = JsonUtil.toJsonObjectArray(data, JSONObject.class);

		List<PracticeSet> practiceSets = Lists.newArrayList();
		List<PracticeTheory> theoryList = Lists.newArrayList();
		List<PracticeOperation> operationList = Lists.newArrayList();
		// 数据解析文件上传
		for (JSONObject obj : dumpLoadBos) {
			JSONArray practice = (JSONArray) obj.remove(JsonKeyConstant.PRACTICE_KEY);
			// 习题集
			PracticeSetDumpLoadBo setDumpLoadBo = obj.toJavaObject(PracticeSetDumpLoadBo.class);
			PracticeSet practiceSet = loadPracticeSet(setDumpLoadBo, userId, filePath);
			// 根据resource_id判断是新增还是已存在习题集
			if (StringUtils.isBlank(practiceSet.getResourceId())) {
				practiceSets.add(practiceSet);
				practiceSet.setResourceId(IDGeneratorUtil.getID());
			}

			// 理论习题
			if (practiceSet.getPracticeType().intValue() == PracticeSet.PracticeType.THEORY
					.getCode().intValue()) {
				List<PracticeTheoryDumpLoadBo> theoryDumpLoadBos = practice
						.toJavaList(PracticeTheoryDumpLoadBo.class);

				List<PracticeTheory> theories = loadPracticeTheory(practiceSet.getResourceId(),
						theoryDumpLoadBos, userId);
				theoryList.addAll(theories);
			}

			// 实操习题
			if (practiceSet.getPracticeType().intValue() == PracticeSet.PracticeType.OPERATION
					.getCode().intValue()) {
				List<PracticeOperationDumpLoadBo> operationDumpLoadBos = practice
						.toJavaList(PracticeOperationDumpLoadBo.class);

				List<PracticeOperation> operations = loadPracticeOperation(
						practiceSet.getResourceId(), operationDumpLoadBos, userId,
						setDumpLoadBo.getFilePathMap(), filePath);
				operationList.addAll(operations);
			}
		}

		return transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				if (CollectionUtils.isNotEmpty(theoryList)) {
					practiceTheoryService.saveBatch(theoryList);
				}
				if (CollectionUtils.isNotEmpty(operationList)) {
					practiceOperationService.saveBatch(operationList);
				}
				if (CollectionUtils.isNotEmpty(practiceSets)) {
					return saveBatch(practiceSets);
				}

				return CommonConstant.TRUE;
			}
		});
	}

	/**
	 * 加载实操习题
	 * @param practiceSetId
	 * @param operationDumpLoadBos
	 * @param userId
	 * @return
	 */
	private List<PracticeOperation> loadPracticeOperation(String practiceSetId,
			List<PracticeOperationDumpLoadBo> operationDumpLoadBos, String userId,
			Map<String, String> filePathMap, String filePath) {
		return StreamApiUtils.map(operationDumpLoadBos, dumpBo -> practiceOperationService
				.loadPracticeOperation(practiceSetId, dumpBo, filePathMap, filePath, userId));
	}

	/**
	 * 加载理论习题数据
	 * @param practiceSetId
	 * @param theoryDumpLoadBos
	 * @param userId
	 * @return
	 */
	private List<PracticeTheory> loadPracticeTheory(String practiceSetId,
			List<PracticeTheoryDumpLoadBo> theoryDumpLoadBos, String userId) {
		return StreamApiUtils.map(theoryDumpLoadBos,
				dumpBo -> practiceTheoryService.loadPracticeTheory(practiceSetId, dumpBo, userId));
	}

	/**
	 * 习题集数据加载
	 * @param setDumpLoadBo
	 * @param userId
	 * @return
	 */
	private PracticeSet loadPracticeSet(PracticeSetDumpLoadBo setDumpLoadBo, String userId,
			String filePath) {
		Map<String, String> filePathMap = setDumpLoadBo.getFilePathMap();

		String practiceSetId = practiceSetMapper.selectByName(setDumpLoadBo.getName());
		if (StringUtils.isNotBlank(practiceSetId)) {
			log.error("Practice set exist! practice_set_name=[{}], practice_set_id=[{}]",
					setDumpLoadBo.getName(), practiceSetId);
			return practiceSetMapper.selectById(practiceSetId);
		}

		// 基本数据设置 先不设置resource_id 用来判断是新增还是已存在
		PracticeSet practiceSet = BeanUtils.copy(setDumpLoadBo, PracticeSet.class);
		practiceSet.setBuiltin(CommonConstant.TRUE);
		practiceSet.setReadAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		practiceSet.setReadAuthIds(JsonUtil.arrayToString(Lists.newArrayList()));
		practiceSet.setOperationAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		practiceSet.setOperationAuthIds(JsonUtil.arrayToString(Lists.newArrayList()));
		practiceSet.setCreateUserId(userId);

		// logo文件替换
		if (StringUtils.isNotBlank(setDumpLoadBo.getLogo())) {
			// 获取文件路径
			String relativePath = filePathMap.get(setDumpLoadBo.getLogo());
			String logoPath = filePath + relativePath;

			// 获取将要存储的路径
			String logoStorePath = AttachmentPath.LOGO.getDesc();
			String fileSuffix = FileUtil.getFileSuffix(setDumpLoadBo.getLogo());
			String destFilePath = logoStorePath + IDGeneratorUtil.getID() + CommonConstant.PERIOD
					+ fileSuffix;
			// 拷贝文件
			FileUtil.copyFile(logoPath, destFilePath, false);
			String fileUrl = utilService.replaceFileUrl(destFilePath);
			practiceSet.setLogo(fileUrl);
		}

		return practiceSet;
	}

	/**
	 * 
	 * @param practiceSet
	 * @param fileStorePath
	 * @return
	 */
	private PracticeSetDumpLoadBo dumpSet(PracticeSet practiceSet, String fileStorePath,
			Map<String, String> filePathMap) {
		PracticeSetDumpLoadBo setDumpLoadBo = BeanUtils.copy(practiceSet,
				PracticeSetDumpLoadBo.class);
		// logo文件copy
		String realPath = utilService.getRealPath(practiceSet.getLogo());
		String fileName = FileUtil.getFileName(realPath, true);
		String newName = FileUtil.getNewName(fileName);
		String destLogoPath = fileStorePath + CommonConstant.LEFT_OBLIQUE_LINE + newName;
		FileUtil.copyFile(realPath, destLogoPath, false);

		String logoRelativePath = destLogoPath.replace(fileStorePath, CommonConstant.EMPTY_STRING);
		filePathMap.put(practiceSet.getLogo(), logoRelativePath);

		return setDumpLoadBo;
	}

	/**
	 * 根据习题集获取要dump的习题数据
	 * @param practiceSet
	 * @param fileStorePath 
	 * @param pathMap
	 * @return
	 */
	private List<? extends PracticeDumpLoadBo> dumpPractice(PracticeSet practiceSet,
			String fileStorePath, Map<String, String> pathMap) {

		// 理论题
		if (practiceSet.getPracticeType().intValue() == PracticeSet.PracticeType.THEORY.getCode()
				.intValue()) {
			return dumpTheory(practiceSet.getResourceId());
		}
		// 实操题
		if (practiceSet.getPracticeType().intValue() == PracticeSet.PracticeType.OPERATION.getCode()
				.intValue()) {
			return dumpOperation(practiceSet.getResourceId(), fileStorePath, pathMap);
		}

		return Lists.newArrayList();
	}

	/**
	 * 导出实操习题数据
	 * @param practiceSetId
	 * @return
	 */
	private List<PracticeOperationDumpLoadBo> dumpOperation(String practiceSetId,
			String fileStorePath, Map<String, String> filePathMap) {
		// 数据查询
		List<String> operationIds = practiceOperationMapper
				.selectBySetIds(Lists.newArrayList(practiceSetId), false);
		List<PracticeOperation> operationList = practiceOperationMapper
				.selectBatchIds(operationIds);

		// 数据转换
		return StreamApiUtils.map(operationList, operation -> practiceOperationService
				.handlerDumpOperation(operation, fileStorePath, filePathMap));
	}

	/**
	 * 导出理论习题数据
	 * @param practiceSetId
	 * @return
	 */
	private List<PracticeTheoryDumpLoadBo> dumpTheory(String practiceSetId) {
		// 数据查询
		List<String> theoryIds = practiceTheoryMapper
				.selectBySetIds(Lists.newArrayList(practiceSetId), false);
		List<PracticeTheory> practiceTheories = practiceTheoryMapper.selectBatchIds(theoryIds);

		// 数据转换
		return StreamApiUtils.map(practiceTheories,
				practiceTheory -> practiceTheoryService.dumpTheory(practiceTheory));
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
			practiceSet.setPracticeCount(CommonConstant.ZERO);
			practiceSet.setResourceId(IDGeneratorUtil.getID());

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
