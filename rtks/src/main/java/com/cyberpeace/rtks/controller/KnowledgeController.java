package com.cyberpeace.rtks.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.rtks.controller.req.DeleteReq;
import com.cyberpeace.rtks.controller.req.KnowledgeAddReq;
import com.cyberpeace.rtks.controller.req.KnowledgeListReq;
import com.cyberpeace.rtks.controller.res.KnowledgeRes;
import com.cyberpeace.rtks.dao.entity.CourseRelated;
import com.cyberpeace.rtks.dao.entity.Knowledge;
import com.cyberpeace.rtks.dao.entity.RelatedCourse;
import com.cyberpeace.rtks.service.CourseRelatedService;
import com.cyberpeace.rtks.service.KnowledgeService;

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

	@ApiOperation("查询单条")
	@GetMapping()
	public BaseRsp<KnowledgeRes> getKnowledge(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		// ApiParam中的name和java参数String id的两个id必须同名
		KnowledgeRes knowledge = knowledgeService.getKnowledgeByResourceId(resource_id);

		return new BaseRsp<KnowledgeRes>(knowledge);
	}

	@ApiOperation("新增单条")
	@PostMapping()
	public BaseRsp<String> addKnowledge(@RequestBody @Validated KnowledgeAddReq knowledgeAddReq) {
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
	public BaseRsp<String>
			modifyKnowledge(@RequestBody @Validated KnowledgeAddReq knowledgeAddReq) {
		Knowledge knowledge = new Knowledge();
		BeanUtils.copyProperties(knowledgeAddReq, knowledge);
		knowledge.setCourses(JsonUtil.toJsonString(knowledgeAddReq.getCourseList()));

		knowledgeService.validate(knowledge);
		List<RelatedCourse> relatedCourseList = knowledgeAddReq.getCourseList();
		String courses = courseRelatedService.saveCourseRelatedList(relatedCourseList,
				knowledge.getResourceId(), CourseRelated.RELATED_TYPE_KNOWLEDGE);
		knowledge.setCourses(courses);

		knowledgeService.updateById(knowledge);

		return new BaseRsp<>();
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> deleteKnowledge(@RequestBody @Validated DeleteReq deleteReq) {
		knowledgeService.removeByIds(deleteReq.getIds());
		courseRelatedService.deleteCourseRelatedByRelatedIds(deleteReq.getIds(),
				CourseRelated.RELATED_TYPE_KNOWLEDGE);
		return new BaseRsp<>();
	}

	@ApiOperation("分页查询")
	@GetMapping("/list")
	public BaseRsp<List<KnowledgeRes>> list(@Validated KnowledgeListReq knowledgeListReq) {
		ListRsp<KnowledgeRes> data = knowledgeService.page(knowledgeListReq);

		return new BaseRsp(data);
	}
}
