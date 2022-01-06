package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.course.service.bo.PersonalStatsDetail4CountBo;
import com.cyberpeace.oj.course.service.bo.PersonalStatsDetail4CourseBo;
import com.cyberpeace.oj.course.service.bo.UserStatsBo;
import com.cyberpeace.oj.course.service.bo.UserStatsRankBo;

public interface LearnStatsService {

    /**
     * 用户学习统计
     * @param page 分页信息
     * @param organization 所属组织查询
     * @param pattern 用户姓名模糊查询
     * @return 用户学习统计
     */
    ListRsp<UserStatsBo> getUserStats(Page page, String pattern, String organization);

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

}
