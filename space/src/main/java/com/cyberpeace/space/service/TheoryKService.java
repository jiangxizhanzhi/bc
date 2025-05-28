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
    ListRsp<AdminLessonListBo> pageK(ListKReq req);

    void deleteBatch(List<String> ids);

    AdminLessonBo getDetail(String resourceId);
}
