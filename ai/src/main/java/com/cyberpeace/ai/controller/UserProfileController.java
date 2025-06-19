package com.cyberpeace.ai.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.ai.model.vo.UserProfileReportListVO;
import com.cyberpeace.ai.model.vo.UserProfileReportVO;
import com.cyberpeace.ai.service.UserProfileReportService;
import com.cyberpeace.ai.service.UserProfileService;
import com.cyberpeace.common.model.BaseRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "用户画像报告接口")
@RestController
@RequestMapping("/ai/user_profile")
@RequiresAuthentication
public class UserProfileController {

	@Autowired
	private UserProfileReportService userProfileReportService;

	@Autowired
	private UserProfileService userProfileService;

	@ApiOperation("根据维度获取用户画像报告数据")
	@GetMapping("/info")
	public BaseRsp<UserProfileReportVO> getUserProfileReport(
			@ApiParam(value = "维度", required = true) @RequestParam String dimension) {

		UserProfileReportVO vo = userProfileService.getUserProfileData(dimension);

		return new BaseRsp<>(vo);
	}

	@ApiOperation("保存用户画像报告")
	@PostMapping("/report")
	public BaseRsp<Boolean>
			saveUserProfileReport(@RequestBody UserProfileReportVO userProfileReportVO) {
		Boolean result = userProfileReportService.save(userProfileReportVO);
		return new BaseRsp<>(result);
	}

	@ApiOperation("获取用户画像报告列表")
	@GetMapping("/report/list")
	public BaseRsp<List<UserProfileReportListVO>> getUserProfileReportList() {
		List<UserProfileReportListVO> list = userProfileReportService.getReportList();
		return new BaseRsp<>(list);
	}

	@ApiOperation("根据报告ID获取用户画像报告详情")
	@GetMapping("/report/detail")
	public BaseRsp<UserProfileReportVO> getUserProfileReportDetail(
			@ApiParam(value = "报告ID", required = true) @RequestParam String reportId) {
		UserProfileReportVO vo = userProfileReportService.getReportDetail(reportId);
		return new BaseRsp<>(vo);
	}

}