package com.cyberpeace.oj.course.service;

import com.cyberpeace.oj.course.controller.req.TrainCourseReq;
import com.cyberpeace.oj.course.controller.req.TrainMangeReq;
import com.cyberpeace.oj.course.controller.req.TrainMangeSortReq;
import com.cyberpeace.oj.course.service.bo.TrainCourseBo;
import com.cyberpeace.oj.course.service.bo.TrainManageTreeBo;

import java.util.List;

public interface TrainManageService {

    TrainManageTreeBo trainMangeList();

    List<TrainManageTreeBo> webTrainMangeList();

    Boolean addLevel(TrainMangeReq req);

    Boolean updateLevel(TrainMangeReq req);

    Boolean move(TrainMangeSortReq req);

    Boolean deleteLevel(String resourceId);

    Boolean copy(String resourceId);

    List<TrainCourseBo> getDetail(String resourceId);

    Boolean updateCourse(TrainCourseReq req);

    Boolean addCourse(TrainCourseReq req);

    Boolean deleteCourse(TrainCourseReq resourceId);

}
