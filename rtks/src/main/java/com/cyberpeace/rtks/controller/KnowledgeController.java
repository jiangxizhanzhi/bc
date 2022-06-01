package com.cyberpeace.rtks.controller;

import java.util.List;

import com.cyberpeace.common.constant.MenuConstant;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.rtks.controller.req.KnowledgeAddReq;
import com.cyberpeace.rtks.controller.req.KnowledgeListReq;
import com.cyberpeace.rtks.controller.res.KnowledgeRes;
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

		knowledgeService.validate(knowledge);
		List<RelatedCourse> relatedCourseList = knowledgeAddReq.getCourseList();
		String courses = courseRelatedService.saveCourseRelatedList(relatedCourseList,
				knowledge.getResourceId(), CourseRelated.RELATED_TYPE_KNOWLEDGE);
		knowledge.setCourses(courses);
		roleService.updateBatchByModifyRelatedIds(knowledgeAddReq.getResourceId(), "knowledge");

		knowledgeService.updateById(knowledge);

		return new BaseRsp<>();
	}

	@ApiOperation("删除")
	@DeleteMapping()
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<String> deleteKnowledge(@RequestBody @Validated DeleteReq deleteReq) {
		knowledgeService.removeByIds(deleteReq.getIds());
		courseRelatedService.deleteCourseRelatedByRelatedIds(deleteReq.getIds(),
				CourseRelated.RELATED_TYPE_KNOWLEDGE);
		roleService.updateBatchByDeleteRelatedIds(deleteReq, "knowledge");
		return new BaseRsp<>();
	}

	@ApiOperation("分页查询")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<List<KnowledgeRes>> list(@Validated KnowledgeListReq knowledgeListReq) {
		ListRsp<KnowledgeRes> data = knowledgeService.page(knowledgeListReq);

		return new BaseRsp(data);
	}
}
