package com.cyberpeace.cr.crbase.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.crbase.controller.rsp.constants.MissionCategoryRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.service.CrBaseService;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
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

	@Autowired
	private CrConstantsService crConstantsService;

	@ApiOperation("个人中心训练统计")
	@GetMapping("/training_statistics")
	public BaseRsp<TrainingStatisticsRsp>
			getTrainingStatistics(@ApiParam @Validated PageReqBase req) {

		TrainingStatisticsPage page = new TrainingStatisticsPage(req);

		String userId = UserInfoContext.getUserContext().getUserId();
		TrainingStatisticsRsp rsp = crBaseService.getTrainingStatistics(userId, page);

		return new BaseRsp<>(rsp);
	}

	@ApiOperation("获取常量配置")
	@GetMapping("/constants_config")
	public BaseRsp getConstantsConfig(@ApiParam(name = "type", value = "类型") Integer type) {
		List<CrConstantsConfig> constants = crConstantsService.getByType(type);
		boolean isEn = CommonContext.getLanguage().equals(WebConstant.LANGUAGE_EN);
		// 任务类型
		if (type.equals(CrConstantsConfig.ConstantsType.MISSION_CATEGORY.getCode())) {
			List<MissionCategoryRsp> rsp = StreamApiUtils.map(constants, constant -> isEn
					? new MissionCategoryRsp(constant.getResourceId(), constant.getEnName())
					: new MissionCategoryRsp(constant.getResourceId(), constant.getCnName()));

			return new BaseRsp(rsp);
		} else {
			List<CodeNameRsp> rsp = StreamApiUtils.map(constants,
					constant -> isEn ? new CodeNameRsp(constant.getCode(), constant.getEnName())
							: new CodeNameRsp(constant.getCode(), constant.getCnName()));
			return new BaseRsp(rsp);
		}
	}

}
