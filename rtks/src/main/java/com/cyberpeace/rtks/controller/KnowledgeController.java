package com.cyberpeace.rtks.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.rtks.controller.req.KnowledgeAddReq;
import com.cyberpeace.rtks.controller.req.KnowledgeListReq;
import com.cyberpeace.rtks.res.KnowledgeRes;
import com.cyberpeace.rtks.controller.res.RelatedCourse;
import com.cyberpeace.rtks.dao.entity.CourseRelated;
import com.cyberpeace.rtks.dao.entity.Knowledge;
import com.cyberpeace.rtks.service.CourseRelatedService;
import com.cyberpeace.rtks.service.KnowledgeService;
import com.cyberpeace.rtks.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "RTKS-知识点管理")
@RestController
@RequestMapping("/rtks/knowledge")
@RequiresAuthentication
public class KnowledgeController {

	@Autowired
	private KnowledgeService knowledgeService;

	@Autowired
	private CourseRelatedService courseRelatedService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private RemoteLogService remoteLogService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@ApiOperation("查询单条")
	@GetMapping()
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<KnowledgeRes> getKnowledge(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		// ApiParam中的name和java参数String id的两个id必须同名
		KnowledgeRes knowledge = knowledgeService.getKnowledgeByResourceId(resource_id);

		return new BaseRsp<KnowledgeRes>(knowledge);
	}

	@ApiOperation("新增单条")
	@PostMapping()
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<String> addKnowledge(@RequestBody @Validated KnowledgeAddReq knowledgeAddReq) {

		// 数据校验
		knowledgeAddReq.validate();

		Knowledge knowledge = new Knowledge();
		BeanUtils.copyProperties(knowledgeAddReq, knowledge);
		knowledge.setCourses(JsonUtil.toJsonString(knowledgeAddReq.getCourseList()));

		knowledgeService.validate(knowledge);
		List<RelatedCourse> relatedCourseList = knowledgeAddReq.getCourseList();

		String courses = courseRelatedService.saveCourseRelatedList(relatedCourseList,
				knowledge.getResourceId(), CourseRelated.RELATED_TYPE_KNOWLEDGE);
		knowledge.setCourses(courses);
		knowledgeService.save(knowledge);

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RTKS,
				OperationType.CREATE, knowledge.getResourceId(),
				LogContent.CR_FORMAT.format(OperationType.CREATE, LogContent.KNOWLEDGE.getMsg(),
						knowledge.getResourceId()));

		return new BaseRsp<>();
	}

	@ApiOperation("修改单条")
	@PutMapping()
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<String>
			modifyKnowledge(@RequestBody @Validated KnowledgeAddReq knowledgeAddReq) {

		// 数据校验
		knowledgeAddReq.validate();

		Knowledge knowledge = new Knowledge();
		BeanUtils.copyProperties(knowledgeAddReq, knowledge);
		knowledge.setCourses(JsonUtil.toJsonString(knowledgeAddReq.getCourseList()));

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				knowledgeService.validate(knowledge);
				List<RelatedCourse> relatedCourseList = knowledgeAddReq.getCourseList();
				String courses = courseRelatedService.saveCourseRelatedList(relatedCourseList,
						knowledge.getResourceId(), CourseRelated.RELATED_TYPE_KNOWLEDGE);
				knowledge.setCourses(courses);
				knowledgeService.updateById(knowledge);
				roleService.updateBatchByModifyRelatedIds(knowledgeAddReq.getResourceId(),
						"knowledge");
			}
		});

		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RTKS,
				OperationType.UPDATE, knowledge.getResourceId(),
				LogContent.CR_FORMAT.format(OperationType.UPDATE, LogContent.KNOWLEDGE.getMsg(),
						knowledge.getResourceId()));

		return new BaseRsp<>();
	}

	@ApiOperation("删除")
	@DeleteMapping()
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<String> deleteKnowledge(@RequestBody @Validated DeleteReq deleteReq) {

		List<Knowledge> knowledgeList = knowledgeService.listByIds(deleteReq.getIds());

		knowledgeService.removeByIds(deleteReq.getIds());
		courseRelatedService.deleteCourseRelatedByRelatedIds(deleteReq.getIds(),
				CourseRelated.RELATED_TYPE_KNOWLEDGE);
		roleService.updateBatchByDeleteRelatedIds(deleteReq, "knowledge");

		List<String> columns = StreamApiUtils.getColumn(knowledgeList, Knowledge::getContent);
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RTKS,
				OperationType.DELETE, LogContent.BATCH_DELETE_FORMAT.format(LogContent.KNOWLEDGE.getMsg()),
				LogContent.CR_FORMAT.format(OperationType.DELETE, LogContent.KNOWLEDGE.getMsg(),
						columns.toString()));

		return new BaseRsp<>();
	}

	@ApiOperation("分页查询")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<ListRsp<KnowledgeRes>> list(@Validated KnowledgeListReq knowledgeListReq) {
		ListRsp<KnowledgeRes> data = knowledgeService.page(knowledgeListReq);
		return new BaseRsp<>(data);
	}
}
