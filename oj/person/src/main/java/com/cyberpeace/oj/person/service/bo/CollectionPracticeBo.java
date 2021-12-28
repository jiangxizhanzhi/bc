package com.cyberpeace.oj.person.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class CollectionPracticeBo implements Serializable {

	private String resourceId;

	private Integer practiceType;

	private String categoryId;

	private String categoryName;

}
