package com.cyberpeace.oj.course.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.oj.course.controller.req.ClassroomSceneReq;
import com.cyberpeace.oj.course.controller.req.GroupSceneReq;
import com.cyberpeace.oj.course.service.ClassroomSceneService;
import com.cyberpeace.oj.course.service.bo.GroupInfoBo;
import com.cyberpeace.oj.ojbase.service.bo.SceneMonitorBo;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api(tags = "oj-后台课堂场景相关api")
@RestController
@RequestMapping("/oj/admin/classroom/scene")
@RequiresAuthentication
public class AdminClassroomSceneController {

	@Autowired
	private ClassroomSceneService classroomSceneService;

	@ApiOperation("课堂组场景启动")
	@PostMapping("")
	public BaseRsp<JSONObject> startScene(@RequestBody @Validated GroupSceneReq groupSceneReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		JSONObject sceneData = classroomSceneService.startScene(groupSceneReq.getGroupId(),
				authContext);
		return new BaseRsp<>(sceneData);
	}

	@ApiOperation("课堂场景删除")
	@DeleteMapping("")
	public BaseRsp<Boolean> deleteScene(@RequestBody @Validated GroupSceneReq groupSceneReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);

		Boolean result = classroomSceneService.deleteScene(groupSceneReq.getGroupId(), authContext);
		return new BaseRsp<>(result);
	}

	@ApiOperation("课堂课堂场景一键启动")
	@PostMapping("/batch")
	public BaseRsp<List<GroupInfoBo>>
			batchStartScene(@RequestBody @Validated ClassroomSceneReq classroomSceneReq)
					throws InterruptedException {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		List<GroupInfoBo> groupInfoBos = classroomSceneService
				.batchStartScene(classroomSceneReq.getClassroomId(), authContext);
		return new BaseRsp<>(groupInfoBos);
	}

	@ApiOperation("课堂场景一键清除")
	@DeleteMapping("/batch")
	public BaseRsp<Boolean>
			batchDeleteScene(@RequestBody @Validated ClassroomSceneReq classroomSceneReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		classroomSceneService.batchDeleteScene(classroomSceneReq.getClassroomId(), authContext);
		return new BaseRsp<>(CommonConstant.TRUE);
	}

	@ApiOperation("课堂场景监控")
	@GetMapping("/classroom_monitor")
	public BaseRsp<List<SceneMonitorBo>> classroomMonitor(@ApiParam(name = "classroom_id",
			value = "课堂编号") @RequestParam("classroom_id") String classroomId) {

		List<SceneMonitorBo> sceneMonitorBos = classroomSceneService.selectMonitorList(classroomId);
		return new BaseRsp<>(sceneMonitorBos);
	}

}
