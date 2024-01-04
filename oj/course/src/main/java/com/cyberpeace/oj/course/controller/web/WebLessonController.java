package com.cyberpeace.oj.course.controller.web;

import java.util.List;

import com.cyberpeace.auth.service.bo.UserInfoContext;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.course.controller.req.LessonPracticeSubmitReq;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.LessonSceneService;
import com.cyberpeace.oj.course.service.bo.LessonInfoBo;
import com.cyberpeace.oj.course.service.bo.LessonPracticeBo;
import com.cyberpeace.oj.course.service.bo.PracticeAnswerContext;
import com.cyberpeace.oj.course.service.bo.SubmitAnswerContext;
import com.cyberpeace.oj.ojbase.controller.req.SceneNodeReq;
import com.cyberpeace.oj.ojbase.controller.req.SceneOperationReq;
import com.cyberpeace.oj.ojbase.controller.req.SceneStatusReq;
import com.cyberpeace.oj.ojbase.service.bo.TopoSceneDataBo;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "oj-前台课时相关api")
@RestController
//@RequiresAuthentication
@RequestMapping("/oj/lesson")
@Slf4j
public class WebLessonController {

	@Autowired
	private CourseLessonService courseLessonService;

	@Autowired
	private LessonSceneService lessonSceneService;

	@ApiOperation("课时详情查询")
	@GetMapping("")
	@ApiImplicitParams({ @ApiImplicitParam(name = "lesson_id", value = "课时编号", dataTypeClass = String.class),
			@ApiImplicitParam(name = "course_id", value = "课程编号", dataTypeClass = String.class) })
//    @RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
    public BaseRsp<LessonInfoBo> lessonDetail(@RequestParam("course_id") String courseId, @RequestParam(value = "learned_type", required = true) Integer learnedType,
                                              @RequestParam("lesson_id") String lessonId, @RequestParam(value = "room_id", required = false) String roomId) {

        AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
                CommonConstant.TRUE);
        LessonInfoBo infoBo = courseLessonService.getLessonDetail(courseId, lessonId, roomId, learnedType ,authContext);
		// 记录远程操作日志
		courseLessonService.createMpLog(infoBo);
		return new BaseRsp<>(infoBo);
	}

	@ApiOperation("课后练习数据查询")
	@GetMapping("/practice_after_class")
	@ApiImplicitParams({ @ApiImplicitParam(name = "resource_id", value = "课时编号", dataTypeClass = String.class),
			@ApiImplicitParam(name = "course_id", value = "课程编号", dataTypeClass = String.class) })
//	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
	public BaseRsp<LessonPracticeBo> lessonPractice(@RequestParam("resource_id") String resourceId,
			@RequestParam("course_id") String courseId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);

		LessonPracticeBo practiceBo = courseLessonService.practiceByLesson(courseId, resourceId,
				authContext);
		return new BaseRsp<>(practiceBo);

	}

	@ApiOperation("课后练习解答")
	@PostMapping("/submit_answer")
//	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
	public BaseRsp<EvaluateResult>
			submitAnswer(@RequestBody @Validated LessonPracticeSubmitReq submitReq) {

		List<PracticeAnswerContext> answerContexts = BeanUtils.deepCopyList(submitReq.getAnswers(),
				PracticeAnswerContext.class);
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);
		SubmitAnswerContext answerContext = BeanUtils.copy(submitReq, SubmitAnswerContext.class);

		EvaluateResult evaluateResult = courseLessonService.submitLessonPractice(answerContext,
				answerContexts, authContext);

		return new BaseRsp<>(evaluateResult);

	}

    @ApiOperation("前台课时获取拓扑信息")
    @GetMapping("/scene_topo")
//    @RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE}, logical = Logical.OR)
    public BaseRsp<TopoSceneDataBo> getTopoScene(@ApiParam(name = "resource_id",
            value = "课时编号") @RequestParam("resource_id") String lessonId, @RequestParam(value = "room_id", required = false) String roomId) {
        AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
                true);
		TopoSceneDataBo topoScene = courseLessonService.getTopoScene(lessonId, roomId, authContext);
		return new BaseRsp<>(topoScene);
	}

	@ApiOperation("前台课程场景启动")
	@PostMapping("/start_scene")
//	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
	public BaseRsp<JSONObject>
			startScene(@RequestBody @Validated SceneOperationReq lessonSceneReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		JSONObject scene = lessonSceneService.startScene(lessonSceneReq.getResourceId(),
				authContext);
		return new BaseRsp<>(scene);
	}

	@ApiOperation("前台场景删除")
	@DeleteMapping("/delete_scene")
//	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
	public BaseRsp<Boolean> deleteScene(@RequestBody @Validated SceneOperationReq lessonSceneReq) {
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		Boolean result = lessonSceneService.deleteScene(lessonSceneReq.getResourceId(),
				authContext);
		return new BaseRsp<>(result);
	}

	@ApiOperation("前台课时场景节点重启")
	@PostMapping("/node_restart")
//	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
	public BaseRsp<Boolean> restartNode(@RequestBody @Validated SceneNodeReq sceneNodeReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		Boolean result = lessonSceneService.restartNode(sceneNodeReq.getResourceId(),
				sceneNodeReq.getNodeId(), sceneNodeReq.getRoomId(), authContext);
		return new BaseRsp<>(result);
	}

	@ApiOperation("前台课时场景节点重建")
	@PostMapping("/node_recreate")
//	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
	public BaseRsp<Boolean> recreateNode(@RequestBody @Validated SceneNodeReq sceneNodeReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		Boolean result = lessonSceneService.recreateNode(sceneNodeReq.getResourceId(),
				sceneNodeReq.getNodeId(), sceneNodeReq.getRoomId(), authContext);
		return new BaseRsp<>(result);
	}

	@ApiOperation("前台课时场景暂停与恢复")
	@PatchMapping("/update_status")
//	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
	public BaseRsp<Boolean>
			updateSceneStatus(@RequestBody @Validated SceneStatusReq sceneStatusReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		Boolean result = lessonSceneService.updateSceneStatus(sceneStatusReq.getRoomId(), sceneStatusReq.getResourceId(),
				sceneStatusReq.getStatus(), authContext);
		return new BaseRsp<>(result);
	}

}
