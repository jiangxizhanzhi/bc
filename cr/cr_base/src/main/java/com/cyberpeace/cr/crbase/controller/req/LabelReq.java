package com.cyberpeace.cr.crbase.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LabelReq {

 	@NotNull
	private List<String> labels;
 

}
