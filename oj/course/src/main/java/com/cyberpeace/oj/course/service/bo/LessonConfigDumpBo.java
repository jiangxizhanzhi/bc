package com.cyberpeace.oj.course.service.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LessonConfigDumpBo implements Serializable {

	private Integer index;

	private String parentId;

	private String name;

	private Integer nodeType;

}
