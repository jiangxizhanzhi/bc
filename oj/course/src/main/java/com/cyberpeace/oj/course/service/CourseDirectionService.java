package com.cyberpeace.oj.course.service;

import java.util.List;

public interface CourseDirectionService {

    /**
     * 根据当前课程方向 获取所有子节点的方向编号
     * @param directionId
     * @return
     */
    List<String> getAllChildrenIds(String directionId);

}
