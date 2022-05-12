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
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import com.cyberpeace.cr.crbase.service.CrSceneService;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.evaluation.controller.req.*;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTemplateListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTemplateRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTaskMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalTemplateService;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepConfig;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.LocalSceneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "靶场测评-模板")
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
	private EvalTemplateMapper evalTemplateMapper;

	@Autowired
	private LocalSceneService localSceneService;

	@ApiOperation("模板新增")
	@PostMapping("")
	public BaseRsp<String> addEvalTemplate(@RequestBody EvaTemplateAddReq evaTemplateAddReq) {

		EvalTemplate evalTemplate = new EvalTemplate();

		reqToTemplate(evaTemplateAddReq, evalTemplate);

		evalTemplate.setTopoId(crSceneService.saveTopo(evaTemplateAddReq.getSceneConfigData(),
				LocalScene.BusinessCode.RANGE_EVALUATION, null));

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
					.setLogoPath(evalTemplateService.getLogoPath(evalTemplate.getLogo()));
			return evalTemplateListRsp;
		});

		return new BaseRsp<>(PageUtil.getListResult(data));
	}

	@ApiOperation("模板查询")
	@GetMapping("")
	public BaseRsp<EvalTemplateRsp> getEvalTemplate(@ApiParam(name = "template_id",
			value = "测评靶场模板id", required = true) @RequestParam("template_id") String templateId) {

		// 权限校验
		EvalTemplate evalTemplate = evalTemplateService.getTemplate(templateId);

		EvalTemplateRsp evalTemplateRsp = BeanUtils.copy(evalTemplate, EvalTemplateRsp.class);
		evalTemplateRsp.setLogoPath(evalTemplateService.getLogoPath(evalTemplate.getLogo()));
		evalTemplateRsp.setStepConfig(
				JsonUtil.toJsonObjectArray(evalTemplate.getStepConfig(), EvalTaskStepConfig.class));
		evalTemplateRsp.setSceneConfigData(localSceneService.getTopoData(evalTemplate.getTopoId()));
		evalTemplateRsp.setTaskList(evalTaskMapper.selectResourceIdList(templateId));
		return new BaseRsp<>(evalTemplateRsp);
	}

	@ApiOperation("模板修改")
	@PutMapping("")
	public BaseRsp<String>
			modifyEvalTemplate(@RequestBody EvaTemplateModifyReq evaTemplateModifyReq) {

		String templateId = evaTemplateModifyReq.getResourceId();

		// 权限校验
		EvalTemplate evalTemplate = evalTemplateService.getTemplate(templateId);

		reqToTemplate(evaTemplateModifyReq, evalTemplate);

		crSceneService.saveTopo(evaTemplateModifyReq.getSceneConfigData(),
				LocalScene.BusinessCode.RANGE_EVALUATION, evalTemplate.getTopoId());

		evalTemplateService.updateById(evalTemplate);

		return new BaseRsp<>(templateId);
	}

	@ApiOperation("模板删除")
	@DeleteMapping("")
	public BaseRsp<Boolean>
			deleteEvalTemplate(@RequestBody EvalTemplateDeleteReq evalTemplateDeleteReq) {

		Boolean result = evalTemplateService.batchDelete(evalTemplateDeleteReq.getTemplateIds());

		return new BaseRsp<>(result);
	}

	private void validateReq(EvaTemplateAddReq evaTemplateAddReq) {

		String templateId = null;
		if (evaTemplateAddReq instanceof EvaTemplateModifyReq) {
			templateId = ((EvaTemplateModifyReq) evaTemplateAddReq).getResourceId();
		}

		// 校验名称长度
		CrCommonValidator.validateNameLen(evaTemplateAddReq.getName(),
				CrBusinessErrorCode.ILLEGAL_NAME_LEN);

		// 校验名称重复
		if (evalTemplateMapper.existByNameAndUserIdAndId(evaTemplateAddReq.getName(),
				UserInfoContext.getUserContext().getUserId(), templateId)) {
			throw new BusinessException(CrBusinessErrorCode.TEMPLATE_NAME_DUPLICATE);
		}

		// 校验描述长度

		CrCommonValidator.validateLength(evaTemplateAddReq.getDescription(),
				CrCommonConstants.ZERO_STR_LEN, CrCommonConstants.MAX_DESC_LEN,
				CrBusinessErrorCode.ILLEGAL_DESC_LEN);

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

	private EvalTemplate reqToTemplate(EvaTemplateAddReq evaTemplateAddReq,
			EvalTemplate evalTemplate) {

		validateReq(evaTemplateAddReq);

		BeanUtils.copyProperties(evaTemplateAddReq, evalTemplate);

		evalTemplate.setStepConfig(JsonUtil.arrayToString(evaTemplateAddReq.getStepConfig()));

		evalTemplate.setLogo(evaTemplateAddReq.getLogoId());

		evalTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		return evalTemplate;
	}

}