package com.cyberpeace.oj.person.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.person.dao.entity.*;
import com.cyberpeace.oj.person.service.PersonCenterService;
import com.cyberpeace.oj.person.service.bo.CollectionPracticeBo;
import com.cyberpeace.oj.practice.controller.res.PracticeWrongRes;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.bo.WebOperationBo;
import com.cyberpeace.oj.practice.service.bo.WebTheoryBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Api(tags = "oj-前台个人中心api")
@RestController
@RequestMapping("/oj/person")
@RequiresAuthentication
public class PersonController {

	private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);

	@Autowired
	private PersonCenterService personCenterService;

	@Autowired
	private PracticeTheoryService practiceTheoryService;

	@Autowired
	private PracticeOperationService practiceOperationService;

	@Autowired
	RemoteUserService remoteUserService;

	@ApiOperation("当前用户所有评论")
	@GetMapping("/comments")
	@RequiresPermissions(MenuConstant.WEB_PERSON_CENTER)
	public BaseRsp<ListRsp<PersonComment>> listComments(@ApiParam @Validated PageReqBase pageReq) {
		Page page = pageReq.toPageParam();
		String userId = UserInfoContext.getUserContext().getUserId();
		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		String parent = "";
		IPage<PersonComment> comments = personCenterService.pageComment(page, userId, parent,
				authContext);
		return new BaseRsp<>(PageUtil.getListResult(comments));
	}

	@ApiOperation("当前用户所有回复")
	@GetMapping("/replies")
	@RequiresPermissions(MenuConstant.WEB_PERSON_CENTER)
	public BaseRsp<ListRsp<PersonComment>> listReplies(@ApiParam @Validated PageReqBase pageReq) {
		Page page = pageReq.toPageParam();
		String userId = UserInfoContext.getUserContext().getUserId();
		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		String parent = "parent";
		IPage<PersonComment> comments = personCenterService.pageComment(page, userId, parent,
				authContext);
		return new BaseRsp<>(PageUtil.getListResult(comments));
	}

	@ApiOperation("当前用户所有笔记")
	@GetMapping("/notes")
	@RequiresPermissions(MenuConstant.WEB_PERSON_CENTER)
	public BaseRsp<ListRsp<PersonNote>> listNotes(@ApiParam @Validated PageReqBase pageReq) {
		Page page = pageReq.toPageParam();
		String userId = UserInfoContext.getUserContext().getUserId();
		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		IPage<PersonNote> comments = personCenterService.pageNotes(page, userId, authContext);
		return new BaseRsp<>(PageUtil.getListResult(comments));
	}

	@ApiOperation("当前用户所有收藏")
	@GetMapping("/collections")
	@RequiresPermissions(MenuConstant.WEB_PERSON_CENTER)
	public BaseRsp<ListRsp<PersonCollection>> listCollections(
			@ApiParam @Validated PageReqBase pageReq, @ApiParam @Validated String type) {
		Page page = pageReq.toPageParam();
		String userId = UserInfoContext.getUserContext().getUserId();

		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);

		IPage<PersonCollection> collections = personCenterService.pageCollections(page, userId,
				type, authContext);
		return new BaseRsp<>(PageUtil.getListResult(collections));
	}

	@ApiOperation("当前用户学习课程和练习记录")
	@GetMapping("/study_records")
	@RequiresPermissions(MenuConstant.WEB_PERSON_CENTER)
	public BaseRsp<StudyRecord> courseAndPracticeRecords() {
		String userId = UserInfoContext.getUserContext().getUserId();
		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		return personCenterService.studyRecords(userId, authContext);
	}

	@ApiOperation("当前用户30天内课程、实验报告数量")
	@GetMapping("/study_period_records")
	@RequiresPermissions(
			value = { MenuConstant.WEB_PERSON_CENTER, MenuConstant.CMS_COURSE_STATISTICS },
			logical = Logical.OR)
	public BaseRsp<BrokenLine> courseAndPracticeRecordsByMonth(
			@RequestParam(value = "user_id", required = false) String userId,
			@RequestParam(value = "isWeb", required = false) Boolean isWeb) {
		Date endTime = new Date();
		Date startTime = DateUtils.addDays(endTime, -CommonConstant.THIRTY);
		if (StringUtils.isEmpty(userId)){
			userId = UserInfoContext.getUserContext().getUserId();
		} else {
			UserInfo userInfo = remoteUserService.getById(userId);
			UserInfoContext.setUserContext(userInfo);
		}
		if (null == isWeb) {
			isWeb = true;
		}
		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				isWeb);
		return personCenterService.studyPeriodRecords(userId, startTime, endTime, authContext);
	}

	@ApiOperation("查询收藏习题列表")
	@GetMapping("/my_all_collection_practice")
	@RequiresPermissions(MenuConstant.WEB_PERSON_CENTER)
	public BaseRsp<List<CollectionPracticeBo>> allCollectionPractice() {

		// 查询收藏的理论习题
		List<CollectionPracticeBo> theoryCollection = personCenterService.selectCollectionPractice(
				UserInfoContext.getUserContext().getUserId(),
				ResourceCollection.BusinessType.THEORY.getCode());

		// 查询收藏的实操习题
		List<CollectionPracticeBo> operationCollection = personCenterService
				.selectCollectionPractice(UserInfoContext.getUserContext().getUserId(),
						ResourceCollection.BusinessType.OPERATION.getCode());

		theoryCollection.addAll(operationCollection);
		return new BaseRsp<>(theoryCollection);

	}

	@ApiOperation("收藏理论题详情页")
	@GetMapping("/collection_theory_practice_detail")
	@RequiresPermissions(MenuConstant.WEB_PERSON_CENTER)
	public BaseRsp<WebTheoryBo> collectionTheoryRetrieve(@RequestParam(value = "resource_id") String resourceId) {
		WebTheoryBo theoryBo = practiceTheoryService.selectById(resourceId,
				UserInfoContext.getUserContext().getUserId(), Boolean.TRUE);
		return new BaseRsp<>(theoryBo);
	}

	@ApiOperation("收藏实操题详情页")
	@GetMapping("/collection_operate_practice_detail")
	@RequiresPermissions(MenuConstant.WEB_PERSON_CENTER)
	public BaseRsp<WebOperationBo> collectionOperateRetrieve(@RequestParam(value = "resource_id") String resourceId) {
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);
		WebOperationBo theoryBo = practiceOperationService.selectById(resourceId, authContext,
				Boolean.TRUE);
		return new BaseRsp<>(theoryBo);
	}

	@ApiOperation("查询错题集列表")
	@GetMapping("/practice_wrong_set")
	public BaseRsp<ListRsp<PracticeWrongRes>> getPracticeWringSet(PageReqBase pageReqBase) {
		if (pageReqBase.getSort() == null) {
			pageReqBase.setSort("-submitTime");
		}
		Page page = pageReqBase.toPageParam();
		UserInfo userInfo = UserInfoContext.getUserContext();
		List<PracticeWrongRes> practiceWrongResList = personCenterService.getPracticeWrongSetByPerson(userInfo);
		return new BaseRsp<>(PageUtil.getListResult(practiceWrongResList, page, practiceWrongResList.size()));
	}
}
