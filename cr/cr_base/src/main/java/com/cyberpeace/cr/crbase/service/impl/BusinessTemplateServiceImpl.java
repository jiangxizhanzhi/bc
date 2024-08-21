package com.cyberpeace.cr.crbase.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.enums.SceneStatusEnum;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplateData;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.*;
import com.cyberpeace.cr.crbase.service.context.resource.ResourceDumpContext;
import com.cyberpeace.cr.crbase.service.context.template.BTPageReq;
import com.cyberpeace.cr.crbase.service.context.template.BusinessTemplateSimple;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.entity.LocalSceneMeta;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.context.scene.SceneData;
import com.cyberpeace.cs.service.context.scene.SceneDataImpl;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Service
@Slf4j
public class BusinessTemplateServiceImpl extends ServiceImpl<BusinessTemplateMapper, BusinessTemplate> implements BusinessTemplateService {

	@Autowired
	private BusinessTemplateMapper businessTemplateMapper;

	@Autowired
	private MissionService missionService;

	@Autowired
	private MissionTemplateService missionTemplateService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private RtksRelatedService rtksRelatedService;

	@Autowired
	private RemoteLogService remoteLogService;

	@Autowired
	@Qualifier("businessTemplateHandler")
	private BusinessTemplateHandler businessTemplateHandler;

	@Autowired
	@Qualifier("taskExecutor")
	private Executor executor;

	@Override
	public String addTemplate(BaseInfo baseInfo) {
		// validate length
		baseInfo.validate();
		// 1、从输入业务中解析公共模板
		BusinessTemplate businessTemplate = new BusinessTemplate();
		businessTemplate.setBusinessType(baseInfo.getType());
		businessTemplate.setName(baseInfo.getName());
		businessTemplate.setDescription(baseInfo.getDescription());
		businessTemplate.setDifficulty(baseInfo.getDifficult());
		businessTemplate.setLogo(baseInfo.getLogo());
		// validate name
		validateName(businessTemplate.getName(), businessTemplate.getBusinessType(), "");

		businessTemplate.setOperationAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		businessTemplate.setOperationAuthIds("[]");
		businessTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		if (baseInfo.getType().equals(BusinessType.TRAINING_TEAM.getCode())) {
			businessTemplate.setTopoId("");
		} else {
			// 拓扑场景id保存获取
			SceneConfigData topoData = baseInfo.getSceneConfigData();
			JSONObject topoJson = new JSONObject();
			topoJson.put("scene_config", topoData.getSceneConfig());
			topoJson.put("logo", topoData.getLogo());
			topoJson.put("support_partial_start", topoData.getSupportPartialStart());
			topoJson.put("id", topoData.getId());

			BusinessCode businessCode = convertTypeToCode(baseInfo.getType());
			String topoId = localSceneService.saveTopo(topoJson, businessCode.getCode());

			// 保存基础模板
			businessTemplate.setTopoId(topoId);
		}
		// 2、执行实际的保存
		// 1、保存公共模版数据
		save(businessTemplate);

		return businessTemplate.getResourceId();
	}

	@Override
	public void checkPermission(String businessId, String userId) {
		List<String> businessIds = selectAuthIdsByUserId(userId);

		if (!businessIds.contains(businessId)&&!UserInfoContext.getUserContext()
															   .isSuperuser()) {
			log.error("businessId is illegal,businessId =[{}],userId =[{}]", businessId,userId);
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}
	}

	@Override
	public boolean checkPermission(String resourceId) {
		BusinessTemplate template = businessTemplateMapper.selectById(resourceId);
		if (null == template) {
			return false;
		}
		UserInfo userInfo = UserInfoContext.getUserContext();
		// admin，模板授权所有，是创建者，或者被授权
		return userInfo.isSuperuser()
				|| template.getOperationAuthType()
						.equals(AuthTable.AuthType.ALL_SHARE_MODE.getCode())
				|| template.getCreateUserId().equals(userInfo.getUserId())
				|| template.getOperationAuthIds().contains(userInfo.getUserId());
	}

	@Override
	public Boolean existByName(String templateName) {
		return businessTemplateMapper.existByName(templateName);
	}

	@Override
	public List<String> getExistedNames(List<String> names) {
		if (names == null || names.isEmpty()) {
			return Lists.newArrayList();
		}
		return businessTemplateMapper.getExistedNames(names);
	}

