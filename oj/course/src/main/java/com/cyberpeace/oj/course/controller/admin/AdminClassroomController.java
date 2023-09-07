package com.cyberpeace.oj.course.controller.admin;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.oj.course.controller.req.*;
import com.cyberpeace.oj.course.dao.context.ClassRoomListQuery;
import com.cyberpeace.oj.course.dao.context.ClassRoomPageQuery;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.dao.entity.TrainPlan;
import com.cyberpeace.oj.course.service.ClassroomService;
import com.cyberpeace.oj.course.service.ClassroomSignService;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.course.service.impl.ClassroomValidateHandler;
import com.cyberpeace.oj.ojbase.service.bo.check.PrepareNoteReq;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "oj-后台课程表api")
@RestController
@RequestMapping("/oj/admin/course/classroom")
@RequiresAuthentication
@Slf4j
public class AdminClassroomController {

	@Autowired
	private ClassroomService classroomService;

	@Autowired
	private RemoteOrganizationService remoteOrganizationService;

	@Autowired
	private ClassroomSignService classroomSignService;

	@Autowired
	private ClassroomValidateHandler classroomValidateHandler;

	@Autowired
	private CourseLessonService courseLessonService;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private UtilService utilService;

	@ApiOperation("后台课程表查询")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
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

		// 非超级管理员仅查看自己创建的课程表
		if (!authContext.getSuperAdmin()) {
			roomListQuery.setUserId(authContext.getUserId());
		}

		// 数据查询
		List<ClassroomBo> classroomBos = classroomService.selectList(roomListQuery, authContext);

