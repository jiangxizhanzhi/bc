package com.cyberpeace.cr.crbase.service.context.wiki;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WikiRsp extends WikiListRsp implements Serializable {

	@ApiModelProperty(value = "内容")
	private String content;

}