	@Override
	public BusinessTemplate getByName(String templateName) {
		return businessTemplateMapper.getByName(templateName);
	}

	@Override
	public void updateTemplate(BusinessTemplate businessTemplate, BaseInfo baseInfo) {
		// validate length
		baseInfo.validate();
		// base info
		businessTemplate.setName(baseInfo.getName());
		businessTemplate.setDescription(baseInfo.getDescription());
		businessTemplate.setDifficulty(baseInfo.getDifficult());
		businessTemplate.setLogo(baseInfo.getLogo());
		// validate name
		validateName(businessTemplate.getName(), businessTemplate.getBusinessType(),
				businessTemplate.getResourceId());
		if (!businessTemplate.getBusinessType().equals(BusinessType.TRAINING_TEAM.getCode())) {
			// update topo
			SceneConfigData topoData = baseInfo.getSceneConfigData();
			if (topoData != null) {
				JSONObject topoJson = new JSONObject();
				topoJson.put("scene_config", topoData.getSceneConfig());
				topoJson.put("logo", topoData.getLogo());
				topoJson.put("support_partial_start", topoData.getSupportPartialStart());

				localSceneService.saveTopo(topoJson, businessTemplate.getTopoId());
			}
		}
		// save
		updateById(businessTemplate);
	}

	@Override
	public void validateName(String name, Integer type, String resourceId) {
		CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		if (businessTemplateMapper.existByNameAndTypeAndId(name, type, resourceId)) {
			log.error("template name already exist！name=[{}]", name);
			throw new BusinessException(CrBusinessErrorCode.TEMPLATE_NAME_DUPLICATE);
		}
	}

	@Override
	public void updateMissionTemplateRelation(String businessTemplateId, String before,
			String after) {
		List<String> oldMissionTemplateIds = missionTemplateService
				.getIdsByMissionsSchemeJson(before);
		List<String> newMissionTemplateIds = missionTemplateService
				.getIdsByMissionsSchemeJson(after);
		// remove id in both list if both list contains the id
		int i = 0;
		while (i < oldMissionTemplateIds.size()) {
			if (newMissionTemplateIds.remove(oldMissionTemplateIds.get(i))) {
				oldMissionTemplateIds.remove(i);
			} else {
				i++;
			}
		}
		// delete
		missionTemplateService.batchDelete(oldMissionTemplateIds);
		// update
		missionTemplateService.batchUpdateBusinessTemplateId(businessTemplateId,
				newMissionTemplateIds);
	}

	@Override
	public List<String> getAttackRelatedIds(List<String> businessTemplateIds) {
		if (businessTemplateIds.isEmpty()) {
			return businessTemplateIds;
		}
		return businessTemplateMapper.getAttackRelatedIds(businessTemplateIds);
	}

	@Override
	public List<String> getEngageRelatedIds(List<String> businessTemplateIds) {
		if (businessTemplateIds.isEmpty()) {
			return businessTemplateIds;
		}
		return businessTemplateMapper.getEngageRelatedIds(businessTemplateIds);
	}

	@Override
	public ListRsp<BusinessTemplateSimple> pageSimple(BTPageReq btPageReq) {
		Page page = btPageReq.toPageParam();

		IPage<BusinessTemplateSimple> result = businessTemplateMapper.listSimple(page,
				btPageReq.getType(), btPageReq.getDifficulty(), btPageReq.getPattern());
		return PageUtil.getListResult(result);
	}

	@Override
	public void clearUserAuth(String userId) {
		List<BusinessTemplate> businessTemplateList = businessTemplateMapper
				.selectBusinessTemplatesByUserId(userId);

		for (BusinessTemplate businessTemplate : businessTemplateList) {
			businessTemplate.setOperationAuthIds(LocalStringUtil
					.strRemoveSubStr(businessTemplate.getOperationAuthIds(), userId));
		}

		updateBatchById(businessTemplateList);
	}

	@Override
	public List<String> selectAuthIdsByUserId(String userId) {
		UserInfo userInfo = UserInfoContext.getUserContext();
		if (userInfo.isSuperuser()) {
			// ??? 传 userId 的意义何在？
			return businessTemplateMapper.getAllIds();
		} else {
			return businessTemplateMapper.selectIdsByUserId(userId);
		}
	}

