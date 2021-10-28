package com.cyberpeace.oj.course.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.course.dao.context.CourseContext;
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
     * @param page
     * @param courseContext
     * @return
     */
    ListRsp<CourseInfoBo> selectPage(Page page, CourseContext courseContext, AuthContext authContext);

    /**
     * 筛选未被删除的课程编号
     * @param courseIds
     * @return
     */
    List<String> idsByNotDeleteIds(List<String> courseIds);

    /**
     * 新增或修改课程
     * @param course
     * @param userId
     * @return
     */
    Course addOrUpdate(String userId, Course course);

    /**
     * 根据分类查询课程编号
     * @param category
     * @param authContext
     * @return
     */
    List<String> selectIdsByCategory(String category, AuthContext authContext);
}
