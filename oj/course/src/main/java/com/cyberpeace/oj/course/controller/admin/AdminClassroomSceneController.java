package com.cyberpeace.oj.course.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.oj.course.controller.req.ClassroomSceneReq;
import com.cyberpeace.oj.course.controller.req.GroupSceneReq;
import com.cyberpeace.oj.course.service.ClassroomSceneService;
import com.cyberpeace.oj.course.service.bo.GroupInfoBo;
import com.cyberpeace.oj.ojbase.service.PushMessageHandler;
import com.cyberpeace.oj.ojbase.service.bo.SceneMonitorBo;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * 管理课堂场景Controller
 *
 * @author SN
 * @date 2024/11/14
 */
@Api(tags = "oj-后台课堂场景相关api")
@RestController
@RequestMapping("/oj/admin/classroom/scene")
@RequiresAuthentication
public class AdminClassroomSceneController {

	/**
	 * 课堂场景Service
	 */
	@Autowired
	private ClassroomSceneService classroomSceneService;

	/**
	 * 推送消息处理器
	 */
	@Autowired
	private PushMessageHandler pushMessageHandler;

	/**
	 * 启动场景
	 *
	 * @param groupSceneReq 组场景请求体
	 * @return {@code BaseRsp<JSONObject> }
	 */
	@ApiOperation("课堂组场景启动")
	@PostMapping("")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<JSONObject> startScene(@RequestBody @Validated GroupSceneReq groupSceneReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		JSONObject sceneData = classroomSceneService.startScene(groupSceneReq.getGroupId(),
				authContext);
		return new BaseRsp<>(sceneData);
	}

	/**
	 * 删除场景
	 *
	 * @param groupSceneReq 组场景请求体
	 * @return {@code BaseRsp<String> }
	 */
	@ApiOperation("课堂场景删除")
	@DeleteMapping("")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<String> deleteScene(@RequestBody @Validated GroupSceneReq groupSceneReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);

		String result = classroomSceneService.deleteScene(groupSceneReq.getGroupId(), authContext);
		if (StringUtils.isNotBlank(result)) {
			pushMessageHandler.pushMessageToNoSceneClassroom(pushMessageHandler.getDeleteMessage(),
					groupSceneReq.getGroupId());
		}
		return new BaseRsp<>(result);
	}

	/**
	 * 批量启动场景
	 *
	 * @param classroomSceneReq 课堂场景请求体
	 * @return {@code BaseRsp<List<GroupInfoBo>> }
	 * @throws InterruptedException 中断异常
	 */
	@ApiOperation("课堂课堂场景一键启动")
	@PostMapping("/batch")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<List<GroupInfoBo>>
			batchStartScene(@RequestBody @Validated ClassroomSceneReq classroomSceneReq)
					throws InterruptedException {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		List<GroupInfoBo> groupInfoBos = classroomSceneService
				.batchStartScene(classroomSceneReq.getClassroomId(), authContext);
		return new BaseRsp<>(groupInfoBos);
	}

	/**
	 * 批量删除场景
	 *
	 * @param classroomSceneReq 课堂场景请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("课堂场景一键清除")
	@DeleteMapping("/batch")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<Boolean>
			batchDeleteScene(@RequestBody @Validated ClassroomSceneReq classroomSceneReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		classroomSceneService.batchDeleteScene(classroomSceneReq.getClassroomId(), authContext);
		return new BaseRsp<>(CommonConstant.TRUE);
	}

	/**
	 * 教室监视器
	 *
	 * @param classroomId 课堂id
	 * @return {@code BaseRsp<List<SceneMonitorBo>> }
	 */
	@ApiOperation("课堂场景监控")
	@GetMapping("/classroom_monitor")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<List<SceneMonitorBo>> classroomMonitor(@ApiParam(name = "classroom_id",
			value = "课堂编号") @RequestParam("classroom_id") String classroomId) {

		List<SceneMonitorBo> sceneMonitorBos = classroomSceneService.selectMonitorList(classroomId);
		return new BaseRsp<>(sceneMonitorBos);
	}

	/**
	 * 暂停
	 *
	 * @param groupSceneReq 组场景请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("课堂场景暂停与恢复")
	@PatchMapping("/pause")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<Boolean> pause(@RequestBody @Validated GroupSceneReq groupSceneReq) {
		Boolean result = classroomSceneService.updateSceneStatus(groupSceneReq.getGroupId());
		return new BaseRsp<>(result);
	}

}
