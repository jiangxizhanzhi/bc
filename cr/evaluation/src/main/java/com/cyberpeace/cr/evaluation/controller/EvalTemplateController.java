package com.cyberpeace.cr.evaluation.controller;

import java.util.ArrayList;
import java.util.List;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemTemplate;
import com.cyberpeace.cr.evaluation.service.EvalTemTemplateService;
import com.cyberpeace.cr.evaluation.service.impl.bo.TechniqueWarfare;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.context.user.OperationUserInfo;
import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.service.CrSceneService;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.evaluation.controller.req.EvaTemplateAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvaTemplateModifyReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTemplateDeleteReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTemplateIdRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTemplateListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTemplateRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTemplateTaskListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.service.EvalTemplateService;
import com.cyberpeace.cr.evaluation.service.context.EvalTemplateBaseInfoContext;
import com.cyberpeace.cr.evaluation.service.context.EvalTemplateListQuery;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "靶场测评-模板")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/template")
public class EvalTemplateController {

	@Autowired
	private CrSceneService crSceneService;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private EvalTemplateService evalTemplateService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private EvalTemTemplateService evalTemTemplateService;

	@ApiOperation("模板新增")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("")
	public BaseRsp<EvalTemplateIdRsp>
			addEvalTemplate(@RequestBody @Validated EvaTemplateAddReq evaTemplateAddReq) {

		EvalTemplate evalTemplate = new EvalTemplate();

		evaTemplateAddReq.setStepConfig(new ArrayList<>());
		// 区分是否使用后台模板新增
		if (null != evaTemplateAddReq.getTemplateId()) {
			EvalTemTemplate temTemplate = evalTemTemplateService.getById(evaTemplateAddReq.getTemplateId());
			if (null == temTemplate) {
				throw new BusinessException(CrBusinessErrorCode.TEMPLATE_NOT_EXIST);
			}
			reqToTemplate(evaTemplateAddReq, evalTemplate, temTemplate);
		} else {
			reqToTemplate(evaTemplateAddReq, evalTemplate, null);
		}

		// 设置模板创建者
		evalTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		evalTemplateService.save(evalTemplate);

		return new BaseRsp<>(new EvalTemplateIdRsp(evalTemplate.getResourceId()));
	}

	@ApiOperation("模板修改")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PatchMapping("")
	public BaseRsp<EvalTemplateIdRsp>
			modifyEvalTemplate(@RequestBody @Validated EvaTemplateModifyReq evaTemplateModifyReq) {

		String templateId = evaTemplateModifyReq.getResourceId();

		// 校验用户身份并获取测评靶场模板实体
		EvalTemplate evalTemplate = evalTemplateService.getTemplate(templateId);

		reqToTemplate(evaTemplateModifyReq, evalTemplate, null);

		evalTemplateService.updateById(evalTemplate);

		return new BaseRsp<>(new EvalTemplateIdRsp(templateId));
	}

	@ApiOperation("模板查询")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@GetMapping("")
	public BaseRsp<EvalTemplateRsp> getEvalTemplate(@ApiParam(name = "template_id",
			value = "测评靶场模板id", required = true) @RequestParam("template_id") String templateId) {

		// 校验用户身份并获取测评靶场模板实体
		EvalTemplate evalTemplate = evalTemplateService.getTemplate(templateId);

		// 构造接口返回数据
		EvalTemplateRsp evalTemplateRsp = BeanUtils.copy(evalTemplate, EvalTemplateRsp.class);
		evalTemplateRsp.setCondition(evalTemplate.getExpCondition());
		TechniqueWarfare techniqueWarfare = JsonUtil.toJsonObject(evalTemplate.getTechniqueWarfare(), TechniqueWarfare.class);
		evalTemplateRsp.setTechniqueWarfare(techniqueWarfare);
		evalTemplateRsp.setLogoPath(evalTemplateService.getLogoPath(evalTemplate.getLogo()));
		evalTemplateRsp.setSceneConfigData(localSceneService.getTopoData(evalTemplate.getTopoId()));
		// auth users
		List<UserInfo> userInfos = remoteUserService.getByIdList(evalTemplate.getAuthIds());
		List<OperationUserInfo> operationUserInfos = StreamApiUtils.map(userInfos, userInfo -> {
			OperationUserInfo operationUserInfo = BeanUtils.copy(userInfo, OperationUserInfo.class);
			operationUserInfo.setId(userInfo.getUserId());
			operationUserInfo.setUsername(userInfo.getUserName());
			return operationUserInfo;
		});
		evalTemplateRsp.setOperationAuthIds(operationUserInfos);
		return new BaseRsp<>(evalTemplateRsp);
	}

