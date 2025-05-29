package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.course.controller.res.HistogramRes;
import com.cyberpeace.oj.course.service.bo.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 学习统计Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LearnStatsService` 的接口，该接口主要用于处理与学习统计相关的服务。以下是对其主要功能和方法的简要分析： 
 * ### 接口概述 
 * - **接口名称**: `LearnStatsService` 
 * - **功能**: 处理用户和组织的学习统计信息。 
 * - **作者**: SN 
 * - **版本**: 1.0.0 
 * - **日期**: 2024年11月8日 
 * ### 主要方法 
 * 1. **getUserStats** 
 * - **功能**: 获取用户学习统计信息。 
 * - **参数**: 
 * - `page`: 分页信息。 
 * - `pattern`: 用户姓名模糊查询条件。 
 * - `organization`: 所属组织查询条件。 
 * - `isPage`: 是否进行分页查询。 
 * - `startTime`: 起始时间。 
 * - `endTime`: 截止时间。 
 * - **返回值**: `ListRsp<UserStatsBo>`，包含用户学习统计信息。 
 * 2. **getUserLearnRank** 
 * - **功能**: 获取用户学习排行信息。 
 * - **返回值**: `UserStatsRankBo`，包含用户学习排行信息。 
 * 3. **getPersonalStatsDetail4Count** 
 * - **功能**: 获取用户课程学习数量统计信息。 
 * - **参数**: `resourceId`，用户资源ID。 
 * - **返回值**: `PersonalStatsDetail4CountBo`，包含用户课程学习数量统计信息。 
 * 4. **getPersonalStatsDetail4Course** 
 * - **功能**: 获取用户课程学习统计信息。 
 * - **参数**: 
 * - `page`: 分页信息。 
 * - `resourceId`: 用户资源ID。 
 * - **返回值**: `ListRsp<PersonalStatsDetail4CourseBo>`，包含用户课程学习统计信息。 
 * 5. **getClassIntegralTopTen** 
 * - **功能**: 获取班级积分排名前十的信息。 
 * - **参数**: 
 * - `ranking_type`: 排名类型。 
 * - `orgId`: 组织ID。 
 * - **返回值**: `HistogramRes`，包含班级积分排名前十的信息。 
 * 6. **getOrgLearnRank** 
 * - **功能**: 获取组织学习统计排行信息。 
 * - **参数**: `request`，HTTP请求对象。 
 * - **返回值**: `UserStatsRankBo`，包含组织学习统计排行信息。 
 * 7. **getOrgStatsData** 
 * - **功能**: 获取组织学习统计数据。 
 * - **参数**: 
 * - `page`: 分页信息。 
 * - `pattern`: 用户姓名模糊查询条件。 
 * - `organization`: 所属组织查询条件。 
 * - `startTime`: 起始时间。 
 * - `endTime`: 截止时间。 
 * - `isPage`: 是否进行分页查询。 
 * - **返回值**: `ListRsp<OrgStatsBo>`，包含组织学习统计数据。 
 * ### 总结 
 * 该接口提供了丰富的学习统计功能，包括用户学习统计、学习排行、课程学习统计等。通过这些方法，可以方便地获取不同维度的学习统计数据，从而帮助管理员或教师更好地了解用户和组织的学习情况。 
**/
public interface LearnStatsService {

    /**
     * 用户学习统计
     *
     * @param page         分页信息
     * @param pattern      用户姓名模糊查询
     * @param organization 所属组织查询
     * @param isPage       是否分页 区分导出和分页查询
     * @param startTime
     * @param endTime
     * @return 用户学习统计
     * @throws
     * @see Page
     * @see String
     * @see String
     * @see boolean
     * @see String
     * @see String
     * @see ListRsp<UserStatsBo>
     */
    ListRsp<UserStatsBo> getUserStats(Page page, String pattern, String organization, boolean isPage, String startTime, String endTime);

    /**
     * 用户学习排行
     *
     * @return 统计信息
     * @throws
     * @see UserStatsRankBo
     */
    UserStatsRankBo getUserLearnRank();

    /**
     * 获取用户课程学习数量统计
     *
     * @param resourceId 用户resource_id
     * @return 统计信息
     * @throws
     * @see String
     * @see PersonalStatsDetail4CountBo
     */
    PersonalStatsDetail4CountBo getPersonalStatsDetail4Count(String resourceId);

    /**
     * 获取用户课程学习统计
     *
     * @param page       分页
     * @param resourceId 用户resource_id
     * @return 统计信息
     * @throws
     * @see Page
     * @see String
     * @see ListRsp<PersonalStatsDetail4CourseBo>
     */
    ListRsp<PersonalStatsDetail4CourseBo> getPersonalStatsDetail4Course(Page page, String resourceId);

    /**
     * 获取班级积分排名Top10
     *
     * @param ranking_type 排名类型
     * @param orgId        组织id
     * @return 统计信息
     * @throws
     * @see String
     * @see String
     * @see HistogramRes
     */
    HistogramRes getClassIntegralTopTen(String ranking_type, String orgId);

    /**
     * 班级学习统计排行
     *
     * @param request 请求
     * @return 统计信息
     * @throws
     * @see HttpServletRequest
     * @see UserStatsRankBo
     */
    UserStatsRankBo getOrgLearnRank(HttpServletRequest request);

    /**
     * 用户学习统计
     *
     * @param page         分页信息
     * @param pattern      用户姓名模糊查询
     * @param organization 所属组织查询
     * @param startTime    起始时间
     * @param endTime      截止时间
     * @param isPage       是分页
     * @return 用户学习统计
     * @throws
     * @see Page
     * @see String
     * @see String
     * @see String
     * @see String
     * @see boolean
     * @see ListRsp<OrgStatsBo>
     */
    ListRsp<OrgStatsBo> getOrgStatsData(Page page, String pattern, String organization, String startTime, String endTime, boolean isPage);
}

