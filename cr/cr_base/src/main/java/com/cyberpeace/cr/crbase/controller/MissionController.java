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
import com.cyberpeace.common.constant.*;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ExtractBase;
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
import com.cyberpeace.cr.crbase.service.context.mission.MissionDetail;
import com.cyberpeace.cr.crbase.service.context.mission.MissionSchemeTemplateConfig;
import com.cyberpeace.cr.crbase.service.context.mission.MissionTemplateRes;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.controller.CommonController;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
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

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private UtilService utilService;

	@Autowired
	private CommonController commonController;

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

	@ApiOperation("后台更新任务技能点")
	@PostMapping("/missions/radar_labels")
	@RequiresPermissions(MenuConstant.CMS_CR_MISSION_SCHEME)
	public BaseRsp updateRadarLabel(@RequestBody @Validated LabelReq labels) {
		// 只有超级用户有权修改
		UserInfo user = UserInfoContext.getUserContext();
		if (!user.isSuperuser()) {
			log.error("Only superuser can edit radar labels! userId = [{}]", user.getUserId());
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

	@ApiOperation("获取任务模板详情")
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

	@ApiOperation("获取前台任务详情")
	@GetMapping("/detail")
	@RequiresPermissions(value = { MenuConstant.CMS_CR_MISSION_SCHEME, MenuConstant.WEB_ALL },
			logical = Logical.OR)
	public BaseRsp<MissionDetail>
			getDetail(@RequestParam("resource_id") @Validated String resourceId) {
		MissionDetail res = missionService.getDetailById(resourceId);
		return new BaseRsp<>(res);
	}

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

	@ApiOperation("后台更新任务")
	@PutMapping("/missions")
	@RequiresPermissions(value = {MenuConstant.CMS_CR_MISSION_SCHEME, MenuConstant.CMS_RED_VS_BLUE,
			MenuConstant.CMS_CR_SINGLE_TRAIN, MenuConstant.CMS_CR_TEAM_TRAIN}, logical = Logical.OR)
	public BaseRsp<MissionTemplateRsp>
			updateMissionTemplate(@RequestBody @Validated MissionTemplateModifyReq modifyReq) {
		if (StringUtils.isBlank(modifyReq.getResourceId())) {
			log.error("Resource id missing! modifyReq = [{}]", JsonUtil.toJsonString(modifyReq));
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

	@ApiOperation("前台更新任务")
	@PutMapping("/mission_update")
	@RequiresPermissions(value = {MenuConstant.CMS_CR_MISSION_SCHEME, MenuConstant.WEB_ALL}, logical = Logical.OR)
	public BaseRsp<?>
			updateMissions(@RequestBody @Validated MissionModifyReq missionModifyReq) {
		missionModifyReq.validate();
		if (StringUtils.isBlank(missionModifyReq.getResourceId())) {
			log.error("Resource id missing! modifyReq = [{}]", JsonUtil.toJsonString(missionModifyReq));
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		Mission toUpdateMission = requestToMission(missionModifyReq);
		missionService.updateById(toUpdateMission);

		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TASK,
				OperationType.UPDATE, missionModifyReq.getTitle());

		return new BaseRsp();
	}

	@ApiOperation("获取任务类型")
	@GetMapping("/missions/get_category/")
	@RequiresPermissions(value = {MenuConstant.CMS_CR_MISSION_SCHEME, MenuConstant.WEB_ALL}, logical = Logical.OR)
	public BaseRsp<List<MissionCategory>> listCategories() {

		List<MissionCategory> categories = missionService.listCategories();

		return new BaseRsp<>(categories);
	}

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

	@ApiOperation("分页查询任务方案")
	@GetMapping("/combat_techs/list")
	@RequiresPermissions(value = {MenuConstant.CMS_CR_MISSION_SCHEME, MenuConstant.CMS_RED_VS_BLUE,
			MenuConstant.CMS_CR_SINGLE_TRAIN, MenuConstant.CMS_CR_TEAM_TRAIN}, logical = Logical.OR)
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

	@ApiOperation("查询单个任务方案")
	@GetMapping("/combat_techs")
	@RequiresPermissions(MenuConstant.CMS_CR_MISSION_SCHEME)
	public BaseRsp<MissionSchemeTemplateRsp> getSchemeTemplate(@ApiParam(name = "resource_id",
			value = "任务方案编号") @RequestParam("resource_id") @Valid @NotBlank String resourceId) {
		MissionSchemeTemplateRsp templateRsp = missionService.getSchemeTemplateRspById(resourceId);
		templateRsp.setRepName(remoteUserService.getById(templateRsp.getCreateUser()).getName());
		return new BaseRsp(templateRsp);
	}

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

	@ApiOperation("RTKS-获取任务模板列表")
	@GetMapping("/mission_templates")
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<List<MissionTemplateRes>>
			listMissionTemplates(@Validated MissionTemplatePageReq pageReq) {
		ListRsp<MissionTemplateRes> missionTemplateRspListRsp = missionService
				.pageMissionTemplate(pageReq);
		return new BaseRsp(missionTemplateRspListRsp);
	}

	@ApiOperation("解析md文件数据")
	@PostMapping("/parse/md")
	public BaseRsp<ExtractBase> parseMDFile(@RequestParam("missionId") String missionId,
							  @RequestParam("md") MultipartFile md){
		String realContent = null;
		String suffix = FileUtil.getFileSuffix(md.getOriginalFilename());
		suffix = suffix.toLowerCase();
		if (!suffix.equals(FileSunffixConstant.ZIP.getDesc()) && !suffix.equals(FileSunffixConstant.RAR.getDesc())){
			log.error("Unsupported file types. Only [{}] is supported! suffix = [{}]", FileSunffixConstant.ZIP.getDesc() + "," + FileSunffixConstant.RAR.getDesc(),suffix );
			throw new BusinessException(ErrorCode.NOT_SUPPORT_FILE_TYPE,
					"" + FileSunffixConstant.ZIP.getDesc() + "," + FileSunffixConstant.RAR.getDesc());
		}
		// 文件上传至服务器 原始文件地址存储
		String markdownPath = String.format(AttachmentPath.MARKDOWN.getDesc(),
				missionId);
		AttachmentBo attachmentBo = attachmentService.uploadDest(markdownPath, false, md);
		ExtractBase extractBase = BeanUtils.copy(attachmentBo, ExtractBase.class);
		// 获取md文件存储地址
		String handoutPath = String.format(AttachmentPath.HANDOUT.getDesc(), missionId);
		String realPath = utilService.getRealPath(attachmentBo.getFilePath());
		// 计算要解压的文件地址
		String unzipPath = handoutPath + FileUtil.getFileName(realPath, false);

		//校验是zip还是rar
		String mdFilePath = null;
		if (suffix.equals(FileSunffixConstant.ZIP.getDesc())) {
			ZipUtil.unzipToDest(realPath, unzipPath);
		}else {
			RarUtil.unrarToDest(realPath, unzipPath);
		}
		// 获取解压的rar文件
		mdFilePath = FileUtil.getOneFile(unzipPath, FileSunffixConstant.MD.getDesc());
		if (StringUtils.isBlank(mdFilePath)) {
			extractBase.setIsExtract(Boolean.FALSE);
			return new BaseRsp<>(extractBase);
		}
		// md文件内容
		String content = MdUtil.parseMarkdown(mdFilePath);
		realContent = utilService.replaceMarkdownFile(content);
		extractBase.setContent(realContent);
		extractBase.setIsExtract(Boolean.TRUE);
		return new BaseRsp<>(extractBase);


	}

	private MissionTemplate requestToMissionTemplate(MissionTemplateReq missionTemplateReq) {
		MissionTemplate missionTemplate = BeanUtils.copy(missionTemplateReq, MissionTemplate.class);

		missionTemplate.setCategoryId(missionTemplateReq.getCategory());
		missionTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		missionTemplate.setSkillPoints(JSON.toJSONString(missionTemplateReq.getSkillPoints()));
		missionTemplate.setResourceId(IDGeneratorUtil.getID());
		missionTemplate.setAttachment(getAttachments(missionTemplate.getWriteUp()));

		return missionTemplate;
	}

	private MissionTemplate requestToMissionTemplate(MissionTemplateModifyReq modifyReq) {
		MissionTemplate missionTemplate = BeanUtils.copy(modifyReq, MissionTemplate.class);

		missionTemplate.setCategoryId(modifyReq.getCategory());
		missionTemplate.setSkillPoints(JSON.toJSONString(modifyReq.getSkillPoints()));
		missionTemplate.setAttachment(getAttachments(missionTemplate.getWriteUp()));

		return missionTemplate;
	}

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

	private MissionSchemeTemplate
			requestToSchemeTemplate(MissionSchemeTemplateModifyReq templateModifyReq) {
		if (StringUtils.isBlank(templateModifyReq.getResourceId())) {
			log.error("Resource id missing! templateModifyReq = [{}]", JsonUtil.toJsonString(templateModifyReq));
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

	private MissionSchemeTemplate requestToSchemeTemplate(MissionSchemeTemplateReq templateReq) {
		MissionSchemeTemplate schemeTemplate = BeanUtils.copy(templateReq,
				MissionSchemeTemplate.class);
		schemeTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		schemeTemplate.setTemplateConfig(JSON.toJSONString(new MissionSchemeTemplateConfig(
				templateReq.getTemplate(), templateReq.getColor())));
		schemeTemplate.setTemplateData(JSON.toJSONString(templateReq.getCombatConfig()));

		return schemeTemplate;
	}

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
		if (missionModifyReq.getDescription() != null) {
			mission.setDescription(missionModifyReq.getDescription());
		}

		return mission;
	}

	private MissionTemplateRsp missionTemplateToRsp(MissionTemplate missionTemplate) {
		MissionTemplateRsp missionTemplateRsp = BeanUtils.copy(missionTemplate,
				MissionTemplateRsp.class);
		missionTemplateRsp.setSkillPoints(
				JsonUtil.toJsonObjectArray(missionTemplate.getSkillPoints(), Integer.class));
		missionTemplateRsp.setCategory(missionTemplate.getCategoryId());
		return missionTemplateRsp;
	}

}
