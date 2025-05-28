package com.cyberpeace.space.service;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.course.service.bo.AdminLessonBo;
import com.cyberpeace.oj.course.service.bo.AdminLessonListBo;
import com.cyberpeace.space.service.context.ListKReq;

import java.util.List;

/**
 * 理论KSERVICE 服务
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
public interface TheoryKService {
    /**
     * pagek
     *
     * @param req 请求体
     * @return 列表RSP<管理课时列表博>
     * @throws
     * @see ListKReq
     * @see ListRsp<AdminLessonListBo>
     */
    ListRsp<AdminLessonListBo> pageK(ListKReq req);

    /**
     * 删除批量
     *
     * @param ids id列表
     * @throws
     * @see List<String>
     */
    void deleteBatch(List<String> ids);

    /**
     * 获取详情
     *
     * @param resourceId 资源id
     * @return 管理员课程bo
     * @throws
     * @see String
     * @see AdminLessonBo
     */
    AdminLessonBo getDetail(String resourceId);
}
