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
 * @date 2024/11/08
 */
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
     */
    ListRsp<UserStatsBo> getUserStats(Page page, String pattern, String organization, boolean isPage, String startTime, String endTime);

    /**
     * 用户学习排行
     *
     * @return 统计信息
     */
    UserStatsRankBo getUserLearnRank();

    /**
     * 获取用户课程学习数量统计
     *
     * @param resourceId 用户resource_id
     * @return 统计信息
     */
    PersonalStatsDetail4CountBo getPersonalStatsDetail4Count(String resourceId);

    /**
     * 获取用户课程学习统计
     *
     * @param page       分页
     * @param resourceId 用户resource_id
     * @return 统计信息
     */
    ListRsp<PersonalStatsDetail4CourseBo> getPersonalStatsDetail4Course(Page page, String resourceId);

    /**
     * 获取班级积分排名Top10
     *
     * @param ranking_type 排名类型
     * @param orgId        组织id
     * @return 统计信息
     */
    HistogramRes getClassIntegralTopTen(String ranking_type, String orgId);

    /**
     * 班级学习统计排行
     *
     * @param request 请求
     * @return 统计信息
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
     */
    ListRsp<OrgStatsBo> getOrgStatsData(Page page, String pattern, String organization, String startTime, String endTime, boolean isPage);
}
