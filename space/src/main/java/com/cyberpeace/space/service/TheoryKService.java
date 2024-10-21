package com.cyberpeace.space.service;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.course.service.bo.AdminLessonBo;
import com.cyberpeace.oj.course.service.bo.AdminLessonListBo;
import com.cyberpeace.space.service.context.ListKReq;

import java.util.List;

public interface TheoryKService {
    ListRsp<AdminLessonListBo> pageK(ListKReq req);

    void deleteBatch(List<String> ids);

    AdminLessonBo getDetail(String resourceId);
}
