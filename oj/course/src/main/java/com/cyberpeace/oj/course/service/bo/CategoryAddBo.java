package com.cyberpeace.oj.course.service.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryAddBo implements Serializable {

	private String resourceId;

	private String name;

	private String parentId;

	private Integer courseType = 0;

}
