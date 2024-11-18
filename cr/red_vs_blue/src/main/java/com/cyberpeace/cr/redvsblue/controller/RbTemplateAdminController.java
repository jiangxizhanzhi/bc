package com.cyberpeace.cr.redvsblue.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrCommonService;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigRb;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.cr.redvsblue.controller.req.RbTemplateAddReq;
import com.cyberpeace.cr.redvsblue.controller.req.RbTemplateModifyReq;
import com.cyberpeace.cr.redvsblue.controller.rsp.BaseInfoRsp;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbTemplateRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.service.RbTemplateService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.integratation.service.RemoteSceneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * RB 模板 Admin Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Api(tags = "红蓝对抗模板后台")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("/cr/admin/red_vs_blue/red_vs_blue_templates/")
public class RbTemplateAdminController {

	/**
	 * 业务模板Service
	 *
	 * @see BusinessTemplateService
	 */
	@Autowired
	private BusinessTemplateService businessTemplateService;

	/**
	 * RB模板Service
	 *
	 * @see RbTemplateService
	 */
	@Autowired
	private RbTemplateService rbTemplateService;

	/**
	 * 靶场常见Service
	 *
	 * @see CrCommonService
	 */
	@Autowired
	private CrCommonService crCommonService;

	/**
	 * 任务模板Service
	 *
	 * @see MissionTemplateService
	 */
	@Autowired
	private MissionTemplateService missionTemplateService;

	/**
	 * 事务模板
	 *
	 * @see TransactionTemplate
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 当地场景Service
	 *
	 * @see LocalSceneService
	 */
	@Autowired
	private LocalSceneService localSceneService;

	/**
	 * 远程日志Service
	 *
	 * @see RemoteLogService
	 */
	@Autowired
	private RemoteLogService remoteLogService;

	/**
	 * 业务模板Mapper
	 *
	 * @see BusinessTemplateMapper
	 */
	@Autowired
	private BusinessTemplateMapper businessTemplateMapper;

	/**
	 * 远程场景Service
	 *
	 * @see RemoteSceneService
	 */
	@Autowired
	private RemoteSceneService remoteSceneService;

