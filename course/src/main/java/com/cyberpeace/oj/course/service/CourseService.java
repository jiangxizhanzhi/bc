package com.cyberpeace.oj.course.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.course.dao.entity.Course;

public interface CourseService extends IService<Course> {

	List<Course> self(String haha);
}
