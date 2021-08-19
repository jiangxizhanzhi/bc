package com.cyberpeace.oj.course.service;

import java.util.List;

import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;

public interface CourseService {

    /**
     * 根据编号查询课程信息
     * @param courseId
     * @return
     */
    CourseInfoBo selectById(String courseId);


    /**
     * 根据课程编号集合查询课程信息
     * @param ids
     * @return
     */
    List<CourseInfoBo> selectByIds(List<String> ids);

}
