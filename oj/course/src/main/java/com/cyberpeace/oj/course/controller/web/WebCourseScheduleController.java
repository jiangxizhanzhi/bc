package com.cyberpeace.oj.course.controller.web;

import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.oj.course.service.bo.TimeScheduleBo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.course.service.ClassroomService;

import io.swagger.annotations.Api;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Api(tags = "oj-前台课程计划相关")
@RestController
@RequestMapping("/oj/class/schedule")
@RequiresAuthentication
public class WebCourseScheduleController {

	@Autowired
	private ClassroomService classroomService;

	@ApiOperation("查询课堂信息")
	@GetMapping("/list")
	public BaseRsp<TimeScheduleBo>
			listCourseSchedule(@ApiParam(name = "month",
					value = "月份 格式xxxx-xx") @RequestParam("month") @NotBlank String month) {

		Date startOfDay = TimeUtil.getStartOfDay(TimeUtil.toDate2(TimeUtil.getHeadOrTailDayOfSepecificMonth(month, true)));
		Date endOfDay = TimeUtil.getEndOfDay(TimeUtil.toDate2(TimeUtil.getHeadOrTailDayOfSepecificMonth(month, false)));
		List<TimeScheduleBo> boList = classroomService.listByTime(startOfDay, endOfDay);

		return new BaseRsp(boList);
	}

}
