package com.cyberpeace.oj.course.controller.admin;

import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ExcelHeader;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.oj.course.controller.req.UserStatsReq;
import com.cyberpeace.oj.course.controller.res.HistogramRes;
import com.cyberpeace.oj.course.service.LearnStatsService;
import com.cyberpeace.oj.course.service.bo.*;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "后台-学习统计-个人统计")
@RestController
@RequiresAuthentication
@RequestMapping("/oj/admin/course/personal-stats")
@Slf4j
public class AdminPersonalStatsController {
	// todo ------- 数据准确性待验证 -------

	@Autowired
	LearnStatsService learnStatsService;

	@ApiOperation("个人统计")
	@GetMapping("/person-stats")
	public BaseRsp<ListRsp<UserStatsBo>> personalStats(UserStatsReq userStatsReq) {
		return new BaseRsp<>(learnStatsService.getUserStats(userStatsReq.toPageParam(),
				userStatsReq.getPattern(), userStatsReq.getOrganization(),
				userStatsReq.getDate_type(), true));
	}

	@ApiOperation("个人统计排名")
	@GetMapping("/person-stats-rank")
	public BaseRsp<UserStatsRankBo> personalStatsRank() {
		return new BaseRsp<>(learnStatsService.getUserLearnRank());
	}

	@ApiOperation("个人课程学习数量统计")
	@GetMapping("/person-stats-detail/count")
	public BaseRsp<PersonalStatsDetail4CountBo>
			personalStatsDetail4Count(@RequestParam("user_id") String resourceId) {
		return new BaseRsp<>(learnStatsService.getPersonalStatsDetail4Count(resourceId));
	}

	@ApiOperation("个人课程学习统计")
	@GetMapping("/person-stats-detail/course")
	public BaseRsp<ListRsp<PersonalStatsDetail4CourseBo>> personalStatsDetail4Course(
			PageReqBase pageReq,
			@ApiParam("用户resource_id") @RequestParam(value = "user_id") String userId) {
		return new BaseRsp<>(
				learnStatsService.getPersonalStatsDetail4Course(pageReq.toPageParam(), userId));
	}

	@ApiOperation("积分排名-组织积分排名Top5")
	@GetMapping("/class_integral")
	public BaseRsp<HistogramRes> classIntegralTopTen() {
		return new BaseRsp<>(learnStatsService.getClassIntegralTopTen("org", ""));
	}

	@ApiOperation("积分排名-个人积分排名Top5")
	@GetMapping("/personal-integral")
	public BaseRsp<HistogramRes> personalIntegralTopTen() {
		return new BaseRsp<>(learnStatsService.getClassIntegralTopTen("person", ""));
	}

	@ApiOperation("学习统计排名-组织数据")
	@GetMapping("/org-stats-rank")
	public BaseRsp<UserStatsRankBo> orgStatsRank() {
		return new BaseRsp<>(learnStatsService.getOrgLearnRank());
	}

	@ApiOperation("学习数据-组织数据")
	@GetMapping("/org-stats-data")
	public BaseRsp<ListRsp<OrgStatsBo>> orgStatsData(UserStatsReq userStatsReq) {
		return new BaseRsp<>(learnStatsService.getOrgStatsData(userStatsReq.toPageParam(),
				userStatsReq.getPattern(), userStatsReq.getOrganization(),
				userStatsReq.getDate_type()));
	}

	@ApiOperation("学习数据-全部数据-导出")
	@GetMapping(value = "/user-stats-data-export")
	public void orgStatsDataExport(HttpServletResponse response, UserStatsReq userStatsReq) {
		ListRsp<UserStatsBo> userStats = learnStatsService.getUserStats(userStatsReq.toPageParam(),
				userStatsReq.getPattern(), userStatsReq.getOrganization(),
				userStatsReq.getDate_type(), false);

		List<String> passFields = Lists.newArrayList("roles", "reportAverage", "resourceId",
				"username");
		String sheetName = "学习数据";
		String filePath = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID()
				+ CommonConstant.PERIOD + FileSunffixConstant.XLS.getDesc();

		File file = ExcelDataHandler.exportExcel(userStats.getList(), filePath, sheetName,
				passFields, ExcelHeader.STUDY_STATISTIC);
		FileUtil.setResponse(file, true, response);
	}

}
