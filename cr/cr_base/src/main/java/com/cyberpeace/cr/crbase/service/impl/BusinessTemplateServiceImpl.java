package com.cyberpeace.cr.crbase.service.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.entity.LocalSceneMeta;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplateData;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.service.BusinessTemplateHandler;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.*;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;
import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.spring.web.json.Json;

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
	private RemoteTopoService remoteTopoService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	@Qualifier("businessTemplateHandler")
	private BusinessTemplateHandler businessTemplateHandler;

	@Override
	public String addTemplate(BasicTemplate basicTemplate) {
		// 1、从输入业务中解析公共模板
		BusinessTemplate businessTemplate = getBusinessTemplate(basicTemplate);
		// 拓扑场景id保存获取
		SceneConfigData topoData = basicTemplate.getBaseInfo().getSceneConfigData();
		JSONObject topoJson = new JSONObject();
		topoJson.put("scene_config", topoData.getSceneConfig());
		String topoId = localSceneService.saveTopo(topoJson, basicTemplate.getBaseInfo().getType());

		// 保存基础模板
		businessTemplate.setTopoId(topoId);

		// 2、执行实际的保存
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				// 1、保存公共模版数据
				save(businessTemplate);

				// 2、保存业务模板数据
				String teamTemplateId = businessTemplateHandler.saveBusiness(basicTemplate,
						businessTemplate.getResourceId());

			}
		});

		return null;
	}

	@Override
	public List<String> selectAuthIdsByUserId(String userId) {

		return businessTemplateMapper.selectIdsByUserId(userId);
	}

	@Override
	public void validateNamePatch(String name, Integer type, String id) {
		BusinessTemplate businessTemplate = businessTemplateMapper.selectByNameAndType(name, type,
				id);
		if (businessTemplate != null) {
			log.error("template name already exist！name=[{}]", name);
			throw new BusinessException(CrBusinessErrorCode.TEMPLATE_NAME_DUPLICATE);
		}

	}

	@Override
	public BusinessTemplate getByLocalSceneId(String localSceneId) {
		BusinessTemplate res = businessTemplateMapper.selectByLocalSceneId(localSceneId);
		if (null == res) {
			log.error("failed to find businessTemplate with localSceneId [{}]", localSceneId);
			throw new RuntimeException();
		}
		return res;
	}

	@Override
	public void batchLoads(LoadTemplate loadTemplate) {

		List<LoadTemplateData> items = loadTemplate.getItems();
		List<MissionTemplate> missionTemplateList = missionService
				.batchInsertMissionTemplate(loadTemplate.getTemplateQuestions());
		for (LoadTemplateData item : items) {
			businessTemplateHandler.loads(item, missionTemplateList);

		}
	}

	@Override
	public IPage<BusinessTemplateData> page(BusinessTemplateQuery businessTemplateQuery) {
		String userId = UserInfoContext.getUserContext().getUserId();
		Page page = businessTemplateQuery.toPageParam();
		businessTemplateQuery.setIds(businessTemplateMapper.selectIdsByUserId(userId));

		IPage<BusinessTemplateData> result = businessTemplateMapper.listTeamTemplate(page,
				businessTemplateQuery);

		for (BusinessTemplateData businessTemplate : result.getRecords()) {
			businessTemplate.setNodesNum(getCount(businessTemplate.getTopoId()));
		}

		return result;

	}

	private Integer getCount(String topoId) {
		Integer count = 0;
		try {
			count = remoteTopoService.getTopoDigest(topoId).getNodeCounts();
		} catch (Exception e) {
			;
		}

		return count;
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

	private BusinessTemplate getBusinessTemplate(BasicTemplate basicTemplate) {
		BaseInfo baseInfo = basicTemplate.getBaseInfo();
		BusinessTemplate businessTemplate = new BusinessTemplate();
		businessTemplate.setName(baseInfo.getName());
		businessTemplate.setDescription(baseInfo.getDescription());
		businessTemplate.setDifficulty(baseInfo.getDifficult());
		businessTemplate.setBusinessType(basicTemplate.getBaseInfo().getType());

		businessTemplate.setOperationAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		businessTemplate.setOperationAuthIds("[]");
		businessTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		return businessTemplate;
	}

	@Override
	public Integer batchDelete(List<String> business_template_ids) {
		return businessTemplateMapper.deleteBatchIds(business_template_ids);
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

		localScene.setBusinessCode(LocalScene.BusinessCode.TEST_SCENE.getCode());
		localScene.setUserId(UserInfoContext.getUserContext().getUserId());
		// 以模板id作为后台测试场景的 businessId，
		// 发给mp的context中会以localScene的businessInstanceId作为透明上下文（passThrough）的businessId，用于ws消息推送
		localScene.setBusinessInstanceId(template.getResourceId());
		localScene.setTopoId(template.getTopoId());
		localScene.setResourceId(IDGeneratorUtil.getID());
		localScene.setSceneStatus(LocalScene.SceneStatusEnum.INIT.getCode());
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
		// 1、尝试获取启动的远程场景
		JSONObject sceneData = getMpSceneData(template);
		if(sceneData != null){
			ret.setSceneData(sceneData);
			ret.setLocalSceneId(template.getLocalSceneId());
			return ret;
		}
		// 2、 远程场景不存在，返回topo数据
		JSONObject topoData = localSceneService.getTopoData(template.getTopoId()).getJSONObject("scene_config");
		ret.setTopoData(topoData);
		return ret;
	}

	private JSONObject getMpSceneData(BusinessTemplate template){
		String localSceneId = template.getLocalSceneId();
		// localSceneId字段为空
		if (StringUtils.isBlank(localSceneId)){
			return null;
		}
		// localScene不存在，localSceneId字段是脏数据
		LocalScene localScene = localSceneService.getById(localSceneId);
		if (null == localScene){
			template.setLocalSceneId("");
			updateById(template);
			return null;
		}
		// localScene 的 mpSceneId为空，localScene是脏数据
		String mpSceneId = localScene.getMpSceneId();
		if(StringUtils.isBlank(mpSceneId)){
			clearLocal(template);
			return null;
		}
		// 请求远程场景数据
		JSONObject sceneData = localSceneService.getSceneData(mpSceneId, UserInfoContext.getUserContext().getUserId());
		// 场景数据为空，清除本地脏数据
		if(null == sceneData){
			clearLocal(template);
		}
		return sceneData;
	}
}
