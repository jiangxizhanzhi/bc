package com.cyberpeace.rtks.controller;

import java.util.List;

import com.cyberpeace.common.annoations.PermissionOperation;
import com.cyberpeace.common.constant.TrialButtonConstant;
import com.cyberpeace.common.util.StreamApiUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import com.cyberpeace.rtks.controller.req.SkillAddReq;
import com.cyberpeace.rtks.dao.entity.Skill;
import com.cyberpeace.rtks.service.RoleService;
import com.cyberpeace.rtks.service.SkillService;
import com.cyberpeace.rtks.service.bo.skill.SkillRes;
import com.cyberpeace.rtks.service.bo.skill.SkillSimpleRes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "RTKS-技能管理")
@RestController
@RequestMapping("/rtks/skill")
@RequiresAuthentication
public class SkillController {

	@Autowired
	private SkillService skillService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private RoleService roleService;

	@Autowired
	private RemoteLogService remoteLogService;

	@ApiOperation("查询单条")
	@GetMapping()
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<SkillRes> getSkill(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		// ApiParam中的name和java参数String id的两个id必须同名
		SkillRes skillRes = skillService.getSkillByResourceId(resource_id);
		return new BaseRsp<>(skillRes);
	}

	@ApiOperation("新增单条")
	@PostMapping()
	@PermissionOperation(TrialButtonConstant.CMS_RTKS_SKILL)
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<String> addSkill(@RequestBody @Validated SkillAddReq skillAddReq) {
		skillAddReq.validate();
		skillService.checkResourceId(skillAddReq.getResourceId());
		Skill skill = new Skill();
		skill.setResourceId(skillAddReq.getResourceId());
		skill.setContent(skillAddReq.getContent());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				// save skill
				skillService.save(skill);
				// save skill-mission relation
				skillService.addMissionRelation(skill.getResourceId(), skillAddReq.getMissions());
			}
		});

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RTKS,
				OperationType.CREATE, skill.getResourceId(), LogContent.CR_FORMAT.format(
						OperationType.CREATE, LogContent.SKILL.getMsg(), skill.getResourceId()));

		return new BaseRsp<>();
	}

	@ApiOperation("修改单条")
	@PutMapping()
	@PermissionOperation(TrialButtonConstant.CMS_RTKS_SKILL)
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<String> modifySkill(@RequestBody @Validated SkillAddReq skillAddReq) {
		skillAddReq.validate();
		String newContent = skillAddReq.getContent();
		Skill skill = skillService.getOr404(skillAddReq.getResourceId());
		// 校验 content
		if (StringUtils.isNotBlank(newContent) && !skill.getContent().equals(newContent)) {
			CrCommonValidator.validateDescLen(skillAddReq.getContent(),
					RtksBusinessErrorCode.INVALID_CONTENT);
			skill.setContent(newContent);

		}
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				// update mission relations
				skillService.updateMissionRelations(skillAddReq.getResourceId(),
						skillAddReq.getMissions());
				// update content
				skillService.updateById(skill);

			}
		});

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RTKS,
				OperationType.UPDATE, skill.getResourceId(), LogContent.CR_FORMAT.format(
						OperationType.UPDATE, LogContent.SKILL.getMsg(), skill.getResourceId()));

		return new BaseRsp<>();
	}

	@ApiOperation("删除")
	@DeleteMapping()
	@PermissionOperation(TrialButtonConstant.CMS_RTKS_SKILL)
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<String> deleteSkill(@RequestBody @Validated DeleteReq deleteReq) {
		List<String> skillIds = deleteReq.getIds();

		List<Skill> skillList = skillService.listByIds(skillIds);

		if (!skillIds.isEmpty()) {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
					// delete skills
					skillService.removeByIds(skillIds);
					// delete mission relations
					skillService.clearMissionRel(skillIds);
					// delete role relations
					roleService.deleteSkillRelBySkillIds(skillIds);
				}
			});
		}

		List<String> columns = StreamApiUtils.getColumn(skillList, Skill::getContent);
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RTKS,
				OperationType.DELETE, LogContent.BATCH_DELETE_FORMAT.format(LogContent.SKILL.getMsg()),
				LogContent.CR_FORMAT.format(OperationType.DELETE, LogContent.SKILL.getMsg(),
						columns.toString()));

		return new BaseRsp<>();
	}

	@ApiOperation("分页查询")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<List<SkillRes>> list(@Validated PageReqBase reqBase) {
		ListRsp<SkillRes> data = skillService.page(reqBase);

		return new BaseRsp(data);
	}

	@ApiOperation("添加技能时分页展示")
	@GetMapping("/list/simple")
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<List<SkillSimpleRes>> listSimple(@Validated PageReqBase reqBase) {
		ListRsp<SkillSimpleRes> res = skillService.pageSimple(reqBase);
		return new BaseRsp(res);
	}

}
