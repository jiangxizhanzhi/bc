package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.service.bo.ClassroomBo;
import com.cyberpeace.oj.course.service.bo.TimeScheduleBo;

import java.util.Date;
import java.util.List;

public interface ClassroomService extends IService<Classroom> {

    /**
     * 根据时间范围获取课堂信息
     * @param startOfDay
     * @param endOfDay
     * @return
     */
    List<TimeScheduleBo> listByTime(Date startOfDay, Date endOfDay);


    List<ClassroomBo> listByTimeAndOrg(Date startOfDay, Date endOfDay, String organization, String createUser);

}
