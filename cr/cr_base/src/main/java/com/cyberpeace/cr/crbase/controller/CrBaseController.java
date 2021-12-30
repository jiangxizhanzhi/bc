package com.cyberpeace.cr.crbase.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.service.CrBaseService;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingStatisticsPage;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingStatisticsRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "靶场公用接口")
@RestController
@Slf4j
@RequestMapping("/cr/common")
@RequiresAuthentication
public class CrBaseController {

	@Autowired
	private CrBaseService crBaseService;

	@ApiOperation("个人中心训练统计")
	@GetMapping("/training_statistics")
	public BaseRsp<TrainingStatisticsRsp>
			getTrainingStatistics(@ApiParam @Validated PageReqBase req) {

		TrainingStatisticsPage page = new TrainingStatisticsPage(req);

		String userId = UserInfoContext.getUserContext().getUserId();
		TrainingStatisticsRsp rsp = crBaseService.getTrainingStatistics(userId, page);

		return new BaseRsp<>(rsp);
	}

}
