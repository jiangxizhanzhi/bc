package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.course.controller.res.HistogramRes;
import com.cyberpeace.oj.course.service.bo.*;

import java.util.List;

public interface LearnStatsService {

    /**
     * 用户学习统计
     * @param page 分页信息
     * @param pattern 用户姓名模糊查询
     * @param organization 所属组织查询
     * @param dateType
     * @param isPage
     * @return 用户学习统计
     */
    ListRsp<UserStatsBo> getUserStats(Page page, String pattern, String organization, String dateType, boolean isPage);

    /**
     * 用户学习排行
     * @return 统计信息
     */
    UserStatsRankBo getUserLearnRank();

    /**
     * 获取用户课程学习数量统计
     * @param resourceId 用户resource_id
     * @return 统计信息
     */
    PersonalStatsDetail4CountBo getPersonalStatsDetail4Count(String resourceId);

    /**
     * 获取用户课程学习统计
     * @param resourceId 用户resource_id
     * @return 统计信息
     */
    ListRsp<PersonalStatsDetail4CourseBo> getPersonalStatsDetail4Course(Page page, String resourceId);

    /**
     * 获取班级积分排名Top10
     * @return 统计信息
     */
    HistogramRes getClassIntegralTopTen(String ranking_type, String orgId);

    /**
     * 班级学习统计排行
     * @return 统计信息
     */
    UserStatsRankBo getOrgLearnRank();

    /**
     * 用户学习统计
     * @param page 分页信息
     * @param organization 所属组织查询
     * @param pattern 用户姓名模糊查询
     * @return 用户学习统计
     */
    ListRsp<OrgStatsBo> getOrgStatsData(Page page, String pattern, String organization, String dateType);
}
