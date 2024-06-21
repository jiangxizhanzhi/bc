package com.cyberpeace.oj.practice.service.impl;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.mdparser.MdParser;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.CustomFont;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.SystemPropertyService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.ResourceDumpConstant;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.cyberpeace.oj.practice.dao.context.SetListQuery;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.*;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.PracticeSetService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.bo.*;
import com.cyberpeace.oj.practice.service.md.MdTheoryPractice;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

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
	private PracticeSolvedMapper practiceSolvedMapper;

	@Autowired
	private PracticeMistakeMapper practiceMistakeMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private PracticeValidateHandler practiceValidateHandler;

	@Autowired
	private SystemPropertyService systemPropertyService;

	private static final Logger EXPORT_LOG = LoggerFactory.getLogger("resourceImport");

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
		
		// 获取习题集创建人ID、名称映射
		Map<String, String> userNameMap = remoteUserService
				.getIdNameMap(Lists.newArrayList(practiceSet.getCreateUserId()));

		return entityToSetBo(practiceSet, authContext, userNameMap);
	}

	@Override
	public void enableAccess(String resourceId, AuthContext authContext) {

		// 数据权限校验
		List<String> accessSetIds = practiceSetMapper.selectByAuthContext(authContext);

		if (CollectionUtils.isEmpty(accessSetIds) || !accessSetIds.contains(resourceId)) {
			log.error("This set not permission! practice_set_id=[{}]", resourceId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}
	}

	@Override
	public List<String> getSetIds(AuthContext authContext, String setId) {
		List<String> setIds = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(setIds)) {
			return Lists.newArrayList();
		}

		if (StringUtils.isNotBlank(setId)) {
			if (!setIds.contains(setId)) {
				return Lists.newArrayList();
			}
			setIds.clear();
			setIds.add(setId);
		}
		return setIds;
	}

	/**
	 * 实体类数据转换
	 *
	 * @param practiceSet
	 * @return
	 */
	private AdminPracticeSetBo entityToSetBo(PracticeSet practiceSet, AuthContext authContext,
			Map<String, String> userNameMap) {

		AdminPracticeSetBo setBo = BeanUtils.copy(practiceSet, AdminPracticeSetBo.class);
		setBo.setLogo(utilService.handlerFileUrl(practiceSet.getLogo()));
		setBo.setCreateUserName(userNameMap.getOrDefault(practiceSet.getCreateUserId(), ""));

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
				.build();
		listQuery.setPattern(pattern);

		IPage<PracticeSet> iPage = practiceSetMapper.pageByNameAndType(page, listQuery,
				authContext.getIsWeb());

		// 获取习题集创建人ID、名称映射
		Map<String, String> userNameMap = remoteUserService.getIdNameMap(iPage.getRecords(),
				PracticeSet::getCreateUserId);

		return iPage.convert(practiceSet -> entityToSetBo(practiceSet, authContext, userNameMap));
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
			log.error("categoryId not permission! categoryId=[{}]",
					categoryId);
			throw new BusinessException(OjBusinessErrorCode.NAME_IS_EXIST);
		}

		// 类型校验 防止习题集类型改变
		if (null != practiceSet.getPracticeType()) {
			if (existSet.getPracticeType().intValue() != practiceSet.getPracticeType().intValue()
					&& existSet.getPracticeCount().intValue() != CommonConstant.ZERO.intValue()) {
				log.error("set under exist practice! existSet=[{}]",
						JsonUtil.toJsonString(existSet));
				throw new BusinessException(OjBusinessErrorCode.SET_UNDER_EXIST_PRACTICE);
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
				log.error("practiceIds isNotEmpty! practiceIds=[{}]",
						JsonUtil.toJsonString(practiceIds));
				throw new BusinessException(OjBusinessErrorCode.RESOURCE_USED_NOT_DELETED);
			}
		}
	}

	@Override
	public File dumpPracticeSet(List<String> practiceSetIds, AuthContext authContext) {

		// 权限校验
		practiceSetIds.forEach(practiceSetId -> practiceValidateHandler
				.validatePracticeSetPermission(practiceSetId, authContext));
		List<PracticeSet> practiceSets = practiceSetMapper.selectBatchIds(practiceSetIds);
		if (!systemPropertyService.getExportBuiltin()) {
			practiceSets.forEach(practiceSet -> practiceValidateHandler.validateBuiltin(practiceSet));
		}

		// 设置数据存储
		String destDir = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID();
		String destFilePath = destDir + File.separatorChar
				+ ResourceImportConstant.DUMP_LOAD_FILE_STORE_PATH;

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
		FileUtil.mkdirs(destDir);
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
				practiceSet.setPracticeCount(practiceSet.getPracticeCount() + theories.size());
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
				practiceSet.setPracticeCount(practiceSet.getPracticeCount() + operations.size());
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

	@Override
	public List<String> duplicatePracticeSetInspect(List<String> practiceSets) {
		List<String> duplicateName = Lists.newArrayList();
		if (null == practiceSets || practiceSets.size() == 0) {
			return duplicateName;
		}

		List<PracticeSet> practiceSetList = practiceSetMapper.selectByNames(practiceSets);
		for (PracticeSet practiceSet : practiceSetList) {
			duplicateName.add(practiceSet.getName());
		}

		return duplicateName;
	}

	@Override
	public String exportPracticeSet(String practiceSetId, String practiceId, Boolean newPath, Boolean disableImageExport) {

		Map<String, List<PracticeOperation>> operationMap;
		List<PracticeOperation> operationList;
		List<PracticeSet> setList;

		EXPORT_LOG.info(System.lineSeparator() + System.lineSeparator()
				+ "----------start export practice-----------" + System.lineSeparator());

		try {
			// 查询要导出的习题集
			EXPORT_LOG.info("Practice set query....");
			setList = setListByIds(practiceSetId, practiceId);
			List<String> setIds = StreamApiUtils.map(setList, PracticeSet::getResourceId);

			// 查询导出的习题数据
			EXPORT_LOG.info("Practice operation query....");
			operationList = practiceOperationService.selectExportPractice(setIds, practiceId);
			operationMap = StreamApiUtils.groupBy(operationList,
					PracticeOperation::getPracticeSetId);
		} catch (Exception e) {
			EXPORT_LOG.error("Practice operation export data query failed! catch error:", e);
			throw new BusinessException(OjBusinessErrorCode.OPERATION_EXPORT_QUERY_FAILED);
		}

		String destPath = newPath ? CommonConstant.SHARE_DIR + IDGeneratorUtil.getID()
				: CommonConstant.EXPORT_CTF_PATH;
		EXPORT_LOG.info("Practice operation export path create successfully, path=[{}]", destPath);

		for (PracticeSet practiceSet : setList) {
			EXPORT_LOG.info("Start export {} practice set....", practiceSet.getName());

			String setPath;
			try {
				setPath = doExportSet(destPath, practiceSet);
			} catch (BusinessException e) {
				throw e;
			} catch (Exception e) {
				EXPORT_LOG.error("Practice set export failed! set_name=[{}], catch error:",
						practiceSet.getName(), e);
				throw new BusinessException(OjBusinessErrorCode.OPERATION_SET_EXPORT_FAILED,
						practiceSet.getName());
			}

			List<PracticeOperation> operations = operationMap.get(practiceSet.getResourceId());
			practiceOperationService.exportPractice(setPath, operations, disableImageExport);

			EXPORT_LOG.info("export {} practice set successfully", practiceSet.getName());
		}

		EXPORT_LOG.info(System.lineSeparator() + "----------end export practice-----------"
				+ System.lineSeparator() + System.lineSeparator());
		return destPath;
	}

	@Override
	public void clearUserAuth(String userId) {
		List<PracticeSet> courseList = practiceSetMapper.selectPracticeSetByUserId(userId);

		for (PracticeSet course : courseList) {
			course.setOperationAuthIds(
					LocalStringUtil.strRemoveSubStr(course.getOperationAuthIds(), userId));
		}

		updateBatchById(courseList);
	}

	@Override
	public void clearOrgAuth(String orgId) {
		List<PracticeSet> courseList = practiceSetMapper.selectPracticeSetByOrgId(orgId);

		for (PracticeSet course : courseList) {
			course.setReadAuthIds(LocalStringUtil.strRemoveSubStr(course.getReadAuthIds(), orgId));
		}

		updateBatchById(courseList);
	}

	@Override
	public MdTheoryPractice exportMdTheoryPractice(String path, List<PracticeTheory> theories) {
		return practiceTheoryService.exportMdTheoryPractice(path, theories);
	}

	@Override
	public List<AdminPracticeSetBo> getMorePracticeSet(AuthContext authContext, String practiceSetId, Integer setType) {
		List<String> authIds = practiceSetMapper.selectByAuthContext(authContext);
		authIds.remove(practiceSetId);
		if (authIds.size() == 0) {
			return new ArrayList<>();
		}
		List<PracticeSet> practiceSets = practiceSetMapper.selectByIdsAndType(authIds, setType);

		// 获取习题集创建人ID、名称映射
		Map<String, String> userNameMap = remoteUserService.getIdNameMap(practiceSets,
				PracticeSet::getCreateUserId);

		return StreamApiUtils.map(practiceSets,
				practiceSet -> entityToSetBo(practiceSet, authContext, userNameMap));
	}

	@Override
	public String createImg(String setName) {
		List<String> practiceSetLogo = ResourceImportConstant.PRACTICE_SET_LOGO;
		int i = (int) Math.floor(Math.random() * 5);
		CustomFont customFont = new CustomFont();
		Font font = customFont.loadFonts("static/YouSheBiaoTiHei.ttf", Font.PLAIN, 46);
		String id = IDGeneratorUtil.getID();
		try {
			return ImgUtil.addTextToImage(practiceSetLogo.get(i), font, setName, AttachmentPath.LOGO.getDesc() + id + ".png").replace("/cpms/", "");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public void reset(String resourceId, Integer setType) {
		String userId = UserInfoContext.getUserContext().getUserId();
		if (PracticeSet.PracticeType.THEORY.getCode().equals(setType)) {
			List<String> practiceIds = practiceTheoryMapper
					.selectBySetIds(Lists.newArrayList(resourceId), false);
			if (practiceIds.isEmpty()) {
				return;
			}
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					practiceSolvedMapper.deleteByTheoryIdsAndUser(practiceIds, userId);
					practiceMistakeMapper.deleteByPracticeIds(practiceIds, userId);
				}
			});
		} else if (PracticeSet.PracticeType.OPERATION.getCode().equals(setType)) {
			practiceSolvedMapper.deleteByOperationSetAndUser(resourceId, userId);
		}
	}

    private String doExportSet(String destPath, PracticeSet practiceSet) {

		if (StringUtils.isBlank(practiceSet.getLogo())) {
			if (new File(ResourceDumpConstant.DEFAULT_LOGO_PATH).exists()) {
				log.warn("use default logo [{}]", ResourceDumpConstant.DEFAULT_LOGO_PATH);
				practiceSet.setLogo(ResourceDumpConstant.DEFAULT_LOGO_PATH);
			} else {
				log.error("file is not exist ! path=[{}]",
						ResourceDumpConstant.DEFAULT_LOGO_PATH);
				throw new BusinessException(OjBusinessErrorCode.PRACTICE_SET_NO_LOGO,
						practiceSet.getName());
			}

		}
		String path = createPath(destPath, practiceSet.getName());
		EXPORT_LOG.info("Practice set path create successfully! set_path=[{}]", path);

		File README = new File(path, ResourceImportConstant.README_NAME);

		if (!README.exists()) {
			EXPORT_LOG.info("Start export practice set README.md, readme_path=[{}]",
					README.getAbsolutePath());
			MdPracticeSet mdPracticeSet = entityToMd(practiceSet);

			if (StringUtils.isNotBlank(practiceSet.getLogo())) {
				String realPath = utilService.getRealPath(practiceSet.getLogo());
				File destLogoFile = new File(path, ResourceImportConstant.PNG_LOGO);

				if (!destLogoFile.exists()) {
					EXPORT_LOG.info("Start export practice set logo, logo_path=[{}]",
							destLogoFile.getAbsolutePath());
					FileUtil.copyFile(new File(realPath), destLogoFile);
					EXPORT_LOG.info("export practice set logo successfully");
				}

				mdPracticeSet.setLogo(String.format(CommonConstant.MD_IMAGE_TEMPLATE,
						ResourceImportConstant.PNG_LOGO));
			}

			MdParser mdParser = new MdParser();
			mdParser.write(mdPracticeSet, README);

			EXPORT_LOG.info("export practice set README.md successfully");
		}

		return path;
	}

	private String createPath(String destPath, String setName) {
		File setPath = new File(destPath, setName);
		FileUtil.createPath(setPath);
		return setPath.getAbsolutePath();
	}

	/** 数据类型转换*/
	private MdPracticeSet entityToMd(PracticeSet practiceSet) {
		return BeanUtils.copy(practiceSet, MdPracticeSet.class);
	}

	private List<PracticeSet> setListByIds(String setId, String practiceId) {

		if (StringUtils.isNotBlank(practiceId)) {
			List<PracticeOperation> operations = practiceListByIds(practiceId);

			List<PracticeSet> setList = StreamApiUtils.map(operations,
					operation -> practiceValidateHandler.validateSetType(
							operation.getPracticeSetId(),
							PracticeSet.PracticeType.OPERATION.getCode()));
			return StreamApiUtils.distinctByColumn(setList, PracticeSet::getResourceId);
		}

		if (StringUtils.isNotBlank(setId)) {
			return StreamApiUtils.map(Arrays.asList(setId.split(CommonConstant.CODE_COMMA)),
					id -> practiceValidateHandler.validateSetType(id,
							PracticeSet.PracticeType.OPERATION.getCode()));
		}

		return practiceSetMapper.selectByType(PracticeSet.PracticeType.OPERATION.getCode(), false);
	}

	private List<PracticeOperation> practiceListByIds(String practiceId) {
		if (StringUtils.isBlank(practiceId)) {
			return Lists.newArrayList();
		}

		List<String> practiceIds = Lists.newArrayList(practiceId.split(CommonConstant.CODE_COMMA));
		return StreamApiUtils.map(practiceIds,
				id -> practiceValidateHandler.validateOperationExist(id));
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
			PracticeSet practiceSet = practiceSetMapper.selectById(practiceSetId);
			practiceValidateHandler.validatePracticeSetPermission(practiceSet, userId);
			return practiceSet;
		}

		// 基本数据设置 先不设置resource_id 用来判断是新增还是已存在
		PracticeSet practiceSet = BeanUtils.copy(setDumpLoadBo, PracticeSet.class);
		practiceSet.setBuiltin(CommonConstant.TRUE);
		practiceSet.setReadAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		practiceSet.setReadAuthIds(JsonUtil.arrayToString(Lists.newArrayList()));
		practiceSet.setOperationAuthType(AuthTable.AuthType.ALL_SHARE_MODE.getCode());
		practiceSet.setOperationAuthIds(JsonUtil.arrayToString(Lists.newArrayList()));
		practiceSet.setCreateUserId(userId);
		practiceSet.setPracticeCount(CommonConstant.ZERO);

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
		if (StringUtils.isNotBlank(practiceSet.getLogo())) {
			String realPath = utilService.getRealPath(practiceSet.getLogo());
			String fileName = FileUtil.getFileName(realPath, true);
			String newName = FileUtil.getNewName(fileName);
			String destLogoPath = fileStorePath + CommonConstant.LEFT_OBLIQUE_LINE + newName;
			FileUtil.copyFile(realPath, destLogoPath, false);

			String logoRelativePath = destLogoPath.replace(fileStorePath,
					CommonConstant.EMPTY_STRING);
			filePathMap.put(practiceSet.getLogo(), logoRelativePath);
		}

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

		List<PracticeOperation> operationList = Lists.newArrayList();
		if (!operationIds.isEmpty()) {
			operationList = practiceOperationMapper.selectBatchIds(operationIds);
		}

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

		List<PracticeTheory> practiceTheories = Lists.newArrayList();
		if (!theoryIds.isEmpty()) {
			practiceTheories = practiceTheoryMapper.selectBatchIds(theoryIds);
		}

		// 数据转换
		return StreamApiUtils.map(practiceTheories,
				practiceTheory -> practiceTheoryService.dumpTheory(practiceTheory));
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
