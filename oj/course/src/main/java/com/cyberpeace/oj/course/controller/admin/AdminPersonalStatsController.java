/**
 * 这段代码定义了一个名为 `AdminPersonalStatsController` 的 Spring Boot 控制器类，用于处理与学习统计相关的管理操作。以下是对该代码的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.oj.course.controller.admin` 
 * - **类名**: `AdminPersonalStatsController` 
 * - **功能**: 提供后台管理接口，用于获取和导出个人及组织的学习统计信息。 
 * ### 主要组件 
 * 1. **依赖注入** 
 * - `LearnStatsService`: 通过 `@Autowired` 注入，用于处理具体的统计逻辑。 
 * 2. **API 文档注解** 
 * - 使用了 Swagger 注解 (`@Api`, `@ApiOperation`) 来生成 API 文档。 
 * 3. **权限控制** 
 * - 使用了 Apache Shiro 的注解 (`@RequiresAuthentication`, `@RequiresPermissions`) 来控制访问权限。 
 * ### 方法列表 
 * 1. **个人统计数据** 
 * - `personalStats`: 根据请求参数获取个人统计数据。 
 * - `personalStatsRank`: 获取个人学习排名。 
 * - `personalStatsDetail4Count`: 获取个人课程学习数量统计。 
 * - `personalStatsDetail4Course`: 获取个人课程学习统计详情。 
 * 2. **组织统计数据** 
 * - `classIntegralTopTen`: 获取组织积分排名前十。 
 * - `personalIntegralTopTen`: 获取个人积分排名前十。 
 * - `orgStatsRank`: 获取组织学习排名。 
 * - `orgStatsData`: 获取组织统计数据。 
 * - `orgStatsDataExport`: 导出组织统计数据到 Excel 文件。 
 * 3. **用户统计数据导出** 
 * - `userStatsDataExport`: 导出用户统计数据到 Excel 文件。 
 * ### 主要特点 
 * - **分页支持**: 多个方法使用了分页参数 (`PageReqBase`) 进行数据分页处理。 
 * - **Excel 导出**: 提供了将统计数据导出为 Excel 文件的功能。 
 * - **权限控制**: 所有方法都要求用户具有特定权限（如 `MenuConstant.CMS_COURSE_STATISTICS`）才能访问。 
 * ### 总结 
 * 这个控制器类提供了一系列关于学习统计的管理接口，包括个人和组织的数据查询和导出功能。通过依赖注入、权限控制和 Swagger 文档注解，确保了接口的安全性和易用性。 
**/
package com.cyberpeace.oj.course.controller.admin;

import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ExcelHeader;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.constant.MenuConstant;
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
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * 管理个人统计Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
@Api(tags = "后台-学习统计-个人统计")
@RestController
@RequiresAuthentication
@RequestMapping("/oj/admin/course/personal-stats")
@Slf4j
public class AdminPersonalStatsController {

	/**
	 * 学习统计Service
	 *
	 * @see LearnStatsService
	 */
	@Autowired
	LearnStatsService learnStatsService;

	/**
	 * 个人统计数据
	 *
	 * @param userStatsReq 用户统计请求体
	 * @return {@code BaseRsp<ListRsp<UserStatsBo>> }
	 * @throws
	 * @see UserStatsReq
	 * @see BaseRsp<ListRsp<UserStatsBo>>
	 */
	@ApiOperation("个人统计")
	@GetMapping("/person-stats")
	@RequiresPermissions(MenuConstant.CMS_COURSE_STATISTICS)
	public BaseRsp<ListRsp<UserStatsBo>> personalStats(UserStatsReq userStatsReq) {
		return new BaseRsp<>(learnStatsService.getUserStats(userStatsReq.toPageParam(), userStatsReq.getPattern(), userStatsReq.getOrganization(),
				true, userStatsReq.getStartTime(), userStatsReq.getEndTime()));
	}

	/**
	 * 个人统计排名
	 *
	 * @return {@code BaseRsp<UserStatsRankBo> }
	 * @throws
	 * @see BaseRsp<UserStatsRankBo>
	 */
	@ApiOperation("个人统计排名")
	@GetMapping("/person-stats-rank")
	@RequiresPermissions(MenuConstant.CMS_COURSE_STATISTICS)
	public BaseRsp<UserStatsRankBo> personalStatsRank() {
		return new BaseRsp<>(learnStatsService.getUserLearnRank());
	}

	/**
	 * 个人统计详情4计数
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<PersonalStatsDetail4CountBo> }
	 * @throws
	 * @see String
	 * @see BaseRsp<PersonalStatsDetail4CountBo>
	 */
	@ApiOperation("个人课程学习数量统计")
	@GetMapping("/person-stats-detail/count")
	@RequiresPermissions(MenuConstant.CMS_COURSE_STATISTICS)
	public BaseRsp<PersonalStatsDetail4CountBo>
			personalStatsDetail4Count(@RequestParam("user_id") String resourceId) {
		return new BaseRsp<>(learnStatsService.getPersonalStatsDetail4Count(resourceId));
	}

