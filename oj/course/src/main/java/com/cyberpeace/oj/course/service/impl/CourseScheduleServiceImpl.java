package com.cyberpeace.oj.course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.course.dao.entity.CourseSchedule;
import com.cyberpeace.oj.course.dao.mapper.CourseScheduleMapper;
import com.cyberpeace.oj.course.service.CourseScheduleService;
import com.cyberpeace.oj.course.service.bo.TimeScheduleBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CourseScheduleServiceImpl extends ServiceImpl<CourseScheduleMapper, CourseSchedule> implements CourseScheduleService {

    @Autowired
    private CourseScheduleMapper courseScheduleMapper;

    @Override
    public List<TimeScheduleBo> listByTime(Date startOfDay, Date endOfDay) {

        List<CourseSchedule> courseSchedules = courseScheduleMapper.listByTime(startOfDay, endOfDay);
        return null;
    }
}
