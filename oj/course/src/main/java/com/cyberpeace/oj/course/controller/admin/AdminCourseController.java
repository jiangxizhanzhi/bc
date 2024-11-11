package com.cyberpeace.oj.course.controller.admin;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.*;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.oj.course.controller.req.*;
import com.cyberpeace.oj.course.controller.res.AdminCourseRes;
import com.cyberpeace.oj.course.controller.res.SimpleDataRes;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import com.cyberpeace.oj.course.dao.mapper.CourseCategoryMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;
import com.cyberpeace.oj.course.service.bo.CourseQueryBo;
import com.cyberpeace.oj.course.service.bo.CourseWebBo;
import com.cyberpeace.oj.ojbase.controller.AuthAndPublicController;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.util.List;

/**
 * 管理课程Controller
 *
 * @author SN
 * @date 2024/11/11
 */
@Slf4j
@Api(tags = "oj-后台课程相关api")
@RestController
@RequestMapping("/oj/admin/course")
@RequiresAuthentication
public class AdminCourseController extends AuthAndPublicController<CourseService, Course> {

	/**
	 * 课程Service
	 */
	@Autowired
	private CourseService courseService;

	/**
	 * 课程Mapper
	 */
	@Autowired
	private CourseMapper courseMapper;

	/**
	 * 课程类别Service
	 */
	@Autowired
	private CourseCategoryService courseCategoryService;

	/**
	 * 课程类别Mapper
	 */
	@Autowired
	private CourseCategoryMapper courseCategoryMapper;

	/**
	 * 附件Service
	 */
	@Autowired
	private AttachmentService attachmentService;

	/**
	 * utilService
	 */
	@Autowired
	private UtilService utilService;

	/**
	 * 远程日志Service
	 */
	@Autowired
	private RemoteLogService remoteLogService;

