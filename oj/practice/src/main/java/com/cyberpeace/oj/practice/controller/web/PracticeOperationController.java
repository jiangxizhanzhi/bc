package com.cyberpeace.oj.practice.controller.web;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.OrderNameConstant;
import com.cyberpeace.oj.ojbase.controller.req.SceneNodeReq;
import com.cyberpeace.oj.ojbase.controller.req.SceneOperationReq;
import com.cyberpeace.oj.ojbase.controller.req.SceneStatusReq;
import com.cyberpeace.oj.ojbase.service.bo.TopoSceneDataBo;
import com.cyberpeace.oj.practice.service.bo.*;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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

	@GetMapping()
	@ApiOperation("获取单个实践操作")
	public BaseRsp<WebOperationBo> get(@RequestParam("resource_id") String resourceId) {
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);
		WebOperationBo operationBo = practiceOperationService.selectById(resourceId, authContext);

		return new BaseRsp<>(operationBo);
	}

	@GetMapping("/list")
	@ApiOperation("获取实践操作列表")
	public BaseRsp<ListRsp<AdminOperationListBo>> list(WebOperationListReq listReq) {

		Page page = listReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}

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

		// 记录远程操作日志
		practiceOperationService.createMpLog(answerReq);

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
	public BaseRsp<JSONObject>
			startScene(@RequestBody @Validated SceneOperationReq sceneOperationReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);

		JSONObject sceneData = practiceOperationService
				.startScene(sceneOperationReq.getResourceId(), authContext);

		return new BaseRsp<>(sceneData);
	}

	@ApiOperation("场景删除")
	@DeleteMapping("/scene")
	public BaseRsp deleteScene(@RequestBody @Validated SceneOperationReq sceneOperationReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		Boolean result = practiceOperationService.deleteScene(sceneOperationReq.getResourceId(),
				authContext);

		return new BaseRsp(result);
	}

	@ApiOperation("场景节点重启")
	@PostMapping("/scene_node_restart")
	public BaseRsp<Boolean> sceneNodeRestart(@RequestBody @Validated SceneNodeReq sceneNodeReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);

		Boolean result = practiceOperationService.nodeRestart(sceneNodeReq.getResourceId(),
				sceneNodeReq.getNodeId(), authContext);
		return new BaseRsp(result);

	}

	@ApiOperation("场景节点重建")
	@PostMapping("/scene_node_recreate")
	public BaseRsp<Boolean> sceneNodeRecreate(@RequestBody @Validated SceneNodeReq sceneNodeReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);

		Boolean result = practiceOperationService.nodeRecreate(sceneNodeReq.getResourceId(),
				sceneNodeReq.getNodeId(), authContext);

		return new BaseRsp<>(result);
	}

	@ApiOperation("场景暂停与恢复")
	@PatchMapping("/scene_update_status")
	public BaseRsp sceneUpdateStatus(@RequestBody @Validated SceneStatusReq sceneStatusReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		Boolean result = practiceOperationService.pauseScene(sceneStatusReq.getResourceId(),
				sceneStatusReq.getStatus(), authContext);
		return new BaseRsp(result);
	}

	@ApiOperation("获取场景和拓扑信息")
	@GetMapping("/topo_scene")
	public BaseRsp<TopoSceneDataBo> getTopoScene(@ApiParam(name = "resource_id",
			value = "课时编号") @RequestParam("resource_id") String operationId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		TopoSceneDataBo topoScene = practiceOperationService.getTopoScene(operationId, authContext);
		return new BaseRsp<>(topoScene);
	}

}
