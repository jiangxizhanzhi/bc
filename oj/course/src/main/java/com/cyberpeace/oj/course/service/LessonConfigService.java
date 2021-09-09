package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import com.cyberpeace.oj.course.service.bo.LessonConfigBo;

public interface LessonConfigService extends IService<LessonConfig> {

    /**
     * 根据课程编号用户编号查询课程目录
     * @param courseId 课程编号
     * @param userId 用户编号
     * @param ispublic
     * @return
     */
    LessonConfigBo getLessonConfig(String courseId, String userId, Boolean ispublic);


    /**
     *
     * @param configBo
     * @return
     */
    LessonConfigBo updateLessonConfig(LessonConfigBo configBo);
}
