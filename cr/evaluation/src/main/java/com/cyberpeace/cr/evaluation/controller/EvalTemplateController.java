package com.cyberpeace.cr.evaluation.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import com.cyberpeace.cr.crbase.service.CrSceneService;
import com.cyberpeace.cr.evaluation.controller.req.*;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTemplateListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTemplateRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTaskMapper;
import com.cyberpeace.cr.evaluation.service.EvalTemplateService;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepConfig;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.LocalSceneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "测试靶场-模板")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/template")
public class EvalTemplateController {

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private CrConstantsService crConstantsService;

	@Autowired
	private CrSceneService crSceneService;

	@Autowired
	private EvalTemplateService evalTemplateService;

	@Autowired
	private EvalTaskMapper evalTaskMapper;

	@Autowired
	private LocalSceneService localSceneService;

	@ApiOperation("模板新增")
	@PostMapping("")
	public BaseRsp<String> addEvalTemplate(@RequestBody EvaTemplateAddReq evaTemplateAddReq) {

		EvalTemplate evalTemplate = reqToTemplate(evaTemplateAddReq);

		evalTemplateService.save(evalTemplate);

		return new BaseRsp<>(evalTemplate.getResourceId());
	}

	@ApiOperation("模板列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<EvalTemplateListRsp>>
			getEvalTemplateList(EvalTemplateListReq queryParams) {

		EvalTemplateListQuery evalTemplateListQuery = BeanUtils.copy(queryParams,
				EvalTemplateListQuery.class);

		evalTemplateListQuery.setUserId(UserInfoContext.getUserContext().getUserId());

		IPage<EvalTemplate> data = evalTemplateService.list(evalTemplateListQuery);

		data.convert(evalTemplate -> {
			EvalTemplateListRsp evalTemplateListRsp = BeanUtils.copy(evalTemplate,
					EvalTemplateListRsp.class);
			evalTemplateListRsp
					.setLogoPath(evalTemplateService.getLogoPath(evalTemplate.getResourceId()));
			return evalTemplateListRsp;
		});

		return new BaseRsp<>(PageUtil.getListResult(data));
	}

	@ApiOperation("模板查询")
	@GetMapping("")
	public BaseRsp<EvalTemplateRsp> getEvalTemplate(@ApiParam(name = "template_id",
			value = "测试靶场id", required = true) @RequestParam("template_id") String templateId) {
		EvalTemplate evalTemplate = evalTemplateService.getById(templateId);
		EvalTemplateRsp evalTemplateRsp = BeanUtils.copy(evalTemplate, EvalTemplateRsp.class);
		evalTemplateRsp.setLogoPath(evalTemplateService.getLogoPath(evalTemplate.getResourceId()));
		evalTemplateRsp.setStepConfig(
				JsonUtil.toJsonObjectArray(evalTemplate.getStepConfig(), EvalTaskStepConfig.class));
		evalTemplateRsp.setSceneConfigData(localSceneService.getTopoData(evalTemplate.getTopoId()));
		evalTemplateRsp.setTaskList(evalTaskMapper.selectResourceIdList(templateId));
		return new BaseRsp<>(evalTemplateRsp);
	}

	@ApiOperation("模板修改")
	@PutMapping("")
	public BaseRsp<EvalTemplateRsp>
			modifyEvalTemplate(@RequestBody EvaTemplateModifyReq evaTemplateModifyReq) {
		return new BaseRsp<>(new EvalTemplateRsp());
	}

	@ApiOperation("模板删除")
	@DeleteMapping("")
	public BaseRsp<Boolean>
			deleteEvalTemplate(@RequestBody EvalTemplateDeleteReq evalTemplateDeleteReq) {
		return new BaseRsp<>(true);
	}

	private void validateReq(EvaTemplateAddReq evaTemplateAddReq) {

		// 校验LOGO文件是否存在
		if (!attachmentService.isExist(evaTemplateAddReq.getLogoId())) {
			throw new BusinessException(CrBusinessErrorCode.LOGO_NOT_EXIST);
		}

		// 校验测试靶场的类型是否存在
		if (!crConstantsService.validateType(
				CrConstantsConfig.ConstantsType.EVALUATION_CATEGORY.getCode(),
				evaTemplateAddReq.getCategoryCode())) {
			throw new BusinessException(CrBusinessErrorCode.EVAL_CATEGORY_NOT_EXIST);
		}
	}

	private EvalTemplate reqToTemplate(EvaTemplateAddReq evaTemplateAddReq) {

		validateReq(evaTemplateAddReq);

		EvalTemplate evalTemplate = BeanUtils.copy(evaTemplateAddReq, EvalTemplate.class);

		evalTemplate.setStepConfig(JsonUtil.arrayToString(evaTemplateAddReq.getStepConfig()));

		evalTemplate.setLogo(evaTemplateAddReq.getLogoId());
		evalTemplate.setTopoId(crSceneService.saveTopo(evaTemplateAddReq.getSceneConfigData(),
				LocalScene.BusinessCode.EVALUATION_RANGE));
		evalTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		return evalTemplate;
	}

}