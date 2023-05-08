package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * 授权
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class Share {

	@ApiModelProperty(value = "授权操作类型")
	private Integer shareMode;

	@ApiModelProperty(value = "授权操作用户列表")
	private List<ShareTeacher> teachers;
}