	/**
	 * 创造
	 *
	 * @param addReq 新增请求体
	 * @return {@code BaseRsp<Object> }
	 * @throws
	 * @see RbTemplateAddReq
	 * @see BaseRsp<Object>
	 */
	@ApiOperation("新增")
	@PostMapping()
	@RequiresPermissions(MenuConstant.CMS_RED_VS_BLUE)
	public BaseRsp<Object> create(@RequestBody @Validated RbTemplateAddReq addReq) {

		// set type
		addReq.getBaseInfo().setType(BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());
		// create team template
		RbTemplate rbTemplate = reqToRbTemplate(addReq, new RbTemplate());
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				// save business template
				String businessTemplateId = businessTemplateService
						.addTemplate(addReq.getBaseInfo());
				// save team template
				rbTemplate.setBusinessTemplateId(businessTemplateId);
				rbTemplateService.save(rbTemplate);
				// update missionTemplates
				businessTemplateService.updateMissionTemplateRelation(businessTemplateId, "",
						rbTemplate.getRoleConfig());
			}
		});

		String rbTrainTemplateName = addReq.getBaseInfo().getName();
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RED_VS_BLUE,
				OperationType.CREATE, rbTrainTemplateName,
				LogContent.CR_FORMAT.format(OperationType.CREATE,
						LogContent.RB_ROOM_TEMPLATE.getMsg(), rbTrainTemplateName));

		return new BaseRsp<>();
	}

	/**
	 * 更新
	 *
	 * @param modifyReq 修改请求体
	 * @return {@code BaseRsp }
	 * @throws
	 * @see RbTemplateModifyReq
	 * @see BaseRsp
	 */
	@ApiOperation("修改")
	@PatchMapping()
	@RequiresPermissions(MenuConstant.CMS_RED_VS_BLUE)
	public BaseRsp update(@RequestBody @Validated RbTemplateModifyReq modifyReq) {
		String resourceId = modifyReq.getResourceId();
		RbTemplate rbTemplate = rbTemplateService.getRbTemplateByBusinessId(resourceId);
		BusinessTemplate businessTemplate = businessTemplateService.getById(resourceId);
		if (businessTemplate == null) {
			log.error("the business template is not exist, id:{}", resourceId);
			throw new ResourceNotFoundException(resourceId);
		}
		String oldRoleConfig = rbTemplate.getRoleConfig();
		// update
		reqToRbTemplate(modifyReq, rbTemplate);

		// save
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				businessTemplateService.updateTemplate(businessTemplate, modifyReq.getBaseInfo());
				rbTemplateService.updateById(rbTemplate);
				// delete&update missionTemplates
				businessTemplateService.updateMissionTemplateRelation(resourceId, oldRoleConfig,
						rbTemplate.getRoleConfig());
			}
		});

		String rbTrainTemplateName = modifyReq.getBaseInfo().getName();
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RED_VS_BLUE,
				OperationType.UPDATE, rbTrainTemplateName,
				LogContent.CR_FORMAT.format(OperationType.UPDATE,
						LogContent.RB_ROOM_TEMPLATE.getMsg(), rbTrainTemplateName));

		return new BaseRsp<>();
	}

	/**
	 * 批量删除
	 *
	 * @param deleteReq 删除请求体
	 * @return {@code BaseRsp<String> }
	 * @throws
	 * @see DeleteReq
	 * @see BaseRsp<String>
	 */
	@ApiOperation("删除")
	@DeleteMapping()
	@RequiresPermissions(MenuConstant.CMS_RED_VS_BLUE)
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		List<BusinessTemplate> businessTemplates = businessTemplateMapper
				.selectBatchIds(deleteReq.getIds());
		rbTemplateService.batchDelete(deleteReq.getIds());

		if (null != businessTemplates) {
			List<String> columns = StreamApiUtils.getColumn(businessTemplates,
					BusinessTemplate::getName);
			remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RED_VS_BLUE,
					OperationType.DELETE,
					LogContent.BATCH_DELETE_FORMAT.format(LogContent.RB_ROOM_TEMPLATE.getMsg()),
					LogContent.CR_FORMAT.format(OperationType.DELETE,
							LogContent.RB_ROOM_TEMPLATE.getMsg(), columns.toString()));
		}
		return new BaseRsp<>();
	}

	/**
	 * 获取RB模板
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<RbTemplateRsp> }
	 * @throws
	 * @see String
	 * @see BaseRsp<RbTemplateRsp>
	 */
	@ApiOperation("查询")
	@GetMapping()
	@RequiresPermissions(value = { MenuConstant.CMS_RED_VS_BLUE, MenuConstant.WEB_CR_RED_VS_BLUE },
			logical = Logical.OR)
	public BaseRsp<RbTemplateRsp> getRbTemplate(String resource_id) {

		businessTemplateService.checkPermission(resource_id,
				UserInfoContext.getUserContext().getUserId());
		RbTemplate template = rbTemplateService.getRbTemplateByBusinessId(resource_id);

		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());

		// 获取红蓝角色
		String roleString = template.getRoleConfig();
		JSONObject roleObject = JsonUtil.toJsonObject(roleString, JSONObject.class);

		Map<String, MissionTemplate> mapping = missionTemplateService
				.getMappingBySchemeJson(roleString);
		List<JSONObject> redRoleRsps = getRoles(roleObject.getJSONArray("red_roles"), mapping);
		List<JSONObject> blueRoleRsps = getRoles(roleObject.getJSONArray("blue_roles"), mapping);
		dealAssetNodesMap(redRoleRsps);
		dealAssetNodesMap(blueRoleRsps);
		// 获取基本信息
		BaseInfoRsp baseInfoRsp = new BaseInfoRsp(template, businessTemplate);

		baseInfoRsp.setSceneConfigData(localSceneService.getTopoData(businessTemplate.getTopoId()));
		String sceneManagerId = "";
		LocalScene localScene = localSceneService
				.getByBusinessInstanceId(businessTemplate.getResourceId());
		if (localScene != null) {
			sceneManagerId = localScene.getResourceId();
		}
		baseInfoRsp.setSceneManagerId(sceneManagerId);

		// 如果场景信息不为null，提取logo参数（默认为空字符串）
		if (baseInfoRsp.getSceneConfigData() != null) {
			baseInfoRsp.setTopo(baseInfoRsp.getSceneConfigData().getString("logo"));
		}

		// 获取返回对象
		RbTemplateRsp teamTrainTemplateRsp = new RbTemplateRsp();

		teamTrainTemplateRsp.setBaseInfo(baseInfoRsp);
		teamTrainTemplateRsp.setResourceId(template.getResourceId());
		teamTrainTemplateRsp.setRedRoles(redRoleRsps);
		teamTrainTemplateRsp.setBlueRoles(blueRoleRsps);

		return new BaseRsp<>(teamTrainTemplateRsp);
	}

	/**
	 * 交易资产节点映射
	 *
	 * @param roleRsps 角色 RSP
	 * @throws
	 * @see List<JSONObject>
	 */
	public void dealAssetNodesMap(List<JSONObject> roleRsps ) {
		for (JSONObject roleRsp : roleRsps) {
			List<Object> assetNodes = roleRsp.getObject("asset_nodes", ArrayList.class);
			Map<String,Integer>  assetMap = new HashMap<>();
			if (CollectionUtils.isEmpty(assetNodes)) {
				roleRsp.put("assetMap",assetMap);
				continue;
			}
			List<CrRoleNode> crRoleNodes = BeanUtils.deepCopyList(assetNodes, CrRoleNode.class);
			Map<Integer, List<CrRoleNode>> nodeMap = crRoleNodes.stream().collect(Collectors.groupingBy(CrRoleNode::getCategory));
			for (CrRoleNode.CategoryGroup value : CrRoleNode.CategoryGroup.values()) {
				assetMap.put(value.getEnglish(),nodeMap.getOrDefault(value.getCode(),new ArrayList<>()).size());
			}
			roleRsp.put("assetMap",assetMap);
		}
	}

	/**
	 * 获取角色
	 *
	 * @param roleArray 角色数组
	 * @param mapping   映射
	 * @return {@code List<JSONObject> }
	 * @throws
	 * @see JSONArray
	 * @see Map<String, MissionTemplate>
	 * @see List<JSONObject>
	 */
	private List<JSONObject> getRoles(JSONArray roleArray, Map<String, MissionTemplate> mapping) {
		List<JSONObject> result = Lists.newArrayList();
		// 逐角色逐阶段读取任务信息
		roleArray.forEach(role -> {
			CrRoleConfig roleRsp = ((JSONObject) role).toJavaObject(CrRoleConfig.class);
			roleRsp.setId(IDGeneratorUtil.getID());
			roleRsp.getMissionsScheme().getSchemeData()
					.forEach(datum -> datum.getMissions().forEach(mission -> {
						MissionTemplate template = mapping.get(mission.getResourceId());
						mission.setTitle(template.getTitle());
						mission.setDescription(template.getDescription());
					}));
			// 转为蛇形
			result.add(JsonUtil.java2JsonObject(roleRsp, PropertyNamingStrategy.SnakeCase));
		});
		return result;
	}

	/**
	 * 请求体自RB模板
	 *
	 * @param req        请求体
	 * @param rbTemplate RB 模板
	 * @return {@code RbTemplate }
	 * @throws
	 * @see RbTemplateAddReq
	 * @see RbTemplate
	 * @see RbTemplate
	 */
	private RbTemplate reqToRbTemplate(RbTemplateAddReq req, RbTemplate rbTemplate) {

		CrRoleConfigRb roleConfigRb = BeanUtils.copy(req, CrRoleConfigRb.class);
		BaseInfo baseInfo = req.getBaseInfo();
		// validate role config
		crCommonService.validateRbRoleConfig(roleConfigRb);
		// validate tag
		rbTemplate.setPreSkill(CrCommonUtil.handleTrainingTag(baseInfo.getPreSkill()));
		rbTemplate.setTrainSkill(CrCommonUtil.handleTrainingTag(baseInfo.getTrainSkill()));
		rbTemplate.setSubType(baseInfo.getCategory());
		rbTemplate.setDurationTime(baseInfo.getDurationTime());
		rbTemplate.setRoleConfig(CrCommonUtil.roleConfig2DbJson(roleConfigRb));

		return rbTemplate;
	}

}
