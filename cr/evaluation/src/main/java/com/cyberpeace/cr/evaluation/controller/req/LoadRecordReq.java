package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
public class LoadRecordReq implements Serializable {

	@ApiModelProperty("评估记录")
	@NotNull
	private MultipartFile file;

}
