package com.cyberpeace.oj.practice.controller.web;

import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PatternReq;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.oj.ojbase.controller.req.SceneNodeReq;
import com.cyberpeace.oj.ojbase.controller.req.SceneOperationReq;
import com.cyberpeace.oj.ojbase.controller.req.SceneStatusReq;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.service.NoteService;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.cyberpeace.oj.ojbase.service.bo.TopoSceneDataBo;
import com.cyberpeace.oj.practice.controller.req.SubmitAnswerReq;
import com.cyberpeace.oj.practice.controller.req.WebOperationListReq;
import com.cyberpeace.oj.practice.controller.res.*;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.bo.AdminOperationListBo;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;
import com.cyberpeace.oj.practice.service.bo.OperationListQueryBo;
import com.cyberpeace.oj.practice.service.bo.WebOperationBo;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api(tags = "oj-前台实践操作练习相关api")
@RequestMapping("/oj/practice/operation")
@RequiresAuthentication
public class PracticeOperationController {

	@Autowired
	private PracticeOperationService practiceOperationService;

	@Autowired
	private PracticeCategoryService practiceCategoryService;

	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	@Autowired
	private RemoteLogService remoteLogService;

	@Autowired
	private ResourceCollectionService resourceCollectionService;

	@Autowired
	private NoteService noteService;

	@GetMapping()
	@ApiOperation("获取单个实践操作")
	@RequiresPermissions(MenuConstant.WEB_OJ_PRACTICE_OPERATION)
	public BaseRsp<WebOperationBo> get(@RequestParam("resource_id") String resourceId) {
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);
		WebOperationBo operationBo = practiceOperationService.selectById(resourceId, authContext,
				Boolean.FALSE);

