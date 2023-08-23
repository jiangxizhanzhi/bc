package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.controller.rsp.CurrentTimeRsp;
import com.cyberpeace.cr.crbase.controller.rsp.TrainStatsOverviewRsp;
import com.cyberpeace.cr.crbase.controller.rsp.TrainUserTreeRsp;
import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.crbase.controller.rsp.constants.MissionCategoryRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.service.CrBaseService;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import com.cyberpeace.cr.crbase.service.TrainUserService;
import com.cyberpeace.cr.crbase.service.context.BarChartUnit;
import com.cyberpeace.cr.crbase.service.context.statistics.*;
import com.cyberpeace.cs.dao.entity.LocalScene;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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

	@Autowired
	private TrainUserService  trainUserService;

	@ApiOperation("所有练习的统计数据")
	@GetMapping("/training_statistics")
	public BaseRsp<TrainingStatisticsRsp>
			getTrainingStatistics(@ApiParam @Validated PageReqBase req) {

		TrainingStatisticsPage page = new TrainingStatisticsPage(req);

		String userId = UserInfoContext.getUserContext().getUserId();
		TrainingStatisticsRsp rsp = crBaseService.getTrainingStatistics(userId, page);

		return new BaseRsp<>(rsp);
	}

	@ApiOperation("累计练习总数")
	@GetMapping("/total")
	public BaseRsp<TrainingStatisticsRsp>
	total(@ApiParam @Validated PageReqBase req) {

		TrainingStatisticsPage page = new TrainingStatisticsPage(req);

		String userId = UserInfoContext.getUserContext().getUserId();
		TrainingStatisticsRsp rsp = crBaseService.getTrainingStatistics(userId, page);

		return new BaseRsp<>(rsp);
	}

	@ApiOperation("资产总数")
	@GetMapping("/training_statistics2")
	public BaseRsp<TrainingStatisticsRsp>
	getTrainingStatistics2(@ApiParam @Validated PageReqBase req) {

		TrainingStatisticsPage page = new TrainingStatisticsPage(req);

		String userId = UserInfoContext.getUserContext().getUserId();
		TrainingStatisticsRsp rsp = crBaseService.getTrainingStatistics(userId, page);

		return new BaseRsp<>(rsp);
	}

	@ApiOperation("选手总数")
	@GetMapping("/training_statistics3")
	public BaseRsp<TrainingStatisticsRsp>
	getTrainingStatistics3(@ApiParam @Validated PageReqBase req) {

		TrainingStatisticsPage page = new TrainingStatisticsPage(req);

		String userId = UserInfoContext.getUserContext().getUserId();
		TrainingStatisticsRsp rsp = crBaseService.getTrainingStatistics(userId, page);

		return new BaseRsp<>(rsp);
	}

	@ApiOperation("战绩总数")
	@GetMapping("/training_statistics4")
	public BaseRsp<TrainingStatisticsRsp>
	getTrainingStatistics4(@ApiParam @Validated PageReqBase req) {

		TrainingStatisticsPage page = new TrainingStatisticsPage(req);

		String userId = UserInfoContext.getUserContext().getUserId();
		TrainingStatisticsRsp rsp = crBaseService.getTrainingStatistics(userId, page);

		return new BaseRsp<>(rsp);
	}

	@ApiOperation("获取常量配置")
	@GetMapping("/constants_config")
	public BaseRsp getConstantsConfig(@ApiParam(name = "type", value = "类型") Integer type) {
		List<CrConstantsConfig> constants = crConstantsService.getByType(type);
		// 任务类型
		if (type.equals(CrConstantsConfig.ConstantsType.MISSION_CATEGORY.getCode())) {
			List<MissionCategoryRsp> rsp = StreamApiUtils.map(constants,
					constant -> new MissionCategoryRsp(constant.getResourceId(),
							constant.selectI18nName()));
			return new BaseRsp(rsp);
		} else {
			List<CodeNameRsp> rsp = StreamApiUtils.map(constants,
					constant -> new CodeNameRsp(constant.getCode(), constant.selectI18nName()));
			return new BaseRsp(rsp);
		}
	}

	@ApiOperation("获取当前服务器时间")
	@GetMapping("/current_time")
	@RequiresPermissions(
			value = { MenuConstant.CMS_CLASS_ROOM, MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE,
					MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN },
			logical = Logical.OR)
	public BaseRsp<CurrentTimeRsp> getCurrentTime() {
		CurrentTimeRsp rsp = new CurrentTimeRsp();
		rsp.setCurrentTime(new Date());
		return new BaseRsp<>(rsp);
	}

	@ApiOperation("获取当前用户拥有权限的训练类型")
	@GetMapping("/authorized_type")
	public BaseRsp<List<CodeNameRsp>> getAuthorizedType() {
		Set<String> permissions = UserInfoContext.getUserContext().getPermissions();
		List<CodeNameRsp> rsp = Lists.newArrayList();
		if (permissions.contains(MenuConstant.CMS_CR_SINGLE_TRAIN)) {
			rsp.add(new CodeNameRsp(BusinessTemplate.BusinessType.TRAINING_SINGLE.getCode(),
					LocalScene.BusinessCode.SINGLE_TRAINING.getDesc()));
		}
		if (permissions.contains(MenuConstant.CMS_CR_TEAM_TRAIN)) {
			rsp.add(new CodeNameRsp(BusinessTemplate.BusinessType.TRAINING_TEAM.getCode(),
					LocalScene.BusinessCode.TEAM_TRAINING.getDesc()));
		}
		if (permissions.contains(MenuConstant.CMS_RED_VS_BLUE)) {
			rsp.add(new CodeNameRsp(BusinessTemplate.BusinessType.RED_VS_BLUE.getCode(),
					LocalScene.BusinessCode.RED_VS_BLUE.getDesc()));
		}
		return new BaseRsp<>(rsp);
	}

	@ApiOperation("获取训练用户数据结构数据")
	@GetMapping("/train_user/tree")
	public BaseRsp<List<TrainUserTreeRsp>> getTrainUser() {
		return new BaseRsp<>(trainUserService.getOrgUsers());
	}

	@ApiOperation("练习统计-累计练习次数")
	@GetMapping("/train_stats/acc")
	public BaseRsp<TrainingStatisticsAccount> getTrainStatsAcc() {
		String userId = UserInfoContext.getUserContext().getUserId();
		return new BaseRsp<>(crBaseService.getTrainingStatisticsAccount(userId));
	}

	@ApiOperation("练习统计-训练资产")
	@GetMapping("/train_stats/asset")
	public BaseRsp<List<BarChartUnit>> getTrainStatsAsset() {
		String userId = UserInfoContext.getUserContext().getUserId();
		return new BaseRsp<>(crBaseService.getTrainingAssetAccount(userId));
	}

	@ApiOperation("练习统计-人员参与次数")
	@GetMapping("/train_stats/part_in")
	public BaseRsp<List<BarChartUnit>> getTrainStatsPartIn() {
		String userId = UserInfoContext.getUserContext().getUserId();
		return new BaseRsp<>(crBaseService.getTrainingPeopleStatistics(userId));
	}

	@ApiOperation("练习统计-战绩统计")
	@GetMapping("/train_stats/standings")
	public BaseRsp<List<BarChartUnit>> getTrainStatsStandings() {
		String userId = UserInfoContext.getUserContext().getUserId();
		return new BaseRsp<>(crBaseService.getTrainingScore(userId));
	}

	@ApiOperation("练习统计-练习概览")
	@GetMapping("/train_stats/overview")
	public BaseRsp<ListRsp<TrainStatsOverviewRsp>> getTrainStatsOverview(PageReqBase pageReqBase) {
		String userId = UserInfoContext.getUserContext().getUserId();
		return new BaseRsp<>(crBaseService.getTrainingOverview(userId, pageReqBase.toPageParam()));
	}

	@ApiOperation("练习统计-攻击方式评分")
	@GetMapping("/train_stats/attack_type_score")
	public BaseRsp<List<BarChartUnit>> getScoreByAttackType(@ApiParam("场景id") @RequestParam("room_id") String roomId,
															   @ApiParam("训练类型") @RequestParam("business_type") Integer businessType) {
		HashMap<String, Integer> attackTypeScoreMap = crBaseService.getScoreByAttackType(businessType, roomId);
		List<BarChartUnit> attackTypeScoreList = new ArrayList<>();
		for (String attackType: attackTypeScoreMap.keySet()) {
			attackTypeScoreList.add(new BarChartUnit(attackType, attackTypeScoreMap.get(attackType)));
		}
		return new BaseRsp<>(attackTypeScoreList);
	}

}