	/**
	 * 获取列表
	 *
	 * @param courseListReq 课程列表请求体
	 * @return {@code BaseRsp<List<CourseWebBo>> }
	 */// LGD在使用 知识点和技能点绑定课程
	@GetMapping("/all/list")
	@ApiOperation("分页查询全部课程信息")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
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
		ListRsp<CourseWebBo> courseInfos = courseService.selectPage(page, context, authContext, false);
		return new BaseRsp(courseInfos);
	}

	/**
	 * 获取管理课程
	 *
	 * @param listReq 列表请求体
	 * @return {@code BaseRsp<List<CourseWebBo>> }
	 */
	@ApiOperation("后台课程列表分页查询")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<List<CourseWebBo>>
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
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}

		// 获取权限
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);

		// 数据查询
		ListRsp<CourseWebBo> rsp = courseService.selectPage(page, adminQueryBo, authContext, false);
		return new BaseRsp(rsp);
	}

	/**
	 * 新增
	 *
	 * @param addReq 新增请求体
	 * @return {@code BaseRsp<Course> }
	 */
	@ApiOperation("课程新增")
	@PostMapping("")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<Course> add(@ApiParam(name = "addReq",
			value = "课程基本信息") @Validated(Create.class) @RequestBody CourseAddReq addReq) {

		addReq.validate();
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

		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.COURSE,
				OperationType.CREATE, courseRsp.getName());

		return new BaseRsp<>(courseRsp);
	}

	/**
	 * 修改
	 *
	 * @param updateReq 更新请求体
	 * @return {@code BaseRsp<Course> }
	 */
	@ApiOperation("课程信息修改")
	@PatchMapping("")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<Course> patch(@RequestBody @Validated(Update.class) CourseUpdateReq updateReq) {

		updateReq.validate();
		String userId = UserInfoContext.getUserContext().getUserId();

		Course course = BeanUtils.copy(updateReq, Course.class);

		Course courseRsp = courseService.updateCourse(userId, course);

		course = courseService.getById(updateReq.getResourceId());
		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.COURSE,
				OperationType.UPDATE, course.getName());

		return new BaseRsp<>(courseRsp);
	}

	/**
	 * 获取
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<AdminCourseRes> }
	 */
	@ApiOperation("课程信息查询")
	@GetMapping("")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
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

	/**
	 * 批量删除
	 *
	 * @param batchDeleteReq 批量删除请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("批量删除课程信息")
	@DeleteMapping("/batch")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<Boolean> batchDelete(@RequestBody @Valid BatchDeleteReq batchDeleteReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);

		List<Course> courseList = courseService.listByIds(batchDeleteReq.getResourceIds());

		Boolean result = courseService.batchDelete(batchDeleteReq.getResourceIds(), authContext);

		courseList.forEach(course -> {
			if (course != null) {
				remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.COURSE,
						OperationType.DELETE, course.getName());
			}
		});

		return new BaseRsp<>(result);
	}

	/**
	 * 上传附件
	 *
	 * @param outlineFile 大纲文件
	 * @return {@code BaseRsp<AttachmentBo> }
	 */
	@ApiOperation("课程大纲文件上传")
	@PostMapping("/outline/upload_attachment")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<AttachmentBo>
			upload_attachment(@RequestParam("file") MultipartFile outlineFile) {

		// 文件校验
		FileUtil.validateSuffix(outlineFile, FileSunffixConstant.JPG.getDesc(),
				FileSunffixConstant.PNG.getDesc(), FileSunffixConstant.JPEG.getDesc());

		// 文件上传
		AttachmentBo attachmentBo = attachmentService.uploadFile(AttachmentPath.OUTLINE.getCode(),
				false, outlineFile, true);

		// 文件路径处理
		String viewUrl = utilService.handlerFileUrl(attachmentBo.getFilePath());
		attachmentBo.setFilePath(viewUrl);

		return new BaseRsp<>(attachmentBo);
	}

	/**
	 * 所有课程
	 *
	 * @return {@code BaseRsp<List<SimpleDataRes>> }
	 */
	@ApiOperation("查询全部课程信息")
	@GetMapping("/all/course")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<List<SimpleDataRes>> allCourse() {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		List<String> courseIds = courseMapper.selectByAuth(authContext);
		if (CollectionUtils.isEmpty(courseIds)) {
			return new BaseRsp<>(Lists.newArrayList());
		}

		List<Course> courses = courseMapper.selectBatchIds(courseIds);

		List<SimpleDataRes> courseRes = StreamApiUtils.map(courses,
				course -> BeanUtils.copy(course, SimpleDataRes.class));
		return new BaseRsp<>(courseRes);
	}

	/**
	 * 导出课程
	 *
	 * @param courseIds 课程id列表
	 * @param response  响应
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("后台课程数据导出")
	@GetMapping("/batch_dumps")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<Boolean> exportCourse(
			@ApiParam(name = "ids",
					value = "课程编号") @RequestParam("ids") @NotEmpty List<String> courseIds,
			HttpServletResponse response) {
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		String zipPath = courseService.dumpCourse(courseIds, authContext);
		FileUtil.setResponse(new File(zipPath), true, response);
		return new BaseRsp(Boolean.TRUE);
	}

	/**
	 * 导入课程
	 *
	 * @param loadCourseReq 加载课程请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("后台课程数据导入")
	@PostMapping("/batch_loads")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<Boolean> importCourse(@Validated LoadCourseReq loadCourseReq) {
		FileUtil.validateSuffix(loadCourseReq.getFile(), FileSunffixConstant.ZIP.getDesc());

		AttachmentBo attachmentBo = attachmentService.uploadFile(AttachmentPath.OTHER.getCode(),
				false, loadCourseReq.getFile(), true);
		String realPath = utilService.getRealPath(attachmentBo.getFilePath());

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		Boolean result = courseService.loadCourse(realPath, authContext);
		return new BaseRsp(result);

	}

}