	@Override
	public BusinessTemplate getByLocalSceneId(String localSceneId) {
		BusinessTemplate res = businessTemplateMapper.selectByLocalSceneId(localSceneId);
		if (null == res) {
			log.error("failed to find businessTemplate with localSceneId [{}]", localSceneId);
			throw new BusinessException(CrBusinessErrorCode.TEMPLATE_NOT_EXIST);
		}
		return res;
	}

	@Override
	public IPage<BusinessTemplateData> page(BusinessTemplateQuery businessTemplateQuery) {
		String userId = UserInfoContext.getUserContext().getUserId();
		Page page = businessTemplateQuery.toPageParam();
		UserInfo userInfo = UserInfoContext.getUserContext();
		if(userInfo.isSuperuser()){
			businessTemplateQuery.setIds(businessTemplateMapper.getAllIds());
		}else {
			businessTemplateQuery.setIds(businessTemplateMapper.selectIdsByUserId(userId));
		}

		IPage<BusinessTemplateData> result = businessTemplateMapper.listTeamTemplate(page,
				businessTemplateQuery);

		// get node count
		if (businessTemplateQuery.getType()
				.equals(BusinessType.TRAINING_TEAM.getCode().toString())) {
			return result;
		}
		List<String> topoIds = result.getRecords().stream().map(BusinessTemplate::getTopoId)
				.collect(Collectors.toList());
		Map<String, TopoDigest> nodeCountMap = remoteTopoService.batchGetTopoDigest(topoIds);
		for (BusinessTemplateData businessTemplate : result.getRecords()) {
			if (nodeCountMap.containsKey(businessTemplate.getTopoId())) {
				businessTemplate.setNodesNum(nodeCountMap.get(businessTemplate.getTopoId()).getNodeCounts());
				businessTemplate.setResourceOccupiedNums(nodeCountMap.get(businessTemplate.getTopoId()).getResourceOccupiedNums());
				businessTemplate.setTotalCpu(nodeCountMap.get(businessTemplate.getTopoId()).getTotalCpu());
				businessTemplate.setTotalRam(nodeCountMap.get(businessTemplate.getTopoId()).getTotalRam());
				businessTemplate.setTotalDisk(nodeCountMap.get(businessTemplate.getTopoId()).getTotalDisk());
			}
		}

		return result;

	}

	@Override
	public Share getShares(String resourceId) {
		BusinessTemplate businessTemplate = businessTemplateMapper.selectById(resourceId);

		Share share = new Share();
		share.setShareMode(businessTemplate.getOperationAuthType());
		List<ShareTeacher> shareTeacherList = Lists.newArrayList();

		if (AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode()
				.equals(businessTemplate.getOperationAuthType())) {
			String teachers = businessTemplate.getOperationAuthIds();

			List<UserInfo> userInfoList = remoteUserService
					.getByIdList(JsonUtil.toJsonObjectArray(teachers, String.class));
			userInfoList.forEach(userInfo -> {
				ShareTeacher shareTeacher = new ShareTeacher();
				shareTeacher.setId(userInfo.getUserId());
				shareTeacher.setName(userInfo.getName());
				shareTeacher.setUsername(userInfo.getUserName());
				shareTeacher.setOrganization(userInfo.getOrganizationId());
				shareTeacher.setOrganizationNameAll(userInfo.getOrganizationData());
				shareTeacherList.add(shareTeacher);
			});

		}
		share.setTeachers(shareTeacherList);

		return share;
	}

	@Override
	public void batchSetShares(ShareContext shareContext) {
		String shareTeachers = JsonUtil.toJsonString(shareContext.getShareTeachers());

		List<BusinessTemplate> businessTemplateList = listByIds(shareContext.getIds());

		businessTemplateList.forEach(businessTemplate -> {
			businessTemplate.setOperationAuthType(shareContext.getShareMode());
			businessTemplate.setOperationAuthIds(shareTeachers);
		});

		updateBatchById(businessTemplateList);
	}

	@Override
	public void batchDelete(List<String> businessTemplateIds) {
		if (CollectionUtils.isEmpty(businessTemplateIds)) {
			return;
		}
		UserInfo userInfo = UserInfoContext.getUserContext();
		List<BusinessTemplate> businessTemplates = businessTemplateMapper
				.selectBatchIds(businessTemplateIds);
		if (businessTemplates.isEmpty()) {
			return;
		}
		// 删除
		this.deleteBatch(businessTemplates);
		// 审计log
		this.addBatchDeleteLog(businessTemplates, userInfo);
	}

