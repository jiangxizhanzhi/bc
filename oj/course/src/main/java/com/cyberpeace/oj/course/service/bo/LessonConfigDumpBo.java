package com.cyberpeace.oj.course.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LessonConfigDumpBo implements Serializable {

	private Integer index;

	private String name;

	private Integer nodeType;

	private List<LessonConfigDumpBo> children;

}
