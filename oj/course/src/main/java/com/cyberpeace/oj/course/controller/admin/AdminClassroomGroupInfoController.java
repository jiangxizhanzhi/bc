package com.cyberpeace.oj.course.controller.admin;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.oj.course.controller.req.ClassroomGroupReq;
import com.cyberpeace.oj.course.service.ClassroomGroupInfoService;
import com.cyberpeace.oj.course.service.ClassroomService;
import com.cyberpeace.oj.course.service.bo.GroupInfoBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = "oj-后台课堂组操作相关api")
@RestController
@RequestMapping("/oj/admin/class/group_info")
public class AdminClassroomGroupInfoController {

	@Autowired
	private ClassroomGroupInfoService classroomGroupInfoService;

	@ApiOperation("查询课堂信息/进入课堂")
	@GetMapping("/get")
	public BaseRsp<List<GroupInfoBo>> getGroupInfo(@RequestParam("classroom_id") String roomId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);

		List<GroupInfoBo> groupInfoBos = classroomGroupInfoService.getGroupInfoByRoom(roomId,
				authContext);
		return new BaseRsp<>(groupInfoBos);

	}

	@ApiOperation("课堂组信息更新")
	@PostMapping("/update")
	public BaseRsp<List<GroupInfoBo>>
			updateGroupInfo(@RequestBody @Validated ClassroomGroupReq groupReq) {

		List<GroupInfoBo> groupInfoBos = classroomGroupInfoService
				.updateGroupInfo(groupReq.getClassroomId(), groupReq.getGroupCount());
		return new BaseRsp<>(groupInfoBos);

	}

}
