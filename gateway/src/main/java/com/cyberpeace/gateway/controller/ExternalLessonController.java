package com.cyberpeace.gateway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.annoations.AuthKeyUser;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.gateway.controller.req.ExternalLessonReq;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.bo.AdminLessonListBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "外部接口-课时相关api")
@Slf4j
@AuthKeyUser
@RestController
@RequestMapping("/gateway/lesson")
public class ExternalLessonController {

	@Autowired
	private CourseLessonService courseLessonService;

	@ApiOperation("查询课时")
	@PostMapping("/list")
	public BaseRsp<List<AdminLessonListBo>> getLessonsByIds(@RequestBody ExternalLessonReq req) {
		List<AdminLessonListBo> res = courseLessonService.listExternal(req.getIds(),
				req.getIsPublic());
		return new BaseRsp<>(res);
	}
}
