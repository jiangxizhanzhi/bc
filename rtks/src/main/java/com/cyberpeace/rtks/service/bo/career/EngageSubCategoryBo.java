package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EngageSubCategoryBo<T extends FrameItemBase> implements Serializable {

	@ApiModelProperty("")
	private String subCategoryName;

	@ApiModelProperty("")
	private List<T> engages;
}
