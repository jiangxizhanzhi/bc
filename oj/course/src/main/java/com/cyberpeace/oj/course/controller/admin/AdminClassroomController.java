package com.cyberpeace.oj.course.controller.admin;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.course.controller.req.ClassroomListReq;
import com.cyberpeace.oj.course.controller.res.AdminClassroomRes;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import com.cyberpeace.oj.course.service.ClassroomService;
import com.cyberpeace.oj.course.service.bo.ClassroomBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@Api(tags = "oj-后台课程表api")
@RestController
@RequestMapping("/oj/admin/course/course_schedules")
@RequiresAuthentication
@Slf4j
public class AdminClassroomController {

	@Autowired
	private ClassroomService classroomService;

	@Autowired
	private ClassroomMapper classroomMapper;

	@ApiOperation("课程表分页查询")
	@GetMapping("/list")
	public BaseRsp<List<AdminClassroomRes>>
			list(@Validated ClassroomListReq classroomListReq) {

		boolean isAdmin = UserInfoContext.getUserContext().isAdmin();
		String user_id = UserInfoContext.getUserContext().getUserId();
		if (!isAdmin && !Objects.equals(classroomListReq.getCreateUser(), user_id)) {
			return new BaseRsp<>();
		}

		List<ClassroomBo> classroomBos = classroomService.listByTimeAndOrg(
				classroomListReq.getStartDate(), classroomListReq.getEndDate(),
				classroomListReq.getOrganization(), classroomListReq.getCreateUser());

		List<AdminClassroomRes> adminClassroomRes = StreamApiUtils.simpleMap(
				classroomBos,
				courseSchedule -> BeanUtils.copy(courseSchedule, AdminClassroomRes.class));

		return new BaseRsp<>(adminClassroomRes);
	}

}
