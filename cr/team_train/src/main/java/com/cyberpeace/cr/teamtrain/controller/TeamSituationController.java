package com.cyberpeace.cr.teamtrain.controller;

import java.util.List;

import com.cyberpeace.common.model.EntranceBo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.teamtrain.service.TeamSituationService;
import com.cyberpeace.cr.teamtrain.service.bo.situation.StudentScoreVo;
import com.cyberpeace.cr.teamtrain.service.bo.situation.TeamSituationInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "团队训练行业态势")
@RestController
@RequestMapping("/cr/training_team/situation")
@Slf4j
public class TeamSituationController {

	@Autowired
	private TeamSituationService teamSituationService;

	@ApiOperation("是否显示行业态势入口")
	@GetMapping("/entrance")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<EntranceBo>
			ifShowEntrance(@ApiParam(name = "room_id", value = "团队训练id") String room_id) {
		CrCommonValidator.validateId(room_id);
		EntranceBo res = teamSituationService.ifShowSituation(room_id);
		return new BaseRsp<>(res);
	}

	@ApiOperation("获取态势信息")
	@GetMapping("/info")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<TeamSituationInfo>
			getTaskStatistics(@ApiParam(name = "room_id", value = "团队训练id") String room_id) {
		CrCommonValidator.validateId(room_id);
		TeamSituationInfo info = teamSituationService.getSituationInfo(room_id);
		return new BaseRsp<>(info);
	}

	@ApiOperation("Top5训练得分")
	@GetMapping("/score")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<List<StudentScoreVo>>
			getScore(@ApiParam(name = "room_id", value = "团队训练id") String room_id) {
		CrCommonValidator.validateId(room_id);
		List<StudentScoreVo> res = teamSituationService.getTop5Score(room_id);
		return new BaseRsp<>(res);
	}

}
