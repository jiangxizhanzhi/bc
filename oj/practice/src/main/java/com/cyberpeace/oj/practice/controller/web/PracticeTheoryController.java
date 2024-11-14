package com.cyberpeace.oj.practice.controller.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.annoations.BehaviorRecord;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.enums.BehaviorModuleEnum;
import com.cyberpeace.common.enums.BehaviorTypeEnum;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.service.NoteService;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.cyberpeace.oj.practice.controller.req.SubmitAnswerReq;
import com.cyberpeace.oj.practice.controller.req.WebTheoryListReq;
import com.cyberpeace.oj.practice.controller.req.WebTheoryRecordReq;
import com.cyberpeace.oj.practice.controller.res.ProgressStatisticsRes;
import com.cyberpeace.oj.practice.controller.res.TheoryStatisticsRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeSolved;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.PracticeMistakeService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.bo.*;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 练习理论Controller
 *
 * @author SN
 * @date 2024/11/14
 */
@Api(tags = "oj-前台理论基础练习相关")
@RestController
@RequestMapping("/oj/practice/theory")
@RequiresAuthentication
@Slf4j
public class PracticeTheoryController {

	/**
	 * 练习理论Service
	 */
	@Autowired
	private PracticeTheoryService practiceTheoryService;

	/**
	 * 练习类别Service
	 */
	@Autowired
	private PracticeCategoryService practiceCategoryService;

	/**
	 * 练习错误Service
	 */
	@Autowired
	private PracticeMistakeService practiceMistakeService;

	/**
	 * 资源收集Service
	 */
	@Autowired
	private ResourceCollectionService resourceCollectionService;

	/**
	 * 笔记Service
	 */
	@Autowired
	private NoteService noteService;

	/**
	 * 获取
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<WebTheoryBo> }
	 */
	@GetMapping("")
	@ApiOperation("查询理论基础练习详细信息")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<WebTheoryBo> get(@RequestParam("resource_id") @NotBlank String resourceId) {
		WebTheoryBo theoryBo = practiceTheoryService.selectById(resourceId,
				UserInfoContext.getUserContext().getUserId(), Boolean.FALSE);

		return new BaseRsp<>(theoryBo);
	}

	/**
	 * 获取列表
	 *
	 * @param listReq 列表请求体
	 * @return {@code BaseRsp<ListRsp<PracticeTheoryListBo>> }
	 */
	@GetMapping("/list")
	@ApiOperation("分页查询理论基础习题")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<ListRsp<PracticeTheoryListBo>> getList(WebTheoryListReq listReq) {

		// 设置默认排序字段
		Page page = listReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			OrderItem orderItem1 = new OrderItem("update_time", false);
			OrderItem orderItem2 = new OrderItem("id", true);
			page.setOrders(Lists.newArrayList(orderItem1, orderItem2));
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

	/**
	 * 获取列表top5
	 *
	 * @param listReq 列表请求体
	 * @return {@code BaseRsp<List<PracticeTheoryListBo>> }
	 */
	@GetMapping("/listTop5")
	@ApiOperation("查询理论基础习题top5")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<List<PracticeTheoryListBo>> getListTop5(WebTheoryListReq listReq) {

		// 设置默认排序字段
		Page page = listReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			OrderItem orderItem1 = new OrderItem("update_time", false);
			OrderItem orderItem2 = new OrderItem("id", true);
			page.setOrders(Lists.newArrayList(orderItem1, orderItem2));
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

		if (result == null) {
			return new BaseRsp<>(new ArrayList<>());
		} else if ( result.getRecords().size() > 5) {
			return new BaseRsp<>(result.getRecords().subList(0, 5));
		}
		return new BaseRsp<>(result.getRecords());
	}

	/**
	 * 提交答案
	 *
	 * @param answerReq 答案请求体
	 * @return {@code BaseRsp<EvaluateResultTheory> }
	 */
	@ApiOperation("理论基础练习题提交")
	@PostMapping("/submit_answer")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	@BehaviorRecord(module = BehaviorModuleEnum.THEORY, action = BehaviorTypeEnum.ANSWER,
			result = "status")
	public BaseRsp<EvaluateResultTheory>
			submitAnswer(@RequestBody @Validated SubmitAnswerReq answerReq) {

		EvaluateResult evaluateResult = practiceTheoryService.submitAnswer(
				UserInfoContext.getUserContext().getUserId(), answerReq.getResourceId(),
				answerReq.getSubmitAnswer(), Boolean.FALSE);

		// 记录远程操作日志
		LogContent logContent = evaluateResult.getSolved() ? LogContent.CORRECT
				: LogContent.INCORRECT;
		practiceTheoryService.createMpLog(answerReq.getResourceId(), logContent);

		EvaluateResultTheory evaluateResultTheory = BeanUtils.copy(evaluateResult,
				EvaluateResultTheory.class);
		if (evaluateResult.getSolved()) {
			evaluateResultTheory.setStatus(PracticeSolved.SolvedStatus.SOLVED.getCode());
		} else {
			evaluateResultTheory.setStatus(PracticeSolved.SolvedStatus.TRIED.getCode());
		}
		return new BaseRsp<>(evaluateResultTheory);
	}

	/**
	 * 获取练习列表id
	 *
	 * @param practiceSetId 练习设置id
	 * @return {@code BaseRsp<List<String>> }
	 */
	@ApiOperation("获取所有理论习题编号列表")
	@GetMapping("/list/ids")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<List<String>>
			getPracticeListId(@ApiParam(name = "practice_set_id", value = "习题集id") @RequestParam(
					value = "practice_set_id", required = false) String practiceSetId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);
		List<String> practiceIds = practiceTheoryService.selectPracticeListIds(authContext,
				practiceSetId);
		return new BaseRsp<>(practiceIds);
	}

