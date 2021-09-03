package com.cyberpeace.oj.course.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.course.controller.req.CourseListReq;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;

public interface CourseService extends IService<Course> {

    /**
     * 根据编号查询课程信息
     * @param courseId
     * @param userId
     * @return
     */
    CourseInfoBo selectById(String courseId, String userId);


    /**
     * 根据课程编号集合查询课程信息
     * @param ids
     * @return
     */
    List<CourseInfoBo> selectByIds(List<String> ids);

    /**
     * 根据课程条件信息查询
     * @param listReq
     * @return
     */
    ListRsp<CourseInfoBo> selectPage(CourseListReq listReq);

    /**
     * 筛选未被删除的课程编号
     * @param courseIds
     * @return
     */
    List<String> idsByNotDeleteIds(List<String> courseIds);

    /**
     * 课程收藏
     * @param userId
     * @param course
     * @param isCollection
     * @return
     */
    String collectCourse(String userId, String course, Boolean isCollection);
}