		return new BaseRsp<>(classroomBos);
	}

	@ApiOperation("后台课程表信息批量删除")
	@DeleteMapping("delete_batch")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<Integer> batchDeleteClassroom(@RequestBody @Validated DeleteReq delReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		for (String id : delReq.getIds()) {
			Integer result = classroomService.deleteClassroom(id, authContext);
		}

		return new BaseRsp<>();
	}

	@ApiOperation("后台课程表分页查询")
	@GetMapping("/list/page")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<ListRsp<ClassroomBo>> page(@Validated ClassroomPageReq classroomPageReq) {
		classroomPageReq.setSort("start_time");

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		if (!authContext.getSuperAdmin() && StringUtils.isBlank(authContext.getOrganization())) {
			return new BaseRsp<>();
		}

		ClassRoomPageQuery roomListQuery = BeanUtils.copy(classroomPageReq,
				ClassRoomPageQuery.class);

		// 查询数据组装
		if (StringUtils.isNotBlank(classroomPageReq.getOrganizationId())) {
			List<String> orgIds = remoteOrganizationService
					.childrenList(classroomPageReq.getOrganizationId(), true);
			roomListQuery.setChildrenOrg(orgIds);
		}

		// 非超级管理员仅查看自己创建的课程表
		if (!authContext.getSuperAdmin()) {
			roomListQuery.setUserId(authContext.getUserId());
		}

		// 数据查询
		IPage<ClassroomBo> iPage = classroomService.selectPage(roomListQuery, authContext);

		return new BaseRsp<>(PageUtil.getListResult(iPage));
	}

	@ApiOperation("后台课程表创建")
	@PostMapping("")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<Classroom>
			createClassRoom(@RequestBody @Validated(Create.class) ClassroomReq classroomReq) {

		//约定临时计划的id需要转换
		if (TrainPlan.TASK_SET_ID.equals(classroomReq.getTrainPlanId())) {
			classroomReq.setTrainPlanId("");
		}
		// 基本数据设置
		Classroom classroom = BeanUtils.copy(classroomReq, Classroom.class);
		classroom.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);

		Classroom insertResult = classroomService.create(classroom, authContext);

		return new BaseRsp(insertResult);
	}

	@ApiOperation("后台课程表信息修改")
	@PatchMapping("")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<Boolean>
			updateClassRoom(@RequestBody @Validated(Update.class) ClassroomReq classroomReq) {

		// 基本数据设置
		Classroom classroom = BeanUtils.copy(classroomReq, Classroom.class);
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		Boolean result = classroomService.updateClassroom(classroom, authContext);
		return new BaseRsp(result);
	}

	@ApiOperation("后台课程表信息删除")
	@DeleteMapping("")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<Integer> deleteClassroom(@RequestBody @Validated ClassroomDeleteReq delReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		Integer result = classroomService.deleteClassroom(delReq.getResourceId(), authContext);
		return new BaseRsp<>(result);
	}

	@ApiOperation("课堂监控获取用户签到列表")
	@GetMapping("/classroom_monitor")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<ListRsp<ClassroomMonitorBo>> classroomMonitor(ClassroomMonitorReq monitorReq) {

		Page page = monitorReq.toPageParam();
		ListRsp<ClassroomMonitorBo> classroomMonitorBoList = classroomService.selectSignList(page,
				monitorReq.getClassroomId());
		return new BaseRsp(classroomMonitorBoList);

	}

	@ApiOperation("进行远程协助")
	@GetMapping("/to_help")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "resource_id", value = "课堂编号", dataTypeClass = String.class),
			@ApiImplicitParam(name = "user_id", value = "用户编号", dataTypeClass = String.class) })
	public BaseRsp<Boolean> toHelp(@RequestParam("resource_id") String classroomId,
			@RequestParam("user_id") String userId) {

		classroomSignService.toHelp(classroomId, userId);
		return new BaseRsp(Boolean.TRUE);
	}

	@ApiOperation("结束远程协助")
	@GetMapping("/end_help")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "resource_id", value = "课堂编号", dataTypeClass = String.class),
			@ApiImplicitParam(name = "user_id", value = "用户编号", dataTypeClass = String.class) })
	public BaseRsp<Boolean> endHelp(@RequestParam("resource_id") String classroomId,
			@RequestParam("user_id") String userId) {

		classroomSignService.endHelp(classroomId, userId);
		return new BaseRsp(Boolean.TRUE);
	}

	@ApiOperation("获取远程协助地址")
	@GetMapping("/get_help")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "resource_id", value = "课堂编号", dataTypeClass = String.class),
			@ApiImplicitParam(name = "user_id", value = "用户编号", dataTypeClass = String.class) })
	public BaseRsp<String> getHelpUrl(@RequestParam("resource_id") String classroomId,
			@RequestParam("user_id") String userId) {
		String helpUrl = classroomSignService.getHelpUrl(classroomId, userId);
		return new BaseRsp<>(helpUrl);
	}

	@ApiOperation("查询课堂实验报告列表")
	@GetMapping("/classroom_report")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<ListRsp<ClassroomReportBo>> classroomReport(ClassroomMonitorReq reportReq) {

		Page page = reportReq.toPageParam();
		PageReportBo pageReportBo = classroomService
				.selectClassroomReport(reportReq.getClassroomId(), reportReq.getPattern(), page);
		return new BaseRsp(
				PageUtil.getListResult(pageReportBo.getData(), page, pageReportBo.getTotal()));
	}

	@ApiOperation("课堂学习统计")
	@GetMapping("/classroom_statistic")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<ClassroomStatisticBo>
			classroomStatistic(@RequestParam("classroom_id") String classroomId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);

		ClassroomStatisticBo statisticBo = classroomService.classroomStatistic(classroomId,
				authContext);
		return new BaseRsp<>(statisticBo);
	}

	@ApiOperation("课堂练习统计")
	@GetMapping("/practice-statistic")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<ClassroomPracticeBo>
			classroomPracticeStatistic(@Validated ClassroomPracticeReq classroomPracticeReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);

		// 操作权限校验
		classroomValidateHandler.validateLessonPracticeAuth(classroomPracticeReq.getClassroomId(),
				authContext);

		Page page = classroomPracticeReq.toPageParam();

		ClassroomPracticeContext classroomPracticeContext = BeanUtils.copy(classroomPracticeReq,
				ClassroomPracticeContext.class);
		ClassroomPracticeBo classroomPracticeBo = classroomService
				.practiceStatistic(classroomPracticeContext, page);

		return new BaseRsp<>(classroomPracticeBo);
	}

	@ApiOperation("获取课堂备课笔记")
	@GetMapping("/prepare/note")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<String>
			getPrepareNote(@ApiParam(name = "resource_id", value = "课堂id") String resource_id) {
		CrCommonValidator.validateId(resource_id);
		String res = classroomService.getPrepareNote(resource_id);
		return new BaseRsp<>(res);
	}

	@ApiOperation("修改课堂备课笔记")
	@PatchMapping("/prepare/note")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<String> modifyPrepareNote(@Validated @RequestBody PrepareNoteReq req) {
		CrCommonValidator.validateId(req.getClassRoomId());
		classroomService.modifyPrepareNote(req.getClassRoomId(), req.getNote());
		return new BaseRsp<>();
	}

	@ApiOperation("课堂练习详情")
	@GetMapping("/practice-detail")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<ClassroomPracticeDetailBo> classroomPracticeDetail(
			@RequestParam("classroom_id") String classroomId,
			@RequestParam("lesson_id") String lessonId, @RequestParam("user_id") String userId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);

		// 操作权限校验
		classroomValidateHandler.validateLessonPracticeAuth(classroomId, authContext);

		ClassroomPracticeDetailBo classroomPracticeDetailBo = classroomService
				.practiceDetail(classroomId, lessonId, userId);

		return new BaseRsp<>(classroomPracticeDetailBo);
	}

	@ApiOperation("课后练习评分")
	@PostMapping("/practice/mark")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<Boolean>
			markLessonPractice(@RequestBody @Validated MarkLessonPracticeReq practiceReq) {
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);

		// 操作权限校验
		classroomValidateHandler.validateLessonPracticeAuth(practiceReq.getClassroomId(),
				authContext);

		Boolean result = courseLessonService.markLessonPractice(practiceReq.getLessonId(),
				practiceReq.getUserId(), practiceReq.getLevel(), authContext);
		return new BaseRsp<>(result);
	}

	@ApiOperation("课后练习下发")
	@PatchMapping("/practice/batch-auth")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<Boolean> batchAuthLessonPractice(
			@RequestBody @Validated BatchAuthLessonPracticeReq practiceReq) {
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);

		// 操作权限校验
		classroomValidateHandler.validateLessonPracticeAuth(practiceReq.getClassroomId(),
				authContext);

		Boolean result = courseLessonService.releaseLessonPractice(practiceReq.getLessonId(),
				practiceReq.getOrganizationId(), authContext);
		return new BaseRsp<>(result);
	}

	@ApiOperation("训练计划导入")
	@PostMapping("/train_plan/import")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<Boolean> importTrainPlan(@RequestParam("file") MultipartFile excelFile,
			@RequestParam("cover") Boolean cover) {

		FileUtil.validateSuffix(excelFile, FileSunffixConstant.XLSX.getDesc(),
				FileSunffixConstant.XLS.getDesc());

		// 文件存储
		AttachmentBo uploadFile = attachmentService.uploadFile(AttachmentPath.OTHER.getCode(), true,
				excelFile, true);
		String realPath = utilService.getRealPath(uploadFile.getFilePath());

		try {
			classroomService.importTrainPlan(realPath, UserInfoContext.getUserContext(), cover);
		} finally {
			try {
				FileUtil.deleteDir(realPath, CommonConstant.TRUE);
			} catch (Exception e) {
				// 不影响导入逻辑就行
				log.error("Import train plan delete file failed! file_url=[{}], catch error:",
						realPath, e);
			}
		}

		return new BaseRsp(Boolean.TRUE);
	}

	@ApiOperation("训练计划导出")
	@GetMapping("/train_plan/export")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<Boolean> exportTrainPlan(HttpServletResponse response) {

		String filePath = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID()
				+ CommonConstant.PERIOD + FileSunffixConstant.XLSX.getDesc();
		classroomService.exportTrainPlan(UserInfoContext.getUserContext(), filePath);
		FileUtil.setResponse(new File(filePath), true, response);

		return new BaseRsp<>(Boolean.TRUE);
	}

	@ApiOperation("实验报告导出")
	@GetMapping("/report/export")
	public void exportReport(HttpServletResponse httpServletResponse,@RequestParam("classroom_id") String classroomId){
		String destPdfPath = classroomService.exportReport(classroomId);
		FileUtil.setResponse(new File(destPdfPath), true, httpServletResponse);
	}

}