	@Override
	public void clearLocal(BusinessTemplate template) {
		String localSceneId = template.getLocalSceneId();
		template.setLocalSceneId("");
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				localSceneService.removeById(localSceneId);
				updateById(template);
			}
		});
	}

	@Override
	public LocalScene createLocalScene(BusinessTemplate template) {

		LocalScene localScene = new LocalScene();

		localScene.setBusinessCode(BusinessCode.TEST_SCENE.getCode());
		localScene.setUserId(UserInfoContext.getUserContext().getUserId());
		// 以模板id作为后台测试场景的 businessId，
		// 发给mp的context中会以localScene的businessInstanceId作为透明上下文（passThrough）的businessId，用于ws消息推送
		localScene.setBusinessInstanceId(template.getResourceId());
		localScene.setTopoId(template.getTopoId());
		localScene.setResourceId(IDGeneratorUtil.getID());
		localScene.setSceneStatus(SceneStatusEnum.INIT.getCode());
		localScene.setDestroyMode(LocalScene.DestroyMode.MANUAL.getCode());
		localScene.setStartTime(new Date());
		localScene.setSceneAttr(SceneStartContext.SceneAttrEnum.NULL.getCode());
		localScene.setSceneName(template.getName());
		localScene.setMeta(JSON.toJSONString(
				LocalSceneMeta.builder().appResourceId(template.getResourceId()).build()));

		return localScene;
	}

	@Override
	public SceneViewContext viewScene(BusinessTemplate template) {
		SceneViewContext ret = new SceneViewContext();
		ret.setRoomId(template.getResourceId());
		ret.setTopoId(template.getTopoId());
		// 1、尝试获取启动的远程场景
		JSONObject sceneData = getMpSceneData(template);
		if (sceneData != null) {
			ret.setSceneData(sceneData);
			ret.setLocalSceneId(template.getLocalSceneId());
			ret.setMpSceneId(sceneData.getString(CrCommonConstants.MP_SCENE_ID_KEY));
			return ret;
		}
		// 2、 远程场景不存在，返回topo数据
		JSONObject topoData = localSceneService.getTopoData(template.getTopoId())
				.getJSONObject(CrCommonConstants.TOPO_DATA_KEY);
		ret.setTopoData(topoData);
		return ret;
	}

	@Override
	public BusinessCode convertTypeToCode(Integer code) {
		BusinessType type = EnumUtils.getEnumByCode(code,
				BusinessType.class);

		if (type.equals(BusinessType.TRAINING_SINGLE)) {
			return BusinessCode.SINGLE_TRAINING;
		}
		if (type.equals(BusinessType.VULN_VALIDATE)) {
			return BusinessCode.LIVE_VULN;
		}
		if (type.equals(BusinessType.TRAINING_TEAM)) {
			return BusinessCode.TEAM_TRAINING;
		}
		if (type.equals(BusinessType.RED_VS_BLUE)) {
			return BusinessCode.RED_VS_BLUE;
		}
		if (type.equals(BusinessType.WEAPON_ASSESS)) {
			return BusinessCode.WEAPON_TEST;
		}
		if (type.equals(BusinessType.SYSTEM_ASSESS)) {
			return BusinessCode.SYSTEM_TEST;
		}
		log.error("template type not supported, code [{}]", code);
		throw new BusinessException(CrBusinessErrorCode.TEMPLATE_TYPE_NOT_SUPPORTED);
	}

	@Override
	public String makeDumpFile(List<String> templateIds, String tempDir) throws IOException {
		if (templateIds.isEmpty()) {
			throw new BusinessException(CrBusinessErrorCode.EMPTY_RESOURCE_ID_LIST);
		}
		// 1. make temp dir
		FileUtil.mkdirs(tempDir);
		List<ResourceDumpContext> contextList = Lists.newArrayList();
		for (String templateId : templateIds) {
			ResourceDumpContext context = dumpTemplate(templateId);
			Map<String, String> fileMap = context.getFiles();
			// 2. copy pics
			for (String fileName : fileMap.values()) {
				String originPath = AttachmentPath.UPLOAD_IMAGE.getDesc() + fileName;
				String destPath = tempDir + File.separator + CrCommonConstants.RESOURCE_IMAGE_DIR
						+ File.separator + fileName;
				try {
					FileUtil.copyFile(originPath, destPath, false);
				} catch (Exception e) {
					log.error("error occurred when copying picture");
					throw new IOException(e);
				}
			}
			// 3.collect template data
			contextList.add(context);
		}

		// 4. generate data.json
		String jsonPath = tempDir + File.separator + CrCommonConstants.RESOURCE_FILE_NAME;
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(jsonPath),
				StandardCharsets.UTF_8);
		String data = JsonUtil.arrayToString(contextList);
		FileUtil.writeString(data, osw);

		// 5. generate zip file, return zip file path
		return ZipUtil.toZip(tempDir);
	}

	@Override
	public ResourceDumpContext dumpTemplate(String templateId) {
		// 1. 获取要导出的模板
		BusinessTemplate template = businessTemplateMapper.selectById(templateId);
		if (template == null) {
			throw new BusinessException(CrBusinessErrorCode.TEMPLATE_NOT_EXIST);
		}
		if (template.getReadAuthType()==null) {
			template.setReadAuthType(2);
		}
		// 2. 生成导出模型, 包含 template, missions
		ResourceDumpContext<Object> context = businessTemplateHandler.dumpBusiness(template);
		context.setBusinessTemplate(template);

		// 3. 获取拓扑
		JSONObject topoData = localSceneService.getTopoData(template.getTopoId());
		context.setSceneData(topoData);
		// 4. 获取文件
		Map<String, String> fileMap = Maps.newHashMap();
		context.setFiles(fileMap);

		for (MissionTemplate missionTemplate : context.getMissions()) {
			List<String> attachmentIds = JSON.parseArray(missionTemplate.getAttachment(),
					String.class);

			List<Attachment> attachments = attachmentService.batchGetByIds(attachmentIds);
			for (Attachment attachment : attachments) {
				fileMap.put(attachment.getResourceId(), attachment.getFileName());
			}
		}
		// 5. 设置业务类型
		context.setType(template.getBusinessType());

		return context;
	}

	@Override
	public Boolean batchLoad(String unZipPath) {
		// 1. read data.json
		String dataJson = FileUtil.getContent(
				new File(unZipPath + File.separator + CrCommonConstants.RESOURCE_FILE_NAME));
		List<ResourceDumpContext> resources = JsonUtil.toJsonObjectArray(dataJson,
				ResourceDumpContext.class);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				for (ResourceDumpContext resource : resources) {
					// 2.load business
					businessTemplateHandler.loadBusinessResource(resource);
					// 3. load missions
					List<MissionTemplate> missionTemplates = resource.getMissions();
					missionService.batchLoadMissionTemplates(missionTemplates);
					// 3. load businessTemplate
					BusinessTemplate template = resource.getBusinessTemplate();
					loadBusinessTemplate(template);

					// 4. save topo
					JSONObject topoData = resource.getSceneData();
					remoteTopoService.save(topoData);

					// 5. save images
					Map<String, String> files = resource.getFiles();
					loadImages(files,
							unZipPath + File.separator + CrCommonConstants.RESOURCE_IMAGE_DIR);
				}
			}
		});
		return true;
	}

	private Boolean loadImages(Map<String, String> files, String imageDir) {
		if (files == null || files.size() == 0) {
			return false;
		}
		for (Map.Entry<String, String> entry : files.entrySet()) {
			attachmentService.loadImage(entry.getKey(), entry.getValue(), imageDir);
		}

		return true;
	}

	private void loadBusinessTemplate(BusinessTemplate template) {
		template.setId(null);
		template.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		template.setCreateTime(null);
		template.setUpdateTime(null);
		template.setLocalSceneId(null);
		template.setOperationAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		template.setOperationAuthIds("[]");
		if (getById(template.getResourceId()) != null) {
			updateById(template);
		} else {
			save(template);
		}
	}

	private JSONObject getMpSceneData(BusinessTemplate template) {
		String localSceneId = template.getLocalSceneId();
		// localSceneId字段为空
		if (StringUtils.isBlank(localSceneId)) {
			return null;
		}
		// localScene不存在，localSceneId字段是脏数据
		LocalScene localScene = localSceneService.getById(localSceneId);
		if (null == localScene) {
			template.setLocalSceneId("");
			updateById(template);
			return null;
		}
		// localScene 的 mpSceneId为空，localScene是脏数据
		String mpSceneId = localScene.getMpSceneId();
		if (StringUtils.isBlank(mpSceneId)) {
			clearLocal(template);
			return null;
		}
		// 请求远程场景数据
		JSONObject sceneData = localSceneService.getSceneData(mpSceneId,
				UserInfoContext.getUserContext().getUserId());
		// 场景数据为空，清除本地脏数据
		if (null == sceneData) {
			clearLocal(template);
			return null;
		}
		// 场景实际已被删除，清除本地脏数据
		SceneData data = sceneData.toJavaObject(SceneDataImpl.class);
		if (data.isStopped()) {
			clearLocal(template);
			return null;
		}
		sceneData.put(CrCommonConstants.MP_SCENE_ID_KEY, mpSceneId);
		return sceneData;
	}

	private void deleteBatch(List<BusinessTemplate> businessTemplates) {
		List<String> businessTemplateIds = StreamApiUtils.map(businessTemplates,
				BusinessTemplate::getResourceId);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				for (BusinessTemplate businessTemplate : businessTemplates) {
					// 校验是否符合删除条件
					BusinessTemplateHandler handler = businessTemplateHandler
							.getHandler(businessTemplate.getBusinessType());
					handler.checkBeforeDel(businessTemplate);
					// 先删除测试场景
					if (StringUtils.isNotBlank(businessTemplate.getLocalSceneId())) {
						LocalScene localScene = localSceneService
								.getById(businessTemplate.getLocalSceneId());

						if (localScene != null
								&& StringUtils.isNotBlank(localScene.getMpSceneId())) {
							try {
								localSceneService.deleteRemoteScene(localScene);
							} catch (Exception e) {
								log.error(String.format(
										"failed to delete test scene [%s] of business template [%s]",
										localScene.getMpSceneId(),
										businessTemplate.getResourceId()));
								// do nothing
							}
						}
						// 删除测试场景的localScene
						localSceneService.removeById(businessTemplate.getLocalSceneId());
					}
					// 删除中台拓扑
					try {
						localSceneService.deleteTopo(businessTemplate.getTopoId());
					} catch (Exception e) {
						log.error(String.format(
								"failed to delete topo [%s] of business template [%s]",
								businessTemplate.getTopoId(), businessTemplate.getResourceId()));
						// do nothing
					}
					// 删除模板相关其他
					handler.deleteTemplateRelated(businessTemplate);
				}
				// 删除模板
				businessTemplateMapper.deleteBatchIds(businessTemplateIds);
				// 删除attack、engage关联
				rtksRelatedService.clearAttackRel(businessTemplateIds);
				rtksRelatedService.clearEngageRel(businessTemplateIds);
			}
		});
	}

	private void addBatchDeleteLog(List<BusinessTemplate> businessTemplateList, UserInfo userInfo) {
		if (businessTemplateList.isEmpty()) {
			return;
		}
		BusinessTemplate businessTemplate = businessTemplateList.get(0);
		Integer businessType = businessTemplate.getBusinessType();
		OperationModule module = null;
		String msg = "";
		if (Objects.equals(businessType, BusinessType.TRAINING_SINGLE.getCode())) {
			module = OperationModule.SINGLE_TRAIN;
			msg = LogContent.SINGLE_TRAIN_TEMPLATE.getMsg();
		} else if (Objects.equals(businessType, BusinessType.TRAINING_TEAM.getCode())) {
			module = OperationModule.TEAM_TRAIN;
			msg = LogContent.TEAM_TRAIN_TEMPLATE.getMsg();
		} else if (Objects.equals(businessType, BusinessType.RED_VS_BLUE.getCode())) {
			module = OperationModule.RED_VS_BLUE;
			msg = LogContent.RB_ROOM_TEMPLATE.getMsg();
		}

		List<String> names = StreamApiUtils.getColumn(businessTemplateList,
				BusinessTemplate::getName);
		remoteLogService.addOjLog(userInfo, module, OperationType.DELETE,
				LogContent.BATCH_DELETE_FORMAT.format(msg),
				LogContent.CR_FORMAT.format(OperationType.DELETE, msg, names.toString()));
	}
}
