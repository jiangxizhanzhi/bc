package com.cyberpeace.oj.course.service;

import com.cyberpeace.oj.course.controller.req.TrainMangeAddReq;
import com.cyberpeace.oj.course.controller.req.TrainMangeCourseUpdateReq;
import com.cyberpeace.oj.course.controller.req.TrainMangeUpdateReq;
import com.cyberpeace.oj.course.service.bo.TrainCourseBo;
import com.cyberpeace.oj.course.service.bo.TrainManageTreeBo;

import java.util.List;

public interface TrainManageService {

    List<TrainManageTreeBo> trainMangeList();

    List<TrainManageTreeBo> webTrainMangeList();

    Boolean add(TrainMangeAddReq req);

    Boolean update(TrainMangeUpdateReq req);

    Boolean updateCourse(TrainMangeCourseUpdateReq req);

    Boolean copy(String resourceId);

    Boolean delete(String resourceId);

    List<TrainCourseBo> getDetail(String resourceId);

    List<TrainCourseBo> getWebDetail(String resourceId);

}