	/**
	 * 个人统计细节4 课程
	 *
	 * @param pageReq 分页请求体
	 * @param userId  用户id
	 * @return {@code BaseRsp<ListRsp<PersonalStatsDetail4CourseBo>> }
	 * @throws
	 * @see PageReqBase
	 * @see String
	 * @see BaseRsp<ListRsp<PersonalStatsDetail4CourseBo>>
	 */
	@ApiOperation("个人课程学习统计")
	@GetMapping("/person-stats-detail/course")
	@RequiresPermissions(value = { MenuConstant.CMS_COURSE_STATISTICS, MenuConstant.WEB_PERSON_CENTER}, logical = Logical.OR)
	public BaseRsp<ListRsp<PersonalStatsDetail4CourseBo>> personalStatsDetail4Course(
			PageReqBase pageReq,
			@ApiParam("用户resource_id") @RequestParam(value = "user_id") String userId) {
		return new BaseRsp<>(
				learnStatsService.getPersonalStatsDetail4Course(pageReq.toPageParam(), userId));
	}

	/**
	 * 类积分前十名
	 *
	 * @return {@code BaseRsp<HistogramRes> }
	 * @throws
	 * @see BaseRsp<HistogramRes>
	 */
	@ApiOperation("积分排名-组织积分排名Top10")
	@GetMapping("/class_integral")
	@RequiresPermissions(MenuConstant.CMS_COURSE_STATISTICS)
	public BaseRsp<HistogramRes> classIntegralTopTen() {
		return new BaseRsp<>(learnStatsService.getClassIntegralTopTen("org", ""));
	}

	/**
	 * 个人积分前十名
	 *
	 * @return {@code BaseRsp<HistogramRes> }
	 * @throws
	 * @see BaseRsp<HistogramRes>
	 */
	@ApiOperation("积分排名-个人积分排名Top10")
	@GetMapping("/personal-integral")
	@RequiresPermissions(MenuConstant.CMS_COURSE_STATISTICS)
	public BaseRsp<HistogramRes> personalIntegralTopTen() {
		return new BaseRsp<>(learnStatsService.getClassIntegralTopTen("person", ""));
	}

	/**
	 * 组织统计排名
	 *
	 * @param request 请求
	 * @return {@code BaseRsp<UserStatsRankBo> }
	 * @throws
	 * @see HttpServletRequest
	 * @see BaseRsp<UserStatsRankBo>
	 */
	@ApiOperation("学习统计排名-组织数据")
	@GetMapping("/org-stats-rank")
	@RequiresPermissions(MenuConstant.CMS_COURSE_STATISTICS)
	public BaseRsp<UserStatsRankBo> orgStatsRank(HttpServletRequest request) {
		return new BaseRsp<>(learnStatsService.getOrgLearnRank(request));
	}

	/**
	 * 组织统计数据
	 *
	 * @param userStatsReq 用户统计请求体
	 * @return {@code BaseRsp<ListRsp<OrgStatsBo>> }
	 * @throws
	 * @see UserStatsReq
	 * @see BaseRsp<ListRsp<OrgStatsBo>>
	 */
	@ApiOperation("学习数据-组织数据")
	@GetMapping("/org-stats-data")
	@RequiresPermissions(MenuConstant.CMS_COURSE_STATISTICS)
	public BaseRsp<ListRsp<OrgStatsBo>> orgStatsData(UserStatsReq userStatsReq) {
        return new BaseRsp<>(learnStatsService.getOrgStatsData(userStatsReq.toPageParam(), userStatsReq.getPattern(), userStatsReq.getOrganization(),
                userStatsReq.getStartTime(), userStatsReq.getEndTime(), true));
	}

	/**
	 * 用户统计数据导出
	 *
	 * @param response     响应
	 * @param userStatsReq 用户统计请求体
	 * @throws
	 * @see HttpServletResponse
	 * @see UserStatsReq
	 */
	@ApiOperation("学习数据-全部数据-导出")
	@GetMapping(value = "/user-stats-data-export")
	@RequiresPermissions(MenuConstant.CMS_COURSE_STATISTICS)
	public void userStatsDataExport(HttpServletResponse response, UserStatsReq userStatsReq) {
		ListRsp<UserStatsBo> userStats = learnStatsService.getUserStats(userStatsReq.toPageParam(), null, null,
				false, userStatsReq.getStartTime(), userStatsReq.getEndTime());

		List<String> passFields = Lists.newArrayList("roles", "reportAverage", "resourceId",
				"username", "avatar");
		String sheetName = ExcelHeader.SHEETNAME.getHeader()[0];
		String filePath = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID()
				+ CommonConstant.PERIOD + FileSunffixConstant.XLS.getDesc();

		File file = ExcelDataHandler.exportExcel(userStats.getList(), filePath, sheetName,
				passFields, ExcelHeader.STUDY_STATISTIC);
		FileUtil.setResponse(file, true, response);
	}

	/**
	 * 组织统计数据导出
	 *
	 * @param response     响应
	 * @param userStatsReq 用户统计请求体
	 * @throws
	 * @see HttpServletResponse
	 * @see UserStatsReq
	 */
	@ApiOperation("学习数据-组织数据-导出")
    @GetMapping(value = "/org-stats-data-export")
    public void orgStatsDataExport(HttpServletResponse response, UserStatsReq userStatsReq) {
        ListRsp<OrgStatsBo> orgStatsData = learnStatsService.getOrgStatsData(userStatsReq.toPageParam(), null, null,
                userStatsReq.getStartTime(), userStatsReq.getEndTime(), false);
        List<String> passFields = Lists.newArrayList( "resourceId", "reportAverage");
        String sheetName = ExcelHeader.SHEETNAME.getHeader()[0];
        String filePath = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID()
                + CommonConstant.PERIOD + FileSunffixConstant.XLS.getDesc();

        File file = ExcelDataHandler.exportExcel(orgStatsData.getList(), filePath, sheetName,
                passFields, ExcelHeader.ORG_STUDY_STATISTIC);
        FileUtil.setResponse(file, true, response);
    }


}
