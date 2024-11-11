package com.cyberpeace.oj.course.controller.web;

import com.cyberpeace.common.constant.MenuConstant;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.oj.course.controller.req.LessonPracticeSceneNodeReq;
import com.cyberpeace.oj.course.controller.req.LessonPracticeSceneReq;
import com.cyberpeace.oj.course.controller.req.LessonPracticeSceneStatusReq;
import com.cyberpeace.oj.course.service.LessonPracticeSceneService;
import com.cyberpeace.oj.ojbase.service.bo.TopoSceneDataBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Web课练习Controller
 *
 * @author SN
 * @date 2024/11/11
 */
@Api(tags = "oj-前台课后练习相关api")
@RestController
@RequestMapping("/oj/lesson/practice")
@RequiresAuthentication
public class WebLessonPracticeController {

	/**
	 * 课练习场景Service
	 */
	@Autowired
	private LessonPracticeSceneService lessonPracticeSceneService;

	/**
	 * 获取拓扑场景
	 *
	 * @param lessonId   课id
	 * @param practiceId 练习id
	 * @return {@code BaseRsp<TopoSceneDataBo> }
	 */
	@ApiOperation("前台课后练习获取拓扑信息")
	@GetMapping("/scene_topo")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE}, logical = Logical.OR)
	public BaseRsp<TopoSceneDataBo> getTopoScene(
			@ApiParam(name = "lesson_id",
					value = "课时编号") @RequestParam("lesson_id") String lessonId,
			@ApiParam(name = "practice_id",
					value = "课后练习习题编号") @RequestParam("practice_id") String practiceId) {
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		TopoSceneDataBo topoScene = lessonPracticeSceneService.getTopoScene(lessonId, practiceId,
				authContext);
		return new BaseRsp<>(topoScene);
	}

	/**
	 * 开始场景
	 *
	 * @param sceneReq 场景请求体
	 * @return {@code BaseRsp<JSONObject> }
	 */
	@ApiOperation("前台课后练习场景启动")
	@PostMapping("/start_scene")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE}, logical = Logical.OR)
	public BaseRsp<JSONObject> startScene(@RequestBody @Validated LessonPracticeSceneReq sceneReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		JSONObject scene = lessonPracticeSceneService.startScene(sceneReq.getLessonId(),
				sceneReq.getPracticeId(), authContext);
		return new BaseRsp<>(scene);
	}

	/**
	 * 删除场景
	 *
	 * @param sceneReq 场景请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("前台课后练习场景删除")
	@DeleteMapping("/delete_scene")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE}, logical = Logical.OR)
	public BaseRsp<Boolean> deleteScene(@RequestBody @Validated LessonPracticeSceneReq sceneReq) {
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		Boolean result = lessonPracticeSceneService.deleteScene(sceneReq.getLessonId(),
				sceneReq.getPracticeId(), authContext);
		return new BaseRsp<>(result);
	}

	/**
	 * 重启节点
	 *
	 * @param sceneNodeReq 场景节点请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("前台课后练习场景节点重启")
	@PostMapping("/node_restart")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE}, logical = Logical.OR)
	public BaseRsp<Boolean>
			restartNode(@RequestBody @Validated LessonPracticeSceneNodeReq sceneNodeReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		Boolean result = lessonPracticeSceneService.restartNode(sceneNodeReq.getLessonId(),
				sceneNodeReq.getPracticeId(), sceneNodeReq.getNodeId(), authContext);
		return new BaseRsp<>(result);
	}

	/**
	 * 重新创建节点
	 *
	 * @param sceneNodeReq 场景节点请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("前台课后练习场景节点重建")
	@PostMapping("/node_recreate")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE}, logical = Logical.OR)
	public BaseRsp<Boolean>
			recreateNode(@RequestBody @Validated LessonPracticeSceneNodeReq sceneNodeReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		Boolean result = lessonPracticeSceneService.recreateNode(sceneNodeReq.getLessonId(),
				sceneNodeReq.getPracticeId(), sceneNodeReq.getNodeId(), authContext);
		return new BaseRsp<>(result);
	}

	/**
	 * 更新场景状态
	 *
	 * @param sceneStatusReq 场景状态请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("前台课后练习场景暂停与恢复")
	@PatchMapping("/update_status")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE}, logical = Logical.OR)
	public BaseRsp<Boolean>
			updateSceneStatus(@RequestBody @Validated LessonPracticeSceneStatusReq sceneStatusReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		Boolean result = lessonPracticeSceneService.updateSceneStatus(sceneStatusReq.getLessonId(),
				sceneStatusReq.getPracticeId(), sceneStatusReq.getStatus(), authContext);
		return new BaseRsp<>(result);
	}

}
