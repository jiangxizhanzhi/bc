package com.cyberpeace.oj.course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @PostConstruct
    public void init() {
        String a = "";
        System.out.println(a);
    }
}
