package com.cyberpeace.cr.crbase.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.controller.req.*;
import com.cyberpeace.cr.crbase.controller.req.mission.MissionTemplatePageReq;
import com.cyberpeace.cr.crbase.controller.req.resource.BatchDumpReq;
import com.cyberpeace.cr.crbase.controller.rsp.MissionSchemeTemplateRsp;
import com.cyberpeace.cr.crbase.controller.rsp.MissionTemplateRsp;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.entity.MissionSchemeTemplate;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.dao.entity.RadarLabel;
import com.cyberpeace.cr.crbase.dao.mapper.MissionSchemeTemplateMapper;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.mission.MissionCategory;
import com.cyberpeace.cr.crbase.service.context.mission.MissionSchemeTemplateConfig;
import com.cyberpeace.cr.crbase.service.context.mission.MissionTemplateRes;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.exception.ZipException;

/**
 * 任务控制器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Api(tags = "任务相关接口")
@RestController
@RequestMapping("/cr/admin/mission")
@RequiresAuthentication
@Slf4j
public class MissionController {

	@Autowired
	private MissionService missionService;

	@Autowired
	private MissionSchemeTemplateMapper missionSchemeTemplateMapper;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private RemoteLogService remoteLogService;

	/**
	 * 获取学员技能点雷达图
	 *
	 * @param resource_id 资源id
	 * @param user_hash   用户希
	 * @return {@link BaseRsp}<{@link List}<{@link Integer}>>
	 */
	@ApiOperation("获取学员技能点雷达图")
	@GetMapping("/missions/radar")
	@RequiresPermissions(value = { MenuConstant.CMS_CR_MISSION_SCHEME, MenuConstant.WEB_ALL },
			logical = Logical.OR)
	public BaseRsp<List<Integer>> getRadar(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
			@ApiParam(name = "user_hash", value = "用户id", required = true) String user_hash) {
		List<Integer> radars = crRoleService.getRadar(resource_id, user_hash);

		return new BaseRsp<>(radars);
	}

	/**
	 * 获取雷达标签
	 *
	 * @return {@link BaseRsp}<{@link JSONObject}>
	 */
	@ApiOperation("获取任务技能点")
	@GetMapping("/missions/radar_labels")
	@RequiresPermissions(value = { MenuConstant.CMS_CR_MISSION_SCHEME, MenuConstant.WEB_ALL },
			logical = Logical.OR)
	public BaseRsp<JSONObject> getRadarLabel() {
		List<RadarLabel> radarLabels = missionService.getRadarLabel();

		List<String> labels = StreamApiUtils.getColumn(radarLabels, RadarLabel::getLabel);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("labels", labels);

		return new BaseRsp<>(jsonObject);
	}

	/**
	 * 后台更新任务技能点
	 *
	 * @param labels 标签
	 * @return {@link BaseRsp}
	 */
	@ApiOperation("后台更新任务技能点")
	@PostMapping("/missions/radar_labels")
	@RequiresPermissions(MenuConstant.CMS_CR_MISSION_SCHEME)
	public BaseRsp updateRadarLabel(@RequestBody @Validated LabelReq labels) {
		// 只有超级用户有权修改
		UserInfo user = UserInfoContext.getUserContext();
		if (!user.isSuperuser()) {
			throw new BusinessException(CrBusinessErrorCode.SUPERUSER_CAN_EDIT_RADAR);
		}

		List<RadarLabel> radarLabelList = Lists.newArrayList();

		for (int i = 0; i < labels.getLabels().size(); i++) {
			RadarLabel radarLabel = new RadarLabel();
			radarLabel.setResourceId(i + 1);
			radarLabel.setLabel(labels.getLabels().get(i));

			radarLabelList.add(radarLabel);
		}
		missionService.updateRadarLabel(radarLabelList);

		return new BaseRsp();
	}

	/**
	 * 获取任务
	 *
	 * @param resourceId 资源id
	 * @return {@link BaseRsp}<{@link MissionTemplateRsp}>
	 */
	@ApiOperation("获取任务")
	@GetMapping("/missions")
	@RequiresPermissions(value = { MenuConstant.CMS_CR_MISSION_SCHEME, MenuConstant.WEB_ALL },
			logical = Logical.OR)
	public BaseRsp<MissionTemplateRsp>
			getMission(@RequestParam("resource_id") @Validated String resourceId) {
		MissionTemplate missionTemplate = missionService.getByMissionTemplateId(resourceId);
		if (missionTemplate == null) {
			return new BaseRsp<>(null);
		}
		return new BaseRsp<>(missionTemplateToRsp(missionTemplate));
	}

	/**
	 * 添加任务
	 *
	 * @param missionTemplateReq 任务模板请求
	 * @return {@link BaseRsp}<{@link MissionTemplateRsp}>
	 */
	@ApiOperation("新增任务")
	@PostMapping("/missions")
	@RequiresPermissions(
			value = { MenuConstant.CMS_CR_MISSION_SCHEME, MenuConstant.CMS_RED_VS_BLUE,
					MenuConstant.CMS_CR_SINGLE_TRAIN, MenuConstant.CMS_CR_TEAM_TRAIN },
			logical = Logical.OR)
	public BaseRsp<MissionTemplateRsp>
			addMission(@RequestBody @Validated MissionTemplateReq missionTemplateReq) {
		missionTemplateReq.validate();
		MissionTemplate toAddMissionTemplate = requestToMissionTemplate(missionTemplateReq);

		MissionTemplate missionTemplate = missionService.addMissionTemplate(toAddMissionTemplate);

		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TASK,
				OperationType.CREATE, missionTemplateReq.getTitle());

		MissionTemplateRsp missionTemplateRsp = missionTemplateToRsp(missionTemplate);
		return new BaseRsp(missionTemplateRsp);
	}

	/**
	 * 更新任务模板
	 *
	 * @param modifyReq 修改请求
	 * @return {@link BaseRsp}<{@link MissionTemplateRsp}>
	 */
	@ApiOperation("后台更新任务")
	@PutMapping("/missions")
	@RequiresPermissions(MenuConstant.CMS_CR_MISSION_SCHEME)
	public BaseRsp<MissionTemplateRsp>
			updateMissionTemplate(@RequestBody @Validated MissionTemplateModifyReq modifyReq) {
		if (StringUtils.isBlank(modifyReq.getResourceId())) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		modifyReq.validate();
		MissionTemplate toUpdateMissionTemplate = requestToMissionTemplate(modifyReq);
		missionService.updateMissionTemplate(toUpdateMissionTemplate);
		MissionTemplate ret = missionService
				.getByMissionTemplateId(toUpdateMissionTemplate.getResourceId());

		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TASK,
				OperationType.UPDATE, modifyReq.getTitle());

		MissionTemplateRsp missionTemplateRsp = missionTemplateToRsp(ret);
		return new BaseRsp(missionTemplateRsp);
	}

	/**
	 * 更新任务
	 *
	 * @param missionModifyReq 任务修改请求
	 * @return {@link BaseRsp}<{@link ?}>
	 */
	@ApiOperation("前台更新任务")
	@PutMapping("/mission_update")
	@RequiresPermissions(value = { MenuConstant.CMS_CR_MISSION_SCHEME, MenuConstant.WEB_ALL },
			logical = Logical.OR)
	public BaseRsp<?> updateMissions(@RequestBody @Validated MissionModifyReq missionModifyReq) {
		missionModifyReq.validate();
		if (StringUtils.isBlank(missionModifyReq.getResourceId())) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		Mission toUpdateMission = requestToMission(missionModifyReq);
		missionService.updateById(toUpdateMission);

		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TASK,
				OperationType.UPDATE, missionModifyReq.getTitle());

		return new BaseRsp();
	}

	/**
	 * 获取任务类型
	 *
	 * @return {@link BaseRsp}<{@link List}<{@link MissionCategory}>>
	 */
	@ApiOperation("获取任务类型")
	@GetMapping("/missions/get_category/")
	@RequiresPermissions(value = { MenuConstant.CMS_CR_MISSION_SCHEME, MenuConstant.WEB_ALL },
			logical = Logical.OR)
	public BaseRsp<List<MissionCategory>> listCategories() {

		List<MissionCategory> categories = missionService.listCategories();

		return new BaseRsp<>(categories);
	}

	/**
	 * 创建计划模板
	 *
	 * @param templateReq 模板请求
	 * @return {@link BaseRsp}<{@link ?}>
	 */
	@ApiOperation("创建任务方案")
	@PostMapping("/combat_techs")
	@RequiresPermissions(MenuConstant.CMS_CR_MISSION_SCHEME)
	public BaseRsp<?>
			createSchemeTemplate(@RequestBody @Validated MissionSchemeTemplateReq templateReq) {
		// validate
		templateReq.validate();
		missionService.checkSchemeTemplateTitleDuplicate(templateReq.getTitle());

		MissionSchemeTemplate schemeTemplate = requestToSchemeTemplate(templateReq);
		missionService.addSchemeTemplate(schemeTemplate);

		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TASK,
				OperationType.CREATE, templateReq.getTitle());

		return new BaseRsp<>();
	}

	/**
	 * 批量删除计划模板
	 *
	 * @param deleteReq 删除请求
	 * @return {@link BaseRsp}<{@link ?}>
	 */
	@ApiOperation("批量删除任务方案")
	@DeleteMapping("/combat_techs/batch_destroy/")
	@RequiresPermissions(MenuConstant.CMS_CR_MISSION_SCHEME)
	public BaseRsp<?> batchDeleteSchemeTemplate(@RequestBody @Validated BatchDeleteReq deleteReq) {

		List<MissionSchemeTemplate> missionSchemeTemplateList = missionSchemeTemplateMapper
				.selectBatchIds(deleteReq.getIds());

		missionService.batchDeleteSchemeTemplate(deleteReq.getIds());

		missionSchemeTemplateList.forEach(missionSchemeTemplate -> {
			if (missionSchemeTemplate != null) {
				remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TASK,
						OperationType.DELETE, missionSchemeTemplate.getTitle());
			}
		});

		return new BaseRsp<>();
	}

	/**
	 * 编辑计划模板
	 *
	 * @param templateModifyReq 模板修改请求
	 * @return {@link BaseRsp}<{@link ?}>
	 */
	@ApiOperation("编辑任务方案")
	@PutMapping("/combat_techs")
	@RequiresPermissions(MenuConstant.CMS_CR_MISSION_SCHEME)
	public BaseRsp<?> editSchemeTemplate(
			@RequestBody @Validated MissionSchemeTemplateModifyReq templateModifyReq) {
		templateModifyReq.validate();
		MissionSchemeTemplate toUpdateSchemeTemplate = requestToSchemeTemplate(templateModifyReq);
		missionService.updateMissionSchemeTemplateById(toUpdateSchemeTemplate);

		MissionSchemeTemplate missionSchemeTemplate = missionSchemeTemplateMapper
				.selectById(templateModifyReq.getResourceId());
		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TASK,
				OperationType.UPDATE, missionSchemeTemplate.getTitle());

		return new BaseRsp<>();
	}

	/**
	 * 分页查询任务方案
	 *
	 * @param pageReqBase 页面请求基地
	 * @return {@link BaseRsp}<{@link List}<{@link MissionSchemeTemplateRsp}>>
	 */
	@ApiOperation("分页查询任务方案")
	@GetMapping("/combat_techs/list")
	@RequiresPermissions(MenuConstant.CMS_CR_MISSION_SCHEME)
	public BaseRsp<List<MissionSchemeTemplateRsp>>
			listSchemeTemplate(@Validated PageReqBase pageReqBase) {
		ListRsp<MissionSchemeTemplateRsp> schemeTemplateListRsp = missionService
				.pageSchemeTemplateRsp(pageReqBase);
		List<String> userIds = Lists.newArrayList();
		for (MissionSchemeTemplateRsp rsp : schemeTemplateListRsp.getList()) {
			userIds.add(rsp.getCreateUser());
		}
		Map<String, String> idNameMap = remoteUserService.getIdNameMap(userIds);

		for (MissionSchemeTemplateRsp rsp : schemeTemplateListRsp.getList()) {
			rsp.setRepName(idNameMap.get(rsp.getCreateUser()));
		}
		return new BaseRsp(schemeTemplateListRsp);
	}

	/**
	 * 查询单个任务方案
	 *
	 * @param resourceId 资源id
	 * @return {@link BaseRsp}<{@link MissionSchemeTemplateRsp}>
	 */
	@ApiOperation("查询单个任务方案")
	@GetMapping("/combat_techs")
	@RequiresPermissions(MenuConstant.CMS_CR_MISSION_SCHEME)
	public BaseRsp<MissionSchemeTemplateRsp> getSchemeTemplate(@ApiParam(name = "resource_id",
			value = "任务方案编号") @RequestParam("resource_id") @Valid @NotBlank String resourceId) {
		MissionSchemeTemplateRsp templateRsp = missionService.getSchemeTemplateRspById(resourceId);
		templateRsp.setRepName(remoteUserService.getById(templateRsp.getCreateUser()).getName());
		return new BaseRsp(templateRsp);
	}

	/**
	 * 批量导出任务方案
	 *
	 * @param response 响应
	 * @param dumpReq  转储请求
	 */
	@ApiOperation("批量导出任务方案")
	@PostMapping("/combat_techs/dump")
	@RequiresPermissions(MenuConstant.CMS_CR_MISSION_SCHEME)
	public void batchDump(HttpServletResponse response, @RequestBody BatchDumpReq dumpReq) {

		List<String> templateIds = dumpReq.getIds();
		// 1. generate dump file
		String tempDir = ZipUtil.generatePath();
		try {
			String zipPath = missionService.makeDumpFile(templateIds, tempDir);
			// 2. generate file response
			File zip = new File(zipPath);
			FileUtil.setResponse(zip, false, response);
		} catch (Exception e) {
			log.error("failed to dump combat techs", e);
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_DUMP_COMBAT_TECH);
		} finally {
			// 3. delete temp dir
			if (!FileUtil.deleteDir(new File(tempDir))) {
				log.error("failed to remove temp dir :{}", tempDir);
			}
		}
	}

	/**
	 * 批量导入任务方案
	 *
	 * @param file 文件
	 * @return {@link BaseRsp}
	 * @throws IOException  ioexception
	 * @throws ZipException 邮政编码异常
	 */
	@ApiOperation("批量导入任务方案")
	@PutMapping("/combat_tech/load")
	@RequiresPermissions(MenuConstant.CMS_CR_MISSION_SCHEME)
	public BaseRsp batchLoad(@RequestParam("file") MultipartFile file)
			throws IOException, ZipException {
		// 1. save file to temp dir
		File localFile = FileUtil.saveFile(file, ZipUtil.generatePath());
		// 2. unzip file
		String unZipPath = ZipUtil.unZipToTmpPath(localFile.getAbsolutePath());
		// 3. load resources
		missionService.batchLoadScheme(unZipPath);
		// 4. delete temp dir
		if (!FileUtil.deleteDir(new File(unZipPath))) {
			log.error("failed to delete temp dir");
		}
		return new BaseRsp();
	}

	/**
	 * 任务列表模板
	 *
	 * @param pageReq 页面请求
	 * @return {@link BaseRsp}<{@link List}<{@link MissionTemplateRes}>>
	 */
	@ApiOperation("RTKS-获取任务模板列表")
	@GetMapping("/mission_templates")
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<List<MissionTemplateRes>>
			listMissionTemplates(@Validated MissionTemplatePageReq pageReq) {
		ListRsp<MissionTemplateRes> missionTemplateRspListRsp = missionService
				.pageMissionTemplate(pageReq);
		return new BaseRsp(missionTemplateRspListRsp);
	}

	/**
	 * 请求任务模板
	 *
	 * @param missionTemplateReq 任务模板请求
	 * @return {@link MissionTemplate}
	 */
	private MissionTemplate requestToMissionTemplate(MissionTemplateReq missionTemplateReq) {
		MissionTemplate missionTemplate = BeanUtils.copy(missionTemplateReq, MissionTemplate.class);

		missionTemplate.setCategoryId(missionTemplateReq.getCategory());
		missionTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		missionTemplate.setSkillPoints(JSON.toJSONString(missionTemplateReq.getSkillPoints()));
		missionTemplate.setResourceId(IDGeneratorUtil.getID());
		missionTemplate.setAttachment(getAttachments(missionTemplate.getWriteUp()));

		return missionTemplate;
	}

	/**
	 * 请求任务模板
	 *
	 * @param modifyReq 修改请求
	 * @return {@link MissionTemplate}
	 */
	private MissionTemplate requestToMissionTemplate(MissionTemplateModifyReq modifyReq) {
		MissionTemplate missionTemplate = BeanUtils.copy(modifyReq, MissionTemplate.class);

		missionTemplate.setCategoryId(modifyReq.getCategory());
		missionTemplate.setSkillPoints(JSON.toJSONString(modifyReq.getSkillPoints()));
		missionTemplate.setAttachment(getAttachments(missionTemplate.getWriteUp()));

		return missionTemplate;
	}

	/**
	 * 获取附件
	 *
	 * @param writeup 那样
	 * @return {@link String}
	 */
	private String getAttachments(String writeup) {
		if (writeup == null || writeup.length() < CrCommonConstants.GENERATED_UUID_LENGTH) {
			return CommonConstant.EMPTY_LIST_JSON;
		}
		String imagePath = AttachmentPath.UPLOAD_IMAGE.getOriginDesc();
		String regex = String.format(PatternConstant.WRITEUP_IMG, imagePath,
				CrCommonConstants.GENERATED_UUID_LENGTH);
		List<String> imageIds = LocalStringUtil.getMatchStr(writeup, regex, 1);

		return JsonUtil.arrayToString(imageIds);
	}

	/**
	 * 请求计划模板
	 *
	 * @param templateModifyReq 模板修改请求
	 * @return {@link MissionSchemeTemplate}
	 */
	private MissionSchemeTemplate
			requestToSchemeTemplate(MissionSchemeTemplateModifyReq templateModifyReq) {
		if (StringUtils.isBlank(templateModifyReq.getResourceId())) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}

		MissionSchemeTemplate schemeTemplate = missionService
				.getSchemeTemplateById(templateModifyReq.getResourceId());
		if (StringUtils.isNotBlank(templateModifyReq.getTitle())
				&& !templateModifyReq.getTitle().equals(schemeTemplate.getTitle())) {
			missionService.checkSchemeTemplateTitleDuplicate(templateModifyReq.getTitle());
			schemeTemplate.setTitle(templateModifyReq.getTitle());
		}
		if (StringUtils.isNotBlank(templateModifyReq.getDescription())) {
			schemeTemplate.setDescription(templateModifyReq.getDescription());
		}
		if (templateModifyReq.getTemplate() != null || templateModifyReq.getColor() != null) {
			MissionSchemeTemplateConfig config = JsonUtil.toJsonObject(
					schemeTemplate.getTemplateConfig(), MissionSchemeTemplateConfig.class);

			Integer template = templateModifyReq.getTemplate() == null ? config.getTemplate()
					: templateModifyReq.getTemplate();

			Integer color = templateModifyReq.getColor() == null ? config.getColor()
					: templateModifyReq.getColor();

			schemeTemplate.setTemplateConfig(
					JSON.toJSONString(new MissionSchemeTemplateConfig(template, color)));
		}
		if (templateModifyReq.getCombatConfig() != null) {
			schemeTemplate.setTemplateData(JSON.toJSONString(templateModifyReq.getCombatConfig()));
		}
		return schemeTemplate;
	}

	/**
	 * 请求计划模板
	 *
	 * @param templateReq 模板请求
	 * @return {@link MissionSchemeTemplate}
	 */
	private MissionSchemeTemplate requestToSchemeTemplate(MissionSchemeTemplateReq templateReq) {
		MissionSchemeTemplate schemeTemplate = BeanUtils.copy(templateReq,
				MissionSchemeTemplate.class);
		schemeTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		schemeTemplate.setTemplateConfig(JSON.toJSONString(new MissionSchemeTemplateConfig(
				templateReq.getTemplate(), templateReq.getColor())));
		schemeTemplate.setTemplateData(JSON.toJSONString(templateReq.getCombatConfig()));

		return schemeTemplate;
	}

	/**
	 * 请求任务
	 *
	 * @param missionModifyReq 任务修改请求
	 * @return {@link Mission}
	 */
	private Mission requestToMission(MissionModifyReq missionModifyReq) {
		Mission mission = missionService.getById(missionModifyReq.getResourceId());
		if (StringUtils.isNotBlank(missionModifyReq.getTitle())) {
			missionService.updateMissionValidate(missionModifyReq.getResourceId(),
					mission.getRoomId(), missionModifyReq.getTitle());
			mission.setTitle(missionModifyReq.getTitle());
		}
		if ((missionModifyReq.getScore() != null)) {
			mission.setScore(missionModifyReq.getScore());
		}
		if (missionModifyReq.getSkillPoints() != null) {
			mission.setSkillPoints(missionModifyReq.getSkillPoints());
		}

		return mission;
	}

	/**
	 * 任务模板返回值
	 *
	 * @param missionTemplate 任务模板
	 * @return {@link MissionTemplateRsp}
	 */
	private MissionTemplateRsp missionTemplateToRsp(MissionTemplate missionTemplate) {
		MissionTemplateRsp missionTemplateRsp = BeanUtils.copy(missionTemplate,
				MissionTemplateRsp.class);
		missionTemplateRsp.setSkillPoints(
				JsonUtil.toJsonObjectArray(missionTemplate.getSkillPoints(), Integer.class));
		missionTemplateRsp.setCategory(missionTemplate.getCategoryId());
		return missionTemplateRsp;
	}

}
