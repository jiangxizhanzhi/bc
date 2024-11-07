package com.cyberpeace.cr.evaluation.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.service.CrSceneService;
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
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * EVAL 模板控制器
 *
 * @author SN
 * @date 2024/11/07
 */
@Api(tags = "靶场测评-模板")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/template")
public class EvalTemplateController {

	/**
	 * 靶场场景Service
	 */
	@Autowired
	private CrSceneService crSceneService;

	/**
	 * 当地场景Service
	 */
	@Autowired
	private LocalSceneService localSceneService;

	/**
	 * 评估模板Service
	 */
	@Autowired
	private EvalTemplateService evalTemplateService;

	/**
	 * 新增评估模板
	 *
	 * @param evaTemplateAddReq 伊娃模板新增请求体
	 * @return {@code BaseRsp<EvalTemplateIdRsp> }
	 */
	@ApiOperation("模板新增")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("")
	public BaseRsp<EvalTemplateIdRsp>
			addEvalTemplate(@RequestBody @Validated EvaTemplateAddReq evaTemplateAddReq) {

		EvalTemplate evalTemplate = new EvalTemplate();

		reqToTemplate(evaTemplateAddReq, evalTemplate);

		evalTemplateService.save(evalTemplate);

		return new BaseRsp<>(new EvalTemplateIdRsp(evalTemplate.getResourceId()));
	}

	/**
	 * 修改 EVAL 模板
	 *
	 * @param evaTemplateModifyReq 伊娃模板修改请求体
	 * @return {@code BaseRsp<EvalTemplateIdRsp> }
	 */
	@ApiOperation("模板修改")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PatchMapping("")
	public BaseRsp<EvalTemplateIdRsp>
			modifyEvalTemplate(@RequestBody @Validated EvaTemplateModifyReq evaTemplateModifyReq) {

		String templateId = evaTemplateModifyReq.getResourceId();

		// 校验用户身份并获取测评靶场模板实体
		EvalTemplate evalTemplate = evalTemplateService.getTemplate(templateId);

		reqToTemplate(evaTemplateModifyReq, evalTemplate);

		evalTemplateService.updateById(evalTemplate);

		return new BaseRsp<>(new EvalTemplateIdRsp(templateId));
	}

	/**
	 * 获取评估模板
	 *
	 * @param templateId 模板id
	 * @return {@code BaseRsp<EvalTemplateRsp> }
	 */
	@ApiOperation("模板查询")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@GetMapping("")
	public BaseRsp<EvalTemplateRsp> getEvalTemplate(@ApiParam(name = "template_id",
			value = "测评靶场模板id", required = true) @RequestParam("template_id") String templateId) {

		// 校验用户身份并获取测评靶场模板实体
		EvalTemplate evalTemplate = evalTemplateService.getTemplate(templateId);

		// 构造接口返回数据
		EvalTemplateRsp evalTemplateRsp = BeanUtils.copy(evalTemplate, EvalTemplateRsp.class);
		evalTemplateRsp.setLogoPath(evalTemplateService.getLogoPath(evalTemplate.getLogo()));
		evalTemplateRsp.setSceneConfigData(localSceneService.getTopoData(evalTemplate.getTopoId()));
		return new BaseRsp<>(evalTemplateRsp);
	}

	/**
	 * 获取评估模板列表
	 *
	 * @param evalTemplateListQuery EVAL 模板列表查询
	 * @return {@code BaseRsp<ListRsp<EvalTemplateListRsp>> }
	 */
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

	/**
	 * 获取评估模板任务列表
	 *
	 * @param templateId 模板id
	 * @param isFinished 已完成
	 * @return {@code BaseRsp<EvalTemplateTaskListRsp> }
	 */
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
				.setTaskList(evalTemplateService.listTask(evalTemplate, isFinished));;

		if (StringUtils.isNotEmpty(evalTemplate.getCurrentTaskId())) {
			evalTemplateTaskListRsp.setCurrentTaskId(evalTemplate.getCurrentTaskId());
		}

		return new BaseRsp<>(evalTemplateTaskListRsp);
	}

	/**
	 * 删除 EVAL 模板
	 *
	 * @param evalTemplateDeleteReq 评估模板删除请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("模板删除")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@DeleteMapping("")
	public BaseRsp<Boolean> deleteEvalTemplate(
			@RequestBody @Validated EvalTemplateDeleteReq evalTemplateDeleteReq) {

		Boolean result = evalTemplateService.batchDelete(evalTemplateDeleteReq.getTemplateIds());

		return new BaseRsp<>(result);
	}

	/**
	 * 验证基础信息
	 *
	 * @param evalTemplateBaseInfoContext eval 模板 base info 上下文
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("模板基础信息校验")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("validate_info")
	public BaseRsp<Boolean> validateBaseInfo(
			@RequestBody @Validated EvalTemplateBaseInfoContext evalTemplateBaseInfoContext) {
		Boolean result = evalTemplateService.validateBaseInfo(evalTemplateBaseInfoContext);
		return new BaseRsp<>(result);
	}

	/**
	 * 请求体自模板
	 *
	 * @param evaTemplateAddReq 伊娃模板新增请求体
	 * @param evalTemplate      eval 模板
	 * @return {@code EvalTemplate }
	 */
	private EvalTemplate reqToTemplate(EvaTemplateAddReq evaTemplateAddReq,
			EvalTemplate evalTemplate) {

		// 校验测评模板配置
		validateReq(evaTemplateAddReq);

		// 复制模板信息
		BeanUtils.copyProperties(evaTemplateAddReq, evalTemplate);
		evalTemplate.setStepConfig(JsonUtil.arrayToString(evaTemplateAddReq.getStepConfig()));

		// 设置模板用户
		evalTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		// 设置模板拓扑
		evalTemplate.setTopoId(crSceneService.saveTopo(evaTemplateAddReq.getSceneConfigData(),
				LocalScene.BusinessCode.RANGE_EVALUATION, evalTemplate.getTopoId()));

		return evalTemplate;
	}

	/**
	 * 验证请求体
	 *
	 * @param evaTemplateAddReq 伊娃模板新增请求体
	 */
	private void validateReq(EvaTemplateAddReq evaTemplateAddReq) {

		// 校验基础信息
		EvalTemplateBaseInfoContext evalTemplateBaseInfoContext = BeanUtils.copy(evaTemplateAddReq,
				EvalTemplateBaseInfoContext.class);
		evalTemplateService.validateBaseInfo(evalTemplateBaseInfoContext);

		// 校验测评步骤配置
		evalTemplateService.validateStepConfig(evaTemplateAddReq.getStepConfig());

	}

}