	/**
	 * 获取进展统计学
	 *
	 * @param practiceSetId 练习设置id
	 * @return {@code BaseRsp<ProgressStatisticsRes> }
	 */
	@ApiOperation("获取进度统计")
	@GetMapping("/progress")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<ProgressStatisticsRes> getProgressStatistics(
			@ApiParam(name = "practice_set_id", value = "习题集id") @RequestParam(
					value = "practice_set_id", required = false) String practiceSetId) {
		return new BaseRsp<>(practiceTheoryService.getProgressStatistics(practiceSetId));
	}

	/**
	 * 获取理论统计学
	 *
	 * @return {@code BaseRsp<TheoryStatisticsRes> }
	 */
	@ApiOperation("统计错题、收藏、笔记数量")
	@GetMapping("/statistics")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<TheoryStatisticsRes> getTheoryStatistics() {
		TheoryStatisticsRes theoryStatisticsRes = new TheoryStatisticsRes();
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		String userId = authContext.getUserId();
		List<String> practiceIds = practiceTheoryService.getPracticeIdsByUserAndSet(authContext,
				null);
		if (CollectionUtils.isEmpty(practiceIds)) {
			theoryStatisticsRes.setCollection(0);
			theoryStatisticsRes.setNote(0);
			theoryStatisticsRes.setMistake(0);
			return new BaseRsp<>(theoryStatisticsRes);
		}
		theoryStatisticsRes
				.setMistake(practiceMistakeService.getTheoryMistakeCount(userId, practiceIds));
		theoryStatisticsRes.setCollection(
				resourceCollectionService.countCollectionByUserIdAndBusinessId(userId,
						ResourceCollection.BusinessType.THEORY.getCode(), practiceIds));
		theoryStatisticsRes
				.setNote(noteService.getCount(userId, Note.NoteType.NOTE.getCode(), practiceIds));
		return new BaseRsp<>(theoryStatisticsRes);
	}

	/**
	 * 获取理论记录
	 *
	 * @return {@code BaseRsp<List<PracticeTheoryListBo>> }
	 */
	@ApiOperation("最近5条练习记录")
	@GetMapping("/record")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<List<PracticeTheoryListBo>> getTheoryRecord() {
		List<PracticeTheoryListBo> practiceTheoryList = practiceTheoryService
				.getPracticeRecord(new WebTheoryRecordReq());
		if (practiceTheoryList.size() > 5) {
			return new BaseRsp<>(practiceTheoryList.subList(0, 5));
		}
		return new BaseRsp<>(practiceTheoryList);
	}

	/**
	 * 获取理论记录分页
	 *
	 * @param webTheoryRecordReq Web理论记录请求体
	 * @return {@code BaseRsp<ListRsp<PracticeTheoryListBo>> }
	 */
	@ApiOperation("练习记录")
	@GetMapping("/record_page")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<ListRsp<PracticeTheoryListBo>>
			getTheoryRecordPage(WebTheoryRecordReq webTheoryRecordReq) {
		List<PracticeTheoryListBo> practiceTheoryList = practiceTheoryService
				.getPracticeRecord(webTheoryRecordReq);
		Page page = webTheoryRecordReq.toPageParam();
		return new BaseRsp<>(
				PageUtil.getListResult(practiceTheoryList, page, practiceTheoryList.size()));
	}

	/**
	 * 获取记录id列表
	 *
	 * @return {@code BaseRsp<List<String>> }
	 */
	@ApiOperation("获取练习记录练习id列表")
	@GetMapping("/record_practice_ids")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<List<String>> getRecordIds() {
		List<PracticeTheoryListBo> practiceTheoryList = practiceTheoryService
				.getPracticeRecord(new WebTheoryRecordReq());
		return new BaseRsp<>(
				StreamApiUtils.map(practiceTheoryList, PracticeTheoryListBo::getResourceId));
	}

	/**
	 * 获取解决top5
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<List<SolvedTop5>> }
	 */
	@ApiOperation("最近答题")
	@GetMapping("/solved/top5")
	@RequiresPermissions(MenuConstant.WEB_OJ_THEORY_PRACTICE)
	public BaseRsp<List<SolvedTop5>> getSolvedTop5(@ApiParam(name = "resource_id", value = "习题编号") @RequestParam("resource_id") String resourceId) {
		return new BaseRsp<>(practiceTheoryService.getSolvedTop5(resourceId));
	}

}
