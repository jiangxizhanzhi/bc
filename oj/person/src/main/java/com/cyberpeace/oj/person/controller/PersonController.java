package com.cyberpeace.oj.person.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.cyberpeace.oj.person.dao.entity.*;
import com.cyberpeace.oj.person.service.PersonCenterService;
import com.cyberpeace.oj.person.service.bo.CollectionPracticeBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Api(tags = "oj-前台个人中心api")
@RestController
@RequestMapping("/oj/person")
@RequiresAuthentication
public class PersonController {

	private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);

	@Autowired
	private PersonCenterService personCommentService;

	@ApiOperation("当前用户所有评论")
	@GetMapping("/comments")
	public BaseRsp<ListRsp<PersonComment>> listComments(@ApiParam @Validated PageReqBase pageReq) {
		Page page = pageReq.toPageParam();
		String userId = UserInfoContext.getUserContext().getUserId();
		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		String parent = "";
		IPage<PersonComment> comments = personCommentService.pageComment(page, userId, parent,
				authContext);
		return new BaseRsp<>(PageUtil.getListResult(comments));
	}

	@ApiOperation("当前用户所有回复")
	@GetMapping("/replies")
	public BaseRsp<ListRsp<PersonComment>> listReplies(@ApiParam @Validated PageReqBase pageReq) {
		Page page = pageReq.toPageParam();
		String userId = UserInfoContext.getUserContext().getUserId();
		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		String parent = "parent";
		IPage<PersonComment> comments = personCommentService.pageComment(page, userId, parent,
				authContext);
		return new BaseRsp<>(PageUtil.getListResult(comments));
	}

	@ApiOperation("当前用户所有笔记")
	@GetMapping("/notes")
	public BaseRsp<ListRsp<PersonNote>> listNotes(@ApiParam @Validated PageReqBase pageReq) {
		Page page = pageReq.toPageParam();
		String userId = UserInfoContext.getUserContext().getUserId();
		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		IPage<PersonNote> comments = personCommentService.pageNotes(page, userId, authContext);
		return new BaseRsp<>(PageUtil.getListResult(comments));
	}

	@ApiOperation("当前用户所有收藏")
	@GetMapping("/collections")
	public BaseRsp<ListRsp<PersonCollection>> listCollections(
			@ApiParam @Validated PageReqBase pageReq, @ApiParam @Validated String type) {
		Page page = pageReq.toPageParam();
		String userId = UserInfoContext.getUserContext().getUserId();

		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);

		IPage<PersonCollection> collections = personCommentService.pageCollections(page, userId,
				type, authContext);
		return new BaseRsp<>(PageUtil.getListResult(collections));
	}

	@ApiOperation("当前用户学习课程和练习记录")
	@GetMapping("/study_records")
	public BaseRsp<StudyRecord> courseAndPracticeRecords() {
		String userId = UserInfoContext.getUserContext().getUserId();
		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		return personCommentService.studyRecords(userId, authContext);
	}

	@ApiOperation("当前用户30天内课程、实验报告数量")
	@GetMapping("/study_period_records")
	public BaseRsp<BrokenLine> courseAndPracticeRecordsByMonth(@RequestParam(value = "user_id", required = false)String userId) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String endTime = format.format(date);
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.DAY_OF_MONTH, -30);
		String startTime = format.format(now.getTime());
		boolean startValidate = TimeUtil.isValidDate(startTime);
		boolean endValidate = TimeUtil.isValidDate(endTime);
		if (!startValidate || !endValidate) {
			throw new InvalidParamException("时间格式不正确！");
		}
		if (StringUtils.isEmpty(userId)){
			userId = UserInfoContext.getUserContext().getUserId();
		}
		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		return personCommentService.studyPeriodRecords(userId, TimeUtil.toDate1(startTime),
				TimeUtil.toDate1(endTime), authContext);
	}

	@ApiOperation("当前用户课程表")
	@GetMapping("/class_schedule")
	public BaseRsp<List<ListSchedule>> listSchedules(@ApiParam @Validated String type) {
		String userId = UserInfoContext.getUserContext().getUserId();

		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);

		List<ListSchedule> listSchedules = personCommentService.listSchedules(userId, type,
				authContext);
		return new BaseRsp<>(listSchedules);
	}

	@ApiOperation("查询收藏习题列表")
	@GetMapping("/my_all_collection_practice")
	public BaseRsp<List<CollectionPracticeBo>> allCollectionPractice() {

		// 查询收藏的理论习题
		List<CollectionPracticeBo> theoryCollection = personCommentService.selectCollectionPractice(
				UserInfoContext.getUserContext().getUserId(),
				ResourceCollection.BusinessType.THEORY.getCode());

		// 查询收藏的实操习题
		List<CollectionPracticeBo> operationCollection = personCommentService
				.selectCollectionPractice(UserInfoContext.getUserContext().getUserId(),
						ResourceCollection.BusinessType.OPERATION.getCode());

		theoryCollection.addAll(operationCollection);
		return new BaseRsp<>(theoryCollection);

	}
}
