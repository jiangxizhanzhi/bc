package com.cyberpeace.oj.rtks.dao.entity;

import com.cyberpeace.oj.course.service.bo.CourseInfoBo;
import lombok.Data;

@Data
public class RelatedCourse extends CourseInfoBo {

	private Integer weight;

}