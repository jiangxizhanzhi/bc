package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 共享上下文
 * 授权模型
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class ShareContext {

	@ApiModelProperty(value = "模板id")
	private List<String> ids;

	@ApiModelProperty(value = "授权类别")
	private Integer shareMode;

	@ApiModelProperty(value = "授权人员id")
	private List<String> shareTeachers;

}
