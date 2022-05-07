package com.cyberpeace.oj.practice.controller.web;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.constant.OrderNameConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.oj.practice.controller.req.SubmitAnswerReq;
import com.cyberpeace.oj.practice.controller.req.WebTheoryListReq;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;
import com.cyberpeace.oj.practice.service.bo.PracticeTheoryListBo;
import com.cyberpeace.oj.practice.service.bo.TheoryListQueryBo;
import com.cyberpeace.oj.practice.service.bo.WebTheoryBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "oj-前台理论基础练习相关")
@RestController
@RequestMapping("/oj/practice/theory")
@RequiresAuthentication
@Slf4j
@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
public class PracticeTheoryController {

	@Autowired
	private PracticeTheoryService practiceTheoryService;

	@Autowired
	private PracticeCategoryService practiceCategoryService;

	@GetMapping("")
	@ApiOperation("查询理论基础练习详细信息")
	public BaseRsp<WebTheoryBo> get(@RequestParam("resource_id") @NotBlank String resourceId) {
		WebTheoryBo theoryBo = practiceTheoryService.selectById(resourceId,
				UserInfoContext.getUserContext().getUserId(), Boolean.FALSE);

		return new BaseRsp<>(theoryBo);
	}

	@GetMapping("/list")
	@ApiOperation("分页查询理论基础习题")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<ListRsp<PracticeTheoryListBo>> getList(WebTheoryListReq listReq) {

		// 设置默认排序字段
		Page page = listReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}

		// 查询子分类信息
		List<String> categoryIds = practiceCategoryService
				.selectChildrenIds(listReq.getCategoryId(), CommonConstant.TRUE);
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);

		TheoryListQueryBo listQueryBo = BeanUtils.copy(listReq, TheoryListQueryBo.class);
		listQueryBo.setCategoryIds(categoryIds);

		IPage<PracticeTheoryListBo> result = practiceTheoryService.pageByQuery(page, listQueryBo,
				authContext);

		return new BaseRsp<>(PageUtil.getListResult(result));
	}

	@ApiOperation("理论基础练习题提交")
	@PostMapping("/submit_answer")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<EvaluateResult> submitAnswer(@RequestBody @Validated SubmitAnswerReq answerReq) {

		EvaluateResult evaluateResult = practiceTheoryService.submitAnswer(
				UserInfoContext.getUserContext().getUserId(), answerReq.getResourceId(),
				answerReq.getSubmitAnswer(), Boolean.FALSE);

		// 记录远程操作日志
		LogContent logContent = evaluateResult.getSolved() ? LogContent.CORRECT
				: LogContent.INCORRECT;
		practiceTheoryService.createMpLog(answerReq.getResourceId(), logContent);

		return new BaseRsp<>(evaluateResult);
	}

	@ApiOperation("获取所有理论习题编号列表")
	@GetMapping("/list/ids")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<List<String>> getPracticeListId() {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);
		List<String> practiceIds = practiceTheoryService.selectPracticeListIds(authContext);
		return new BaseRsp<>(practiceIds);
	}

}