	@ApiOperation("模板列表查询")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@GetMapping("/list")
	public BaseRsp<ListRsp<EvalTemplateListRsp>>
			getEvalTemplateList(EvalTemplateListQuery evalTemplateListQuery) {

		IPage<EvalTemplate> data = evalTemplateService.list(evalTemplateListQuery);

		data.convert(evalTemplate -> {
			EvalTemplateListRsp evalTemplateListRsp = BeanUtils.copy(evalTemplate,
					EvalTemplateListRsp.class);
			evalTemplateListRsp
					.setLogoPath(evalTemplateService.getLogoPath(evalTemplate.getLogo()));
			return evalTemplateListRsp;

		});

		return new BaseRsp<>(PageUtil.getListResult(data));
	}

	@ApiOperation("模板任务列表查询")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@GetMapping("/task_list")
	public BaseRsp<EvalTemplateTaskListRsp> getEvalTemplateTaskList(
			@ApiParam(name = "template_id", value = "测评靶场模板id",
					required = true) @RequestParam("template_id") String templateId,
			@ApiParam(name = "is_finished", value = "是否只展示已结束任务", required = false) @RequestParam(
					value = "is_finished", required = false) Boolean isFinished) {

		// 校验用户身份并获取测评靶场模板实体
		EvalTemplate evalTemplate = evalTemplateService.getTemplate(templateId);

		EvalTemplateTaskListRsp evalTemplateTaskListRsp = new EvalTemplateTaskListRsp();

		evalTemplateTaskListRsp
				.setTaskList(evalTemplateService.listTask(evalTemplate, isFinished));

		if (StringUtils.isNotEmpty(evalTemplate.getCurrentTaskId())) {
			evalTemplateTaskListRsp.setCurrentTaskId(evalTemplate.getCurrentTaskId());
		}

		return new BaseRsp<>(evalTemplateTaskListRsp);
	}

	@ApiOperation("模板删除")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@DeleteMapping("")
	public BaseRsp<Boolean> deleteEvalTemplate(
			@RequestBody @Validated EvalTemplateDeleteReq evalTemplateDeleteReq) {

		Boolean result = evalTemplateService.batchDelete(evalTemplateDeleteReq.getTemplateIds());

		return new BaseRsp<>(result);
	}

	@ApiOperation("模板基础信息校验")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("validate_info")
	public BaseRsp<Boolean> validateBaseInfo(
			@RequestBody @Validated EvalTemplateBaseInfoContext evalTemplateBaseInfoContext) {
		Boolean result = evalTemplateService.validateBaseInfo(evalTemplateBaseInfoContext);
		return new BaseRsp<>(result);
	}

	private EvalTemplate reqToTemplate(EvaTemplateAddReq evaTemplateAddReq,
			EvalTemplate evalTemplate, EvalTemTemplate evalTemTemplate) {

		// 校验测评模板配置
		validateReq(evaTemplateAddReq);

		// 复制模板信息
		BeanUtils.copyProperties(evaTemplateAddReq, evalTemplate);
		evalTemplate.setExpCondition(evaTemplateAddReq.getCondition());
		evalTemplate.setStepConfig(JsonUtil.arrayToString(evaTemplateAddReq.getStepConfig()));
		// 设置技战法方案-任务方案
		evalTemplate.setTechniqueWarfare(null == evalTemTemplate ? JsonUtil.toJsonString(evaTemplateAddReq.getMissionScheme()) : evalTemTemplate.getTechniqueWarfare());

		// 设置模板用户
		if (evaTemplateAddReq.getOperationAuthType().equals(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode())) {
			evalTemplate.setOperationAuthIds(
					JsonUtil.arrayToString(evaTemplateAddReq.getAuthUserIds()));
		}

		// 设置模板拓扑
		evalTemplate.setTopoId(null == evalTemTemplate ? crSceneService.saveTopo(evaTemplateAddReq.getSceneConfigData(),
				BusinessCode.RANGE_EVALUATION, evalTemplate.getTopoId()) : evalTemTemplate.getTopoId());

		return evalTemplate;
	}

	private void validateReq(EvaTemplateAddReq evaTemplateAddReq) {

		// 校验基础信息
		EvalTemplateBaseInfoContext evalTemplateBaseInfoContext = BeanUtils.copy(evaTemplateAddReq,
				EvalTemplateBaseInfoContext.class);
		evalTemplateService.validateBaseInfo(evalTemplateBaseInfoContext);

		CrCommonValidator.validateEnumCode(evaTemplateAddReq.getOperationAuthType(),
				AuthTable.AuthType.class);
	}

}