		return new BaseRsp<>(operationBo);
	}

	@GetMapping("/knowledge/list")
	@ApiOperation("获取知识点列表，按频次排序")
	@RequiresPermissions(MenuConstant.WEB_OJ_PRACTICE_OPERATION)
	public BaseRsp<List<String>> listKnowledge(PatternReq pattern) {
		List<String> knowledgeList = practiceOperationService
				.getKnowledgeListByFrequency(pattern, true);
		return new BaseRsp<>(knowledgeList);
	}

	@GetMapping("/list")
	@ApiOperation("获取实践操作列表")
	@RequiresPermissions(MenuConstant.WEB_OJ_PRACTICE_OPERATION)
	public BaseRsp<ListRsp<AdminOperationListBo>> list(WebOperationListReq listReq) {

		Page page = listReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			OrderItem orderItem1 = new OrderItem("update_time", false);
			OrderItem orderItem2 = new OrderItem("id", true);
			page.setOrders(Lists.newArrayList(orderItem1, orderItem2));
		}

		OperationListQueryBo listQueryBo = BeanUtils.copy(listReq, OperationListQueryBo.class);
		// 知识点
		listQueryBo.setKnowledgeList(listReq.getKnowledge());

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
	@RequiresPermissions(MenuConstant.WEB_OJ_PRACTICE_OPERATION)
	public BaseRsp<EvaluateResult> submitAnswer(@RequestBody @Validated SubmitAnswerReq answerReq) {

		EvaluateResult evaluateResult = practiceOperationService.submitAnswer(
				UserInfoContext.getUserContext().getUserId(), answerReq.getResourceId(),
				answerReq.getSubmitAnswer(), Boolean.FALSE);

		// 记录远程操作日志
		LogContent logContent = evaluateResult.getSolved() ? LogContent.CORRECT
				: LogContent.INCORRECT;
		practiceOperationService.createMpLog(answerReq.getResourceId(), logContent);

		return new BaseRsp<>(evaluateResult);
	}

	@ApiOperation("获取所有实操习题编号")
	@GetMapping("/list/ids")
	@RequiresPermissions(MenuConstant.WEB_OJ_PRACTICE_OPERATION)
	public BaseRsp<List<String>> getPracticeListIds(@ApiParam(name = "practice_set_id", value = "习题集id") @RequestParam(value = "practice_set_id", required = false) String practiceSetId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);
		List<String> practiceIds = practiceOperationService.selectPracticeIds(authContext, practiceSetId);

		return new BaseRsp<>(practiceIds);
	}

	@ApiOperation("场景启动")
	@PostMapping("/scene")
	@RequiresPermissions(MenuConstant.WEB_OJ_PRACTICE_OPERATION)
	public BaseRsp<JSONObject>
			startScene(@RequestBody @Validated SceneOperationReq sceneOperationReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);

		JSONObject sceneData = practiceOperationService
				.startScene(sceneOperationReq.getResourceId(), authContext);

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.CAPTURE_FLAG,
				OperationType.START, LogContent.EXERCISE.getMsg(),
				LogContent.CR_FORMAT.format(OperationType.START, LogContent.EXERCISE.getMsg(), practiceOperationMapper.selectById(sceneOperationReq.getResourceId()).getTitle()));

		return new BaseRsp<>(sceneData);
	}

	@ApiOperation("场景删除")
	@DeleteMapping("/scene")
	@RequiresPermissions(MenuConstant.WEB_OJ_PRACTICE_OPERATION)
	public BaseRsp<Boolean> deleteScene(@RequestBody @Validated SceneOperationReq sceneOperationReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		Boolean result = practiceOperationService.deleteScene(sceneOperationReq.getResourceId(),
				authContext);

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.CAPTURE_FLAG,
				OperationType.STOP, LogContent.EXERCISE.getMsg(),
				LogContent.CR_FORMAT.format(OperationType.STOP, LogContent.EXERCISE.getMsg(), practiceOperationMapper.selectById(sceneOperationReq.getResourceId()).getTitle()));

		return new BaseRsp<>(result);
	}

	@ApiOperation("场景节点重启")
	@PostMapping("/scene_node_restart")
	@RequiresPermissions(MenuConstant.WEB_OJ_PRACTICE_OPERATION)
	public BaseRsp<Boolean> sceneNodeRestart(@RequestBody @Validated SceneNodeReq sceneNodeReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);

		Boolean result = practiceOperationService.nodeRestart(sceneNodeReq.getResourceId(),
				sceneNodeReq.getNodeId(), authContext);
		return new BaseRsp<>(result);

	}

	@ApiOperation("场景节点重建")
	@PostMapping("/scene_node_recreate")
	@RequiresPermissions(MenuConstant.WEB_OJ_PRACTICE_OPERATION)
	public BaseRsp<Boolean> sceneNodeRecreate(@RequestBody @Validated SceneNodeReq sceneNodeReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);

		Boolean result = practiceOperationService.nodeRecreate(sceneNodeReq.getResourceId(),
				sceneNodeReq.getNodeId(), authContext);

		return new BaseRsp<>(result);
	}

	@ApiOperation("场景暂停与恢复")
	@PatchMapping("/scene_update_status")
	@RequiresPermissions(MenuConstant.WEB_OJ_PRACTICE_OPERATION)
	public BaseRsp<Boolean> sceneUpdateStatus(@RequestBody @Validated SceneStatusReq sceneStatusReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		Boolean result = practiceOperationService.pauseScene(sceneStatusReq.getResourceId(),
				sceneStatusReq.getStatus(), authContext);
		return new BaseRsp<>(result);
	}

	@ApiOperation("获取场景和拓扑信息")
	@GetMapping("/topo_scene")
	@RequiresPermissions(MenuConstant.WEB_OJ_PRACTICE_OPERATION)
	public BaseRsp<TopoSceneDataBo> getTopoScene(@ApiParam(name = "resource_id",
			value = "课时编号") @RequestParam("resource_id") String operationId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		TopoSceneDataBo topoScene = practiceOperationService.getTopoScene(operationId, authContext);
		return new BaseRsp<>(topoScene);
	}

	@ApiOperation("获取进度统计")
	@GetMapping("/progress")
	@RequiresPermissions(MenuConstant.WEB_OJ_PRACTICE_OPERATION)
	public BaseRsp<ProgressStatisticsRes> getProgressStatistics (
			@ApiParam(name = "practice_set_id", value = "习题集id") @RequestParam(value = "practice_set_id", required = false) String practiceSetId) {
		return new BaseRsp<>(practiceOperationService.getProgressStatistics(practiceSetId));
	}

	@ApiOperation("收藏数量和笔记数量")
	@GetMapping("/statistics")
	@RequiresPermissions(MenuConstant.WEB_OJ_PRACTICE_OPERATION)
	public BaseRsp<OptionStatisticsRes> getOperationStatisticsRes() {
		OptionStatisticsRes optionStatisticsRes = new OptionStatisticsRes();
		UserInfo userInfo = UserInfoContext.getUserContext();
		AuthContext authContext = AuthContextUtils.constructAuth(userInfo, true);
		String userId = userInfo.getUserId();
		List<String> operationIds = practiceOperationService.getOptionIdsByAuth(authContext, null);
		if (CollectionUtils.isEmpty(operationIds)){
			optionStatisticsRes.setCollection(0);
			optionStatisticsRes.setNote(0);
			return new BaseRsp<>(optionStatisticsRes);
		}
		optionStatisticsRes.setCollection(resourceCollectionService.countCollectionByUserIdAndBusinessId(userId,
				ResourceCollection.BusinessType.OPERATION.getCode(), operationIds));
		optionStatisticsRes.setNote(noteService.getCount(userId, Note.NoteType.NOTE.getCode(), operationIds));
		return new BaseRsp<>(optionStatisticsRes);
	}

	@ApiOperation("解出记录Top5")
	@GetMapping("/record")
	@RequiresPermissions(MenuConstant.WEB_OJ_PRACTICE_OPERATION)
	public BaseRsp<List<OperationRecordRes>> getOperationRecordTop5() {
		List<OperationRecordRes> operationRecordResList = practiceOperationService.getOperationRecord(UserInfoContext.getUserContext(), null, null);
		if (operationRecordResList.size() > 5){
			return new BaseRsp<>(operationRecordResList.subList(0, 5));
		}
		return new BaseRsp<>(operationRecordResList);
	}

	@ApiOperation("所有解出记录")
	@GetMapping("/all_record")
	@RequiresPermissions(MenuConstant.WEB_OJ_PRACTICE_OPERATION)
	public BaseRsp<List<OperationRecordResByDay>> getAllOperationRecord(
			@ApiParam(name = "start_time", value = "开始时间") @RequestParam(name = "start_time", required = false) String startTime,
			@ApiParam(name = "end_time", value = "开始时间") @RequestParam(name = "end_time", required = false) String endTime) {
		Map<String, List<OperationRecordRes>> operationRecordMap = new HashMap<>();
		Date startDate = TimeUtil.toDate2(startTime);
		startDate = startDate == null ? null : TimeUtil.getStartOfDay(startDate);
		Date endDate = TimeUtil.toDate2(endTime);
		endDate = endDate == null ? null : TimeUtil.getEndOfDay(endDate);

		List<OperationRecordRes> operationRecordResList = practiceOperationService
				.getOperationRecord(UserInfoContext.getUserContext(), startDate, endDate);
		for (OperationRecordRes operationRecordRes : operationRecordResList) {
			String time = TimeUtil.format6(TimeUtil.toDate1(operationRecordRes.getTime()));
			if (operationRecordMap.get(time) == null) {
				List<OperationRecordRes> recordRes = new ArrayList<>();
				recordRes.add(operationRecordRes);
				operationRecordMap.put(time, recordRes);
			} else {
				operationRecordMap.get(time).add(operationRecordRes);
			}
		}
		List<OperationRecordResByDay> operationRecordResByDays = new ArrayList<>();
		for (Map.Entry<String, List<OperationRecordRes>> entry : operationRecordMap.entrySet()) {
			OperationRecordResByDay operationRecordResByDay = new OperationRecordResByDay(entry.getKey(), entry.getValue());
			operationRecordResByDays.add(operationRecordResByDay);
		}
		operationRecordResByDays.sort(Comparator.comparing(OperationRecordResByDay::getTime).reversed());
		return new BaseRsp<>(operationRecordResByDays);
	}

	@ApiOperation("知识技能Top6")
	@GetMapping("/category_top6")
	@RequiresPermissions(MenuConstant.WEB_OJ_PRACTICE_OPERATION)
	public BaseRsp<List<CategoryProgressRes>> getCategoryTop6() {
		List<CategoryProgressRes> categoryProgressResList = practiceOperationService.getOperationCategoryProgress();
		categoryProgressResList.sort(Comparator.comparing(categoryProgressRes -> categoryProgressRes.getCount() * 1.0 / categoryProgressRes.getSum()));
		Collections.reverse(categoryProgressResList);
		if (categoryProgressResList.size() > 6) {
			return new BaseRsp<>(categoryProgressResList.subList(0, 6));
		}
		return new BaseRsp<>(categoryProgressResList);
	}

	@ApiOperation("再练一次状态下的提交")
	@PostMapping("/submit_answer_again")
	@RequiresPermissions(MenuConstant.WEB_OJ_PRACTICE_OPERATION)
	public BaseRsp<EvaluateResult> submitAnswerAgain(@RequestBody @Validated SubmitAnswerReq answerReq) {

		EvaluateResult evaluateResult = practiceOperationService.submitAnswerAgain(
				UserInfoContext.getUserContext().getUserId(), answerReq.getResourceId(),
				answerReq.getSubmitAnswer());

		return new BaseRsp<>(evaluateResult);
	}

	@ApiOperation("解题记录题目id列表")
	@GetMapping("/record_all_id")
	@RequiresPermissions(MenuConstant.WEB_OJ_PRACTICE_OPERATION)
	public BaseRsp<List<String>> getRecordPracticeIds() {
		List<OperationRecordRes> operationRecordResList = practiceOperationService.getOperationRecord(
				UserInfoContext.getUserContext(), null, null);
		return new BaseRsp<>(StreamApiUtils.map(operationRecordResList, OperationRecordRes::getPracticeId));
	}
}
