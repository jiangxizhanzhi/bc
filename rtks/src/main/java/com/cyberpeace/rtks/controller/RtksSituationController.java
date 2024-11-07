package com.cyberpeace.rtks.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.rtks.service.RtksSituationService;
import com.cyberpeace.rtks.service.bo.situation.LevelStatisticBo;
import com.cyberpeace.rtks.service.bo.situation.NewsBo;
import com.cyberpeace.rtks.service.bo.situation.RtksSituationRankings;
import com.cyberpeace.rtks.service.bo.situation.SituationTrendBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * RTKS 态势控制器
 *
 * @author SN
 * @date 2024/11/07
 */
@Api(tags = "RTKS-人才培养态势")
@RestController
@RequestMapping("/rtks/situation")
@RequiresAuthentication
public class RtksSituationController {

	/**
	 * RTKS情况Service
	 */
	@Autowired
	private RtksSituationService rtksSituationService;

	/**
	 * 获取用户排名
	 *
	 * @return {@code BaseRsp<RtksSituationRankings> }
	 */
	@ApiOperation("网安排名：个人-组织-复合")
	@GetMapping("/ranking")
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<RtksSituationRankings> getUserRanking() {
		return new BaseRsp<>(rtksSituationService.getRankings());
	}

	/**
	 * 获取新闻
	 *
	 * @return {@code BaseRsp<List<NewsBo>> }
	 */
	@ApiOperation("动态")
	@GetMapping("/news")
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<List<NewsBo>> getNews() {
		List<NewsBo> newsBos = rtksSituationService.getNews();
		return new BaseRsp<>(newsBos);
	}

	/**
	 * 获取情况
	 *
	 * @return {@code BaseRsp<List<LevelStatisticBo>> }
	 */
	@ApiOperation("网安人才岗位态势")
	@GetMapping("/situation")
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<List<LevelStatisticBo>> getSituation() {
		List<LevelStatisticBo> result = rtksSituationService.getSituation();
		return new BaseRsp<>(result);
	}

	/**
	 * 获取趋势
	 *
	 * @return {@code BaseRsp<SituationTrendBo> }
	 */
	@ApiOperation("网安人才发展趋势")
	@GetMapping("/trend")
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<SituationTrendBo> getTrend() {
		SituationTrendBo result = rtksSituationService.getTrends();
		return new BaseRsp<>(result);
	}

}
