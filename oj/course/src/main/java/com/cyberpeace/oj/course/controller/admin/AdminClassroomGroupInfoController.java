package com.cyberpeace.oj.course.controller.admin;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.oj.course.controller.req.*;
import com.cyberpeace.oj.course.service.ClassroomGroupInfoService;
import com.cyberpeace.oj.course.service.bo.ClassroomGroupArchiveBo;
import com.cyberpeace.oj.course.service.bo.GroupInfoBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "oj-后台课堂组操作相关api")
@RestController
@RequestMapping("/oj/admin/class/group_info")
@RequiresAuthentication
public class AdminClassroomGroupInfoController {

	@Autowired
	private ClassroomGroupInfoService classroomGroupInfoService;

	@ApiOperation("查询课堂信息/进入课堂")
	@GetMapping("/get")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<List<GroupInfoBo>> getGroupInfo(@RequestParam("classroom_id") String roomId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);

		List<GroupInfoBo> groupInfoBos = classroomGroupInfoService.getGroupInfoByRoom(roomId,
				authContext);
		return new BaseRsp<>(groupInfoBos);

	}

	@ApiOperation("课堂组信息更新")
	@PostMapping("/update")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<List<GroupInfoBo>>
			updateGroupInfo(@RequestBody @Validated ClassroomGroupReq groupReq) {

		List<GroupInfoBo> groupInfoBos = classroomGroupInfoService
				.updateGroupInfo(groupReq.getClassroomId(), groupReq.getGroupCount());
		return new BaseRsp<>(groupInfoBos);

	}

	@ApiOperation("保存当前分组模板信息")
	@PostMapping("/save")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<Integer>
			saveGroupInfo(@RequestBody @Validated SaveGroupInfoReq saveGroupInfoReq) {

		Integer result = classroomGroupInfoService.saveGroupInfo(saveGroupInfoReq.getClassroomId(),
				saveGroupInfoReq.getName(), saveGroupInfoReq.getData());
		return new BaseRsp<>(result);
	}

	@ApiOperation("删除课堂分组模板")
	@DeleteMapping("/delete")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<Integer> deleteGroupInfo(@RequestBody @Validated ClassroomArchiveReq deleteReq) {

		int result = classroomGroupInfoService.deleteClassroomArchive(deleteReq.getClassroomId(),
				deleteReq.getGroupArchiveId());
		return new BaseRsp<>(result);
	}

	@ApiOperation("使用课堂分组模板")
	@PostMapping("/use")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<List<GroupInfoBo>> userGroupInfo(@RequestBody ClassroomArchiveReq archiveReq) {

		List<GroupInfoBo> groupInfoBos = classroomGroupInfoService
				.userGroupInfo(archiveReq.getClassroomId(), archiveReq.getGroupArchiveId());
		return new BaseRsp<>(groupInfoBos);
	}

	@ApiOperation("查询课堂分组模板列表")
	@GetMapping("/archive")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<List<ClassroomGroupArchiveBo>>
			getGroupArchive(@RequestParam("classroom_id") String classroomId) {
		List<ClassroomGroupArchiveBo> archives = classroomGroupInfoService
				.selectGroupArchive(classroomId);
		return new BaseRsp<>(archives);
	}

	@ApiOperation("分组用户移动")
	@PatchMapping("/move")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<Boolean>
			moveGroupInfo(@RequestBody @Validated MoveGroupUserReq moveGroupUserReq) {
		Boolean result = classroomGroupInfoService.moveGroupUser(moveGroupUserReq.getClassroomId(),
				moveGroupUserReq.getFromGroupId(), moveGroupUserReq.getToGroupId(),
				moveGroupUserReq.getUserId());
		return new BaseRsp<>(result);
	}

	@ApiOperation("设置场景延时")
	@PatchMapping("/set_scene_destroy_delay")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<Boolean> setDestroyDelay(@RequestBody GroupSceneDelayReq delayReq) {

		Boolean result = classroomGroupInfoService.setGroupSceneDelay(delayReq.getClassroomId(),
				delayReq.getEnd());
		return new BaseRsp<>(result);
	}

	@ApiOperation("获取场景延时状态")
	@GetMapping("/get_destroy_delay_status")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<Boolean> getDestroyDelay(@RequestParam("classroomId") String classroomId) {
		Boolean result = classroomGroupInfoService.getGroupSceneDelayStatus(classroomId);
		return new BaseRsp<>(result);
	}

}
