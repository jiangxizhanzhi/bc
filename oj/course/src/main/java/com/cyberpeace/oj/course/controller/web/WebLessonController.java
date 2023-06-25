package com.cyberpeace.oj.course.controller.web;

import java.util.List;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.oj.course.controller.res.WebContextInfoStatusRes;
import com.cyberpeace.oj.course.service.LessonConfigService;
import com.cyberpeace.oj.course.service.bo.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.course.controller.req.LessonPracticeSubmitReq;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.LessonSceneService;
import com.cyberpeace.oj.ojbase.controller.req.SceneNodeReq;
import com.cyberpeace.oj.ojbase.controller.req.SceneOperationReq;
import com.cyberpeace.oj.ojbase.controller.req.SceneStatusReq;
import com.cyberpeace.oj.ojbase.service.bo.TopoSceneDataBo;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "oj-前台课时相关api")
@RestController
@RequiresAuthentication
@RequestMapping("/oj/lesson")
@Slf4j
public class WebLessonController {

	@Autowired
	private CourseLessonService courseLessonService;

	@Autowired
	private LessonSceneService lessonSceneService;

	@Autowired
	private LessonConfigService lessonConfigService;

	@ApiOperation("课时详情查询")
	@GetMapping("")
	@ApiImplicitParams({ @ApiImplicitParam(name = "lesson_id", value = "课时编号", dataTypeClass = String.class),
			@ApiImplicitParam(name = "course_id", value = "课程编号", dataTypeClass = String.class) })
    @RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
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
	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
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
	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
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
    @RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE}, logical = Logical.OR)
    public BaseRsp<TopoSceneDataBo> getTopoScene(@ApiParam(name = "resource_id",
            value = "课时编号") @RequestParam("resource_id") String lessonId, @RequestParam(value = "room_id", required = false) String roomId) {
        AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
                true);
		TopoSceneDataBo topoScene = courseLessonService.getTopoScene(lessonId, roomId, authContext);
		return new BaseRsp<>(topoScene);
	}

	@ApiOperation("前台课程场景启动")
	@PostMapping("/start_scene")
	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
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
	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
	public BaseRsp<Boolean> deleteScene(@RequestBody @Validated SceneOperationReq lessonSceneReq) {
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		Boolean result = lessonSceneService.deleteScene(lessonSceneReq.getResourceId(),
				authContext);
		return new BaseRsp<>(result);
	}

	@ApiOperation("前台课时场景节点重启")
	@PostMapping("/node_restart")
	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
	public BaseRsp<Boolean> restartNode(@RequestBody @Validated SceneNodeReq sceneNodeReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		Boolean result = lessonSceneService.restartNode(sceneNodeReq.getResourceId(),
				sceneNodeReq.getNodeId(), sceneNodeReq.getRoomId(), authContext);
		return new BaseRsp<>(result);
	}

	@ApiOperation("前台课时场景节点重建")
	@PostMapping("/node_recreate")
	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
	public BaseRsp<Boolean> recreateNode(@RequestBody @Validated SceneNodeReq sceneNodeReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		Boolean result = lessonSceneService.recreateNode(sceneNodeReq.getResourceId(),
				sceneNodeReq.getNodeId(), sceneNodeReq.getRoomId(), authContext);
		return new BaseRsp<>(result);
	}

	@ApiOperation("前台课时场景暂停与恢复")
	@PatchMapping("/update_status")
	@RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
	public BaseRsp<Boolean>
			updateSceneStatus(@RequestBody @Validated SceneStatusReq sceneStatusReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		Boolean result = lessonSceneService.updateSceneStatus(sceneStatusReq.getRoomId(), sceneStatusReq.getResourceId(),
				sceneStatusReq.getStatus(), authContext);
		return new BaseRsp<>(result);
	}

    @ApiOperation("前台课时场景-接入：上一题当前题下一题lessionid")
    @GetMapping("/context_info_status")
    @RequiresPermissions(MenuConstant.WEB_OJ_ALL_COURSE)
    public BaseRsp<WebContextInfoStatusRes> contextInfoStatus(
            @ApiParam(name = "course_id", value = "课程id") @RequestParam(name = "course_id") String courseId,
            @ApiParam(name = "lesson_id", value = "课时id") @RequestParam(name = "lesson_id") String lessonId,
            @ApiParam(name = "start_lesson_id", value = "第一次进入的课时id") @RequestParam(name = "start_lesson_id") String startLessonId,
            @ApiParam(name = "navigate", value = "操作提示参数：1=下一题，0=当前题，-1=上一题") Integer navigate) {
        // TODO

        AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
                true);
        //  1.查询课时编排，
        LessonConfigBo lessonConfig = lessonConfigService.getLessonConfig(courseId, authContext);
        // 2.按顺序展开课时编排
        List<LessonConfigBo> lessonConfigBos = lessonConfigService.expandLessonConfig(lessonConfig);
        // 3.找到要查询的课时在列表中的定位，校验前端传的lessonId和navigate的是否合理
        if (CollectionUtils.isEmpty(lessonConfigBos)) {
            log.error("lesson configs not found! course_id=[{}]", courseId);
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        // 4.1.查询上/下是否实操课，若否：navigate=-1/1则抛出（上/下一节是理论课，无法在当前操作环境中切换）
        // 4.2.查询上/下的实操课的场景是否为空，若非空则抛出（上/下一节使用新的环境，无法在当前操作环境中切换）
        WebContextInfoStatusRes webContextInfoStatusRes = lessonConfigService.lessonIdOfExpandedLessonConfigs(navigate, lessonId, lessonConfigBos, authContext, startLessonId);
        webContextInfoStatusRes.setStartLessonId(startLessonId);

        return new BaseRsp<>(webContextInfoStatusRes);
    }

}
