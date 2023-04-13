package com.cyberpeace.cs.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class FileRemoveMarkingReq implements Serializable {

	@ApiModelProperty("资源关联课时id")
	private String resourceId;

}
