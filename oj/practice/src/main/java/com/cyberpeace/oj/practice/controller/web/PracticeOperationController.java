package com.cyberpeace.oj.practice.controller.web;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.practice.controller.req.SceneShareModeReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.practice.controller.req.SubmitAnswerReq;
import com.cyberpeace.oj.practice.controller.req.WebOperationListReq;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.bo.AdminOperationListBo;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;
import com.cyberpeace.oj.practice.service.bo.OperationListQueryBo;
import com.cyberpeace.oj.practice.service.bo.WebOperationBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "oj-前台实践操作练习相关api")
@RestController
@RequestMapping("/oj/practice/operation")
@RequiresAuthentication
public class PracticeOperationController {

	@Autowired
	private PracticeOperationService practiceOperationService;

	@Autowired
	private PracticeCategoryService practiceCategoryService;

	@GetMapping()
	@ApiOperation("获取单个实践操作")
	public BaseRsp<WebOperationBo> get(@RequestParam("resource_id") String resourceId) {

		WebOperationBo operationBo = practiceOperationService.selectById(resourceId,
				UserInfoContext.getUserContext().getUserId());

		return new BaseRsp<>(operationBo);
	}

	@GetMapping("/list")
	@ApiOperation("获取实践操作列表")
	public BaseRsp<ListRsp<AdminOperationListBo>> list(WebOperationListReq listReq) {

		Page page = listReq.toPageParam();
		OperationListQueryBo listQueryBo = BeanUtils.copy(listReq, OperationListQueryBo.class);

		List<String> categoryIds = practiceCategoryService
				.selectChildrenIds(listReq.getCategoryId(), CommonConstant.TRUE);
		listQueryBo.setCategoryIds(categoryIds);

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);

		IPage<AdminOperationListBo> listBoIPage = practiceOperationService.pageByQuery(page,
				listQueryBo, authContext);

		return new BaseRsp<>(PageUtil.getListResult(listBoIPage));
	}

	@ApiOperation("实操练习解答")
	@PostMapping("/submit_answer")
	public BaseRsp<EvaluateResult> submitAnswer(@RequestBody @Validated SubmitAnswerReq answerReq) {

		EvaluateResult evaluateResult = practiceOperationService.submitAnswer(
				UserInfoContext.getUserContext().getUserId(), answerReq.getResourceId(),
				answerReq.getSubmitAnswer());

		return new BaseRsp<>(evaluateResult);
	}

	@ApiOperation("获取所有实操习题编号")
	@GetMapping("/list/ids")
	public BaseRsp<List<String>> getPracticeListIds() {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);
		List<String> practiceIds = practiceOperationService.selectPracticeIds(authContext);

		return new BaseRsp<>(practiceIds);
	}

	@ApiOperation("场景启动")
	@PostMapping("/scene")
	public BaseRsp<JSONObject> startScene(@RequestParam("resource_id") String resourceId) {

		JSONObject sceneData = practiceOperationService.startScene(resourceId);

		return new BaseRsp<>(sceneData);
	}
}
