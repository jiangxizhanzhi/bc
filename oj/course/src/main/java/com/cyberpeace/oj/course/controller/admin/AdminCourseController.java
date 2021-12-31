package com.cyberpeace.oj.course.controller.admin;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.course.controller.res.SimpleDataRes;
import com.cyberpeace.cs.service.UtilService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.course.controller.req.*;
import com.cyberpeace.oj.course.controller.res.AdminCourseRes;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import com.cyberpeace.oj.course.dao.mapper.CourseCategoryMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;
import com.cyberpeace.oj.course.service.bo.CourseQueryBo;
import com.cyberpeace.oj.course.service.bo.CourseWebBo;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.oj.ojbase.controller.AuthAndPublicController;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.context.AttachmentBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "oj-后台课程相关api")
@RestController
@RequestMapping("/oj/admin/course")
@RequiresAuthentication
public class AdminCourseController extends AuthAndPublicController<CourseService, Course> {

	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private CourseCategoryService courseCategoryService;

	@Autowired
	private CourseCategoryMapper courseCategoryMapper;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private UtilService utilService;

	// LGD在使用 知识点和技能点绑定课程
	@GetMapping("/all/list")
	@ApiOperation("分页查询全部课程信息")
	public BaseRsp<List<CourseWebBo>> getList(
			@ApiParam(name = "listReq", value = "查询条件") @Valid CourseListReq courseListReq) {

		// 组装筛选条件
		CourseQueryBo context = BeanUtils.copy(courseListReq, CourseQueryBo.class);

		Page page = courseListReq.toPageParam();

		// 分类数据组装
		if (StringUtils.isNotBlank(courseListReq.getDirection())) {
			List<String> childrenIds = courseCategoryService
					.getChildrenIds(courseListReq.getDirection(), CommonConstant.TRUE);
			context.setDirections(childrenIds);
		}
		context.setUserId(UserInfoContext.getUserContext().getUserId());

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		authContext.setIsSuperAdmin(true);
		// 数据查询
		ListRsp<CourseInfoBo> courseInfos = courseService.selectPage(page, context, authContext);
		BaseRsp rsp = new BaseRsp(courseInfos);
		return rsp;
	}

	@ApiOperation("后台课程列表分页查询")
	@GetMapping("/list")
	public BaseRsp<List<CourseInfoBo>>
			getAdminCourse(@ApiParam(name = "listReq", value = "查询条件") CourseAdminListReq listReq) {

		// 查询数据组装
		CourseQueryBo adminQueryBo = BeanUtils.copy(listReq, CourseQueryBo.class);

		// 分类数据组装
		if (StringUtils.isNotBlank(listReq.getDirection())) {
			List<String> childrenIds = courseCategoryService.getChildrenIds(listReq.getDirection(),
					CommonConstant.TRUE);
			adminQueryBo.setDirections(childrenIds);
		}
		adminQueryBo.setUserId(UserInfoContext.getUserContext().getUserId());

		// 分页数据获取
		Page page = listReq.toPageParam();

		// 获取权限
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);

		// 数据查询
		ListRsp<CourseInfoBo> rsp = courseService.selectPage(page, adminQueryBo, authContext);
		return new BaseRsp(rsp);
	}

	@ApiOperation("课程新增")
	@PostMapping("")
	public BaseRsp<Course> add(@ApiParam(name = "addReq",
			value = "课程基本信息") @Validated(Create.class) @RequestBody CourseAddReq addReq) {

		String userId = UserInfoContext.getUserContext().getUserId();

		Course course = BeanUtils.copy(addReq, Course.class);
		course.setOperationAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		course.setOperationAuthIds(JsonUtil.arrayToString(Lists.newArrayList()));
		course.setReadAuthIds(JsonUtil.arrayToString(Lists.newArrayList()));
		course.setReadAuthType(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode());
		course.setCourseType(CommonConstant.ZERO);

		CourseCategory courseCategory = courseCategoryMapper.selectById(addReq.getCategoryId());
		if (null == courseCategory) {
			log.error("When add course but category not found! category_id=[{}]",
					addReq.getCategoryId());
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		Course courseRsp = courseService.addCourse(userId, course);
		return new BaseRsp<>(courseRsp);
	}

	@ApiOperation("课程信息修改")
	@PatchMapping("")
	public BaseRsp<Course> patch(@RequestBody @Validated(Update.class) CourseUpdateReq updateReq) {
		String userId = UserInfoContext.getUserContext().getUserId();

		Course course = BeanUtils.copy(updateReq, Course.class);

		Course courseRsp = courseService.updateCourse(userId, course);
		return new BaseRsp<>(courseRsp);
	}

	@ApiOperation("课程信息查询")
	@GetMapping("")
	public BaseRsp<AdminCourseRes> get(@ApiParam(name = "resource_id",
			value = "课程编号") @RequestParam("resource_id") @Valid @NotBlank String resourceId) {

		Course course = courseMapper.selectById(resourceId);

		if (null == course) {
			log.error("Course not found! course_id=[{}]", resourceId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		AdminCourseRes adminCourseRes = BeanUtils.copy(course, AdminCourseRes.class);
		adminCourseRes.setLogo(utilService.handlerFileUrl(course.getLogo()));
		return new BaseRsp<>(adminCourseRes);
	}

	@ApiOperation("批量删除课程信息")
	@DeleteMapping("/batch")
	public BaseRsp<Boolean> batchDelete(@RequestBody @Valid BatchDeleteReq batchDeleteReq) {

		Boolean result = courseService.batchDelete(batchDeleteReq.getResourceIds());
		return new BaseRsp<>(result);
	}

	@ApiOperation("课程大纲文件上传")
	@PostMapping("/outline/upload_attachment")
	public BaseRsp<AttachmentBo>
			upload_attachment(@RequestParam("file") MultipartFile outlineFile) {
		AttachmentBo attachmentBo = attachmentService.uploadFile(AttachmentPath.OUTLINE.getCode(),
				false, outlineFile, true);

		// 文件路径处理
		String viewUrl = utilService.handlerFileUrl(attachmentBo.getFilePath());
		attachmentBo.setFilePath(viewUrl);

		return new BaseRsp<>(attachmentBo);
	}

	@ApiOperation("查询全部课程信息")
	@GetMapping("/all/course")
	public BaseRsp<List<SimpleDataRes>> allCourse() {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		List<String> courseIds = courseMapper.selectByAuth(authContext);

		List<Course> courses = courseMapper.selectBatchIds(courseIds);

		List<SimpleDataRes> courseRes = StreamApiUtils.map(courses,
				course -> BeanUtils.copy(course, SimpleDataRes.class));
		return new BaseRsp<>(courseRes);
	}

}
