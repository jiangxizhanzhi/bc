package com.cyberpeace.oj.course.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.controller.req.ClassroomListReq;
import com.cyberpeace.oj.course.controller.req.ClassroomMonitorReq;
import com.cyberpeace.oj.course.controller.req.ClassroomReq;
import com.cyberpeace.oj.course.dao.context.ClassRoomListQuery;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import com.cyberpeace.oj.course.service.ClassroomService;
import com.cyberpeace.oj.course.service.bo.ClassroomBo;
import com.cyberpeace.oj.course.service.bo.ClassroomMonitorBo;
import com.cyberpeace.oj.course.service.bo.GroupInfoBo;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "oj-后台课程表api")
@RestController
@RequestMapping("/oj/admin/course/classroom")
@RequiresAuthentication
@Slf4j
public class AdminClassroomController {

	@Autowired
	private ClassroomService classroomService;

	@Autowired
	private ClassroomMapper classroomMapper;

	@Autowired
	private RemoteOrganizationService remoteOrganizationService;

	@ApiOperation("后台课程表查询")
	@GetMapping("/list")
	public BaseRsp<List<ClassroomBo>> list(@Validated ClassroomListReq classroomListReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		if (!authContext.getSuperAdmin() && StringUtils.isBlank(authContext.getOrganization())) {
			return new BaseRsp<>();
		}

		ClassRoomListQuery roomListQuery = BeanUtils.copy(classroomListReq,
				ClassRoomListQuery.class);

		// 查询数据组装
		if (StringUtils.isNotBlank(classroomListReq.getOrganizationId())) {
			List<String> orgIds = remoteOrganizationService
					.childrenList(classroomListReq.getOrganizationId(), true);
			roomListQuery.setChildrenOrg(orgIds);
		}

		// 数据查询
		List<ClassroomBo> classroomBos = classroomService.selectList(roomListQuery, authContext);

		return new BaseRsp<>(classroomBos);
	}

	@ApiOperation("后台课程表创建")
	@PostMapping("")
	public BaseRsp
			createClassRoom(@RequestBody @Validated(Create.class) ClassroomReq classroomReq) {

		// 组织数据获取
		List<String> orgIds = remoteOrganizationService
				.childrenList(classroomReq.getOrganizationId(), true);

		// 基本数据设置
		Classroom classroom = BeanUtils.copy(classroomReq, Classroom.class);
		classroom.setOrganizationIds(JsonUtil.arrayToString(orgIds));
		classroom.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		Classroom insertResult = classroomService.create(classroom);

		return new BaseRsp(insertResult);
	}

	@ApiOperation("后台课程表信息修改")
	@PatchMapping("")
	public BaseRsp
			updateClassRoom(@RequestBody @Validated(Update.class) ClassroomReq classroomReq) {
		// 组织数据获取
		List<String> orgIds = remoteOrganizationService
				.childrenList(classroomReq.getOrganizationId(), true);

		// 基本数据设置
		Classroom classroom = BeanUtils.copy(classroomReq, Classroom.class);
		classroom.setOrganizationIds(JsonUtil.arrayToString(orgIds));

		int result = classroomMapper.updateById(classroom);
		return new BaseRsp(result);
	}

	@ApiOperation("课堂监控获取用户签到列表")
	@GetMapping("/classroom_monitor")
	public BaseRsp<ListRsp<ClassroomMonitorBo>> classroomMonitor(ClassroomMonitorReq monitorReq) {

		Page page = monitorReq.toPageParam();
		ListRsp<ClassroomMonitorBo> classroomMonitorBoList = classroomService.selectSignList(page,
				monitorReq.getClassroomId());
		return new BaseRsp(classroomMonitorBoList);

	}

}
